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

import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvEntity;
import com.jakewharton.trakt.entities.TvShow;
import com.jakewharton.trakt.enumerations.Rating;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SummaryInfo {

    private Rating rating;
    private boolean inWatchlist;
    private boolean inCollection;
    private boolean watched;
    private int plays;

    public SummaryInfo() {
        this.rating = null;
        this.inCollection = Boolean.FALSE;
        this.inWatchlist = Boolean.FALSE;
        this.watched = Boolean.FALSE;
        this.plays = 0;
    }

    public SummaryInfo(String rating, boolean inWatchlist, boolean inCollection, boolean watched, int plays) {
        this.rating = Rating.fromValue(rating);
        this.inWatchlist = inWatchlist;
        this.inCollection = inCollection;
        this.watched = watched;
        this.plays = plays;
    }

    public SummaryInfo(String rating, boolean inWatchlist) {
        this.rating = Rating.fromValue(rating);
        this.inWatchlist = inWatchlist;
        this.inCollection = Boolean.FALSE;
        this.watched = Boolean.FALSE;
        this.plays = 0;
    }

    public boolean isInCollection() {
        return inCollection;
    }

    public void setInCollection(boolean inCollection) {
        this.inCollection = inCollection;
    }

    public boolean isInWatchlist() {
        return inWatchlist;
    }

    public void setInWatchlist(boolean inWatchlist) {
        this.inWatchlist = inWatchlist;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = Rating.fromValue(rating);
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public void addSummaryInfo(Movie movie) {
        this.inCollection = movie.inCollection;
        this.inWatchlist = movie.inWatchlist;
        this.rating = movie.rating;
        this.watched = movie.watched;
        this.plays = movie.plays;
    }

    public void addSummaryInfo(TvShow show) {
        this.inWatchlist = show.inWatchlist;
        this.rating = show.rating;
    }

    public void addSummaryInfo(TvEntity tvEntity) {
//        this.inCollection = tvEntity.episode.inCollection;
        this.inWatchlist = tvEntity.episode.inWatchlist;
        this.rating = tvEntity.episode.rating;
        this.watched = tvEntity.episode.watched;
        this.plays = tvEntity.episode.plays;
    }
}
