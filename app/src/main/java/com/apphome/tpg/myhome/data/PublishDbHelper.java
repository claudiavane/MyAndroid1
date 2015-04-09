package com.apphome.tpg.myhome.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CLAUDIA on 09/04/2015.
 */
public class PublishDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "publish.db";

    public PublishDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + "Publish" + " (" +
                PublishContract.Publish._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PublishContract.Publish.COL_HOUSE_TYPE + " TEXT NOT NULL," +
                PublishContract.Publish.COL_RENTAL_TYPE + " TEXT NOT NULL," +
                PublishContract.Publish.COL_CITY + " TEXT," +
                PublishContract.Publish.COL_ZONE + " TEXT," +
                PublishContract.Publish.COL_PRICE + " INTEGER," +
                PublishContract.Publish.COL_ADDRESS + " TEXT," +
                PublishContract.Publish.COL_LAND_AREA + " INTEGER," +
                PublishContract.Publish.COL_BUILD_AREA + " INTEGER," +
                PublishContract.Publish.COL_DESCRIPTION + " TEXT);" ;
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "Publish");
        onCreate(db);
    }
}
