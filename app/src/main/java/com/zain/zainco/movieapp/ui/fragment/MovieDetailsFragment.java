package com.zain.zainco.movieapp.ui.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;
import com.zain.zainco.movieapp.R;
import com.zain.zainco.movieapp.model.Movie;
import com.zain.zainco.movieapp.model.Review;
import com.zain.zainco.movieapp.model.Trailer;
import com.zain.zainco.movieapp.ui.adapter.ReviewsAdapter;
import com.zain.zainco.movieapp.ui.adapter.TrailersAdapter;
import com.zain.zainco.movieapp.ui.repository.ReviewsPresenterImpl;
import com.zain.zainco.movieapp.ui.repository.ReviewsView;
import com.zain.zainco.movieapp.ui.repository.TrailersPresenterImpl;
import com.zain.zainco.movieapp.ui.repository.TrailersView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zain.zainco.movieapp.app.MoviesProvider._URI;
import static com.zain.zainco.movieapp.database.Constant.AVERAGERATING;
import static com.zain.zainco.movieapp.database.Constant.BACKDROPPATH;
import static com.zain.zainco.movieapp.database.Constant.ID;
import static com.zain.zainco.movieapp.database.Constant.OVERVIEW;
import static com.zain.zainco.movieapp.database.Constant.POSTERPATH;
import static com.zain.zainco.movieapp.database.Constant.REALEASEDATE;
import static com.zain.zainco.movieapp.database.Constant.TITLE;

public class MovieDetailsFragment extends Fragment implements TrailersView, ReviewsView {
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


    @BindView(R.id.rv_trailers)
    RecyclerView recycle_trailers;

    @BindView(R.id.favourite)
    ToggleButton favourite;

    @BindView(R.id.rv_reviews)
    RecyclerView recycle_reviews;


    private TrailersAdapter adapter;
    private TrailersPresenterImpl presenter;
    private ReviewsAdapter adapterReviews;
    private ReviewsPresenterImpl presenterReviews;

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
            movie = (Movie) getActivity().getIntent().getParcelableExtra("selectedMovie"); //Obtaining data
            if (movie != null) {
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
                String movieId = movie.getId().toString();

                presenter = new TrailersPresenterImpl();
                presenter.setView(this);
                presenter.getTrailers(movieId);
                //fill trailers recycler


                presenterReviews = new ReviewsPresenterImpl();
                presenterReviews.setView(this);
                presenterReviews.getReviews(movieId);

                favourite.setText(null);
                favourite.setTextOff(null);
                favourite.setTextOn(null);


                Uri uri = _URI.buildUpon().appendPath(movie.getId().toString()).build();
                Cursor c = getActivity().getContentResolver().query(uri, null, null, null, null);
                if (c != null) {
                    if (c.getCount() >= 1) {
                        favourite.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.star_on));
                        favourite.setChecked(true);
                    } else {
                        favourite.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.star_off));
                        favourite.setChecked(false);
                    }
                    c.close();
                }
                favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            ContentValues values = new ContentValues();
                            values.put(ID,
                                    movie.getId());

                            values.put(TITLE,
                                    movie.getTitle());

                            values.put(BACKDROPPATH,
                                    movie.getBackdropPath());

                            values.put(POSTERPATH,
                                    movie.getPosterPath());

                            values.put(AVERAGERATING,
                                    movie.getVoteAverage());

                            values.put(OVERVIEW,
                                    movie.getOverview());

                            values.put(REALEASEDATE,
                                    movie.getReleaseDate());
                            Uri uri = getContext().getContentResolver().insert(_URI, values);
                            favourite.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.star_on));
                            Toast.makeText(getActivity(), movie.getTitle() + " movie added", Toast.LENGTH_SHORT).show();
                        } else {
                            Uri uri = _URI.buildUpon().appendPath(Integer.toString(movie.getId())).build();
                            int deleted = getActivity().getContentResolver().delete(uri, null, null);
                            if (deleted != 0) {
                                favourite.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.star_off));
                                Toast.makeText(getActivity(), movie.getTitle() + " movie removed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            }
        }
        return view;
    }

    @Override
    public void showReviews(ArrayList<Review> reviewsList) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity());
        recycle_reviews.setLayoutManager(layoutManager);
        adapterReviews = new ReviewsAdapter(reviewsList);
        recycle_reviews.setAdapter(adapterReviews);
    }

    @Override
    public void showTrailers(ArrayList<Trailer> trailersList) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycle_trailers.setLayoutManager(layoutManager);
        adapter = new TrailersAdapter(getActivity(), trailersList);
        recycle_trailers.setAdapter(adapter);
    }

    @Override
    public void showErrorMessage() {

    }
}
