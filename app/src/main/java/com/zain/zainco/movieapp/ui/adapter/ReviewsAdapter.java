package com.zain.zainco.movieapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zain.zainco.movieapp.R;
import com.zain.zainco.movieapp.model.Review;

import java.util.ArrayList;

/**
 * Created by Zain on 07/10/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewholder> {


    private ArrayList<Review> listReviews;
    private LayoutInflater inflater;


    public ReviewsAdapter( ArrayList<Review> listReviews) {
        this.listReviews = listReviews;
    }

    @Override
    public ReviewViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_review_row, parent, false);
        ReviewViewholder holder = new ReviewViewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewViewholder trailerViewholder, int position) {
        trailerViewholder.review.setText(listReviews.get(position).getContent());



    }

    @Override
    public int getItemCount() {
        return listReviews.size();
    }

    class ReviewViewholder extends RecyclerView.ViewHolder  {
        TextView review;

        public ReviewViewholder(View itemView) {
            super(itemView);
            review = itemView.findViewById(R.id.tv_review);
        }


    }
}
