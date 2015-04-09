package com.apphome.tpg.myhome.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by CLAUDIA on 09/04/2015.
 */
public class PublishProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = buildUriMatcher();

    static final int RESULT = 100;
    static final int RESULT_WITH_FILTERS = 101;
    //static final int RESULT_WITH_TEAM_AND_DATE = 102;

    private PublishDbHelper dbHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PublishContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, PublishContract.PATH_RESULT, RESULT);
        matcher.addURI(authority, PublishContract.PATH_RESULT + "/#", RESULT_WITH_FILTERS);
        //matcher.addURI(authority, PublishContract.PATH_RESULT + "/#/#", RESULT_WITH_TEAM_AND_DATE);

        return matcher;
    }

    private static final String publishWithFiltersSelection =
            PublishContract.Publish.TABLE_NAME + "." + PublishContract.Publish.COL_CITY + "= ? AND " +
                    PublishContract.Publish.TABLE_NAME + "." + PublishContract.Publish.COL_HOUSE_TYPE + " = ? ";


    private Cursor getResultByFilter(Uri uri, String []projection, String sortOrder) {
        int teamSetting = PublishContract.Publish.getPublishFromUri(uri);

        String [] selectionArgs;
        String selection;

        selection = publishWithFiltersSelection;
        selectionArgs = new String [] {Integer.toString(teamSetting), Long.toString(0)};

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return db.query(PublishContract.Publish.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new PublishDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match) {
            case RESULT:
                return PublishContract.Publish.CONTENT_TYPE;
            case RESULT_WITH_FILTERS:
                return PublishContract.Publish.CONTENT_TYPE;
            //case RESULT_WITH_TEAM_AND_DATE:
              //  return ResultContract.ResultEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (uriMatcher.match(uri)) {
            case RESULT:
                retCursor = dbHelper.getReadableDatabase().query(PublishContract.Publish.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case RESULT_WITH_FILTERS:
                retCursor = getResultByFilter(uri, projection, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        Uri returnUri;

        if (match == RESULT) {

            long id = db.insert(PublishContract.Publish.TABLE_NAME, null, values);

            if (id > 0) // comprobar si inserto bien
                returnUri = PublishContract.Publish.buildResulUri(id);
            else
                throw new SQLException("Failed to insert row into " + uri);
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int deleted;

        if (selection == null)
            selection = "1";

        if (match == RESULT) {
            deleted = db.delete(PublishContract.Publish.TABLE_NAME, selection, selectionArgs);
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (deleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int updated;

        if (selection == null)
            selection = "1";

        if (match == RESULT) {
            updated = db.update(PublishContract.Publish.TABLE_NAME, values, selection, selectionArgs);
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (updated != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return updated;
    }


}
