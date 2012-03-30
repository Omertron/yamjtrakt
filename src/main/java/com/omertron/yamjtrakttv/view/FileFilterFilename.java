package com.omertron.yamjtrakttv.view;

import java.io.File;

/**
 * Custom file filter for jFileChooser
 *
 * @author Stuart
 */
public class FileFilterFilename extends javax.swing.filechooser.FileFilter {

    private String filename;
    private String description;

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
