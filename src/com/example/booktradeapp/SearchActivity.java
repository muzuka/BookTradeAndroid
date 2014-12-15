package com.example.booktradeapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.app.SearchManager;
import android.os.AsyncTask;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;

/**
 * Created by seanbrown on 2014-12-11.
 */
public class SearchActivity extends Activity {

    private String results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            results = search(query);
        }
    }

    protected String search(String q) {
        try {
            String link = new String();
            String data = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(q, "UTF-8");

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
