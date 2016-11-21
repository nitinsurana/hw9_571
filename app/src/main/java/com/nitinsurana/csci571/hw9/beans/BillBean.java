package com.nitinsurana.csci571.hw9.beans;

import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import lombok.Data;

/**
 * Created by coding_idiot on 20/11/16.
 */

@Data
public class BillBean extends Bean implements Serializable {
    private String bill_type, chamber, sponsor_id, bill_id, official_title, short_title;
    private Date introduced_on;

    private SponsorBean sponsor;

    private HistoryBean history;

    private Map<String, Object> last_version, urls;

    public void setIntroduced_on(String d) throws ParseException {
        this.introduced_on = DateUtils.parseDate(d, "YYYY-MM-DD");
    }
//    "sres613-114"
//    bill_type
//    :
//            "sres"
//    chamber
//    :
//            "senate"
//    committee_ids
//    :
//            ["SSHR"]
//    congress
//    :
//            114
//    cosponsors_count
//    :
//            2
//    enacted_as
//    :
//            null
//    history
//    :
//    {active: false, awaiting_signature: false, enacted: false, vetoed: false}
//    introduced_on
//    :
//            "2016-09-29"
//    last_action_at
//    :
//            "2016-09-29"
//    last_version
//    :
//    {version_code: "is", issued_on: "2016-09-29", version_name: "Introduced in Senate",…}
//    last_version_on
//    :
//            "2016-09-29"
//    last_vote_at
//    :
//            null
//    number
//    :
//            613
//    official_title
//    :
//            "A resolution recognizing the 50th anniversary of North Mississippi Rural Legal Services in Oxford, Mississippi."
//    popular_title
//    :
//            null
//    related_bill_ids
//    :
//            []
//    short_title
//    :
//            null
//    sponsor
//    :
//    {first_name: "Roger", nickname: null, last_name: "Wicker", middle_name: "F.", name_suffix: null,…}
//    sponsor_id
//    :
//            "W000437"
//    urls
//    :
//    {congress: "http://beta.congress.gov/bill/114th/senate-resolution/613",…}
//    withdrawn_cosponsors_count
//    :
//            0
}
