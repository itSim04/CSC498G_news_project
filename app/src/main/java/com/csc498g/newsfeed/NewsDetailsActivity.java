package com.csc498g.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NewsDetailsActivity extends AppCompatActivity {

    News news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        news = new News();
        news.setAuthor(getIntent().getStringExtra(TABLE_COLUMNS.AUTHOR.label));
        news.setHeadline(getIntent().getStringExtra(TABLE_COLUMNS.HEADLINE.label));
        news.setDescription(getIntent().getStringExtra(TABLE_COLUMNS.DESCRIPTION.label));
        news.setPublished_at(getIntent().getStringExtra(TABLE_COLUMNS.PUBLISHED_AT.label));
        news.setLocation(getIntent().getStringExtra(TABLE_COLUMNS.LOCATION.label));
        populateDisplay();

    }

    private void populateDisplay() {

        ((TextView)findViewById(R.id.headlingEdit)).setText(news.getHeadline());
        ((TextView)findViewById(R.id.descriptionEdit)).setText(news.getDescription());
        ((TextView)findViewById(R.id.detailsText)).setText(String.format("In %s\nPublished By %s\nOn %s", news.getLocation(), news.getAuthor(), news.getPublished_at()));

    }
}