package com.nitinsurana.csci571.hw9;

import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyHttpRequest extends AsyncTask<String, String, String> {

    private MyCallback callback;

    public MyHttpRequest(MyCallback c) {
        this.callback = c;
    }

    @Override
    protected String doInBackground(String... uri) {
        String responseString = null;
        try {
            URL url = new URL(uri[0]);
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

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (callback != null) {
            callback.done(result);
        }
    }
}

//http://hw8-env.5mmquaicpi.us-west-2.elasticbeanstalk.com/api.php?submit=true&db=Legislators&keyword=&chamber=&page=undefined