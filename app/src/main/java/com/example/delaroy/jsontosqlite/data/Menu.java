package com.example.delaroy.jsontosqlite.data;

import android.database.Cursor;

/**
 * Created by delaroy on 11/6/17.
 */

public class Menu {

    public String name;
    public String description;
    public String price;
    public String category;
    public long id;
    public int photo;


    public Menu(Cursor cursor) {

        this.id = cursor.getLong(cursor.getColumnIndex(DbContract.MenuEntry._ID));
        this.name = cursor.getString(cursor.getColumnIndex(DbContract.MenuEntry.COLUMN_NAME));
        this.description = cursor.getString(cursor.getColumnIndex(DbContract.MenuEntry.COLUMN_DESCRIPTION));
        this.price = cursor.getString(cursor.getColumnIndex(DbContract.MenuEntry.COLUMN_PRICE));
        this.category = cursor.getString(cursor.getColumnIndex(DbContract.MenuEntry.COLUMN_CATEGORY));
        this.photo = cursor.getInt(cursor.getColumnIndex(DbContract.MenuEntry.COLUMN_PHOTO));
    }
}
