
/*
 *  Main activity of Book Trade app
 *  Handles search and results
 *
 *  Author: Sean Brown, Laura Berry, Andrew Lata
 */

package com.example.booktradeapp;

import android.util.Pair;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {

    private ListView listView;
    private SearchView searchView;
    private Pair<ArrayList<String>, ArrayList<String> > results;
    private ArrayList<String> info;
    private ArrayList<String> bookIDs;
    private Context thisContext = this;
    private AsyncTask<String, Void, Pair<ArrayList<String>, ArrayList<String> > > activity;
    private BookActivity book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView1);
        searchView = (SearchView)findViewById(R.id.searchView1);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                activity = new SearchActivity().execute(query);

                try {
                    results = activity.get();
                    info = results.first;
                    bookIDs = results.second;
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
                catch(ExecutionException e) {
                    e.printStackTrace();
                    return false;
                }
                catch(NullPointerException e) {
                    e.printStackTrace();
                    return false;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(thisContext, R.layout.list_text_view, info);
                listView.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                onQueryTextSubmit(newText);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String bookID = bookIDs.get(position);

                Intent intent = new Intent(thisContext, BookActivity.class);
                intent.putExtra("com.example.booktradeapp.MESSAGE", bookID);

                startActivity(intent);
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
