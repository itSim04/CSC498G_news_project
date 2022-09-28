package com.csc498g.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    ListView news;
    ArrayList<String> newsContent;
    SQLiteDatabase sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        news = findViewById(R.id.newsView);
        newsContent = new ArrayList<>();
        sql = SQLiteDatabase.openOrCreateDatabase("newsfeeddb",  null);
        sql.execSQL("CREATE Table IF NOT EXISTS students (author VARCHAR, headline VARCHAR, description VARCHAR, published_at VARCHAR, location VARCHAR)");


    }

    private void populateListView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newsContent);
        news.setAdapter(adapter);

    }

    private void retrieveDatabaseData() {

        Cursor c = sql.rawQuery("Select * from students", null);
        int author_id = c.getColumnIndex("headline");
        int headline_id = c.getColumnIndex("headline");
        int description_id = c.getColumnIndex("headline");
        int published_at_id = c.getColumnIndex("headline");
        int location_id = c.getColumnIndex("headline");

        c.moveToFirst();

        while(c != null) {

            String author = c.getString(author_id);
            String headline = c.getString(headline_id);
            String description = c.getString(description_id);
            String published_at = c.getString(published_at_id);
            String location = c.getString(location_id);
            c.moveToNext();

        }

    }
}