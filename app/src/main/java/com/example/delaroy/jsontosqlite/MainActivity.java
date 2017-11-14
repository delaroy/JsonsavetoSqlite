package com.example.delaroy.jsontosqlite;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;


import com.example.delaroy.jsontosqlite.data.DbContract;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "MainActivity";

    RecyclerView recyclerView;
    MenuListAdapter adapter;

    private static final String[] PROJECTION = new String[]{
            DbContract.MenuEntry._ID,
            DbContract.MenuEntry.COLUMN_NAME,
            DbContract.MenuEntry.COLUMN_DESCRIPTION,
            DbContract.MenuEntry.COLUMN_PRICE,
            DbContract.MenuEntry.COLUMN_CATEGORY,
            DbContract.MenuEntry.COLUMN_PHOTO
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new MenuListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String queryUri = DbContract.MenuEntry.CONTENT_URI.toString();
        return new CursorLoader(this, Uri.parse(queryUri), PROJECTION, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        adapter.setData(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        adapter.setData(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                //onSearchRequested();
                return true;
            default:
                return false;
        }
    }

}
