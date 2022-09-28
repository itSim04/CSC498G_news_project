package com.csc498g.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
        sql = this.openOrCreateDatabase("newsfeeddb", MODE_PRIVATE,  null);
        sql.execSQL("CREATE Table IF NOT EXISTS students (author VARCHAR, headline VARCHAR, description VARCHAR, published_at VARCHAR, location VARCHAR)");

        Log.i("Database", retrieveDatabaseData().toString());


    }

    private void populateListView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newsContent);
        news.setAdapter(adapter);

    }

    private ArrayList<HashMap<String, String>> retrieveDatabaseData() {

        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        Cursor c = sql.rawQuery("Select * from students", null);

        c.moveToFirst();

        while(!c.isAfterLast()) {

            HashMap<String, String> row = new HashMap<>();
            row.put(TABLE_COLUMNS.AUTHOR.label, c.getString(TABLE_COLUMNS.AUTHOR.index));
            row.put(TABLE_COLUMNS.HEADLINE.label, c.getString(TABLE_COLUMNS.HEADLINE.index));
            row.put(TABLE_COLUMNS.DESCRIPTION.label, c.getString(TABLE_COLUMNS.DESCRIPTION.index));
            row.put(TABLE_COLUMNS.PUBLISHED_AT.label, c.getString(TABLE_COLUMNS.PUBLISHED_AT.index));
            row.put(TABLE_COLUMNS.LOCATION.label, c.getString(TABLE_COLUMNS.LOCATION.index));
            c.moveToNext();
            result.add(row);

        }
        return result;

    }
}