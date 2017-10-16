package com.zain.zainco.movieapp.ui.repository;

import android.content.Context;

/**
 * Created by Zain on 28/09/2017.
 */

public interface MoviesPresenter {
    void setView(MoviesView view);

    void getMovies(String sortBy, Context context);

}
