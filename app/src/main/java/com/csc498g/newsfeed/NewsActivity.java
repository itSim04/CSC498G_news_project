package com.csc498g.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class NewsActivity extends AppCompatActivity {

    ListView news;
    List<String> newsContent;
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
        sql.execSQL("CREATE Table IF NOT EXISTS news (author VARCHAR, headline VARCHAR, description VARCHAR, published_at VARCHAR, location VARCHAR)");
        newsContent = retrieveDatabaseData().stream().map(t -> t.getAuthor()).collect(Collectors.toList());
        populateListView();

    }

    private void populateListView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newsContent);
        news.setAdapter(adapter);
        news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NewsDetailsActivity.class);
                intent.putExtra("news", newsContent.get(position));
            }
        });

    }

    private ArrayList<News> retrieveDatabaseData() {

        ArrayList<News> result = new ArrayList<>();
        Cursor c = sql.rawQuery("Select * from news", null);

        c.moveToFirst();

        while(!c.isAfterLast()) {

            News news = new News();
            news.setAuthor(c.getString(TABLE_COLUMNS.AUTHOR.index));
            news.setHeadline(c.getString(TABLE_COLUMNS.HEADLINE.index));
            news.setDescription(c.getString(TABLE_COLUMNS.DESCRIPTION.index));
            news.setPublished_at(c.getString(TABLE_COLUMNS.PUBLISHED_AT.index));
            news.setLocation(c.getString(TABLE_COLUMNS.LOCATION.index));
            c.moveToNext();
            result.add(news);

        }
        return result;

    }
}