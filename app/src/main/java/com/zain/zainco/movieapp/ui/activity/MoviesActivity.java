package com.zain.zainco.movieapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zain.zainco.movieapp.R;
import com.zain.zainco.movieapp.ui.adapter.CustomPagerAdapter;
import com.zain.zainco.movieapp.ui.fragment.MostPopularFragment;
import com.zain.zainco.movieapp.ui.fragment.TopRatedFragment;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    List<Fragment> listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        viewPager = (ViewPager) findViewById(R.id.movies_view_pager);


        //initialing ViewPager
        viewPager.addOnPageChangeListener(this);

        listFragment = new ArrayList<>();

        listFragment.add(new MostPopularFragment());
        listFragment.add(new TopRatedFragment());


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


}
