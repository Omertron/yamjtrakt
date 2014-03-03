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
package com.omertron.yamjtrakttv.tools;

import com.omertron.yamjtrakttv.model.Episode;
import com.omertron.yamjtrakttv.model.Video;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CompleteMoviesTools {

    /**
     * Parse the video element and extract the information from it.
     *
     * @param eVideo
     * @return
     */
    public static Video parseVideo(Element eVideo) {
        Video v = new Video();
        v.setTitle(DOMHelper.getValueFromElement(eVideo, "title"));
        v.setYear(DOMHelper.getValueFromElement(eVideo, "year"));
        v.setType(DOMHelper.getValueFromElement(eVideo, "movieType"));
        v.setWatched(Boolean.parseBoolean(DOMHelper.getValueFromElement(eVideo, "watched")));

        String stringDate = DOMHelper.getValueFromElement(eVideo, "watchedDate");
        if (StringUtils.isNumeric(stringDate)) {
            v.setWatchedDate(new Date(Long.parseLong(stringDate)));
        } else {
            v.setWatchedDate(new Date());
        }

        NodeList nlID = eVideo.getElementsByTagName("id");
        if (nlID.getLength() > 0) {
            Node nID;
            Element eID;
            for (int loop = 0; loop < nlID.getLength(); loop++) {
                nID = nlID.item(loop);
                if (nID.getNodeType() == Node.ELEMENT_NODE) {
                    eID = (Element) nID;
                    String moviedb = eID.getAttribute("movieDatabase");
                    if (StringUtils.isNotBlank(moviedb)) {
                        v.addId(moviedb, eID.getTextContent());
                    }
                }
            }
        }

        // TV specific processing
        if (v.isTvshow()) {
            v.addEpisodes(parseTvFiles(eVideo));
        }

        return v;
    }

    /**
     * Get the season and episode information from the files section for TV Shows
     *
     * @param eVideo
     * @return
     */
    private static List<Episode> parseTvFiles(Element eVideo) {
        List<Episode> episodes = new ArrayList<Episode>();

        NodeList nlFile = eVideo.getElementsByTagName("file");
        if (nlFile.getLength() > 0) {
            Node nFile;
            Element eFile;
            for (int loop = 0; loop < nlFile.getLength(); loop++) {
                nFile = nlFile.item(loop);
                if (nFile.getNodeType() == Node.ELEMENT_NODE) {
                    eFile = (Element) nFile;
                    int season = Integer.parseInt(DOMHelper.getValueFromElement(eFile, "season"));
                    int firstPart = Integer.parseInt(eFile.getAttribute("firstPart"));
                    int lastPart = Integer.parseInt(eFile.getAttribute("lastPart"));
                    boolean watched = Boolean.parseBoolean(DOMHelper.getValueFromElement(eFile, "watched"));
                    Date watchedDate;

                    String stringDate = DOMHelper.getValueFromElement(eVideo, "watchedDate");
                    if (StringUtils.isNumeric(stringDate)) {
                        watchedDate = new Date(Long.parseLong(stringDate));
                    } else {
                        watchedDate = new Date();
                    }

                    for (int episode = firstPart; episode <= lastPart; episode++) {
                        episodes.add(new Episode(season, episode, watched, watchedDate));
                    }
                }
            }
        }
        return episodes;
    }
}
