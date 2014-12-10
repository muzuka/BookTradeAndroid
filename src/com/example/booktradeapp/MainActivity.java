package com.example.booktradeapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;


public class MainActivity extends Activity {

    private SearchView search;
    private TextView   textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = (SearchView)findViewById(R.id.searchView1);
        search.setQueryHint("search");

        textView = (TextView)findViewById(R.id.textView1);
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
    public void searchBar()
    {
        CharSequence searchTerm = search.getQuery();
    		//needs to put a search bar on the screen
    		//needs to take user input
    		// call thing that returns books
    }
    public void searchResults()
    {
    	//takes input from searchBar

    	//returns results, prefribly in a grid format.
    }
}
