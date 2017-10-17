package com.zain.zainco.movieapp.helpers;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import static com.zain.zainco.movieapp.database.FavouriteDatabase.getFavouritesDatabaseInstance;


public class ApplicationClass extends Application {
    private static ApplicationClass instance;

    // Database instance
    private static SQLiteDatabase favouriteWritableDatabase;
    private static SQLiteDatabase favouriteReadableDatabase;

    public static ApplicationClass getInstance() {
        if (instance == null)
            return instance = new ApplicationClass();
        return instance;
    }

    public static SQLiteDatabase getFavouriteWritableDatabase() {
        if (favouriteWritableDatabase == null || !favouriteWritableDatabase.isOpen()) {
            return favouriteWritableDatabase = getFavouritesDatabaseInstance
                    (getInstance().getApplicationContext()).getWritableDatabase();
        }
        return favouriteWritableDatabase;
    }
         //////
    public static SQLiteDatabase getFavouriteReadableDatabase() {
        if (favouriteReadableDatabase == null || !favouriteReadableDatabase.isOpen()) {
            return favouriteReadableDatabase = getFavouritesDatabaseInstance
                    (getInstance().getApplicationContext()).getReadableDatabase();
        }
        return favouriteReadableDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        favouriteWritableDatabase = getFavouritesDatabaseInstance(getApplicationContext()).getWritableDatabase();
        favouriteReadableDatabase = getFavouritesDatabaseInstance(getApplicationContext()).getReadableDatabase();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();


        if (favouriteReadableDatabase != null && favouriteReadableDatabase.isOpen())
            favouriteReadableDatabase.close();
        if (favouriteWritableDatabase != null && favouriteWritableDatabase.isOpen())
            favouriteWritableDatabase.close();
        getFavouritesDatabaseInstance(getApplicationContext()).close();
    }
}
