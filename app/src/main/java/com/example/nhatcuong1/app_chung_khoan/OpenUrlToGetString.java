package com.example.nhatcuong1.app_chung_khoan;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by nhatcuong1 on 11/17/15.
 */
public class OpenUrlToGetString {
    public OpenUrlToGetString() {
    }

    public String OpenHttpConnection(String urlString)
            throws IOException {
        InputStream in = null;
        int response = -1;
        String result=null;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            //if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            //}
        } catch (Exception ex) {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        StringBuilder bd=new StringBuilder();
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        while ((result=br.readLine())!=null){
            bd.append(result);
        }
        return bd.toString();
    }
}
