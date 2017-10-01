package com.zain.zainco.movieapp.network;

import com.zain.zainco.movieapp.app.Constants;
import com.zain.zainco.movieapp.model.MovieResultsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Zain on 28/09/2017.
 */

public interface MoviedbApi {
    @GET("popular?api_key=" + Constants.API_KEY)
    Call<MovieResultsResponse> getMoviesListPopular();

    @GET("top_rated?api_key=" + Constants.API_KEY)
    Call<MovieResultsResponse> getMoviesListTopRated();

}
