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
    List<News> newsContent;
    SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        news = findViewById(R.id.newsView);

        newsContent = new ArrayList<>();
        sql = this.openOrCreateDatabase("newsfeeddb", MODE_PRIVATE,  null);
        sql.execSQL("CREATE Table IF NOT EXISTS news (author VARCHAR, headline VARCHAR, description VARCHAR, published_at VARCHAR, location VARCHAR)");
        newsContent = retrieveDatabaseData();
        populateListView();
        news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NewsDetailsActivity.class);
                intent.putExtra(TABLE_COLUMNS.AUTHOR.label, newsContent.get(position).getAuthor());
                intent.putExtra(TABLE_COLUMNS.DESCRIPTION.label, newsContent.get(position).getDescription());
                intent.putExtra(TABLE_COLUMNS.HEADLINE.label, newsContent.get(position).getHeadline());
                intent.putExtra(TABLE_COLUMNS.PUBLISHED_AT.label, newsContent.get(position).getPublished_at());
                intent.putExtra(TABLE_COLUMNS.LOCATION.label, newsContent.get(position).getLocation());
                startActivity(intent);
            }
        });

    }

    private void populateListView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newsContent.stream().map(t -> t.getHeadline()).collect(Collectors.toList()));
        news.setAdapter(adapter);

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

    public void postNews(View view) {

        Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
        startActivity(intent);

    }

}