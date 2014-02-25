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
package com.omertron.yamjtrakttv.view;

import java.io.File;

/**
 * Custom file filter for jFileChooser
 * @author Stuart
 */
public class FileFilterExtension extends javax.swing.filechooser.FileFilter {

    private final String extension;
    private final String description;

    /**
     * Create the custom filter with just the extension
     * @param extension
     */
    public FileFilterExtension(String extension) {
        if (extension.startsWith(".")) {
            this.extension = extension;
        } else {
            this.extension = "." + extension;
        }

        description = "All " + extension.substring(1).toUpperCase() + " files";
    }

    /**
     * Create the custom filter with the extension and the description
     * @param extension
     * @param description
     */
    public FileFilterExtension(String extension, String description) {
        if (extension.startsWith(".")) {
            this.extension = extension;
        } else {
            this.extension = "." + extension;
        }

        this.description = description + " (*" + extension + ")";
    }

    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with extension
        return file.isDirectory() || file.getAbsolutePath().endsWith(extension);
    }

    @Override
    public String getDescription() {
        // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return description;
    }
}
