package com.zain.zainco.movieapp.ui.repository;

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

import static com.zain.zainco.movieapp.app.Constants.POPULAR;

public class MoviesPresenterImpl implements MoviesPresenter {
    MoviesView moviesView;

    @Override
    public void setView(MoviesView view) {
        moviesView = view;
    }

    @Override
    public void getMovies(String sortBy) {
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
                    moviesView.showMovies(movieList);
                    moviesView.hideLoading();
                }

                @Override
                public void onFailure(Call<MovieResultsResponse> call, Throwable t) {
                    moviesView.showErrorMessage();
                }
            });
        }else{
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
        }
    }
}
