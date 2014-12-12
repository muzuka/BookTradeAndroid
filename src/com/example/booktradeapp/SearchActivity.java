package com.example.booktradeapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import android.os.AsyncTask;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by seanbrown on 2014-12-11.
 */
public class SearchActivity extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... arg0) {
        try {
            String query = arg0[0];
            String link = new String();
            String data = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(query, "UTF-8");

            URL address = new URL(link);
            HttpClient client = new DefaultHttpClient();

            URLConnection connection = address.openConnection();
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String output = null;
            String line = null;

            while((line = reader.readLine()) != null) {
                output = output + line;
            }

            return output;
        }
        catch(Exception e) {
            return e.getMessage();
        }
    }

}
