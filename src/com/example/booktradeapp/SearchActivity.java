package com.example.booktradeapp;

import android.os.AsyncTask;

/**
 * Created by seanbrown on 2014-12-11.
 */
public class SearchActivity extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... arg0) {
        try {
            String query = arg0[0];

            return "";
        }
        catch(Exception e) {
            return new String(e.getMessage());
        }
    }

}
