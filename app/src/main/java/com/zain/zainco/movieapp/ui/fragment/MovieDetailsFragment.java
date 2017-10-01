package com.zain.zainco.movieapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zain.zainco.movieapp.R;
import com.zain.zainco.movieapp.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsFragment extends Fragment {
    @BindView(R.id.backdrop)
    ImageView imgBackdrop;
    @BindView(R.id.poster)
    ImageView imgPoster;

    @BindView(R.id.movie_title)
    TextView movieTitle;

    @BindView(R.id.movie_release_date)
    TextView realeaseDate;

    @BindView(R.id.movie_average_rating)
    TextView AverageRating;

    @BindView(R.id.overview)
    TextView overview;

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, view);
        Bundle extras = getActivity().getIntent().getExtras();

        Movie movie;
        if (extras != null) {
            movie = (Movie) getActivity().getIntent().getSerializableExtra("selectedMovie"); //Obtaining data

            Picasso.with(getActivity())
                    .load("http://image.tmdb.org/t/p/w185/" + movie.getBackdropPath())
                    .into(imgBackdrop);
            Picasso.with(getActivity())
                    .load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
                    .into(imgPoster);
            movieTitle.setText("Title : " + movie.getTitle());
            realeaseDate.setText("Release Date : " + movie.getReleaseDate());
            AverageRating.setText("Rating : " + String.valueOf(movie.getVoteAverage()));
            overview.setText("OverView : " + movie.getOverview());
        }

        return view;
    }
}
