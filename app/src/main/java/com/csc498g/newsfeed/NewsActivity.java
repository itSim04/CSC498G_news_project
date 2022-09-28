package com.csc498g.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

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
        sql.execSQL("CREATE Table IF NOT EXISTS students (headline VARCHAR, description VARCHAR, published_at DATE, location VARCHAR)");


    }

    private void populateListView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newsContent);
        news.setAdapter(adapter);

    }

    private void retrieveDatabaseData() {

    }
}