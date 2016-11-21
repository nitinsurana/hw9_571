package com.nitinsurana.csci571.hw9.beans;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by coding_idiot on 20/11/16.
 */

@Data
public class SponsorBean implements Serializable{

    private String first_name, last_name, title;

    public String getFullname() {
        return title + ". " + last_name + ", " + first_name;
    }
}
