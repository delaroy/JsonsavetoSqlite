package com.example.delaroy.jsontosqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.delaroy.jsontosqlite.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by delaroy on 7/22/17.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();

    private Resources mResources;
    private static final String DATABASE_NAME = "menu.db";
    private static final int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase db;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();

        db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_BUGS_TABLE = "CREATE TABLE " + DbContract.MenuEntry.TABLE_NAME + " (" +
                DbContract.MenuEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DbContract.MenuEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                DbContract.MenuEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                DbContract.MenuEntry.COLUMN_PRICE + " TEXT NOT NULL, " +
                DbContract.MenuEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                DbContract.MenuEntry.COLUMN_PHOTO + " INTEGER NOT NULL " + " );";


        db.execSQL(SQL_CREATE_BUGS_TABLE);
        Log.d(TAG, "Database Created Successfully" );


        try {
            readDataToDb(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void readDataToDb(SQLiteDatabase db) throws IOException, JSONException {


        final String MNU_NAME = "name";
        final String MNU_DESCRIPTION = "description";
        final String MNU_PRICE = "price";
        final String MNU_CATEGORY = "category";
        final String MNU_PHOTO = "photo";

        try {
            String jsonDataString = readJsonDataFromFile();
            JSONArray menuItemsJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < menuItemsJsonArray.length(); ++i) {

                String name;
                String description;
                String price;
                String category;
                String photo;


                JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(i);


                name = menuItemObject.getString(MNU_NAME);
                description = menuItemObject.getString(MNU_DESCRIPTION);
                price = menuItemObject.getString(MNU_PRICE);
                category = menuItemObject.getString(MNU_CATEGORY);
                photo = menuItemObject.getString(MNU_PHOTO);


                ContentValues menuValues = new ContentValues();

                menuValues.put(DbContract.MenuEntry.COLUMN_NAME, name);
                menuValues.put(DbContract.MenuEntry.COLUMN_DESCRIPTION, description);
                menuValues.put(DbContract.MenuEntry.COLUMN_PRICE, price);
                menuValues.put(DbContract.MenuEntry.COLUMN_CATEGORY, category);
                menuValues.put(DbContract.MenuEntry.COLUMN_PHOTO, photo);

                db.insert(DbContract.MenuEntry.TABLE_NAME, null, menuValues);


                Log.d(TAG, "Inserted Successfully " + menuValues );
            }


        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }

    }

    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = mResources.openRawResource(R.raw.menu_item);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }
}
