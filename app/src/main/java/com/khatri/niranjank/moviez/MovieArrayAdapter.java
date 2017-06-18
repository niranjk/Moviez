package com.khatri.niranjank.moviez;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by niranjank on 05/06/17.
 */

public class MovieArrayAdapter extends ArrayAdapter {

    private List<MovieDetails> movieDetailsList;
    private int resource;
    private Context context;

    public MovieArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<MovieDetails> movieDetailsList) {
        super(context, resource, movieDetailsList);
        this.movieDetailsList=movieDetailsList;
        this.resource =resource;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MovieDetails details = movieDetailsList.get(position);
        View view = LayoutInflater.from(context).inflate(resource,parent,false);

        TextView movieName = (TextView) view.findViewById(R.id.textView1);
        ImageView image = (ImageView) view.findViewById(R.id.imageView1);

        movieName.setText(details.getOriginal_title());

        Glide.with(this.context).load("https://image.tmdb.org/t/p/w500/"+details.getPoster_path()).into(image);
        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return movieDetailsList.get(position);
    }
}
