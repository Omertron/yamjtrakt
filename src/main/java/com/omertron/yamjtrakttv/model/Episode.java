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
package com.omertron.yamjtrakttv.model;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Episode {

    private int season;
    private int episode;
    private boolean watched;
    private Date watchedDate;
    private SummaryInfo summaryInfo = new SummaryInfo();
    private boolean searchOnTrakt = Boolean.FALSE;
    private boolean foundOnTrakt = Boolean.FALSE;

    public Episode() {
        this.season = 0;
        this.episode = 0;
        this.watched = Boolean.FALSE;
        this.watchedDate = null;
    }

    public Episode(int season, int episode) {
        this.season = season;
        this.episode = episode;
        this.watched = Boolean.FALSE;
        this.watchedDate = null;
    }

    public Episode(int season, int episode, boolean watched) {
        this.season = season;
        this.episode = episode;
        this.watched = watched;
        this.watchedDate = new Date();
    }

    public Episode(int season, int episode, boolean watched, Date watchedDate) {
        this.season = season;
        this.episode = episode;
        this.watched = watched;
        this.watchedDate = watchedDate;
    }

    public int getEpisode() {
        return episode;
    }

    public int getSeason() {
        return season;
    }

    public boolean isWatched() {
        return watched;
    }

    public SummaryInfo getSummaryInfo() {
        return summaryInfo;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public void setSummaryInfo(SummaryInfo summaryInfo) {
        this.summaryInfo = summaryInfo;
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

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public Date getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(Date watchedDate) {
        this.watchedDate = watchedDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
