package com.nitinsurana.csci571.hw9.beans;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
 * Created by coding_idiot on 21/11/16.
 */

@Data
public class CommitteeBean extends Bean {
    private String chamber, committee_id, name, parent_committee_id, phone, subcommittee;
    private String office = "N.A.";

    public String getChamber() {
        if (StringUtils.isNotBlank(chamber)) {
            return StringUtils.capitalize(chamber);
        }
        return chamber;
    }
//    "house"
//    committee_id
//    :
//            "HSBA20"
//    name
//    :
//            "Monetary Policy and Trade"
//    parent_committee_id
//    :
//            "HSBA"
//    phone
//    :
//            "(202) 225-7502"
//    subcommittee
//    :
//            true
}
