package com.csc498g.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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

        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        Cursor c = sql.rawQuery("Select * from students", null);
        int author_id = c.getColumnIndex("headline");
        int headline_id = c.getColumnIndex("description");
        int description_id = c.getColumnIndex("description");
        int published_at_id = c.getColumnIndex("published_at");
        int location_id = c.getColumnIndex("location");

        c.moveToFirst();

        while(c != null) {

            HashMap<String, String> row = new HashMap<>();
            row.put("author", c.getString(author_id));
            row.put("headline", c.getString(headline_id));
            row.put("description", c.getString(description_id));
            row.put("published_at", c.getString(published_at_id));
            row.put("location", c.getString(location_id));
            c.moveToNext();
            result.add(row);

        }

    }
}