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
package com.omertron.yamjtrakttv.view;

import java.io.File;

/**
 * Custom file filter for jFileChooser
 *
 * @author Stuart
 */
public class FileFilterFilename extends javax.swing.filechooser.FileFilter {

    private final String filename;
    private final String description;

    /**
     * Create the custom filter with just the filename
     *
     * @param filename
     */
    public FileFilterFilename(String filename) {
        this.filename = filename;
        description = "Single " + filename + " file";
    }

    /**
     * Create the custom filter with the filename and the description
     *
     * @param filename
     * @param description
     */
    public FileFilterFilename(String filename, String description) {
        this.filename = filename;
        this.description = description + " (*" + filename + ")";
    }

    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with extension
        return file.isDirectory() || file.getAbsolutePath().endsWith(filename);
    }

    @Override
    public String getDescription() {
        // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return description;
    }
}
