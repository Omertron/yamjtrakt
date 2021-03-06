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
package com.omertron.yamjtrakttv.model;

import org.apache.commons.codec.digest.DigestUtils;

public class Credentials {

    private String username;
    private String password;
    private String apikey;
    private boolean valid;
    private String validMessage;

    public Credentials() {
        this.username = "";
        this.password = "";
        this.apikey = "";
        this.valid = Boolean.FALSE;
        this.validMessage = "";
    }

    public Credentials(String username, String password, String apikey) {
        this.username = username;
        this.password = password;
        this.apikey = apikey;
        this.valid = Boolean.FALSE;
        this.validMessage = "";
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // If the password is not 40 characters it's probably plain text, so encode it
        if (password.length() != 40) {
            this.password = DigestUtils.shaHex(password);
        } else {
            this.password = password;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getValidMessage() {
        return validMessage;
    }

    public void setValidMessage(String validMessage) {
        this.validMessage = validMessage;
    }
}
