/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omertron.yamjtrakttv.tools;

import com.omertron.yamjtrakttv.model.Video;
import java.util.concurrent.Callable;

/**
 *
 * @author Stuart
 */
public class UpdateTrakt implements Runnable, Callable<Integer> {

    private Video video;
    private boolean forceWatched;

    public UpdateTrakt(Video video, boolean forceWatched) {
        this.video = video;
        this.forceWatched = forceWatched;
    }

    public UpdateTrakt(Video video) {
        this.video = video;
        this.forceWatched = Boolean.FALSE;
    }

    private int process() {
        String returnMessage;
        
        ProgressProcessor.progressMessage("Processing " + video.getType() + " - " + video.getTitle());
        TraktTools.addSeen(video, forceWatched);
        TraktTools.addToCollection(video);

        ProgressProcessor.progressMessage("Finished " + video.getType() + " - " + video.getTitle());

        return 1;
    }

    public void run() {
        process();
    }

    public Integer call() {
        return process();
    }
}
