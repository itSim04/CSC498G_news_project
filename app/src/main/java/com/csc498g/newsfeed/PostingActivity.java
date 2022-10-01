package com.csc498g.newsfeed;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PostingActivity extends AppCompatActivity {

    String owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        owner = this.getSharedPreferences(Constants.PREFERENCES_NAME, MODE_PRIVATE).getString("username", "Anonymous");
        ((EditText)findViewById(R.id.authorEdit)).setText(owner);
        ((EditText)findViewById(R.id.dateEdit)).setText(new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Calendar.getInstance().getTime()));

    }

    public void postNews(View view) {

        News new_news = new News();
        new_news.setOwner(owner);
        new_news.setAuthor(((EditText)findViewById(R.id.authorEdit)).getText().toString());
        new_news.setHeadline(((EditText)findViewById(R.id.headingEdit)).getText().toString());
        new_news.setDescription(((EditText)findViewById(R.id.descriptionEdit)).getText().toString());
        new_news.setPublished_at(((EditText)findViewById(R.id.dateEdit)).getText().toString());
        new_news.setLocation(((EditText)findViewById(R.id.locationEdit)).getText().toString());

        SQLiteDatabase sql = this.openOrCreateDatabase(Constants.DATABASE_NAME, MODE_PRIVATE, null);
        sql.execSQL("INSERT INTO news(author, owner, headline, description, published_at, location) VALUES (?, ?, ?, ?, ?, ?)", new String[]{
                new_news.getAuthor(),
                new_news.getOwner(),
                new_news.getHeadline(),
                new_news.getDescription(),
                new_news.getPublished_at(),
                new_news.getLocation()
        });
        Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
        startActivity(intent);

    }

}