package com.zain.zainco.movieapp.ui.repository;

import com.zain.zainco.movieapp.model.Movie;

import java.util.ArrayList;

/**
 * Created by Zain on 28/09/2017.
 */

public interface MoviesView {
    void showMovies(ArrayList<Movie> moviesList);

    void showErrorMessage();

}
