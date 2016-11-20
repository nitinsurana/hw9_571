package com.nitinsurana.csci571.hw9.beans;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by coding_idiot on 19/11/16.
 */

@Data
public class LegislatorBean implements Serializable {
    private String bioguide_id, chamber, first_name, last_name;
    private String party;
    private String state_name, district;

    public String getFullname() {
        return last_name + ", " + first_name;
    }

    public String getImageUrl() {
        return "https://theunitedstates.io/images/congress/original/" + bioguide_id + ".jpg";
    }

    public String getDistrict() {
        if (StringUtils.isNotBlank(district)) {
            return "District " + district;
        }
        return "";
    }
//    bioguide_id
//    :
//            "D000626"
//    birthday
//    :
//            "1970-03-01"
//    chamber
//    :
//            "house"
//    contact_form
//    :
//            null
//    crp_id
//    :
//            "N00038767"
//    district
//    :
//            8
//    fax
//    :
//            null
//    fec_ids
//    :
//            ["H6OH08315"]
//    first_name
//    :
//            "Warren"
//    gender
//    :
//            "M"
//    govtrack_id
//    :
//            "412675"
//    in_office
//    :
//            true
//    last_name
//    :
//            "Davidson"
//    leadership_role
//    :
//            null
//    middle_name
//    :
//            null
//    name_suffix
//    :
//            null
//    nickname
//    :
//            null
//    oc_email
//    :
//            null
//    ocd_id
//    :
//            "ocd-division/country:us/state:oh/cd:8"
//    office
//    :
//            "1011 Longworth House Office Building"
//    party
//    :
//            "R"
//    phone
//    :
//            "202-225-6205"
//    state
//    :
//            "OH"
//    state_name
//    :
//            "Ohio"
//    term_end
//    :
//            "2017-01-03"
//    term_start
//    :
//            "2016-06-09"
//    thomas_id
//    :
//            "02296"
//    title
//    :
//            "Rep"
//    votesmart_id
//    :
//            166760
//    website
//    :
//            null
}
