
/*
 * Activity for retrieving book information from webpage
 *
 * Author: Sean Brown, Laura Berry, Andrew Lata
 */

package com.example.booktradeapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import android.os.AsyncTask;

public class GetBookActivity extends AsyncTask<String, Void, ArrayList<String>> {

    @Override
    protected ArrayList<String> doInBackground(String... args) {

        String link = "http://10.0.2.2/BookTrade/appSearchPage.php";
        String data;

        URL address;
        URLConnection connection;
        BufferedReader reader;
        OutputStreamWriter writer;
        ArrayList<String> output;

        try {
            data = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(args[0], "UTF-8");

            address = new URL(link);

            connection = address.openConnection();
            connection.setDoOutput(true);

            writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            output = new ArrayList<String>();
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return output;
    }
}
