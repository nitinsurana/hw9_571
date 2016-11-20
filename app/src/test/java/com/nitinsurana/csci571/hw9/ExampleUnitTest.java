package com.nitinsurana.csci571.hw9;

import com.google.gson.Gson;
import com.nitinsurana.csci571.hw9.beans.LegislatorsJson;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    private Date getUTCDate(){
        try{
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
            dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
            //Local time zone
            SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
            //Time in GMT
            Date d = dateFormatLocal.parse(dateFormatGmt.format(new Date()));
            return d;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Test
    public void getUTCDateTest() throws ParseException {
        Date d= getUTCDate();
        System.out.println(d);
    }

    public String makeHttpCall(String u) {
        String responseString = null;
        try {
            URL url = new URL(u);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                responseString = IOUtils.toString(conn.getInputStream());
            } else {
                responseString = "FAILED"; // See documentation for more info on response handling
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }

    @Test
    public void jsonParseTest() throws ExecutionException, InterruptedException {
        String url = "http://hw8-env.5mmquaicpi.us-west-2.elasticbeanstalk.com/api.php?submit=true&db=Legislators&keyword=&chamber=&page=undefined";
        String response = makeHttpCall(url);//new MyHttpRequest(null).execute(url).get();
        Gson gson = new Gson();
        LegislatorsJson lj = gson.fromJson(response, LegislatorsJson.class);
        System.out.println(lj.getCount());
//        System.out.println(lj.getResults().get(0).getBioguide_id());
//        System.out.println(lj.getResults().get(0).getParty());
    }
}