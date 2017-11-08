package com.example.delaroy.jsontosqlite;

/**
 * Created by delaroy on 7/28/17.
 */


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.delaroy.jsontosqlite.data.DbContract;
import com.example.delaroy.jsontosqlite.data.Menu;


/**
 * RecyclerView adapter extended with project-specific required methods.
 */

public class MenuListAdapter extends
        RecyclerView.Adapter<MenuListAdapter.MenuHolder> {

    private static final String TAG = MenuListAdapter.class.getSimpleName();

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    /* ViewHolder for each insect item */
    public class MenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView menuName, menuPrice, menuCategory, menuDescription;
        ImageView image;
        int pos;


        public MenuHolder(View itemView) {
            super(itemView);


            menuName = (TextView) itemView.findViewById(R.id.menu_item_name);
            menuPrice = (TextView) itemView.findViewById(R.id.menu_item_price);
            menuCategory = (TextView) itemView.findViewById(R.id.menu_item_category);
            menuDescription = (TextView) itemView.findViewById(R.id.menu_item_description);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
           postItemClick(this);
        }
    }

    private Cursor mCursor = null;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private final LayoutInflater mInflater;

    public MenuListAdapter(Cursor cursor, Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        mCursor = cursor;
        //this.mCursor = cursor;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    private void postItemClick(MenuHolder holder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
        }
    }

    public void setData(Cursor cursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.menu_item, parent, false);
        return new MenuHolder(view);
    }


    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {

        if (mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                int menuname = mCursor.getColumnIndex(DbContract.MenuEntry.COLUMN_NAME);
                int menuprice = mCursor.getColumnIndex(DbContract.MenuEntry.COLUMN_PRICE);
                int menucategory = mCursor.getColumnIndex(DbContract.MenuEntry.COLUMN_CATEGORY);
                int menudescription = mCursor.getColumnIndex(DbContract.MenuEntry.COLUMN_DESCRIPTION);

                String menuName = mCursor.getString(menuname);
                String menuPrice = mCursor.getString(menuprice);
                String menuCategory = mCursor.getString(menucategory);
                String menuDescription = mCursor.getString(menudescription);

                holder.menuName.setText(menuName);

                holder.menuPrice.setText(menuPrice);
                holder.menuCategory.setText(menuCategory);
                holder.menuDescription.setText(menuDescription);

                Log.e (TAG, "onBindViewHolder: is on point");
            } else {
                Log.e (TAG, "onBindViewHolder: Cursor is null.");
            }
        } else {
            Log.e (TAG, "onBindViewHolder: Cursor is null.");
        }

    }



    @Override
    public int getItemCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }


    public Menu getItem(int position) {
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("Invalid item position requested");
        }

        return new Menu(mCursor);
    }
    @Override
    public long getItemId(int position) {
        return getItem(position).id;
    }

}
