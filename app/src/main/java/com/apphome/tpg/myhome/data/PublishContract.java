package com.apphome.tpg.myhome.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by CLAUDIA on 09/04/2015.
 */
public class PublishContract {

    public static final String CONTENT_AUTHORITY = "com.apphome.tpg.myhome";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_RESULT = "publish";

    public static class Publish implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESULT).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESULT;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESULT;

        //table name
        public static final String TABLE_NAME = "publish";

        //columnas
        public static final String COL_HOUSE_TYPE= "houseType";
        public static final String COL_RENTAL_TYPE ="rentalType";
        public static final String COL_CITY ="city";
        public static final String COL_ZONE ="zone";
        public static final String COL_ADDRESS ="address";
        public static final String COL_PRICE ="price";
        public static final String COL_LAND_AREA ="landArea";
        public static final String COL_BUILD_AREA ="buildArea";
        public static final String COL_DESCRIPTION ="description";
        public static final String COL_IMAGES ="images";

        public static Uri buildResulUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static int getPublishFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

    }

}
