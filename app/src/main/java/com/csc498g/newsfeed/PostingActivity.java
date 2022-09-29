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
    SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        String author_name = this.getSharedPreferences("com.csc498g.newsfeed", MODE_PRIVATE).getString("username", "Anonymous");
        ((EditText)findViewById(R.id.authorEdit)).setText(author_name);
        ((EditText)findViewById(R.id.dateEdit)).setText(new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));

    }

    public void postNews(View view) {

        news = retrieveData();
        sql = this.openOrCreateDatabase("newsfeeddb", MODE_PRIVATE, null);
        sql.execSQL("INSERT INTO news(author, headline, description, published_at, location) VALUES (?, ?, ?, ?, ?)", new String[]{
                news.getAuthor(),
                news.getHeadline(),
                news.getDescription(),
                news.getPublished_at(),
                news.getLocation()
        });
        Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
        startActivity(intent);

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