package com.omertron.yamjtrakttv;

import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;
import com.omertron.yamjtrakttv.model.Credentials;
import com.omertron.yamjtrakttv.model.Library;
import com.omertron.yamjtrakttv.model.Video;
import com.omertron.yamjtrakttv.tools.TraktTools;
import com.omertron.yamjtrakttv.view.MainWindow;
import java.io.*;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

public class YamjTraktApp {

    private static final Logger logger = Logger.getLogger(YamjTraktApp.class);
    private static final String FILE_CREDENTIALS = "Credentials.xml";
    private static Credentials credentials;
    private static Library appLibrary = new Library();
    private static boolean completeMoviesLoaded = Boolean.FALSE;
    private static boolean completeMoviesProcessed = Boolean.FALSE;

    public static void main(String[] args) {
        credentials = new Credentials();

        // Open the GUI
        MainWindow.windowMain(args);
    }

    public static boolean testScanTrakt() {
        boolean response = TraktTools.initialise(credentials);
        if (response) {
            Map<String, Video> videos = appLibrary.getVideos();
            for (String title : videos.keySet()) {
                Video v = videos.get(title);
                if (v.isMovie()) {
                    logger.info("Processing movie: " + title + " (" + v.getId(Video.ID_IMDB) + ")");
                    Movie movie = TraktTools.getMovieSummary(v);

                    logger.info("Watched   : " + movie.watched);
                    logger.info("Plays     : " + movie.plays);
                    logger.info("Rating    : " + movie.rating);
                    logger.info("Watchlist : " + movie.inWatchlist);
                    logger.info("Collection: " + movie.inCollection);
                } else {
                    logger.info("Processing TV show: " + title + " (" + v.getId(Video.ID_TVDB) + ")");
                    TvShow tvshow = TraktTools.getTvShowSummary(v);

//                    logger.info("Watched   : " + tvshow.watched);
//                    logger.info("Plays     : " + tvshow.plays);
                    logger.info("Rating    : " + tvshow.rating);
                    logger.info("Watchlist : " + tvshow.inWatchlist);
//                    logger.info("Collection: " + tvshow.inCollection);


                }
            }
        } else {
            logger.error("ABORTING!!!");
        }
        return response;
    }

    public static Library getAppLibrary() {
        return appLibrary;
    }

    public static Credentials getCredentials() {
        return credentials;
    }

    public static void setCredentials(Credentials credentials) {
        YamjTraktApp.credentials = credentials;
    }

    public static void saveCredentials(Credentials credentials) {
        if (credentials.isValid()) {
            OutputStream os = null;
            try {
                Properties props = new Properties();
                props.setProperty("username", credentials.getUsername());
                props.setProperty("password", credentials.getPassword());
                props.setProperty("apikey", credentials.getApikey());
                os = new FileOutputStream(FILE_CREDENTIALS);
                props.storeToXML(os, "Credentials file written by YamjTraktTv", "UTF8");
            } catch (FileNotFoundException ex) {
                logger.warn("File not found! " + ex.getMessage());
            } catch (IOException ex) {
                logger.warn("Error writing file: " + ex.getMessage());
            } finally {
                try {
                    os.close();
                } catch (IOException ex) {
                    logger.warn("Error closing file: " + ex.getMessage());
                }
            }
        } else {
            logger.warn("Credentials are not valid!");
            logger.warn("Not saving!");
        }
    }

    public static void loadCredentials(Credentials credentials) {
        try {
            FileInputStream fis = new FileInputStream(FILE_CREDENTIALS);
            Properties props = new Properties();
            props.loadFromXML(fis);

            credentials.setUsername(props.getProperty("username", ""));
            credentials.setPassword(props.getProperty("password", ""));
            credentials.setApikey(props.getProperty("apikey", ""));

        } catch (InvalidPropertiesFormatException ex) {
            logger.warn("Properties file is not well formed, can not load: " + ex.getMessage());
        } catch (IOException ex) {
            logger.warn("Unable to load properties file!" + ex.getMessage());
        }
    }

    public static boolean isCompleteMoviesLoaded() {
        return completeMoviesLoaded;
    }

    public static void setCompleteMoviesLoaded(boolean completeMoviesLoaded) {
        YamjTraktApp.completeMoviesLoaded = completeMoviesLoaded;
    }

    public static boolean isCompleteMoviesProcessed() {
        return completeMoviesProcessed;
    }

    public static void setCompleteMoviesProcessed(boolean completeMoviesProcessed) {
        YamjTraktApp.completeMoviesProcessed = completeMoviesProcessed;
    }

}
