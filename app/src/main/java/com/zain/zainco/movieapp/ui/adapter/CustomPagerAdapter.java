package com.zain.zainco.movieapp.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.zain.zainco.movieapp.R;

import java.util.List;


public class CustomPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    Context c;

    public CustomPagerAdapter(Context c, FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.c = c;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
            return position == 0 ? c.getResources().getString(R.string.most_popular) : c.getResources().getString(R.string.top_rated);

    }


}
