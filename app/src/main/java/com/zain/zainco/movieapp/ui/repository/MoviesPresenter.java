package com.zain.zainco.movieapp.ui.repository;

/**
 * Created by Zain on 28/09/2017.
 */

public interface MoviesPresenter {
    void setView(MoviesView view);

    void getMovies(String sortBy);
}
