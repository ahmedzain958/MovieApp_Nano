package com.zain.zainco.movieapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zain.zainco.movieapp.R;
import com.zain.zainco.movieapp.ui.adapter.CustomPagerAdapter;
import com.zain.zainco.movieapp.ui.fragment.FavouriteFragment;
import com.zain.zainco.movieapp.ui.fragment.MostPopularFragment;
import com.zain.zainco.movieapp.ui.fragment.TopRatedFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnPageChange;

public class MoviesActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    List<Fragment> listFragment;

    @BindView(R.id.movies_view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        ButterKnife.bind(this);


        //initialing ViewPager
//        viewPager.addOnPageChangeListener(this);

        listFragment = new ArrayList<>();

        listFragment.add(new MostPopularFragment());
        listFragment.add(new TopRatedFragment());
        listFragment.add(new FavouriteFragment());


        CustomPagerAdapter fragmentPagerAdapter = new CustomPagerAdapter
                (this, getSupportFragmentManager(), listFragment);

        viewPager.setAdapter(fragmentPagerAdapter);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnPageChange(R.id.movies_view_pager)
    public void OnPageChange() {
        // TODO submit data to server...
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_movie_details, menu);
        } catch (Exception e) {


        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            try {
                switch (item.getItemId()) {

                    case R.id.most_popular:
                        viewPager.setCurrentItem(0);

                        return true;
                    case R.id.top_rated:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.favourite:
                        viewPager.setCurrentItem(2);
                        return true;


                }
            } catch (Exception e) {


            }
        } catch (Exception e) {


        }
        return true;
    }

}
