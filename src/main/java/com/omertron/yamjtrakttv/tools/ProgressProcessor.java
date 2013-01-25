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

import com.omertron.yamjtrakttv.YamjTraktApp;
import com.omertron.yamjtrakttv.model.Library;
import com.omertron.yamjtrakttv.model.Video;
import com.omertron.yamjtrakttv.view.MainWindow;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProgressProcessor {

    private static final Logger logger = Logger.getLogger(ProgressProcessor.class);
    private static MainWindow progressWindow;

    public static void setProgressWindow(MainWindow newProgressWindow) {
        progressWindow = newProgressWindow;
        progressWindow.progestSetTitle("Parsing CompleteMovies.xml");
        progressWindow.progressClearText();
    }

    public static void progressMessage(String message) {
        progressWindow.progressAddText(message);
    }

    /**
     * Process a YAMJ CompleteMovies.xml file and store the contents in the
     * library
     * http://stackoverflow.com/questions/2710712/output-to-jtextarea-in-realtime
     *
     * @param cmLibrary
     * @param completeMoviesPath
     * @return
     */
    public static void parseCompleteMovies(final Library cmLibrary, final String completeMoviesPath) {
        File cm = new File(completeMoviesPath);

        if (!cm.exists()) {
            logger.error("Failed to load 'CompleteMovies.xml' from: " + completeMoviesPath);
            return;
        }

        Document cmDoc = DOMHelper.getEventDocFromUrl(cm);

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
                                Video video = CompleteMoviesTools.parseVideo(eVideo);
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

    public static void sendToTrakt(final int numberOfThreads) {
        // Clear the progress window and set up the bar
        progressWindow.progressClearText();

        // Print the stats
        progressWindow.progressAddText("Processing Videos on Trakt.tv");
        progressWindow.progressAddText(YamjTraktApp.getLibrary().getStats());

        (new Thread() {

            @Override
            public void run() {
                // This date will be used to mark videos as watched if needed
                Date currentDate = new Date();
                logger.info("Will use the date " + currentDate.toString() + " for videos if required");

                Map<String, Video> libVids = YamjTraktApp.getLibrary().getVideos();

                ExecutorService exec = Executors.newFixedThreadPool(numberOfThreads);
                List<Future<Integer>> list = new ArrayList<Future<Integer>>();

                for (String videoTitle : libVids.keySet()) {
                    Callable<Integer> worker = new UpdateTrakt(libVids.get(videoTitle), YamjTraktApp.isMarkAllWatched());
                    Future<Integer> submit = exec.submit(worker);
                    list.add(submit);
                }
                progressWindow.progressAddText("Submitted " + list.size() + " tasks to be processed on " + numberOfThreads + " threads.");
                progressWindow.progressBarLimits(1, list.size());

                // Retrive the list
                int count = 0;
                for (Future<Integer> future : list) {
                    try {
                        count += future.get();
                    } catch (InterruptedException ex) {
                        logger.warn("InterruptedException: " + ex);
                    } catch (ExecutionException ex) {
                        logger.warn("ExecutionException: " + ex);
                    }
                    progressWindow.progressBarProgress(count);
                }

                exec.shutdown();
                while (!exec.isTerminated()) {
                    try {
                        logger.debug("Waiting for threads to finish...");
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        // If we get interrupted it doesn't matter
                    }
                }
                progressWindow.progressAddText("All threads have finished");
            }
        }).start();

        progressWindow.progressAddText("Done!");
        progressWindow.progressAddText("");
    }
}
