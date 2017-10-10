package com.zain.zainco.movieapp.network;

import com.zain.zainco.movieapp.app.Constants;
import com.zain.zainco.movieapp.model.MovieResultsResponse;
import com.zain.zainco.movieapp.model.MovieReviewResponse;
import com.zain.zainco.movieapp.model.MovieTrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Zain on 28/09/2017.
 */

public interface MoviedbApi {
    @GET("popular?api_key=" + Constants.API_KEY)
    Call<MovieResultsResponse> getMoviesListPopular();

    @GET("top_rated?api_key=" + Constants.API_KEY)
    Call<MovieResultsResponse> getMoviesListTopRated();


    @GET("{id}/videos?api_key=" + Constants.API_KEY)
    Call<MovieTrailersResponse> getTrailers(@Path("id") String id);

    @GET("{id}/reviews?api_key=" + Constants.API_KEY)
    Call<MovieReviewResponse> getReviews(@Path("id") String id);

}
