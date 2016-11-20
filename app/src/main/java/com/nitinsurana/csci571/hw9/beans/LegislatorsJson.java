package com.nitinsurana.csci571.hw9.beans;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * Created by coding_idiot on 19/11/16.
 */

@Data
public class LegislatorsJson {

    private int count;
    private Map<String, Object> page;

    @Getter(AccessLevel.NONE)
    private ArrayList<LegislatorBean> results;

    public ArrayList<LegislatorBean> byState() {
        Collections.sort(results, new Comparator<LegislatorBean>() {
            @Override
            public int compare(LegislatorBean o1, LegislatorBean o2) {
                if (o1.getState_name().equals(o2.getState_name())) {
                    return o1.getLast_name().compareTo(o2.getLast_name());
                } else {
                    return o1.getState_name().compareTo(o2.getState_name());
                }
            }
        });
        return results;
    }

    public ArrayList<LegislatorBean> byHouse() {
        ArrayList<LegislatorBean> hlist = new ArrayList<>();
        for (LegislatorBean lb : results) {
            if (lb.getChamber().equalsIgnoreCase("house")) {
                hlist.add(lb);
            }
        }
        return hlist;
    }

    public ArrayList<LegislatorBean> bySenate() {
        ArrayList<LegislatorBean> lst = new ArrayList<>();
        for (LegislatorBean lb : results) {
            if (lb.getChamber().equalsIgnoreCase("senate")) {
                lst.add(lb);
            }
        }
        return lst;
    }
}
