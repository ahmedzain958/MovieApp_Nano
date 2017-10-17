package com.zain.zainco.movieapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zain.zainco.movieapp.helpers.ApplicationClass;

import static com.zain.zainco.movieapp.database.Constant.AVERAGERATING;
import static com.zain.zainco.movieapp.database.Constant.BACKDROPPATH;
import static com.zain.zainco.movieapp.database.Constant.DATABASE_NAME;
import static com.zain.zainco.movieapp.database.Constant.DATABASE_VERSION;
import static com.zain.zainco.movieapp.database.Constant.FAVOURITE_TABLE;
import static com.zain.zainco.movieapp.database.Constant.ID;
import static com.zain.zainco.movieapp.database.Constant.OVERVIEW;
import static com.zain.zainco.movieapp.database.Constant.POSTERPATH;
import static com.zain.zainco.movieapp.database.Constant.REALEASEDATE;
import static com.zain.zainco.movieapp.database.Constant.TITLE;
import static com.zain.zainco.movieapp.database.DatabaseHelper.FAVOURITES_CREATE;
import static com.zain.zainco.movieapp.database.DatabaseHelper.FAVOURITES_DROP;
import static com.zain.zainco.movieapp.helpers.ApplicationClass.getFavouriteReadableDatabase;

public class FavouriteDatabase extends SQLiteOpenHelper {

    private static FavouriteDatabase favouritesDatabaseInstance;


    public FavouriteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public synchronized static FavouriteDatabase getFavouritesDatabaseInstance(Context context) {
        if (favouritesDatabaseInstance == null)
            favouritesDatabaseInstance = new FavouriteDatabase(context.getApplicationContext());
        return favouritesDatabaseInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FAVOURITES_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(FAVOURITES_DROP);

        onCreate(db);
    }

    public static Cursor selectFromFavourite(String columnsSeparatedByCommas, String condition) {
        try {
            SQLiteDatabase db = getFavouriteReadableDatabase();
            String query = "select " + columnsSeparatedByCommas + " from " + FAVOURITE_TABLE +
                    " where " + condition;
            Cursor c = db.rawQuery(query, null);
            return c;
        } catch (Exception e) {

        }
        return null;
    }

    public static boolean markedAsFav(String movieId) {
        try {
            Cursor c = selectFromFavourite("*", ID + "=" + movieId + "");
            return c.getCount() > 0;
        } catch (Exception e) {

        }
        return false;
    }

    public static long markFavourite(String id, String title, String backdroppath,
                                     String posterpath, String realeasedate, String averagerating, String overview) {
        // Open the database for writing
        SQLiteDatabase db = ApplicationClass.getFavouriteWritableDatabase();
        // Start the transaction.
        long inserted = 0;
        db.beginTransaction();
        ContentValues values;
        try {
            values = new ContentValues();
            values.put(ID, id);
            values.put(TITLE, title);
            values.put(BACKDROPPATH, backdroppath);
            values.put(POSTERPATH, posterpath);
            values.put(REALEASEDATE, realeasedate);
            values.put(AVERAGERATING, averagerating);
            values.put(OVERVIEW, overview);

            inserted = db.insert(FAVOURITE_TABLE, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();

        }
        return inserted;
    }


    public static long UPDATE(String id, String title, String backdroppath,
                              String posterpath, String realeasedate, String averagerating, String overview) {
        try {
            SQLiteDatabase db = ApplicationClass.getFavouriteWritableDatabase();
            if (markedAsFav(id)) {
                try {
                    ContentValues cv = new ContentValues();
                    if (!id.equals(null))
                        cv.put(ID, id);
                    if (!title.equals(null))
                        cv.put(TITLE, title);
                    if (!backdroppath.equals(null))
                        cv.put(BACKDROPPATH, backdroppath);
                    if (!posterpath.equals(null))
                        cv.put(POSTERPATH, posterpath);
                    if (!realeasedate.equals(null))
                        cv.put(REALEASEDATE, realeasedate);
                    if (!averagerating.equals(null))
                        cv.put(AVERAGERATING, averagerating);
                    if (!overview.equals(null))
                        cv.put(OVERVIEW, overview);
                    int update = db.update(FAVOURITE_TABLE, cv, ID +
                            " =?", new String[]{id});

                    return update;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                markFavourite(id, title, backdroppath,
                        posterpath, realeasedate, averagerating, overview);
            }

        } catch (Exception e) {

        }
        return 0;

    }


    public static boolean delete(String id) {

        try {
            SQLiteDatabase db = ApplicationClass.getFavouriteWritableDatabase();
            String sql = "delete from " + FAVOURITE_TABLE + " where " + ID + "=" + id;
            db.execSQL(sql);
            return true;
        } catch (Exception e) {

        }
        return false;
    }
}
