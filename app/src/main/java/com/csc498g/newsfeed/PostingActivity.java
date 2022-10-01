package com.csc498g.newsfeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PostingActivity extends AppCompatActivity {

    News news;
    String owner;
    SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        owner = this.getSharedPreferences("com.csc498g.newsfeed", MODE_PRIVATE).getString("username", "Anonymous");
        ((EditText)findViewById(R.id.authorEdit)).setText(owner);
        ((EditText)findViewById(R.id.dateEdit)).setText(new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));

    }

    public void postNews(View view) {

        news = retrieveData();
        sql = this.openOrCreateDatabase("newsfeeddb", MODE_PRIVATE, null);
        sql.execSQL("INSERT INTO news(author, owner, headline, description, published_at, location) VALUES (?, ?, ?, ?, ?, ?)", new String[]{
                news.getAuthor(),
                news.getOwner(),
                news.getHeadline(),
                news.getDescription(),
                news.getPublished_at(),
                news.getLocation()
        });
        Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
        startActivity(intent);

    }

    private News retrieveData() {

        News new_news = new News();
        new_news.setOwner(owner);
        new_news.setAuthor(((EditText)findViewById(R.id.authorEdit)).getText().toString());
        new_news.setHeadline(((EditText)findViewById(R.id.headlingEdit)).getText().toString());
        new_news.setDescription(((EditText)findViewById(R.id.descriptionEdit)).getText().toString());
        new_news.setPublished_at(((EditText)findViewById(R.id.dateEdit)).getText().toString());
        new_news.setLocation(((EditText)findViewById(R.id.locationEdit)).getText().toString());
        return new_news;

    }
}