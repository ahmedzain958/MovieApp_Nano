package com.zain.zainco.movieapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zain.zainco.movieapp.R;
import com.zain.zainco.movieapp.model.Trailer;
import com.zain.zainco.movieapp.utils.AnimationUtil;

import java.util.ArrayList;

/**
 * Created by Zain on 07/10/2017.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewholder> {


    private Context mcontext;
    private ArrayList<Trailer> listTrailers;
    private LayoutInflater inflater;

    private int previousPosition = 0;

    public TrailersAdapter(Context context, ArrayList<Trailer> listTrailers) {
        this.mcontext = context;
        this.listTrailers = listTrailers;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TrailerViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_trailer_row, parent, false);
        TrailerViewholder holder = new TrailerViewholder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(TrailerViewholder trailerViewholder, int position) {
        trailerViewholder.trailerNo.setText("Trailer no. " + String.valueOf(position + 1));

        if (position > previousPosition) { // We are scrolling DOWN

            AnimationUtil.animate(trailerViewholder, true);

        } else { // We are scrolling UP

            AnimationUtil.animate(trailerViewholder, false);
        }
        trailerViewholder.setClickListener(new ItemClickListener() {
            @Override
            public void onClickItem(int pos) {
                mcontext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="
                        + listTrailers.get(position).getKey())));
            }
        });
        previousPosition = position;

    }

    @Override
    public int getItemCount() {
        return listTrailers.size();
    }

    class TrailerViewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView trailerNo;
        ImageView imageView;
        private ItemClickListener mListener;

        public TrailerViewholder(View itemView) {
            super(itemView);
            trailerNo = itemView.findViewById(R.id.tv_trailer);
            imageView = itemView.findViewById(R.id.img_play);
            itemView.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener listener) {
            this.mListener = listener;
        }

        @Override
        public void onClick(View view) {
            mListener.onClickItem(getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}
