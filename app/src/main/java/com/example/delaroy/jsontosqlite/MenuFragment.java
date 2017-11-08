package com.example.delaroy.jsontosqlite;


import android.app.FragmentTransaction;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.delaroy.jsontosqlite.data.DbContract;

/**
 * Created by delaroy on 11/6/17.
 */

public class MenuFragment extends Fragment implements MenuListAdapter.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    RecyclerView recyclerView;
    MenuListAdapter adapter;
    private static final int MENU_LOADER = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        adapter = new MenuListAdapter(null, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(MENU_LOADER,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] PROJECTION = new String[]{
                DbContract.MenuEntry._ID,
                DbContract.MenuEntry.COLUMN_NAME,
                DbContract.MenuEntry.COLUMN_DESCRIPTION,
                DbContract.MenuEntry.COLUMN_PRICE,
                DbContract.MenuEntry.COLUMN_CATEGORY,
                DbContract.MenuEntry.COLUMN_PHOTO
        };

        return new CursorLoader(getActivity(),   // Parent activity context
                DbContract.MenuEntry.CONTENT_URI,   // Provider content URI to query
                PROJECTION,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order

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
    public void onItemClick(View v, int position) {

        Bundle bundle = new Bundle();
        Uri currentTaskUri = ContentUris.withAppendedId(DbContract.MenuEntry.CONTENT_URI, adapter.getItemId(position));
        String uri = currentTaskUri.toString();
        bundle.putString("MENUDATA", uri);
        MenuDetailFragment fragmentObject = new MenuDetailFragment();
        fragmentObject.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,fragmentObject);
        ft.addToBackStack(null).commit();;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
