package com.csc498g.newsfeed;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PostingActivity extends AppCompatActivity {

    News news;
    SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);
        news = retrieveData();


    }

    public void postNews(View view) {

        sql = this.openOrCreateDatabase("newsfeeddb", MODE_PRIVATE, null);
        sql.execSQL("INSERT INTO students(author, headline, description, published_at, location) VALUES (?, ?, ?, ?, ?)", new String[]{
                news.getAuthor(),
                news.getHeadline(),
                news.getDescription(),
                news.getPublished_at(),
                news.getLocation()
        });

    }

    private News retrieveData() {

        News news = new News();
        news.setAuthor(((EditText)findViewById(R.id.authorEdit)).getText().toString());
        news.setHeadline(((EditText)findViewById(R.id.headlingEdit)).getText().toString());
        news.setDescription(((EditText)findViewById(R.id.descriptionEdit)).getText().toString());
        news.setPublished_at(((EditText)findViewById(R.id.dateEdit)).getText().toString());
        news.setLocation(((EditText)findViewById(R.id.locationEdit)).getText().toString());
        return news;

    }
}