package com.zain.zainco.movieapp.ui.repository;

import com.zain.zainco.movieapp.model.Review;

import java.util.ArrayList;



public interface ReviewsView {
    void showReviews(ArrayList<Review> reviewsList);

    void showErrorMessage();
}
