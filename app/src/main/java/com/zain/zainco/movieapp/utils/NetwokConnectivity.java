package com.zain.zainco.movieapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zain.zainco.movieapp.helpers.ApplicationClass;



public class NetwokConnectivity {

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ApplicationClass.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
