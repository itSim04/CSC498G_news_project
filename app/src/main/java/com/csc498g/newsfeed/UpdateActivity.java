package com.csc498g.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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

    public void updateNews(View view) {
        News new_news = retrieveData();
        SQLiteDatabase sql = this.openOrCreateDatabase("newsfeeddb", MODE_PRIVATE, null);
        sql.execSQL("UPDATE news SET author = ?, owner = ?, headline = ?, description = ?, published_at = ?, location = ? WHERE owner = ? AND headline = ? ", new String[]{
                new_news.getAuthor(),
                new_news.getOwner(),
                new_news.getHeadline(),
                new_news.getDescription(),
                new_news.getPublished_at(),
                new_news.getLocation(),
                news.getOwner(),
                news.getHeadline()
        });
        Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
        startActivity(intent);
    }

    private News retrieveData() {

        News new_news = new News();
        new_news.setOwner(news.getOwner());
        new_news.setAuthor(((EditText)findViewById(R.id.authorEdit)).getText().toString());
        new_news.setHeadline(((EditText)findViewById(R.id.headlingEdit)).getText().toString());
        new_news.setDescription(((EditText)findViewById(R.id.descriptionEdit)).getText().toString());
        new_news.setPublished_at(((EditText)findViewById(R.id.dateEdit)).getText().toString());
        new_news.setLocation(((EditText)findViewById(R.id.locationEdit)).getText().toString());
        return new_news;

    }

    private void populateData() {

        ((EditText)findViewById(R.id.authorEdit)).setText(news.getAuthor());
        ((EditText)findViewById(R.id.headlingEdit)).setText(news.getHeadline());
        ((EditText)findViewById(R.id.descriptionEdit)).setText(news.getDescription());
        ((EditText)findViewById(R.id.dateEdit)).setText(news.getPublished_at());
        ((EditText)findViewById(R.id.locationEdit)).setText(news.getLocation());

    }

}