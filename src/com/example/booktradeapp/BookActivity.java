
/*
 * Book Activity class
 * Views book page
 *
 * Author: Sean Brown, Laura Berry, Andrew Lata
 */

package com.example.booktradeapp;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class BookActivity extends Activity {

    private ImageView image;
    private BufferedWriter writer;
    private File imageFile;
    private TextView bookData;
    private AsyncTask<String, Void, ArrayList<String> > activity;
    private ArrayList<String> bookInformation;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        image = (ImageView)findViewById(R.id.image);
        bookData = (TextView)findViewById(R.id.bookData);
        intent = getIntent();

        String bookID = intent.getStringExtra("com.example.booktradeapp.MESSAGE");

        activity = new GetBookActivity().execute(bookID);

        try {
            bookInformation = activity.get();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        catch(ExecutionException e) {
            e.printStackTrace();
        }
        catch(NullPointerException e) {
            e.printStackTrace();
            return;
        }

        String textData = "";

        if(bookInformation.size() != 5) {
            return;
        }

        if(!bookInformation.get(0).equals("None")) {
            try {
                imageFile = new File("bookPic.jpeg");
                writer = new BufferedWriter(new FileWriter(imageFile));

                writer.write(bookInformation.get(0));

                image.setImageBitmap(BitmapFactory.decodeFile("bookPic.jpeg"));
            }
            catch(IOException e) {
                e.printStackTrace();
            }

        }

        for(int i = 1; i < bookInformation.size(); i++) {
            textData += bookInformation.get(i) + "\n";
        }

        bookData.setText(textData);

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
