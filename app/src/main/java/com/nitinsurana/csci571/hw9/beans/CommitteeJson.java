package com.nitinsurana.csci571.hw9.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.Data;

/**
 * Created by coding_idiot on 21/11/16.
 */

@Data
public class CommitteeJson implements Serializable {
    private List<CommitteeBean> results;

    public ArrayList<CommitteeBean> byHouse() {
        ArrayList<CommitteeBean> lst = new ArrayList<CommitteeBean>();
        for (CommitteeBean bean : results) {
            if (bean.getChamber().equalsIgnoreCase("house")) {
                lst.add(bean);
            }
        }
        return lst;
    }

    public ArrayList<CommitteeBean> bySenate() {
        ArrayList<CommitteeBean> lst = new ArrayList<CommitteeBean>();
        for (CommitteeBean bean : results) {
            if (bean.getChamber().equalsIgnoreCase("senate")) {
                lst.add(bean);
            }
        }
        return lst;
    }

    public ArrayList<CommitteeBean> byJoint() {
        ArrayList<CommitteeBean> lst = new ArrayList<CommitteeBean>();
        for (CommitteeBean bean : results) {
            if (bean.getChamber().equalsIgnoreCase("joint")) {
                lst.add(bean);
            }
        }
        return lst;
    }
}
