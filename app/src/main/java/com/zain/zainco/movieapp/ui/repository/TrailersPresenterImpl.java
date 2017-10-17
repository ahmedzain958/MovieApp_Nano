package com.zain.zainco.movieapp.ui.repository;

import com.zain.zainco.movieapp.app.Constants;
import com.zain.zainco.movieapp.model.MovieTrailersResponse;
import com.zain.zainco.movieapp.model.Trailer;
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

public class TrailersPresenterImpl implements TrailersPresenter {

    TrailersView trailersView;

    @Override
    public void getTrailers(String movieId) {
        Converter.Factory converter = GsonConverterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(converter).build();

        MoviedbApi moviedbApi = retrofit.create(MoviedbApi.class);
            moviedbApi.getTrailers(movieId).enqueue(new Callback<MovieTrailersResponse>() {
                @Override
                public void onResponse(Call<MovieTrailersResponse> call, Response<MovieTrailersResponse> response) {
                    ArrayList<Trailer> movieList = response.body().getItem();
                    trailersView.showTrailers(movieList);
                }

                @Override
                public void onFailure(Call<MovieTrailersResponse> call, Throwable t) {
                    trailersView.showErrorMessage();
                }
            });
    }

    @Override
    public void setView(TrailersView view) {
        trailersView = view;
    }


}
