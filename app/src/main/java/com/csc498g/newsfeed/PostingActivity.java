package com.csc498g.newsfeed;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PostingActivity extends AppCompatActivity {

    News news;
    SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);
        news = new News();

        sql = this.openOrCreateDatabase("newsfeeddb", MODE_PRIVATE, null);
        sql.execSQL("INSERT INTO students(author, headline, description, published_at, location) VALUES (?, ?, ?, ?, ?)", new String[]{
                news.getAuthor(),
                news.getHeadline(),
                news.getDescription(),
                news.getPublished_at(),
                news.getLocation()
        });
    }
}