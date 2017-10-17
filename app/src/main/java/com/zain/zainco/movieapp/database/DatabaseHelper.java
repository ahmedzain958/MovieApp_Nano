package com.zain.zainco.movieapp.database;

import static com.zain.zainco.movieapp.database.Constant.AVERAGERATING;
import static com.zain.zainco.movieapp.database.Constant.BACKDROPPATH;
import static com.zain.zainco.movieapp.database.Constant.FAVOURITE_TABLE;
import static com.zain.zainco.movieapp.database.Constant.ID;
import static com.zain.zainco.movieapp.database.Constant.OVERVIEW;
import static com.zain.zainco.movieapp.database.Constant.POSTERPATH;
import static com.zain.zainco.movieapp.database.Constant.REALEASEDATE;
import static com.zain.zainco.movieapp.database.Constant.TITLE;

public class DatabaseHelper {

    public static final String FAVOURITES_CREATE = "CREATE TABLE IF NOT EXISTS " + FAVOURITE_TABLE +
            "(" + ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT," + BACKDROPPATH + " TEXT," + POSTERPATH + " TEXT,"
            + REALEASEDATE + " TEXT," + AVERAGERATING + " TEXT," + OVERVIEW + " TEXT)";

    public static final String FAVOURITES_DROP = "DROP TABLE IF EXISTS " + FAVOURITE_TABLE;


}
