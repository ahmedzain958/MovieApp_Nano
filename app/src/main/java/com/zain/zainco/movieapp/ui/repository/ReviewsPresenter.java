package com.zain.zainco.movieapp.ui.repository;

public interface ReviewsPresenter {
    void getReviews(String movieId) ;

    void setView(ReviewsView view);


}
