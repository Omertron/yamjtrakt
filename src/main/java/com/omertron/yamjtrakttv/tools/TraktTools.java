package com.omertron.yamjtrakttv.tools;

import com.jakewharton.trakt.ServiceManager;
import com.jakewharton.trakt.TraktException;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.entities.TvEntity;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.services.MovieService.LibraryBuilder;
import com.jakewharton.trakt.services.MovieService.SeenBuilder;
import com.jakewharton.trakt.services.ShowService.EpisodeLibraryBuilder;
import com.jakewharton.trakt.services.ShowService.EpisodeSeenBuilder;
import com.omertron.yamjtrakttv.model.Credentials;
import com.omertron.yamjtrakttv.model.Episode;
import com.omertron.yamjtrakttv.model.Video;
import com.omertron.yamjtrakttv.view.MainWindow;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class TraktTools {

    private static final Logger logger = Logger.getLogger(TraktTools.class);
    private static final int DEFAULT_PLAYS = 1;
    private static ServiceManager manager = new ServiceManager();
    private static MainWindow progressWindow;

    protected TraktTools() {
        throw new UnsupportedOperationException("Do not use!");
    }

    public static boolean initialise(Credentials creds) {
        manager.setAuthentication(creds.getUsername(), creds.getPassword());
        manager.setApiKey(creds.getApikey());

        try {
            Response response = manager.accountService().test().fire();
            if (response.status.equalsIgnoreCase("success")) {
                logger.info("Authentication successful");
                creds.setValid(true);
                creds.setValidMessage("Authentication successful");
                return true;
            }
        } catch (TraktException ex) {
            logger.error("Failure message: " + ex.getMessage());
            creds.setValid(false);
            creds.setValidMessage(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("Failure message: " + ex.getMessage());
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
                movie = manager.movieService().summary(video.getId(Video.ID_IMDB)).fire();
            } else if (StringUtils.isNotBlank(video.getId(Video.ID_THEMOVIEDB))) {
                movie = manager.movieService().summary(video.getId(Video.ID_THEMOVIEDB)).fire();
            } else {
                movie = manager.movieService().summary(video.getTitle()).fire();
                video.addId(Video.ID_IMDB, movie.imdbId);
                video.addId(Video.ID_THEMOVIEDB, movie.tmdbId);
            }
        } catch (TraktException ex) {
            logger.debug("Error getting information for movie: " + video.getTitle() + " - Error: " + ex.getMessage());
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
                tvshow = manager.showService().summary(video.getId(Video.ID_TVDB)).fire();
            } else {
                tvshow = manager.showService().summary(video.getTitle()).fire();
                video.addId(Video.ID_TVDB, tvshow.tvdbId);
                video.addId(Video.ID_IMDB, tvshow.imdbId);
            }
        } catch (TraktException ex) {
            logger.debug("Error getting information for TV show: " + video.getTitle() + " - Error: " + ex.getMessage());
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
                tvEntity = manager.showService().episodeSummary(video.getId(Video.ID_TVDB), episode.getSeason(), episode.getEpisode()).fire();
            } else {
                tvEntity = manager.showService().episodeSummary(video.getTitle(), episode.getSeason(), episode.getEpisode()).fire();
                video.addId(Video.ID_TVDB, tvEntity.show.tvdbId);
                video.addId(Video.ID_IMDB, tvEntity.show.imdbId);
            }
        } catch (TraktException ex) {
            logger.debug("Error getting information for TV show: " + video.getTitle() + " Episode: " + episode.getEpisode() + " - Error: " + ex.getMessage());
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
        } else if (forceWatched) {
            message.append("Forcing seen: ").append(video.getType()).append(" ").append(video.getTitle());
        } else {
            message.append("Video ").append(video.getType()).append(" ").append(video.getTitle()).append(" has not been watched. Skipping");
        }
        ProgressProcessor.progressMessage(message.toString());

        if (video.isMovie()) {
            addMovieSeen(video);
        } else {
            addShowSeen(video);
        }
    }

    private static void addMovieSeen(Video video) {
        if (!video.isSearchOnTrakt()) {
            getMovieSummary(video);
        }

        if (video.isFoundOnTrakt()) {
            SeenBuilder sb = manager.movieService().seen();
            if (StringUtils.isNotBlank(video.getId(Video.ID_THEMOVIEDB))) {
                int tmdbId = Integer.parseInt(video.getId(Video.ID_THEMOVIEDB));
                sb.movie(tmdbId, DEFAULT_PLAYS, video.getWatchedDate());
            } else if (StringUtils.isNotBlank(video.getId(Video.ID_IMDB))) {
                sb.movie(video.getId(Video.ID_IMDB), DEFAULT_PLAYS, video.getWatchedDate());
            } else {
                logger.debug("No id found for " + video.getTitle());
                return;
            }
            sb.fire();
            logger.info("Updated seen for " + video.getTitle());
        } else {
            logger.debug(video.getTitle() + " was not found on trakt.tv");
        }
    }

    private static void addShowSeen(Video video) {
        if (!video.isSearchOnTrakt()) {
            getTvShowSummary(video);
        }

        if (video.isFoundOnTrakt()) {
            EpisodeSeenBuilder esb = manager.showService().episodeSeen(Integer.parseInt(video.getId(Video.ID_TVDB)));
            for (Episode ep : video.getEpisodes()) {
                if (!ep.isSearchOnTrakt()) {
                    getEpisodeSummary(video, ep);
                }

                if (ep.isFoundOnTrakt()) {
                    esb.episode(ep.getSeason(), ep.getEpisode()).fire();
                } else {
                    logger.debug(video.getTitle() + " Season " + ep.getSeason() + " Episode " + ep.getEpisode() + " was not found on trakt");
                }
            }
        } else {
            logger.debug(video.getTitle() + " was not found on trakt.tv");
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
            LibraryBuilder lb = manager.movieService().library();
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
                logger.error("Failed to add " + video.getTitle() + " to collection: " + ex.getMessage());
            }
        } else {
            logger.debug(video.getTitle() + " was not found on trakt.tv");
        }
    }

    private static void addShowToCollection(Video video) {
        if (video.getEpisodes().isEmpty()) {
            logger.warn("No episodes found for " + video.getTitle());
            return;
        }

        if (!video.isSearchOnTrakt()) {
            getTvShowSummary(video);
        }

        if (video.isFoundOnTrakt()) {
            EpisodeLibraryBuilder elb;

            if (StringUtils.isNotBlank(video.getId(Video.ID_TVDB))) {
                elb = manager.showService().episodeLibrary(Integer.parseInt(video.getId(Video.ID_TVDB)));
            } else if (StringUtils.isNotBlank(video.getId(Video.ID_IMDB))) {
                elb = manager.showService().episodeLibrary(video.getId(Video.ID_IMDB));
            } else {
                elb = manager.showService().episodeLibrary(video.getTitle(), video.getYear());
            }


            for (Episode episode : video.getEpisodes()) {
                elb.episode(episode.getSeason(), episode.getEpisode());
            }

            try {
                elb.fire();
            } catch (TraktException ex) {
                logger.error("Failed to add " + video.getTitle() + " to collection: " + ex.getMessage());
            }
        } else {
            logger.debug(video.getTitle() + " was not found on trakt.tv");
        }

    }

    public static void setProgressWindow(MainWindow newProgressWindow) {
        progressWindow = newProgressWindow;
        progressWindow.progestSetTitle("Processing videos on Trakt.tv");
        progressWindow.progressClearText();
    }
}
