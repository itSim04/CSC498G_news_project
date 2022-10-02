package com.csc498g.newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.csc498g.newsfeed.News;

import java.util.ArrayList;
import java.util.List;


public class NewsAdapter extends ArrayAdapter<News> {

    private Context mContext;
    private List<News> newsList;

    public NewsAdapter(@NonNull Context context, List<News> list) {
        super(context, 0, list);
        mContext = context;
        newsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        News currentNews = newsList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentNews.getHeadline());

        TextView release = (TextView) listItem.findViewById(R.id.textView_release);
        release.setText(currentNews.getAuthor());

        return listItem;
    }
}
