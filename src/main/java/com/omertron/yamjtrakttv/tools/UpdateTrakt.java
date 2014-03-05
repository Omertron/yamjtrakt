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
package com.omertron.yamjtrakttv.tools;

import com.omertron.yamjtrakttv.model.Video;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class UpdateTrakt implements Runnable, Callable<Integer> {

    private final Video video;
    private final boolean forceWatched;
    private int delay;

    public UpdateTrakt(Video video, boolean forceWatched, int delay) {
        this.video = video;
        this.forceWatched = forceWatched;
        this.delay = delay;
    }

    public UpdateTrakt(Video video, boolean forceWatched) {
        this(video, Boolean.FALSE, 0);
    }

    public UpdateTrakt(Video video) {
        this(video, Boolean.FALSE, 0);
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    private int process() {
        ProgressProcessor.progressMessage("Processing " + video.getType() + " - " + video.getTitle());

        TraktTools.addSeen(video, forceWatched);
        TraktTools.addToCollection(video);

        if (delay > 0) {
            try {
                TimeUnit.SECONDS.sleep(delay);
            } catch (InterruptedException ex) {
                // Don't care if we are interrupted or not.
            }
        }

        ProgressProcessor.progressMessage("Finished " + video.getType() + " - " + video.getTitle());

        return 1;
    }

    @Override
    public void run() {
        process();
    }

    @Override
    public Integer call() {
        return process();
    }
}
