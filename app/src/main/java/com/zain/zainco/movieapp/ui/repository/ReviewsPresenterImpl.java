package com.zain.zainco.movieapp.ui.repository;

import com.zain.zainco.movieapp.app.Constants;
import com.zain.zainco.movieapp.model.MovieReviewResponse;
import com.zain.zainco.movieapp.model.Review;
import com.zain.zainco.movieapp.network.MoviedbApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zain on 07/10/2017.
 */

public class ReviewsPresenterImpl implements ReviewsPresenter {

    ReviewsView reviewsView;

    @Override
    public void getReviews(String movieId) {

        Converter.Factory converter = GsonConverterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(converter).build();

        MoviedbApi moviedbApi = retrofit.create(MoviedbApi.class);
        moviedbApi.getReviews(movieId).enqueue(new Callback<MovieReviewResponse>() {
            @Override
            public void onResponse(Call<MovieReviewResponse> call, Response<MovieReviewResponse> response) {
                ArrayList<Review> movieList = response.body().getItem();//.filter(movie -> !movie.get().contains("ERROR"))
                reviewsView.showReviews(movieList);
            }

            @Override
            public void onFailure(Call<MovieReviewResponse> call, Throwable t) {
                reviewsView.showErrorMessage();
            }
        });
    }

    @Override
    public void setView(ReviewsView view) {
        reviewsView = view;
    }


}
