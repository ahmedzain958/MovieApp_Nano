package com.zain.zainco.movieapp.ui.repository;

import com.zain.zainco.movieapp.model.Trailer;

import java.util.ArrayList;

/**
 * Created by Zain on 07/10/2017.
 */

public interface TrailersView {

    void showTrailers(ArrayList<Trailer> trailersList);

    void showErrorMessage();
}
