package com.zain.zainco.movieapp.app;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import com.zain.zainco.movieapp.database.FavouriteDatabase;

import java.util.HashMap;

import static com.zain.zainco.movieapp.database.Constant.FAVOURITE_TABLE;
import static com.zain.zainco.movieapp.database.Constant.ID;

/**
 * Created by Zain on 14/10/2017.
 */

public class MoviesProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.zain.zainco.movieapp.app.MoviesProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/movies";
    public static final Uri _URI = Uri.parse(URL);


    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

    static final int MOVIES = 1;
    static final int MOVIES_ID = 2;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "movies", MOVIES);
        uriMatcher.addURI(PROVIDER_NAME, "movies/#", MOVIES_ID);
    }


    private FavouriteDatabase favouriteDatabase;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        favouriteDatabase = new FavouriteDatabase(context);
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new student record
         */
        final SQLiteDatabase db = favouriteDatabase.getWritableDatabase();

        int match = uriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                long rowID = db.insert(FAVOURITE_TABLE, "", values);
                if (rowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(_URI, rowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                break;
        }


        /**
         * If record is added successfully
         */


        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = favouriteDatabase.getReadableDatabase();

        // Write URI match code and set a variable to return a Cursor
        int match = uriMatcher.match(uri);
        Cursor retCursor;

        // Query for the tasks directory and write a default case
        switch (match) {
            // Query for the tasks directory
            case MOVIES:
                retCursor = db.query(FAVOURITE_TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIES_ID:
                String id = uri.getPathSegments().get(1);
                String whereClause = ID+" = ?";
                String[] whereArgs = new String[] {
                        id
                };
                retCursor = db.query(FAVOURITE_TABLE,
                        null,
                        whereClause,
                        whereArgs,
                        null,
                        null,
                        null);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return retCursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        final SQLiteDatabase db = favouriteDatabase.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                count = db.delete(FAVOURITE_TABLE, selection, selectionArgs);
                break;

            case MOVIES_ID:
                // Get the task ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                count = db.delete(FAVOURITE_TABLE, ID + "=?", new String[]{id});
                break;
            default:
                throw new IllegalArgumentException("Unknown _URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        final SQLiteDatabase db = favouriteDatabase.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                count = db.update(FAVOURITE_TABLE, values, selection, selectionArgs);
                break;

            case MOVIES_ID:
                count = db.update(FAVOURITE_TABLE, values,
                        ID + " = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown _URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            /**
             * Get all student records
             */
            case MOVIES:
                return "vnd.android.cursor.dir/vnd.example.movies";
            /**
             * Get a particular student
             */
            case MOVIES_ID:
                return "vnd.android.cursor.item/vnd.example.movies";
            default:
                throw new IllegalArgumentException("Unsupported _URI: " + uri);
        }
    }
}
