package com.example.project;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    public static final String CONTENT_AUTHORITY = "com.example.delaroy.jsontosqlite";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_ENTRIES = "entries";

    public static class MenuEntry implements BaseColumns {

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.jsontosqlite.entries";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.jsontosqlite.entry";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRIES).build();

        public static final String TABLE_NAME = "my_spot";
        public static final String COLUMN_SNO = "_id";
        public static final String COLUMN_NAME = "spot_name";
        public static final String COLUMN_AREA = "spot_area";
        public static final String COLUMN_LAT = "spot_lat";
        public static final String COLUMN_LNG = "spot_lng";


    }
}
