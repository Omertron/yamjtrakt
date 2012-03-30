package com.omertron.yamjtrakttv.tools;

import com.omertron.yamjtrakttv.model.Episode;
import com.omertron.yamjtrakttv.model.Library;
import com.omertron.yamjtrakttv.model.Video;
import com.omertron.yamjtrakttv.view.MainWindow;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseCompleteMovies {

    private static final Logger logger = Logger.getLogger(ParseCompleteMovies.class);
    private static Document cmDoc;
    private static MainWindow progressWindow;

    /**
     * Process a YAMJ CompleteMovies.xml file and store the contents in the
     * library
     * http://stackoverflow.com/questions/2710712/output-to-jtextarea-in-realtime
     *
     * @param cmLibrary
     * @param completeMoviesPath
     * @return
     */
    public static void parse(final Library cmLibrary, final String completeMoviesPath) {
        File cm = new File(completeMoviesPath);

        if (!cm.exists()) {
            logger.error("Failed to load 'CompleteMovies.xml' from: " + completeMoviesPath);
            return;
        }

        cmDoc = DOMHelper.getEventDocFromUrl(cm);

        if (cmDoc != null) {
            logger.info("Loaded CompleteMovies from " + completeMoviesPath);
            progressWindow.progressAddText("Loaded CompleteMovies from " + completeMoviesPath);

            final NodeList nlVideos = cmDoc.getElementsByTagName("movies");
            if (nlVideos.getLength() > 0) {
                logger.info("Found " + nlVideos.getLength() + " videos to process");
                progressWindow.progressAddText("Found " + nlVideos.getLength() + " videos to process");
                progressWindow.progressBarLimits(1, nlVideos.getLength());

                (new Thread() {

                    @Override
                    public void run() {
                        Node nVideo;
                        for (int loop = 0; loop < nlVideos.getLength(); loop++) {
                            progressWindow.progressBarProgress(loop + 1);
                            nVideo = nlVideos.item(loop);
                            if (nVideo.getNodeType() == Node.ELEMENT_NODE) {
                                Element eVideo = (Element) nVideo;
                                Video video = parseVideo(eVideo);
                                progressWindow.progressAddText("  #" + (loop + 1) + ") " + video.getType() + " - " + video.getTitle());
                                cmLibrary.addVideo(video);
                            }
                        }
                        progressWindow.progressBarProgress(nlVideos.getLength());

                        logger.info("Finished processing " + completeMoviesPath);
                        progressWindow.progressAddText("Finished processing " + completeMoviesPath);
                        logger.info(cmLibrary.getStats());
                        progressWindow.progressAddText(cmLibrary.getStats());

                    }
                }).start();
            } else {
                logger.info("No videos were found in " + completeMoviesPath);
                progressWindow.progressAddText("No videos were found in " + completeMoviesPath);
            }

        } else {
            logger.info("Unable to process " + completeMoviesPath);
            progressWindow.progressAddText("Unable to process " + completeMoviesPath);
        }

        progressWindow.updateLibraryStats(cmLibrary.getStats());
    }

    /**
     * Parse the video element and extract the information from it.
     *
     * @param eVideo
     * @return
     */
    private static Video parseVideo(Element eVideo) {
        Video v = new Video();
        v.setTitle(DOMHelper.getValueFromElement(eVideo, "title"));
        v.setYear(DOMHelper.getValueFromElement(eVideo, "year"));
        v.setType(DOMHelper.getValueFromElement(eVideo, "movieType"));
        v.setWatched(Boolean.parseBoolean(DOMHelper.getValueFromElement(eVideo, "watched")));

        String stringDate = DOMHelper.getValueFromElement(eVideo, "watchedDate");
        if (StringUtils.isNumeric(stringDate)) {
            v.setWatchedDate(new Date(Long.parseLong(stringDate)));
        }else{
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
     * Get the season and episode information from the files section for TV
     * Shows
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
                    for (int episode = firstPart; episode <= lastPart; episode++) {
                        episodes.add(new Episode(season, episode));
                    }
                }
            }
        }
        return episodes;
    }

    public static void setProgressWindow(MainWindow newProgressWindow) {
        progressWindow = newProgressWindow;
        progressWindow.progestSetTitle("Parsing CompleteMovies.xml");
        progressWindow.progressClearText();
    }
}
