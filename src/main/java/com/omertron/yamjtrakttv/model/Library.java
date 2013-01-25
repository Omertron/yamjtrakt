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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Library {

    // List of the videos with the title as key
    private Map<String, Video> videos;
    private String pathCompleteMovie;
    // Stats about the library
    private int numberMovies;
    private int numberTV;
    private int numberEpisodes;
    // Formatter
    DecimalFormat numberFormat = new DecimalFormat("#,##0");

    public Library() {
        videos = new HashMap<String, Video>();
        pathCompleteMovie = "";
        numberMovies = 0;
        numberTV = 0;
        numberEpisodes = 0;
    }

    public void addVideo(Video video) {
        // Check to see if the video already exists
        if (videos.containsKey(video.getTitle())) {
            // check to see if the video is a tv show, otherwise skip it
            if (video.isTvshow()) {
                // add the episodes to the existing tv show
                videos.get(video.getTitle()).addEpisodes(video.getEpisodes());
                numberEpisodes += video.getEpisodes().size();
            }
        } else {
            videos.put(video.getTitle(), video);
            if (video.isTvshow()) {
                numberTV++;
                numberEpisodes += video.getEpisodes().size();
            } else {
                numberMovies++;
            }
        }
    }

    public void removeVideo(Video video) {
        removeVideo(video.getTitle());
    }

    public void removeVideo(String title) {
        if (videos.containsKey(title)) {
            videos.remove(title);
        }
    }

    public Map<String, Video> getVideos() {
        return videos;
    }

    public int getNumberEpisodes() {
        return numberEpisodes;
    }

    public int getNumberMovies() {
        return numberMovies;
    }

    public int getNumberTV() {
        return numberTV;
    }

    public String getPathCompleteMovie() {
        return pathCompleteMovie;
    }

    public String getStats() {
        StringBuilder stats = new StringBuilder("Library contains ");

        stats.append(numberFormat.format(numberMovies));
        if (numberMovies == 1) {
            stats.append(" movie");
        } else {
            stats.append(" movies");
        }

        stats.append(" and ");

        stats.append(numberFormat.format(numberTV));
        if (numberTV == 1) {
            stats.append(" TV show");
        } else {
            stats.append(" TV shows");
        }

        if (numberTV > 0) {
            stats.append(" with ");
            stats.append(numberFormat.format(numberEpisodes));
            stats.append(" episodes total");
        }

        stats.append(".");

        return stats.toString();
    }

    public void setPathCompleteMovie(String pathCompleteMovie) {
        this.pathCompleteMovie = pathCompleteMovie;
    }
}
