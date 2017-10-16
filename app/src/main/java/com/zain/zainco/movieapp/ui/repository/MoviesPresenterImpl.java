package com.zain.zainco.movieapp.ui.repository;

import android.content.Context;
import android.database.Cursor;

import com.zain.zainco.movieapp.app.Constants;
import com.zain.zainco.movieapp.model.Movie;
import com.zain.zainco.movieapp.model.MovieResultsResponse;
import com.zain.zainco.movieapp.network.MoviedbApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.zain.zainco.movieapp.app.Constants.FAVOURITE;
import static com.zain.zainco.movieapp.app.Constants.POPULAR;
import static com.zain.zainco.movieapp.app.Constants.TOP_RATED;
import static com.zain.zainco.movieapp.app.MoviesProvider._URI;
import static com.zain.zainco.movieapp.database.Constant.BACKDROPPATH;
import static com.zain.zainco.movieapp.database.Constant.ID;
import static com.zain.zainco.movieapp.database.Constant.POSTERPATH;
import static com.zain.zainco.movieapp.database.Constant.TITLE;

public class MoviesPresenterImpl implements MoviesPresenter {
    MoviesView moviesView;

    @Override
    public void setView(MoviesView view) {
        moviesView = view;
    }

    @Override
    public void getMovies(String sortBy, Context context) {
        moviesView.showLoading();

        Converter.Factory converter = GsonConverterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(converter).build();

        MoviedbApi moviedbApi = retrofit.create(MoviedbApi.class);
        if (sortBy.equals(POPULAR)) {
            moviedbApi.getMoviesListPopular().enqueue(new Callback<MovieResultsResponse>() {
                @Override
                public void onResponse(Call<MovieResultsResponse> call, Response<MovieResultsResponse> response) {
                    ArrayList<Movie> movieList = response.body().getItem();//.filter(movie -> !movie.get().contains("ERROR"))
                    if (movieList != null) {
                        moviesView.showMovies(movieList);
                        moviesView.hideLoading();
                    }
                }

                @Override
                public void onFailure(Call<MovieResultsResponse> call, Throwable t) {
                    moviesView.showErrorMessage();
                }
            });
        } else if (sortBy.equals(TOP_RATED)) {
            moviedbApi.getMoviesListTopRated().enqueue(new Callback<MovieResultsResponse>() {
                @Override
                public void onResponse(Call<MovieResultsResponse> call, Response<MovieResultsResponse> response) {
                    ArrayList<Movie> movieList = (response.body().getItem());//.filter(movie -> !movie.get().contains("ERROR"))
                    moviesView.showMovies(movieList);
                    moviesView.hideLoading();
                }

                @Override
                public void onFailure(Call<MovieResultsResponse> call, Throwable t) {
                    moviesView.showErrorMessage();
                }
            });
        } else if (sortBy.equals(FAVOURITE)) {
           /* Cursor favouritesCursor = selectFromFavourite("*", "1=1");
            ArrayList<Movie> movieList = new ArrayList<>();
            if (favouritesCursor != null) {
                while (favouritesCursor.moveToNext()) {
                    Movie movie = new Movie();
                    movie.setId(favouritesCursor.getInt(favouritesCursor.getColumnIndex(ID)));
                    movie.setTitle(favouritesCursor.getString(favouritesCursor.getColumnIndex(TITLE)));
                    movie.setBackdropPath(favouritesCursor.getString(favouritesCursor.getColumnIndex(BACKDROPPATH)));
                    movie.setPosterPath(favouritesCursor.getString(favouritesCursor.getColumnIndex(POSTERPATH)));
                    movie.setReleaseDate(favouritesCursor.getString(favouritesCursor.getColumnIndex(REALEASEDATE)));
                    movie.setVoteAverage(favouritesCursor.getDouble(favouritesCursor.getColumnIndex(AVERAGERATING)));
                    movie.setOverview(favouritesCursor.getString(favouritesCursor.getColumnIndex(OVERVIEW)));
                    movieList.add(movie);
                }
                if (movieList != null) {
                    if (movieList.size() > 0) {
                        moviesView.showMovies(movieList);
                    }
                }
            }*/
            ArrayList<Movie> moviesList = new ArrayList<>();
            Cursor c =context.getContentResolver().query(_URI, null, null, null, null);
            if (c.moveToFirst()) {
                while (c.moveToNext()) {
                    Movie movie = new Movie();
                    movie.setId(c.getInt(c.getColumnIndex(ID)));
                    movie.setBackdropPath(c.getString(c.getColumnIndex(BACKDROPPATH)));
                    movie.setTitle(c.getString(c.getColumnIndex(TITLE)));
                    movie.setPosterPath(c.getString(c.getColumnIndex(POSTERPATH)));
              /*  movie.setId(c.getString(c.getColumnIndex(ID)));
                movie.setId(c.getString(c.getColumnIndex(ID)));
                movie.setId(c.getString(c.getColumnIndex(ID)));
                movie.setId(c.getString(c.getColumnIndex(ID)));*/
                    moviesList.add(movie);
                }
            }
            if (moviesList != null) {
                if (moviesList.size() > 0) {
                    moviesView.showMovies(moviesList);
                }
            }
        }
    }


}
