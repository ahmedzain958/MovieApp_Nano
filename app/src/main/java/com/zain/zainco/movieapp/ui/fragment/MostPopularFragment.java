package com.zain.zainco.movieapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.zain.zainco.movieapp.R;
import com.zain.zainco.movieapp.model.Movie;
import com.zain.zainco.movieapp.ui.activity.MovieDetailsActivity;
import com.zain.zainco.movieapp.ui.adapter.MoviesAdapter;
import com.zain.zainco.movieapp.ui.repository.MoviesPresenterImpl;
import com.zain.zainco.movieapp.ui.repository.MoviesView;
import com.zain.zainco.movieapp.utils.NetwokConnectivity;

import java.util.ArrayList;

import static com.zain.zainco.movieapp.app.Constants.POPULAR;

public class MostPopularFragment extends Fragment implements MoviesView {
    GridView moviesGridView;
    MoviesAdapter moviesAdapter;

    private MoviesPresenterImpl presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);
        moviesGridView = view.findViewById(R.id.movies_gv);

        presenter = new MoviesPresenterImpl();
        presenter.setView(this);
        presenter.getMovies(POPULAR);

        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long l) {
                Movie selectedMovie = (Movie) adapterView.getItemAtPosition(position);
                startActivity(new Intent(getActivity(), MovieDetailsActivity.class).putExtra("selectedMovie", selectedMovie));
            }
        });
        if (savedInstanceState != null) {
            ArrayList<Movie> items = savedInstanceState.getParcelableArrayList("gvState");
            moviesAdapter.setMovies(items); // Load saved data if any.
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMovies(ArrayList<Movie> moviesList) {
        if (new NetwokConnectivity(getActivity()).isNetworkAvailable()) {
            moviesAdapter = new MoviesAdapter(getActivity(), moviesList);
            moviesGridView.setAdapter(moviesAdapter);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getActivity(), "Can't Connect", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchMovieDetail(Movie movie) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!outState.containsKey("gvState") && outState.getParcelableArrayList("gvState") != null)
            outState.putParcelableArrayList("gvState", moviesAdapter.getMovies());
    }

   /* @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
            moviesGridView.smoothScrollToPosition(savedInstanceState.getInt(GRIDVIEW_POSITION));
    }*/
}
