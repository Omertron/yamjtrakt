package com.omertron.yamjtrakttv.view;

import java.io.File;

/**
 * Custom file filter for jFileChooser
 * @author Stuart
 */
public class FileFilterExtension extends javax.swing.filechooser.FileFilter {

    private String extension;
    private String description;

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
