package com.csc498g.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {

    News news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updating);

        news = new News();
        news.setAuthor(getIntent().getStringExtra(TABLE_COLUMNS.AUTHOR.label));
        news.setOwner(getIntent().getStringExtra(TABLE_COLUMNS.OWNER.label));
        news.setHeadline(getIntent().getStringExtra(TABLE_COLUMNS.HEADLINE.label));
        news.setDescription(getIntent().getStringExtra(TABLE_COLUMNS.DESCRIPTION.label));
        news.setPublished_at(getIntent().getStringExtra(TABLE_COLUMNS.PUBLISHED_AT.label));
        news.setLocation(getIntent().getStringExtra(TABLE_COLUMNS.LOCATION.label));

        populateData();

    }

    private void populateData() {

        ((EditText)findViewById(R.id.authorEdit)).setText(news.getAuthor());
        ((EditText)findViewById(R.id.headlingEdit)).setText(news.getHeadline());
        ((EditText)findViewById(R.id.descriptionEdit)).setText(news.getDescription());
        ((EditText)findViewById(R.id.dateEdit)).setText(news.getPublished_at());
        ((EditText)findViewById(R.id.locationEdit)).setText(news.getLocation());

    }

}