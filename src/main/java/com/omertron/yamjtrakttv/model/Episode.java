package com.omertron.yamjtrakttv.model;

import java.util.Date;

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
        return "Episode{" + "season=" + season + ", episode=" + episode + ", watched=" + watched + ", summaryInfo=" + summaryInfo + ", searchOnTrakt=" + searchOnTrakt + ", foundOnTrakt=" + foundOnTrakt + '}';
    }
}
