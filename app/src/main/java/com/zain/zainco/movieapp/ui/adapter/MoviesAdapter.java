package com.zain.zainco.movieapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zain.zainco.movieapp.R;
import com.zain.zainco.movieapp.model.Movie;

import java.util.ArrayList;

public class MoviesAdapter extends BaseAdapter {
    Context context;
    ArrayList<Movie> movies;

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        MovieViewHolder mHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gridview_movie_item, viewGroup, false);
            mHolder = new MovieViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (MovieViewHolder) convertView.getTag();
        }
        mHolder.txtViewTitle.setText(movies.get(position).getTitle());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w154" + movies.get(position).getPosterPath()).into(mHolder.imgViewMovie);
        return convertView;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public class MovieViewHolder {
        TextView txtViewTitle;
        ImageView imgViewMovie;

        public MovieViewHolder(View view) {

            txtViewTitle = view.findViewById(R.id.title);
            imgViewMovie = view.findViewById(R.id.img_movie);

        }
    }
}

