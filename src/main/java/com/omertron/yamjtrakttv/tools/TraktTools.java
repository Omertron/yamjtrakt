/*
 *      Copyright (c) 2004-2014 Stuart Boston
 *
 *      This file is part of the YAMJ Trakt Application.
 *
 *      The YAMJ Trakt Application is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      The YAMJ Trakt Application is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with the YAMJ Trakt Application.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.yamjtrakttv.tools;

import com.jakewharton.trakt.ServiceManager;
import com.jakewharton.trakt.TraktException;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.entities.TvEntity;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.entities.TvShowSeason;
import com.jakewharton.trakt.services.MovieService;
import com.jakewharton.trakt.services.MovieService.LibraryBuilder;
import com.jakewharton.trakt.services.MovieService.SeenBuilder;
import com.jakewharton.trakt.services.ShowService;
import com.jakewharton.trakt.services.ShowService.EpisodeLibraryBuilder;
import com.jakewharton.trakt.services.ShowService.EpisodeSeenBuilder;
import com.omertron.yamjtrakttv.model.Credentials;
import com.omertron.yamjtrakttv.model.Episode;
import com.omertron.yamjtrakttv.model.Video;
import com.omertron.yamjtrakttv.view.MainWindow;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

public class TraktTools {

    private static final Logger LOG = Logger.getLogger(TraktTools.class);
    private static final int DEFAULT_PLAYS = 1;
    private static final ServiceManager MANAGER = new ServiceManager();

    protected TraktTools() {
        throw new UnsupportedOperationException("Do not use!");
    }

    public static boolean initialise(Credentials creds) {
        MANAGER.setAuthentication(creds.getUsername(), creds.getPassword());
        MANAGER.setApiKey(creds.getApikey());

        try {
            Response response = MANAGER.accountService().test().fire();
            if (response.status.equalsIgnoreCase("success")) {
                LOG.info("Authentication successful");
                creds.setValid(true);
                creds.setValidMessage("Authentication successful");
                return true;
            }
        } catch (TraktException | IllegalArgumentException ex) {
            LOG.error("Failure message: " + ex.getMessage());
            creds.setValid(false);
            creds.setValidMessage(ex.getMessage());
        }
        return false;
    }

    public static Movie getMovieSummary(Video video) {
        Movie movie;

        video.setSearchOnTrakt(Boolean.TRUE);
        try {
            if (StringUtils.isNotBlank(video.getId(Video.ID_IMDB))) {
                movie = MANAGER.movieService().summary(video.getId(Video.ID_IMDB)).fire();
            } else if (StringUtils.isNotBlank(video.getId(Video.ID_THEMOVIEDB))) {
                movie = MANAGER.movieService().summary(video.getId(Video.ID_THEMOVIEDB)).fire();
            } else {
                movie = MANAGER.movieService().summary(video.getTitle()).fire();
                video.addId(Video.ID_IMDB, movie.imdbId);
                video.addId(Video.ID_THEMOVIEDB, movie.tmdbId);
            }
        } catch (TraktException ex) {
            LOG.debug("Error getting information for movie: " + video.getTitle() + " - Error: " + ex.getMessage());
            video.setFoundOnTrakt(Boolean.FALSE);
            movie = null;
        }

        if (movie != null) {
            video.getSummaryInfo().addSummaryInfo(movie);
            video.setFoundOnTrakt(Boolean.TRUE);
        }
        return movie;
    }

    public static TvShow getTvShowSummary(Video video) {
        TvShow tvshow;

        video.setSearchOnTrakt(Boolean.TRUE);
        try {
            if (StringUtils.isNumeric(video.getId(Video.ID_TVDB))) {
                tvshow = MANAGER.showService().summary(video.getId(Video.ID_TVDB)).fire();
            } else {
                tvshow = MANAGER.showService().summary(video.getTitle()).fire();
                video.addId(Video.ID_TVDB, tvshow.tvdbId);
                video.addId(Video.ID_IMDB, tvshow.imdbId);
            }
        } catch (TraktException ex) {
            LOG.debug("Error getting information for TV show: " + video.getTitle() + " - Error: " + ex.getMessage());
            video.setFoundOnTrakt(Boolean.FALSE);
            tvshow = null;
        }

        if (tvshow != null) {
            video.getSummaryInfo().addSummaryInfo(tvshow);
            video.setFoundOnTrakt(Boolean.TRUE);
        }
        return tvshow;
    }

    public static TvEntity getEpisodeSummary(Video video, Episode episode) {
        TvEntity tvEntity;

        episode.setSearchOnTrakt(Boolean.TRUE);
        try {
            if (StringUtils.isNumeric(video.getId(Video.ID_TVDB))) {
                tvEntity = MANAGER.showService().episodeSummary(video.getId(Video.ID_TVDB), episode.getSeason(), episode.getEpisode()).fire();
            } else {
                tvEntity = MANAGER.showService().episodeSummary(video.getTitle(), episode.getSeason(), episode.getEpisode()).fire();
                video.addId(Video.ID_TVDB, tvEntity.show.tvdbId);
                video.addId(Video.ID_IMDB, tvEntity.show.imdbId);
            }
        } catch (TraktException ex) {
            LOG.debug("Error getting information for TV show: " + video.getTitle() + " Episode: " + episode.getEpisode() + " - Error: " + ex.getMessage());
            episode.setFoundOnTrakt(Boolean.FALSE);
            tvEntity = null;
        }

        if (tvEntity != null) {
            episode.getSummaryInfo().addSummaryInfo(tvEntity);
            episode.setFoundOnTrakt(Boolean.TRUE);
        }

        return tvEntity;
    }

    public static void addSeen(Video video) {
        addSeen(video, true);
    }

    public static void addSeen(Video video, boolean forceWatched) {
        StringBuilder message = new StringBuilder();
        if (video.isWatched()) {
            message.append("Marking seen: ").append(video.getType()).append(" ").append(video.getTitle());
        } else if (video.hasWatchedEpsidoes()) {
            message.append("TV Show ").append(video.getTitle()).append(" has 1 or more watched episodes.");
        } else if (forceWatched) {
            message.append("Forcing seen: ").append(video.getType()).append(" ").append(video.getTitle());
        } else {
            message.append("Video ").append(video.getType()).append(" ").append(video.getTitle()).append(" has not been watched. Skipping");
            ProgressProcessor.progressMessage(message.toString());
            return;
        }

        ProgressProcessor.progressMessage(message.toString());

        if (video.isMovie()) {
            addMovieSeen(video, forceWatched);
        } else {
            addShowSeen(video, forceWatched);
        }
    }

    private static void addMovieSeen(Video video, boolean forceWatched) {
        SeenBuilder sb = MANAGER.movieService().seen();
        if (StringUtils.isNotBlank(video.getId(Video.ID_THEMOVIEDB))) {
            int tmdbId = Integer.parseInt(video.getId(Video.ID_THEMOVIEDB));
            sb.movie(tmdbId, DEFAULT_PLAYS, video.getWatchedDate());
        } else if (StringUtils.isNotBlank(video.getId(Video.ID_IMDB))) {
            sb.movie(video.getId(Video.ID_IMDB), DEFAULT_PLAYS, video.getWatchedDate());
        } else {
            LOG.debug("No id found for " + video.getTitle());
            return;
        }
        sb.fire();
        LOG.info("Updated seen for " + video.getTitle());
    }

    private static void addShowSeen(Video video, boolean forceWatched) {
        EpisodeSeenBuilder esb = MANAGER.showService().episodeSeen(Integer.parseInt(video.getId(Video.ID_TVDB)));
        for (Episode ep : video.getEpisodes()) {
            if (ep.isWatched() || forceWatched) {
                esb.episode(ep.getSeason(), ep.getEpisode()).fire();
            }
        }
    }

    public static void addToCollection(Video video) {
        StringBuilder message = new StringBuilder();
        message.append("Adding ").append(video.getType()).append(" ").append(video.getTitle()).append(" to collection");
        ProgressProcessor.progressMessage(message.toString());

        if (video.isMovie()) {
            addMovieToCollection(video);
        } else {
            addShowToCollection(video);
        }
    }

    private static void addMovieToCollection(Video video) {
        if (!video.isSearchOnTrakt()) {
            getMovieSummary(video);
        }

        if (video.isFoundOnTrakt()) {
            LibraryBuilder lb = MANAGER.movieService().library();
            if (StringUtils.isNotBlank(video.getId(Video.ID_THEMOVIEDB))) {
                int tmdbId = Integer.parseInt(video.getId(Video.ID_THEMOVIEDB));
                lb.movie(tmdbId, DEFAULT_PLAYS, video.getWatchedDate());
            } else if (StringUtils.isNotBlank(video.getId(Video.ID_IMDB))) {
                lb.movie(video.getId(Video.ID_IMDB), DEFAULT_PLAYS, video.getWatchedDate());
            } else {
                lb.movie(video.getTitle(), video.getYear(), DEFAULT_PLAYS, video.getWatchedDate());
            }

            try {
                lb.fire();
            } catch (TraktException ex) {
                LOG.error("Failed to add " + video.getTitle() + " to collection: " + ex.getMessage());
            }
        } else {
            LOG.debug(video.getTitle() + " was not found on trakt.tv");
        }
    }

    private static void addShowToCollection(Video video) {
        if (video.getEpisodes().isEmpty()) {
            LOG.warn("No episodes found for " + video.getTitle());
            return;
        }

        if (!video.isSearchOnTrakt()) {
            getTvShowSummary(video);
        }

        if (video.isFoundOnTrakt()) {
            EpisodeLibraryBuilder elb;

            if (StringUtils.isNotBlank(video.getId(Video.ID_TVDB))) {
                elb = MANAGER.showService().episodeLibrary(Integer.parseInt(video.getId(Video.ID_TVDB)));
            } else if (StringUtils.isNotBlank(video.getId(Video.ID_IMDB))) {
                elb = MANAGER.showService().episodeLibrary(video.getId(Video.ID_IMDB));
            } else {
                elb = MANAGER.showService().episodeLibrary(video.getTitle(), video.getYear());
            }

            for (Episode episode : video.getEpisodes()) {
                elb.episode(episode.getSeason(), episode.getEpisode());
            }

            try {
                elb.fire();
            } catch (TraktException ex) {
                LOG.error("Failed to add " + video.getTitle() + " to collection: " + ex.getMessage());
            }
        } else {
            LOG.debug(video.getTitle() + " was not found on trakt.tv");
        }
    }

    /**
     * Get all movies associated with the account
     *
     * @param credentials
     * @return
     */
    public static List<Movie> getAllMovies(Credentials credentials) {
        LOG.debug("Getting movie list for user '" + credentials.getUsername() + "'");
        List<Movie> movies = MANAGER.userService().libraryMoviesAll(credentials.getUsername()).fire();
        LOG.info("Found " + movies.size() + " movies in the collection for '" + credentials.getUsername() + "'");
        return movies;
    }

    /**
     * Remove a movie from the account completely
     *
     * @param movies
     */
    public static void removeMovies(List<Movie> movies) {
        MovieService service = MANAGER.movieService();

        for (Movie m : movies) {
            LOG.debug("Removing '" + m.title + "' (" + m.imdbId + ")");
            try {
                service.unlibrary().movie(m.imdbId).fire();
                service.unseen().movie(m.imdbId).fire();
                service.unwatchlist().movie(m.imdbId).fire();
            } catch (TraktException ex) {
                LOG.info("Exception: " + ex.getMessage());
            }
        }
        LOG.debug("Done");
    }

    /**
     * Get all shows associated with the account
     *
     * @param credentials
     * @return
     */
    public static List<TvShow> getWatchedShows(Credentials credentials) {
        LOG.debug("Getting TV show list for user '" + credentials.getUsername() + "'");
        List<TvShow> watchedShows = MANAGER.userService().libraryShowsWatched(credentials.getUsername()).fire();
        LOG.info("Found " + watchedShows.size() + " watched shows in the collection");
        return watchedShows;
    }

    /**
     * Remove a show from the account completely
     *
     * @param progressWindow
     * @param shows
     */
    public static void removeShows(MainWindow progressWindow, List<TvShow> shows) {
        // Clear the progress window
        progressWindow.progressClearText();

        // Set the progress bar
        int count = 0;
        progressWindow.progressBarLimits(0, shows.size());

        ShowService service = MANAGER.showService();
        StringBuilder message;

        for (TvShow show : shows) {
            message = new StringBuilder("Removing '");
            message.append(show.title).append("' (IMDB: ").append(show.imdbId).append(", TVDB: ").append(show.tvdbId).append(")");
            progressWindow.progressAddText(message.toString());

            try {
                if (StringUtils.isNotBlank(show.tvdbId)) {
                    int tvdbId = NumberUtils.toInt(show.tvdbId);
                    ShowService.EpisodeUnseenBuilder unseen = service.episodeUnseen(tvdbId);
                    ShowService.EpisodeUnwatchlistBuilder unwatch = service.episodeUnwatchlist(tvdbId);

                    for (TvShowSeason season : show.seasons) {
                        for (Integer epnum : season.episodes.numbers) {
                            unseen = unseen.episode(season.season, epnum);
                            unwatch = unwatch.episode(season.season, epnum);
                        }

                        message = new StringBuilder("Removed Season ");
                        message.append(season.season).append(" with ").append(season.episodes.count).append(" episodes.");
                        progressWindow.progressAddText(message.toString());
                    }

                    unseen.fire();
                    unwatch.fire();
                    message = new StringBuilder("Done '");
                    message.append(show.title).append("'");
                    progressWindow.progressAddText(message.toString());
                } else {
                    message = new StringBuilder("Failed to delete show! ");
                    message.append(ToStringBuilder.reflectionToString(show, ToStringStyle.SIMPLE_STYLE));
                    progressWindow.progressAddText(message.toString());
                }
            } catch (TraktException ex) {
                LOG.info("Exception: " + ex.getMessage(), ex);
            }
            progressWindow.progressBarProgress(++count);
        }
        progressWindow.progressAddText("All entries processed.");
    }
}
