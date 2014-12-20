
package com.example.booktradeapp;

import android.os.AsyncTask;
import android.content.Context;
import android.util.Pair;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchActivity extends AsyncTask<String, Void, Pair<ArrayList<String>, ArrayList<String> > > {

    private String link = new String("http://10.0.2.2/BookTrade/appSearchPage.php");
    private String data;
    private Context context;
    private URL address;
    private URLConnection connection;
    private HttpClient client;
    private BufferedReader reader;
    private OutputStreamWriter writer;
    private Pair<ArrayList<String>, ArrayList<String> > output;

    protected SearchActivity(Context context) {
        this.context = context;
    }

    @Override
    protected Pair<ArrayList<String>, ArrayList<String> > doInBackground(String... args) {
        try {
            data = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(args[0], "UTF-8");

            address = new URL(link);
            client = new DefaultHttpClient();

            connection = address.openConnection();
            connection.setDoOutput(true);

            writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            output = new Pair<ArrayList<String>, ArrayList<String> >(new ArrayList<String>(), new ArrayList<String>());
            String[] rows;
            String[] rowElements;
            String line = null;

            while((line = reader.readLine()) != null) {
                if(line.length() == 0) {
                    continue;
                }
                if(line.charAt(0) == ':' && line.charAt(1) == ':') {
                    rows = line.split("::");
                    for(int i = 0; i < rows.length; i++) {
                        if(rows[i].length() != 0) {
                            rowElements = rows[i].split(":");

                            output.first.add(rowElements[0] + " " + rowElements[1] + " " + rowElements[2]);
                            output.second.add(rowElements[3]);
                        }
                    }
                    break;
                }
            }

            reader.close();
            writer.close();

            return output;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}