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

import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.entities.TvShow;
import com.omertron.yamjtrakttv.YamjTraktApp;
import com.omertron.yamjtrakttv.tools.ProgressProcessor;
import com.omertron.yamjtrakttv.tools.TraktTools;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class MainWindow extends javax.swing.JFrame {

    private static final Logger LOG = Logger.getLogger(MainWindow.class);
    private static final String DEFAULT_DIRECTORY = System.getProperty("user.dir");

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();

        // Resize the main window
        this.setSize(resizeWindow(btnExit, 20, 70));

        int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        this.spnProcessingThreads.setValue(Math.max(numberOfProcessors / 2, 1));

        // Set the default watched
        this.mnuMarkAllWatched.setSelected(YamjTraktApp.isMarkAllWatched());
        // Set the default about text
        StringBuilder initialText = new StringBuilder("<html>");
        initialText.append("This <i>very</i> simple GUI can be used to upload your existing YAMJ library to <b>Trakt.tv</b><br/>");
        initialText.append("<br/>");
        initialText.append("The movies and TV shows will be read from the '<i>CompleteMovies.xml</i>' file in your jukebox and uploaded to the website.<br/>");
        initialText.append("<br/>");
        initialText.append("If you have the shows marked as watched, then they will be marked as '<i>seen</i>' on Trakt.tv too.<br/>");
        initialText.append("<br/>");
        initialText.append("Any files not marked as watched will just be added to your collection unless the '<i>Mark All</i>' setting is enabled.");

        lblAboutDescription.setText(initialText.toString());
    }

    private Dimension resizeWindow(JButton btn, int offsetX, int offsetY) {
        int width = btn.getSize().width + btn.getLocation().x + offsetX;
        int height = btn.getSize().height + btn.getLocation().y + offsetY;
        return new Dimension(width, height);
    }

    private void updateCredentials() {
        YamjTraktApp.getCredentials().setUsername(txtCredUsername.getText());
        YamjTraktApp.getCredentials().setPassword(new String(pwdCredPassword.getPassword()));
        YamjTraktApp.getCredentials().setApikey(txtCredApikey.getText());
    }

    private void validateCredentials() {
        updateCredentials();
        TraktTools.initialise(YamjTraktApp.getCredentials());
        lblCredResponse.setText(YamjTraktApp.getCredentials().getValidMessage());

        btnCredSave.setEnabled(YamjTraktApp.getCredentials().isValid());
    }

    private void updateButtons() {
        btnLoadFile.setEnabled(YamjTraktApp.isCompleteMoviesLoaded());
        btnProcess.setEnabled(YamjTraktApp.isCompleteMoviesProcessed() && YamjTraktApp.getCredentials().isValid());
        updateLibraryStats(YamjTraktApp.getLibrary().getStats());
    }

    /**
     * Set the progress window title
     * @param progressTitle
     */
    public void progessSetTitle(String progressTitle) {
        lblProgressTitle.setText(progressTitle);
    }

    /**
     * Add text to the progress window
     * @param progressText
     */
    public synchronized void progressAddText(String progressText) {
        taProgress.append(progressText);
        taProgress.append("\n");
        taProgress.setCaretPosition(taProgress.getDocument().getLength());
        LOG.debug(progressText);
    }

    /**
     * Clear the text from the progress window
     */
    public void progressClearText() {
        taProgress.setText("");
    }

    /**
     * Show or hide the progress window
     * @param visible
     */
    public void progressVisible(boolean visible) {
        fraProgress.setVisible(visible);
    }

    /**
     * Enable or disable the OK button
     * @param enabled
     */
    public void progressOk(boolean enabled) {
        btnProgressOK.setEnabled(enabled);
    }

    /**
     * Set the progress bar start and end
     * @param min
     * @param max
     */
    public void progressBarLimits(int min, int max) {
        pbProgress.setMinimum(min);
        pbProgress.setMaximum(max);
        pbProgress.setValue(min);
    }

    /**
     * Set the progress bar level
     * @param progress
     */
    public void progressBarProgress(int progress) {
        pbProgress.setValue(progress);
    }

    /**
     * Update the
     * @param updateText
     */
    public void updateLibraryStats(String updateText) {
        txtLibraryStats.setText(updateText);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
     * this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        dlgCredentials = new javax.swing.JDialog();
        lblCredUsername = new javax.swing.JLabel();
        lblCredPassword = new javax.swing.JLabel();
        lblCredApikey = new javax.swing.JLabel();
        txtCredUsername = new javax.swing.JTextField();
        txtCredApikey = new javax.swing.JTextField();
        pwdCredPassword = new javax.swing.JPasswordField();
        btnCredOk = new javax.swing.JButton();
        btnCredTest = new javax.swing.JButton();
        lblCredLogo = new javax.swing.JLabel();
        lblCredResponse = new javax.swing.JLabel();
        btnCredLoad = new javax.swing.JButton();
        btnCredSave = new javax.swing.JButton();
        lblCredApiUrl = new javax.swing.JLabel();
        fraProgress = new javax.swing.JFrame();
        pbProgress = new javax.swing.JProgressBar();
        btnProgressOK = new javax.swing.JButton();
        spProgress = new javax.swing.JScrollPane();
        taProgress = new javax.swing.JTextArea();
        lblProgressTitle = new javax.swing.JLabel();
        dlgAbout = new javax.swing.JDialog();
        lblAboutLogo = new javax.swing.JLabel();
        btnAboutOk = new javax.swing.JButton();
        lblAboutDescription = new javax.swing.JLabel();
        btnBrowse = new javax.swing.JButton();
        txtCompleteMoviesPath = new javax.swing.JTextField();
        btnLoadFile = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        txtLibraryStats = new javax.swing.JTextField();
        lbllogo = new javax.swing.JLabel();
        btnCredentials = new javax.swing.JButton();
        btnProcess = new javax.swing.JButton();
        spnProcessingThreads = new javax.swing.JSpinner();
        lblProcessingThreads = new javax.swing.JLabel();
        mnuBar = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuFileCredentials = new javax.swing.JMenuItem();
        sepMenuFile = new javax.swing.JPopupMenu.Separator();
        mnuFileExit = new javax.swing.JMenuItem();
        mnuSettings = new javax.swing.JMenu();
        mnuMarkAllWatched = new javax.swing.JCheckBoxMenuItem();
        mnuTrakt = new javax.swing.JMenu();
        mnuGetMovieList = new javax.swing.JMenuItem();
        mnuGetShowList = new javax.swing.JMenuItem();
        sepMenuTrakt = new javax.swing.JPopupMenu.Separator();
        mnuClearMovies = new javax.swing.JMenuItem();
        mnuClearShows = new javax.swing.JMenuItem();
        mnuHelp = new javax.swing.JMenu();
        mnuHelpAbout = new javax.swing.JMenuItem();
        mnuHelpDonate = new javax.swing.JMenuItem();

        dlgCredentials.setMinimumSize(new java.awt.Dimension(410, 360));
        dlgCredentials.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        dlgCredentials.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        dlgCredentials.setName("Credentials"); // NOI18N
        dlgCredentials.setResizable(false);

        lblCredUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCredUsername.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCredUsername.setText("Username");
        lblCredUsername.setMaximumSize(new java.awt.Dimension(60, 20));
        lblCredUsername.setMinimumSize(new java.awt.Dimension(60, 20));
        lblCredUsername.setPreferredSize(new java.awt.Dimension(60, 20));

        lblCredPassword.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCredPassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCredPassword.setText("Password");
        lblCredPassword.setMaximumSize(new java.awt.Dimension(60, 20));
        lblCredPassword.setMinimumSize(new java.awt.Dimension(60, 20));
        lblCredPassword.setPreferredSize(new java.awt.Dimension(60, 20));

        lblCredApikey.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCredApikey.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCredApikey.setText("API Key");
        lblCredApikey.setMaximumSize(new java.awt.Dimension(60, 20));
        lblCredApikey.setMinimumSize(new java.awt.Dimension(60, 20));
        lblCredApikey.setPreferredSize(new java.awt.Dimension(60, 20));

        txtCredUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCredUsername.setMinimumSize(new java.awt.Dimension(300, 25));
        txtCredUsername.setPreferredSize(new java.awt.Dimension(300, 25));

        txtCredApikey.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCredApikey.setMinimumSize(new java.awt.Dimension(300, 25));
        txtCredApikey.setPreferredSize(new java.awt.Dimension(300, 25));
        txtCredApikey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCredApikeyActionPerformed(evt);
            }
        });

        pwdCredPassword.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pwdCredPassword.setMinimumSize(new java.awt.Dimension(300, 25));
        pwdCredPassword.setPreferredSize(new java.awt.Dimension(300, 25));

        btnCredOk.setText("OK");
        btnCredOk.setMaximumSize(new java.awt.Dimension(75, 25));
        btnCredOk.setMinimumSize(new java.awt.Dimension(75, 25));
        btnCredOk.setPreferredSize(new java.awt.Dimension(75, 25));
        btnCredOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCredOkActionPerformed(evt);
            }
        });

        btnCredTest.setText("Validate");
        btnCredTest.setMaximumSize(new java.awt.Dimension(75, 25));
        btnCredTest.setMinimumSize(new java.awt.Dimension(75, 25));
        btnCredTest.setPreferredSize(new java.awt.Dimension(75, 25));
        btnCredTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCredTestActionPerformed(evt);
            }
        });

        lblCredLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/YAMJ.png"))); // NOI18N

        lblCredResponse.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCredResponse.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCredResponse.setText("Please validate the credentials");

        btnCredLoad.setText("Load");
        btnCredLoad.setMaximumSize(new java.awt.Dimension(75, 25));
        btnCredLoad.setMinimumSize(new java.awt.Dimension(75, 25));
        btnCredLoad.setPreferredSize(new java.awt.Dimension(75, 25));
        btnCredLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCredLoadActionPerformed(evt);
            }
        });

        btnCredSave.setText("Save");
        btnCredSave.setEnabled(false);
        btnCredSave.setMaximumSize(new java.awt.Dimension(75, 25));
        btnCredSave.setMinimumSize(new java.awt.Dimension(75, 25));
        btnCredSave.setPreferredSize(new java.awt.Dimension(75, 25));
        btnCredSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCredSaveActionPerformed(evt);
            }
        });

        lblCredApiUrl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCredApiUrl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCredApiUrl.setText("API Key can be found here: http://trakt.tv/settings/api");
        lblCredApiUrl.setToolTipText("Click to open in browser");
        lblCredApiUrl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblCredApiUrl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCredApiUrlMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dlgCredentialsLayout = new javax.swing.GroupLayout(dlgCredentials.getContentPane());
        dlgCredentials.getContentPane().setLayout(dlgCredentialsLayout);
        dlgCredentialsLayout.setHorizontalGroup(
            dlgCredentialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgCredentialsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlgCredentialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dlgCredentialsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblCredApiUrl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dlgCredentialsLayout.createSequentialGroup()
                        .addComponent(btnCredTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCredLoad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCredSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCredOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dlgCredentialsLayout.createSequentialGroup()
                        .addGroup(dlgCredentialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dlgCredentialsLayout.createSequentialGroup()
                                .addComponent(lblCredUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCredUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dlgCredentialsLayout.createSequentialGroup()
                                .addComponent(lblCredPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pwdCredPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dlgCredentialsLayout.createSequentialGroup()
                                .addComponent(lblCredApikey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCredApikey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblCredLogo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addComponent(lblCredResponse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        dlgCredentialsLayout.setVerticalGroup(
            dlgCredentialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgCredentialsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCredLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dlgCredentialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCredUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCredUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dlgCredentialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCredPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pwdCredPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dlgCredentialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCredApikey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCredApikey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCredApiUrl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCredResponse)
                .addGap(18, 18, 18)
                .addGroup(dlgCredentialsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCredTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCredLoad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCredSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCredOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        fraProgress.setAlwaysOnTop(true);
        fraProgress.setMinimumSize(new java.awt.Dimension(650, 300));

        pbProgress.setMaximumSize(new java.awt.Dimension(146, 25));
        pbProgress.setMinimumSize(new java.awt.Dimension(146, 25));
        pbProgress.setPreferredSize(new java.awt.Dimension(146, 25));

        btnProgressOK.setText("OK");
        btnProgressOK.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProgressOK.setMaximumSize(new java.awt.Dimension(75, 25));
        btnProgressOK.setMinimumSize(new java.awt.Dimension(75, 25));
        btnProgressOK.setPreferredSize(new java.awt.Dimension(75, 25));
        btnProgressOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProgressOKActionPerformed(evt);
            }
        });

        spProgress.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spProgress.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        spProgress.setAutoscrolls(true);

        taProgress.setEditable(false);
        taProgress.setColumns(132);
        taProgress.setLineWrap(true);
        taProgress.setRows(5000);
        taProgress.setTabSize(4);
        taProgress.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        taProgress.setFocusable(false);
        taProgress.setMinimumSize(new java.awt.Dimension(650, 94));
        spProgress.setViewportView(taProgress);

        lblProgressTitle.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblProgressTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProgressTitle.setText("Title");

        javax.swing.GroupLayout fraProgressLayout = new javax.swing.GroupLayout(fraProgress.getContentPane());
        fraProgress.getContentPane().setLayout(fraProgressLayout);
        fraProgressLayout.setHorizontalGroup(
            fraProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fraProgressLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fraProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fraProgressLayout.createSequentialGroup()
                        .addComponent(pbProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnProgressOK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(lblProgressTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        fraProgressLayout.setVerticalGroup(
            fraProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fraProgressLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProgressTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(fraProgressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProgressOK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pbProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        dlgAbout.setTitle("About");
        dlgAbout.setAlwaysOnTop(true);
        dlgAbout.setMinimumSize(new java.awt.Dimension(410, 410));
        dlgAbout.setModal(true);
        dlgAbout.setName("About Dialog"); // NOI18N
        dlgAbout.setResizable(false);

        lblAboutLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/YAMJ.png"))); // NOI18N

        btnAboutOk.setText("OK");
        btnAboutOk.setMaximumSize(new java.awt.Dimension(75, 25));
        btnAboutOk.setMinimumSize(new java.awt.Dimension(75, 25));
        btnAboutOk.setPreferredSize(new java.awt.Dimension(75, 25));
        btnAboutOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutOkActionPerformed(evt);
            }
        });

        lblAboutDescription.setText("About Text");

        javax.swing.GroupLayout dlgAboutLayout = new javax.swing.GroupLayout(dlgAbout.getContentPane());
        dlgAbout.getContentPane().setLayout(dlgAboutLayout);
        dlgAboutLayout.setHorizontalGroup(
            dlgAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgAboutLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlgAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dlgAboutLayout.createSequentialGroup()
                        .addComponent(lblAboutDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(dlgAboutLayout.createSequentialGroup()
                        .addGroup(dlgAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAboutOk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAboutLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))
                        .addGap(11, 11, 11))))
        );
        dlgAboutLayout.setVerticalGroup(
            dlgAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgAboutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAboutLogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAboutDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAboutOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("YAMJ Trakt.tv Populator");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(400, 300));
        setName("Main Window"); // NOI18N
        setResizable(false);

        btnBrowse.setText("Browse");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        txtCompleteMoviesPath.setEditable(false);
        txtCompleteMoviesPath.setText("Path to CompleteMovies.xml");

        btnLoadFile.setText("Process Complete Movies");
        btnLoadFile.setEnabled(false);
        btnLoadFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLoadFile.setMaximumSize(new java.awt.Dimension(250, 25));
        btnLoadFile.setMinimumSize(new java.awt.Dimension(250, 25));
        btnLoadFile.setPreferredSize(new java.awt.Dimension(250, 25));
        btnLoadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadFileActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.setMaximumSize(new java.awt.Dimension(100, 25));
        btnExit.setMinimumSize(new java.awt.Dimension(100, 25));
        btnExit.setPreferredSize(new java.awt.Dimension(100, 25));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        txtLibraryStats.setEditable(false);
        txtLibraryStats.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLibraryStats.setText("Select the 'CompleteMovies.xml' file");

        lbllogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbllogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/YAMJ.png"))); // NOI18N

        btnCredentials.setText("Credentials");
        btnCredentials.setMaximumSize(new java.awt.Dimension(100, 25));
        btnCredentials.setMinimumSize(new java.awt.Dimension(100, 25));
        btnCredentials.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCredentials.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCredentialsActionPerformed(evt);
            }
        });

        btnProcess.setText("Process on Trakt");
        btnProcess.setEnabled(false);
        btnProcess.setMaximumSize(new java.awt.Dimension(250, 25));
        btnProcess.setMinimumSize(new java.awt.Dimension(250, 25));
        btnProcess.setPreferredSize(new java.awt.Dimension(250, 25));
        btnProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcessActionPerformed(evt);
            }
        });

        lblProcessingThreads.setText("Processing Threads");

        mnuFile.setText("File");

        mnuFileCredentials.setText("Credentials");
        mnuFile.add(mnuFileCredentials);
        mnuFile.add(sepMenuFile);

        mnuFileExit.setText("Exit");
        mnuFile.add(mnuFileExit);

        mnuBar.add(mnuFile);

        mnuSettings.setText("Settings");

        mnuMarkAllWatched.setSelected(true);
        mnuMarkAllWatched.setText("Mark all videos watched");
        mnuMarkAllWatched.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMarkAllWatchedActionPerformed(evt);
            }
        });
        mnuSettings.add(mnuMarkAllWatched);

        mnuBar.add(mnuSettings);

        mnuTrakt.setText("Trakt");
        mnuTrakt.setActionCommand("mnuTrakt");

        mnuGetMovieList.setText("Get Movie List");
        mnuGetMovieList.setActionCommand("mnuGetMovieList");
        mnuGetMovieList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuGetMovieListActionPerformed(evt);
            }
        });
        mnuTrakt.add(mnuGetMovieList);

        mnuGetShowList.setText("Get Show List");
        mnuGetShowList.setActionCommand("mnuGetShowList");
        mnuGetShowList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuGetShowListActionPerformed(evt);
            }
        });
        mnuTrakt.add(mnuGetShowList);
        mnuTrakt.add(sepMenuTrakt);

        mnuClearMovies.setText("Clear ALL Movies");
        mnuClearMovies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuClearMoviesActionPerformed(evt);
            }
        });
        mnuTrakt.add(mnuClearMovies);

        mnuClearShows.setText("Clear ALL TV Shows");
        mnuClearShows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuClearShowsActionPerformed(evt);
            }
        });
        mnuTrakt.add(mnuClearShows);

        mnuBar.add(mnuTrakt);

        mnuHelp.setText("Help");

        mnuHelpAbout.setText("About");
        mnuHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHelpAboutActionPerformed(evt);
            }
        });
        mnuHelp.add(mnuHelpAbout);

        mnuHelpDonate.setText("Donate");
        mnuHelp.add(mnuHelpDonate);

        mnuBar.add(mnuHelp);

        setJMenuBar(mnuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblProcessingThreads)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spnProcessingThreads, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtLibraryStats)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCompleteMoviesPath, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBrowse))
                    .addComponent(lbllogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnProcess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLoadFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCredentials, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbllogo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCompleteMoviesPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLibraryStats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnProcessingThreads, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProcessingThreads))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoadFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCredentials, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnProcess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        fileChooser.setDialogTitle("Locate CompleteMovies.xml");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileFilterFilename("CompleteMovies.xml"));

        if (StringUtils.isBlank(YamjTraktApp.getLibrary().getPathCompleteMovie())) {
            fileChooser.setCurrentDirectory(new File(DEFAULT_DIRECTORY));
        } else {
            fileChooser.setCurrentDirectory(new File(YamjTraktApp.getLibrary().getPathCompleteMovie()));
        }

        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            YamjTraktApp.getLibrary().setPathCompleteMovie(file.getAbsolutePath());
            txtCompleteMoviesPath.setText(file.getAbsolutePath());
            txtLibraryStats.setText("Please process the file when ready");

            YamjTraktApp.setCompleteMoviesLoaded(Boolean.TRUE);
            YamjTraktApp.setCompleteMoviesProcessed(Boolean.FALSE);

            updateButtons();
        } else {
            LOG.info("File access cancelled by user.");
        }

    }//GEN-LAST:event_btnBrowseActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // Exit the application
        LOG.info("App exited normally");
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnLoadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadFileActionPerformed
        fraProgress.setVisible(Boolean.TRUE);
        ProgressProcessor.setProgressWindow(this, "Parsing CompleteMovies.xml");

        // Load the complete movies
        ProgressProcessor.parseCompleteMovies(YamjTraktApp.getLibrary(), YamjTraktApp.getLibrary().getPathCompleteMovie());

        YamjTraktApp.setCompleteMoviesProcessed(Boolean.TRUE);
        updateButtons();
    }//GEN-LAST:event_btnLoadFileActionPerformed

    private void btnCredTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCredTestActionPerformed
        validateCredentials();
    }//GEN-LAST:event_btnCredTestActionPerformed

    private void btnCredentialsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCredentialsActionPerformed
        dlgCredentials.setVisible(true);
    }//GEN-LAST:event_btnCredentialsActionPerformed

    private void btnCredOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCredOkActionPerformed
        validateCredentials();
        dlgCredentials.setVisible(false);
        updateButtons();
    }//GEN-LAST:event_btnCredOkActionPerformed

    private void btnCredLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCredLoadActionPerformed
        YamjTraktApp.loadCredentials(YamjTraktApp.getCredentials());

        txtCredUsername.setText(YamjTraktApp.getCredentials().getUsername());
        pwdCredPassword.setText(YamjTraktApp.getCredentials().getPassword());
        txtCredApikey.setText(YamjTraktApp.getCredentials().getApikey());

        validateCredentials();
    }//GEN-LAST:event_btnCredLoadActionPerformed

    private void btnCredSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCredSaveActionPerformed
        validateCredentials();
        boolean result = YamjTraktApp.saveCredentials(YamjTraktApp.getCredentials());
        if (result) {
            lblCredResponse.setText("Credentials saved OK");
        } else {
            lblCredResponse.setText("Failed to save the credentials");
        }

    }//GEN-LAST:event_btnCredSaveActionPerformed

    private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcessActionPerformed
        fraProgress.setVisible(Boolean.TRUE);
        btnProgressOK.setEnabled(Boolean.FALSE);
        ProgressProcessor.setProgressWindow(this, "Adding videos to Trakt.tv");

        int numberOfThreads = (Integer) spnProcessingThreads.getValue();
        ProgressProcessor.sendToTrakt(numberOfThreads);

        btnProgressOK.setEnabled(Boolean.TRUE);
        updateButtons();
    }//GEN-LAST:event_btnProcessActionPerformed

    private void btnProgressOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProgressOKActionPerformed
        updateButtons();
        fraProgress.setVisible(Boolean.FALSE);
        Thread.interrupted();
    }//GEN-LAST:event_btnProgressOKActionPerformed

    private void txtCredApikeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCredApikeyActionPerformed
    }//GEN-LAST:event_txtCredApikeyActionPerformed

    private void lblCredApiUrlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCredApiUrlMouseClicked
        try {
            URI uri = new URI("http://trakt.tv/settings/api");
            Desktop.getDesktop().browse(uri);
        } catch (IOException | URISyntaxException ex) {
            LOG.error("Failed to open URL - " + ex.getMessage());
        }
    }//GEN-LAST:event_lblCredApiUrlMouseClicked

    private void mnuMarkAllWatchedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuMarkAllWatchedActionPerformed
        YamjTraktApp.setMarkAllWatched(mnuMarkAllWatched.isSelected());
        LOG.debug("Setting MarkAllWatched to " + YamjTraktApp.isMarkAllWatched());
    }//GEN-LAST:event_mnuMarkAllWatchedActionPerformed

    private void btnAboutOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutOkActionPerformed
        dlgAbout.setVisible(false);
    }//GEN-LAST:event_btnAboutOkActionPerformed

    private void mnuHelpAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHelpAboutActionPerformed
        dlgAbout.setVisible(true);
    }//GEN-LAST:event_mnuHelpAboutActionPerformed

    private void mnuGetMovieListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuGetMovieListActionPerformed
        LOG.debug("Getting Movie List..");
        List<Movie> movies = TraktTools.getAllMovies(YamjTraktApp.getCredentials());

        int count = 1;
        StringBuilder sb;
        for (Movie m : movies) {
            sb = new StringBuilder("#");
            sb.append(count++).append(": ");
            sb.append("'").append(m.title).append("' ");
            sb.append("IMDB=").append(m.imdbId);
            sb.append(" TMDB=").append(m.tmdbId);
            LOG.debug(sb.toString());
        }

    }//GEN-LAST:event_mnuGetMovieListActionPerformed

    private void mnuGetShowListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuGetShowListActionPerformed
        LOG.debug("Getting Show List..");
        List<TvShow> shows = TraktTools.getWatchedShows(YamjTraktApp.getCredentials());

        int count = 1;
        StringBuilder sb;
        for (TvShow tv : shows) {
            sb = new StringBuilder("#");
            sb.append(count++).append(": ");
            sb.append("'").append(tv.title).append("' ");
            sb.append("IMDB=").append(tv.imdbId);
            sb.append(" TVDB=").append(tv.tvdbId);
            LOG.debug(sb.toString());
        }

    }//GEN-LAST:event_mnuGetShowListActionPerformed

    private void mnuClearMoviesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuClearMoviesActionPerformed
        List<Movie> movies = TraktTools.getAllMovies(YamjTraktApp.getCredentials());
        TraktTools.removeMovies(movies);
    }//GEN-LAST:event_mnuClearMoviesActionPerformed

    private void mnuClearShowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuClearShowsActionPerformed
        ProgressProcessor.setProgressWindow(this, "Removing TV Shows from Trakt");
        btnProgressOK.setEnabled(Boolean.FALSE);

        progressBarLimits(0, 1);
        ProgressProcessor.progressMessage("Loading shows from Trakt");
        List<TvShow> shows = TraktTools.getWatchedShows(YamjTraktApp.getCredentials());
        ProgressProcessor.progressMessage("Done loading shows");
        progressBarProgress(1);

        TraktTools.removeShows(this, shows);

        btnProgressOK.setEnabled(Boolean.TRUE);
    }//GEN-LAST:event_mnuClearShowsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void windowMain(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            LOG.warn("ClassNotFoundException: " + ex, ex);
        } catch (InstantiationException ex) {
            LOG.warn("InstantiationException: " + ex, ex);
        } catch (IllegalAccessException ex) {
            LOG.warn("IllegalAccessException: " + ex, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            LOG.warn("UnsupportedLookAndFeelException: " + ex, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAboutOk;
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnCredLoad;
    private javax.swing.JButton btnCredOk;
    private javax.swing.JButton btnCredSave;
    private javax.swing.JButton btnCredTest;
    private javax.swing.JButton btnCredentials;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLoadFile;
    private javax.swing.JButton btnProcess;
    private javax.swing.JButton btnProgressOK;
    private javax.swing.JDialog dlgAbout;
    private javax.swing.JDialog dlgCredentials;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JFrame fraProgress;
    private javax.swing.JLabel lblAboutDescription;
    private javax.swing.JLabel lblAboutLogo;
    private javax.swing.JLabel lblCredApiUrl;
    private javax.swing.JLabel lblCredApikey;
    private javax.swing.JLabel lblCredLogo;
    private javax.swing.JLabel lblCredPassword;
    private javax.swing.JLabel lblCredResponse;
    private javax.swing.JLabel lblCredUsername;
    private javax.swing.JLabel lblProcessingThreads;
    private javax.swing.JLabel lblProgressTitle;
    private javax.swing.JLabel lbllogo;
    private javax.swing.JMenuBar mnuBar;
    private javax.swing.JMenuItem mnuClearMovies;
    private javax.swing.JMenuItem mnuClearShows;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenuItem mnuFileCredentials;
    private javax.swing.JMenuItem mnuFileExit;
    private javax.swing.JMenuItem mnuGetMovieList;
    private javax.swing.JMenuItem mnuGetShowList;
    private javax.swing.JMenu mnuHelp;
    private javax.swing.JMenuItem mnuHelpAbout;
    private javax.swing.JMenuItem mnuHelpDonate;
    private javax.swing.JCheckBoxMenuItem mnuMarkAllWatched;
    private javax.swing.JMenu mnuSettings;
    private javax.swing.JMenu mnuTrakt;
    private javax.swing.JProgressBar pbProgress;
    private javax.swing.JPasswordField pwdCredPassword;
    private javax.swing.JPopupMenu.Separator sepMenuFile;
    private javax.swing.JPopupMenu.Separator sepMenuTrakt;
    private javax.swing.JScrollPane spProgress;
    private javax.swing.JSpinner spnProcessingThreads;
    private javax.swing.JTextArea taProgress;
    private javax.swing.JTextField txtCompleteMoviesPath;
    private javax.swing.JTextField txtCredApikey;
    private javax.swing.JTextField txtCredUsername;
    private javax.swing.JTextField txtLibraryStats;
    // End of variables declaration//GEN-END:variables
}
