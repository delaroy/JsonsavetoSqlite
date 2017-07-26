package com.example.delaroy.jsontosqlite.data;

import android.provider.BaseColumns;

/**
 * Created by delaroy on 7/22/17.
 */

public class DbContract {

    public static final class MenuEntry implements BaseColumns {

        public static final String TABLE_NAME = "menu";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_PHOTO = "photo";
    }
}
