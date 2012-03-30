package com.omertron.yamjtrakttv.model;

import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class Video {
    /*
     * Video class
     */

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
        episodes.add(episode);
    }

    public void addEpisodes(List<Episode> episodeList) {
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
        return "Video{" + "title=" + title + ", year=" + year + ", type=" + type + ", idMap=" + idMap + ", watched=" + watched + ", watchedDate=" + watchedDate + ", searchOnTrakt=" + searchOnTrakt + ", foundOnTrakt=" + foundOnTrakt + ", summaryInfo=" + summaryInfo + ", episodes=" + episodes + '}';
    }
}