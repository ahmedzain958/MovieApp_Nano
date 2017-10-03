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

}
