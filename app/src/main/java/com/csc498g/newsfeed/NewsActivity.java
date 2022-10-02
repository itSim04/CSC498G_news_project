package com.csc498g.newsfeed;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NewsActivity extends AppCompatActivity {

    ListView news_view;
    List<News> news_content;
    SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();

        sql = this.openOrCreateDatabase(Constants.DATABASE_NAME, MODE_PRIVATE,  null);
        sql.execSQL("CREATE Table IF NOT EXISTS news (author VARCHAR, owner VARCHAR, headline VARCHAR, description VARCHAR, published_at VARCHAR, location VARCHAR)");

        if(this.getSharedPreferences(Constants.PREFERENCES_NAME, MODE_PRIVATE).getBoolean("first_run", true)) {
            sql.execSQL("INSERT INTO news(author, owner, headline, description, published_at, location) VALUES (?, ?, ?, ?, ?, ?)", new String[]{
                    "Ashley Strickland",
                    "Simon",
                    "NASA spacecraft captures image of ocean world orbiting Jupiter during flyby",
                    "The Juno spacecraft, which has been orbiting Jupiter since 2016, made its closest approach yet to the moon Europa at 5:36 a.m. ET, flying within 219 miles (352 kilometers) of its icy surface.\nJuno captured some of the highest-resolution images ever taken of Europa’s ice shell. The first one has already been transmitted to Earth and shows surface features in a region north of the moon’s equator called Annwn Regio.\nDue to the enhanced contrast between light and shadow seen along the terminator (the nightside boundary), rugged terrain features are easily seen, including tall shadow-casting blocks, while bright and dark ridges and troughs curve across the surface,” a NASA release said. “The oblong pit near the terminator might be a degraded impact crater.",
                    "29-09-2022",
                    "USA"
            });

            sql.execSQL("INSERT INTO news(author, owner, headline, description, published_at, location) VALUES (?, ?, ?, ?, ?, ?)", new String[]{
                    "UN News",
                    "Simon",
                    "Delivering justice for abused child brides in The Comoros",
                    "Despite the challenges, the UN is committed to ending all forms of violence against women and girls in the Comoros. The UN reproductive health agency, UNFPA, has set up a toll-free hotline that survivors can call for help and information about receiving medical and legal assistance, and supports the Listening and Protection Service for Children and Women Victims of Violence, in the capital city, Moroni.\n\nThe Service also provides midwifery and contraceptive services, post-rape care and screenings for sexually-transmitted infections, as well as referrals to hospitals. Since 2021 a psychologist has also been deployed to help women and girls who have been left to take care of their families alone.\n\nSince the Service began, around 17 years ago, awareness of the issue of sexual violence has grown in The Comoros, says Mr. Said, and women and girls are more likely to report attacks than they were before it opened.\n\nAfter her attack Mariama, determined to seek help and justice, received medical and legal assistance from the centre, and staff supported her as the case made its way through the courts after the man’s arrest.",
                    "01-10-2022",
                    "Comoros"
            });

            sql.execSQL("INSERT INTO news(author, owner, headline, description, published_at, location) VALUES (?, ?, ?, ?, ?, ?)", new String[]{
                    "MIT News",
                    "Simon",
                    "Wiggling toward bio-inspired machine intelligence",
                    "Juncal Arbelaiz Mugica is a native of Spain, where octopus is a common menu item. However, Arbelaiz appreciates octopus and similar creatures in a different way, with her research into soft-robotics theory.\n\nMore than half of an octopus’ nerves are distributed through its eight arms, each of which has some degree of autonomy. This distributed sensing and information processing system intrigued Arbelaiz, who is researching how to design decentralized intelligence for human-made systems with embedded sensing and computation. At MIT, Arbelaiz is an applied math student who is working on the fundamentals of optimal distributed control and estimation in the final weeks before completing her PhD this fall.",
                    "02-10-2022",
                    "Spain"
            });
            this.getSharedPreferences(Constants.PREFERENCES_NAME, MODE_PRIVATE).edit().putBoolean("first_run", false).apply();
        }
        String username = this.getSharedPreferences(Constants.PREFERENCES_NAME, MODE_PRIVATE).getString("username", "Anonymous");

        news_content = retrieveDatabaseData();

        news_view = findViewById(R.id.newsView);
        news_view.setOnItemClickListener((parent, view, position, id) -> {

            News news = news_content.get(position);

            if(news.getOwner().equals(username)) {

                displayMenu(view, position);

            } else {

                Intent intent = new Intent(getApplicationContext(), NewsDetailsActivity.class);
                storeAndRunIntent(intent, position);

            }


        });

        populateListView();

    }

    private void displayMenu(View view, int position) {

        PopupMenu popup = new PopupMenu(getApplicationContext(), view);
        popup.setOnMenuItemClickListener(item -> {
            Intent intent;
                if(item.getItemId() == R.id.viewDetailsItem) {

                    intent = new Intent(getApplicationContext(), NewsDetailsActivity.class);
                    storeAndRunIntent(intent, position);
                    return true;

                } else if(item.getItemId() == R.id.editItem) {

                    intent = new Intent(getApplicationContext(), UpdateActivity.class);
                    storeAndRunIntent(intent, position);
                    return true;

                } else if (item.getItemId() == R.id.deleteItem) {

                    sql.execSQL("DELETE FROM news WHERE owner = ? AND headline = ?", new String[]{
                            news_content.get(position).getOwner(),
                            news_content.get(position).getHeadline()
                    });
                    news_content.remove(position);
                    populateListView();
                    return true;

                } else {
                    return false;

                }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_details, popup.getMenu());
        popup.show();

    }


    private void storeAndRunIntent(Intent intent, int position) {

        intent.putExtra(TABLE_COLUMNS.AUTHOR.label, news_content.get(position).getAuthor());
        intent.putExtra(TABLE_COLUMNS.OWNER.label, news_content.get(position).getOwner());
        intent.putExtra(TABLE_COLUMNS.DESCRIPTION.label, news_content.get(position).getDescription());
        intent.putExtra(TABLE_COLUMNS.HEADLINE.label, news_content.get(position).getHeadline());
        intent.putExtra(TABLE_COLUMNS.PUBLISHED_AT.label, news_content.get(position).getPublished_at());
        intent.putExtra(TABLE_COLUMNS.LOCATION.label, news_content.get(position).getLocation());
        startActivity(intent);

    }

    private void populateListView() {

        NewsAdapter adapter = new NewsAdapter(this, news_content);
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

        c.close();
        return result;

    }

    public void postNews(View view) {

        Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
        startActivity(intent);

    }

}