package com.nitinsurana.csci571.hw9;

import com.nitinsurana.csci571.hw9.beans.BillBean;
import com.nitinsurana.csci571.hw9.beans.CommitteeBean;
import com.nitinsurana.csci571.hw9.beans.LegislatorBean;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * Created by coding_idiot on 20/11/16.
 */

public class FavoriteDAO {

    public static Map<String, LegislatorBean> legislatorBeanMap = new HashMap<>();
    public static Map<String, BillBean> billBeanMap = new HashMap<>();
    public static Map<String, CommitteeBean> committeeBeanMap = new HashMap<>();
}
