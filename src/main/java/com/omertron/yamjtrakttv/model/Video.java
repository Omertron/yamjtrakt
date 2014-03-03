/*
 *      Copyright (c) 2004-2013 Stuart Boston
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
package com.omertron.yamjtrakttv.model;

import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Video class
 *
 * @author Stuart
 */
public class Video {

    // Static ID descriptions
    public static final String ID_IMDB = "imdb";
    public static final String ID_TVDB = "thetvdb";
    public static final String ID_THEMOVIEDB = "themoviedb";
    // Properties of the Video from the CompelteMovies.xml file
    private String title = "";
    private int year = 0;
    private String type = "";
    private Map<String, String> idMap = new HashMap<String, String>();
    private boolean watched = Boolean.FALSE;
    private boolean watchedEpsidoes = Boolean.FALSE;
    private Date watchedDate = null;
    private boolean searchOnTrakt = Boolean.FALSE;
    private boolean foundOnTrakt = Boolean.FALSE;
    //Summary information from Trakt.tv
    private SummaryInfo summaryInfo = new SummaryInfo();
    // Only for TV Shows
    private List<Episode> episodes = new ArrayList<Episode>();

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getIdMap() {
        return idMap;
    }

    public String getId(String moviedb) {
        if (idMap.containsKey(moviedb)) {
            return idMap.get(moviedb);
        } else {
            return "";
        }
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public Date getWatchedDate() {
        return watchedDate;
    }

    public boolean isWatched() {
        return watched;
    }

    public boolean hasWatchedEpsidoes() {
        return watchedEpsidoes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addId(String moviedb, String id) {
        if (StringUtils.isNotBlank(id)) {
            idMap.put(moviedb, id);
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isTvshow() {
        return type.equals("TVSHOW");
    }

    public boolean isMovie() {
        return type.equals("MOVIE");
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public void setIdMap(Map<String, String> idMap) {
        this.idMap = idMap;
    }

    public void setWatchedDate(Date watchedDate) {
        this.watchedDate = watchedDate;
    }

    public void addEpisode(int season, int episode) {
        addEpisode(new Episode(season, episode));
    }

    public void addEpisode(Episode episode) {
        // Update the watched episodes flag
        this.watchedEpsidoes |= episode.isWatched();
        episodes.add(episode);
    }

    public void addEpisodes(List<Episode> episodeList) {
        // Update the watched episodes flag
        for (Episode episode : episodeList) {
            this.watchedEpsidoes |= episode.isWatched();
        }
        episodes.addAll(episodeList);
    }

    public SummaryInfo getSummaryInfo() {
        return summaryInfo;
    }

    public void setSummaryInfo(SummaryInfo summaryInfo) {
        this.summaryInfo = summaryInfo;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public boolean isFoundOnTrakt() {
        return foundOnTrakt;
    }

    public void setFoundOnTrakt(boolean foundOnTrakt) {
        this.foundOnTrakt = foundOnTrakt;
    }

    public boolean isSearchOnTrakt() {
        return searchOnTrakt;
    }

    public void setSearchOnTrakt(boolean searchOnTrakt) {
        this.searchOnTrakt = searchOnTrakt;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setYear(String year) {
        if (StringUtils.isNumeric(year)) {
            this.year = Integer.parseInt(year);
        } else {
            this.year = 0;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
