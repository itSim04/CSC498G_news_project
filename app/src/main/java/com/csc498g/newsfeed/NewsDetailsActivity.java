package com.csc498g.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NewsDetailsActivity extends AppCompatActivity {

    News news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        getIntent().getStringExtra("news");
    }
}