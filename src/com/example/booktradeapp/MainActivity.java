package com.example.booktradeapp;

import android.util.Pair;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {

    private ListView listView;
    private SearchView searchView;
    private ArrayList<String> results;
    private Context thisContext = this;
    private AsyncTask<String, Void, Pair<ArrayList<String>, ArrayList<String> > > activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView1);
        searchView = (SearchView)findViewById(R.id.searchView1);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                activity = new SearchActivity(thisContext).execute(query);

                try {
                    results = activity.get().first;
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
                catch(ExecutionException e) {
                    e.printStackTrace();
                    return false;
                }

                if(results == null) {
                    results = new ArrayList<String>();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(thisContext, R.layout.list_text_view, results);
                listView.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                onQueryTextSubmit(newText);
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
