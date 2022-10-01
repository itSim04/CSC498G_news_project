package com.csc498g.newsfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NewsActivity extends AppCompatActivity {

    ListView news_view;
    List<News> news_content;
    SQLiteDatabase sql;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        sql = this.openOrCreateDatabase("newsfeeddb", MODE_PRIVATE,  null);
        sql.execSQL("CREATE Table IF NOT EXISTS news (author VARCHAR, owner VARCHAR, headline VARCHAR, description VARCHAR, published_at VARCHAR, location VARCHAR)");

        username = this.getSharedPreferences("com.csc498g.newsfeed", MODE_PRIVATE).getString("username", "Anonymous");

        news_content = retrieveDatabaseData();

        news_view = findViewById(R.id.newsView);
        news_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                News news = news_content.get(position);

                if(news.getOwner().equals(username)) {

                    displayMenu(view, position);

                } else {

                    openDetails(position);

                }


            }
        });

        populateListView();

    }

    private void displayMenu(View view, int position) {

        PopupMenu popup = new PopupMenu(getApplicationContext(), view);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.viewDetailsItem:

                        openDetails(position);
                        return true;
                    case R.id.editItem:

                        editEntry();
                        return true;
                    case R.id.deleteItem:

                        deleteEntry();
                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_details, popup.getMenu());
        popup.show();

    }

    private void deleteEntry() {
    }

    private void editEntry() {
    }

    private void openDetails(int position) {

        Intent intent = new Intent(getApplicationContext(), NewsDetailsActivity.class);
        intent.putExtra(TABLE_COLUMNS.AUTHOR.label, news_content.get(position).getAuthor());
        intent.putExtra(TABLE_COLUMNS.OWNER.label, news_content.get(position).getOwner());
        intent.putExtra(TABLE_COLUMNS.DESCRIPTION.label, news_content.get(position).getDescription());
        intent.putExtra(TABLE_COLUMNS.HEADLINE.label, news_content.get(position).getHeadline());
        intent.putExtra(TABLE_COLUMNS.PUBLISHED_AT.label, news_content.get(position).getPublished_at());
        intent.putExtra(TABLE_COLUMNS.LOCATION.label, news_content.get(position).getLocation());
        startActivity(intent);

    }

    private void populateListView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, news_content.stream().map(t -> t.getHeadline()).collect(Collectors.toList()));
        news_view.setAdapter(adapter);

    }

    private ArrayList<News> retrieveDatabaseData() {

        ArrayList<News> result = new ArrayList<>();
        Cursor c = sql.rawQuery("Select * from news", null);

        c.moveToFirst();

        while(!c.isAfterLast()) {

            News news = new News();
            news.setAuthor(c.getString(TABLE_COLUMNS.AUTHOR.index));
            news.setOwner(c.getString(TABLE_COLUMNS.OWNER.index));
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