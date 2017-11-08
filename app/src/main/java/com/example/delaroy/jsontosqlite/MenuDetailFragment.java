package com.example.delaroy.jsontosqlite;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delaroy.jsontosqlite.data.DbContract;


/**
 * Created by delaroy on 11/6/17.
 */

public class MenuDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    TextView rname, rdescription, rprice;
    String sname,sdescription,sprice;
    Uri data;
    private static final int MENU_LOADER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_detail_fragment, container,
                false);
        rname = (TextView) view.findViewById(R.id.menu_title) ;
        rdescription = (TextView) view.findViewById(R.id.menu_description) ;
        rprice = (TextView) view.findViewById(R.id.menu_price) ;

        if (getArguments() != null) {
           String mname =  getArguments().getString("MENUDATA");
            data = Uri.parse(mname);
        }


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if (data != null){
            getLoaderManager().initLoader(MENU_LOADER, null, this);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] PROJECTION = new String[]{
                DbContract.MenuEntry._ID,
                DbContract.MenuEntry.COLUMN_NAME,
                DbContract.MenuEntry.COLUMN_DESCRIPTION,
                DbContract.MenuEntry.COLUMN_PRICE,
                DbContract.MenuEntry.COLUMN_CATEGORY,
                DbContract.MenuEntry.COLUMN_PHOTO
        };

        return new CursorLoader(getActivity(),   // Parent activity context
                data,   // Provider content URI to query
                PROJECTION,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {

            int name = cursor.getColumnIndex(DbContract.MenuEntry.COLUMN_NAME);
            int description = cursor.getColumnIndex(DbContract.MenuEntry.COLUMN_DESCRIPTION);
            int price = cursor.getColumnIndex(DbContract.MenuEntry.COLUMN_PRICE);

            sname = cursor.getString(name);
            sdescription = cursor.getString(description);
            sprice = cursor.getString(price);

            rname.setText(sname);
            rdescription.setText(sdescription);
            rprice.setText(sprice);

        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
