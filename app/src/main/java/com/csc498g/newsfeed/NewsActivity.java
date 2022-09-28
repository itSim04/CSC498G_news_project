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

    enum TABLE_COLUMNS {

        AUTHOR("author", 0), HEADLINE("headline", 1), DESCRIPTION("description", 2), PUBLISHED_AT("published_at", 3), LOCATION("location", 4);

        final String label;
        final int index;

        TABLE_COLUMNS(String label, int index) {
            this.label = label;
            this.index = index;
        }


    }

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
//        int author_id = c.getColumnIndex("headline");
//        int headline_id = c.getColumnIndex("description");
//        int description_id = c.getColumnIndex("description");
//        int published_at_id = c.getColumnIndex("published_at");
//        int location_id = c.getColumnIndex("location");

        c.moveToFirst();

        while(c != null) {

            HashMap<String, String> row = new HashMap<>();
            row.put(TABLE_COLUMNS.AUTHOR.label, c.getString(TABLE_COLUMNS.AUTHOR.index));
            row.put(TABLE_COLUMNS.HEADLINE.label, c.getString(TABLE_COLUMNS.HEADLINE.index));
            row.put(TABLE_COLUMNS.DESCRIPTION.label, c.getString(TABLE_COLUMNS.DESCRIPTION.index));
            row.put(TABLE_COLUMNS.PUBLISHED_AT.label, c.getString(TABLE_COLUMNS.PUBLISHED_AT.index));
            row.put(TABLE_COLUMNS.LOCATION.label, c.getString(TABLE_COLUMNS.LOCATION.index));
            c.moveToNext();
            result.add(row);

        }

    }
}