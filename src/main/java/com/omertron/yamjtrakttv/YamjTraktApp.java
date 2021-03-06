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
package com.omertron.yamjtrakttv;

import com.omertron.yamjtrakttv.model.Credentials;
import com.omertron.yamjtrakttv.model.Library;
import com.omertron.yamjtrakttv.view.MainWindow;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class YamjTraktApp {

    private static final Logger LOG = Logger.getLogger(YamjTraktApp.class);
    private static final String LOG_FILENAME = YamjTraktApp.class.getSimpleName();
    private static final String PROP_FILENAME = "properties/log4j.properties";
    private static final String FILE_CREDENTIALS = "Credentials.xml";
    private static Credentials credentials;
    private static final Library LIBRARY = new Library();
    private static boolean completeMoviesLoaded = Boolean.FALSE;
    private static boolean completeMoviesProcessed = Boolean.FALSE;
    private static boolean markAllWatched = Boolean.TRUE;

    public static void main(String[] args) {
        credentials = new Credentials();
        System.err.println("Log filename: " + LOG_FILENAME);
        System.setProperty("file.name", LOG_FILENAME);

        // Load the properties file
        PropertyConfigurator.configure(PROP_FILENAME);

        LOG.info("YAMJ Trakt.tv App");
        LOG.info("~~~~ ~~~~~~~~ ~~~");
        LOG.info("     Version: " + YamjTraktApp.class.getPackage().getSpecificationVersion());
        LOG.info("  Build date: " + YamjTraktApp.class.getPackage().getImplementationTitle());
        LOG.info("Java version: " + java.lang.System.getProperties().getProperty("java.version"));
        LOG.info("");

        // Open the GUI
        MainWindow.windowMain(args);
    }

    public static Library getLibrary() {
        return LIBRARY;
    }

    public static Credentials getCredentials() {
        return credentials;
    }

    public static void setCredentials(Credentials credentials) {
        YamjTraktApp.credentials = credentials;
    }

    public static boolean saveCredentials(Credentials credentials) {
        if (credentials.isValid()) {
            OutputStream os = null;
            try {
                Properties props = new Properties();
                props.setProperty("username", credentials.getUsername());
                props.setProperty("password", credentials.getPassword());
                props.setProperty("apikey", credentials.getApikey());
                os = new FileOutputStream(FILE_CREDENTIALS);
                props.storeToXML(os, "Credentials file written by YamjTraktTv", "UTF8");
                return true;
            } catch (FileNotFoundException ex) {
                LOG.warn("File not found! " + ex.getMessage());
                return false;
            } catch (IOException ex) {
                LOG.warn("Error writing file: " + ex.getMessage());
                return false;
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException ex) {
                    LOG.warn("Error closing file: " + ex.getMessage());
                }
            }
        } else {
            LOG.warn("Credentials are not valid!");
            LOG.warn("Not saving!");
            return false;
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
            LOG.warn("Properties file is not well formed, can not load: " + ex.getMessage());
        } catch (IOException ex) {
            LOG.warn("Unable to load properties file!" + ex.getMessage());
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

    public static boolean isMarkAllWatched() {
        return markAllWatched;
    }

    public static void setMarkAllWatched(boolean markAllWatched) {
        YamjTraktApp.markAllWatched = markAllWatched;
    }
}
