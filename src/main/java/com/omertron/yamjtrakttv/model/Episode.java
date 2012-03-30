package com.omertron.yamjtrakttv.model;

public class Episode {

    private int season;
    private int episode;
    private SummaryInfo summaryInfo = new SummaryInfo();
    private boolean searchOnTrakt = Boolean.FALSE;
    private boolean foundOnTrakt = Boolean.FALSE;

    public Episode() {
        this.season = 0;
        this.episode = 0;
    }

    public Episode(int season, int episode) {
        this.season = season;
        this.episode = episode;
    }

    public int getEpisode() {
        return episode;
    }

    public int getSeason() {
        return season;
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

    @Override
    public String toString() {
        return "Episode{" + "season=" + season + ", episode=" + episode + ", summaryInfo=" + summaryInfo + ", searchOnTrakt=" + searchOnTrakt + ", foundOnTrakt=" + foundOnTrakt + '}';
    }

}
