package com.bendaschel.lab2;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Benjamin Daschel
 * The planet images came from the Google Navigation drawer sample code
 * https://developer.android.com/training/implementing-navigation/nav-drawer.html
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // Handy annotations to remove "findViewById" and casting boilerplate
    // Requires installation via gradle
    // https://jakewharton.github.io/butterknife/
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.lv_drawer)
    ListView mDrawerListView;

    @Bind(R.id.imageView)
    ImageView mImageView;

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mActivityTitle;
    private int mCurrentItem;
    private List<NavDrawerItem> mNavDrawerItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mActivityTitle = getTitle();
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setActionBarTitle(getString(R.string.navigation_drawer_open));
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                setActionBarTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerListView.setOnItemClickListener(this);
        setupActionBar();
        setupListView();

        if(savedInstanceState != null){
            int lastPosition = savedInstanceState.getInt(AppConstants.EXTRA_LIST_ITEM);
            setImageViewItem(lastPosition);
        }else{
            setImageViewItem(0);
        }
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (mDrawerLayout != null && ! mDrawerLayout.isDrawerOpen(mDrawerListView)){
            getMenuInflater().inflate(R.menu.main, menu);
        }else{
            menu.clear();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Per Android docs, hosting activity must invoke this method on the drawer toggle
        // https://developer.android.com/reference/android/support/v7/app/ActionBarDrawerToggle.html#onOptionsItemSelected(android.view.MenuItem)
        if (mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()){
            case R.id.action_about:
                Toast.makeText(this, R.string.toast_about, Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Per Android docs, hosting activity must invoke this method on the drawer toggle
        // https://developer.android.com/reference/android/support/v7/app/ActionBarDrawerToggle.html#onConfigurationChanged(android.content.res.Configuration)
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupListView() {
        mNavDrawerItemList = makeNavDrawerList();
        ListAdapter listAdapter = new ListAdapter();
        mDrawerListView.setAdapter(listAdapter);
    }

    private void setActionBarTitle(CharSequence title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(title);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("MainActivity", "Item Clicked");
        setImageViewItem(position);
        mDrawerLayout.closeDrawer(mDrawerListView);
    }

    private void setImageViewItem(int position) {
        int resId = mNavDrawerItemList.get(position).getFullSizeId();
        mImageView.setImageResource(resId);
        mCurrentItem = position;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(AppConstants.EXTRA_LIST_ITEM, mCurrentItem);
    }

    private List<NavDrawerItem> makeNavDrawerList() {

        TypedArray drawables = getResources().obtainTypedArray(R.array.nav_item_drawables);
        TypedArray names = getResources().obtainTypedArray(R.array.nav_item_names);
        TypedArray mainContent = getResources().obtainTypedArray(R.array.nav_item_main_content);
        List<NavDrawerItem> navDrawerItems = new ArrayList<>();
        int i = 0;
        while (i < drawables.length() && i < names.length()){
            navDrawerItems.add(
                    new NavDrawerItem(
                            drawables.getResourceId(i, 0),
                            names.getString(i),
                            mainContent.getResourceId(i, 0)
                    )
            );
            i++;
        }
        drawables.recycle();
        names.recycle();
        mainContent.recycle();
        return navDrawerItems;
    }

    private class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNavDrawerItemList.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavDrawerItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*
               Setting images inside of a textview is more efficient than using a
               linear layout with an image view and a textview. (According to Android studio warning)
               https://developer.android.com/reference/android/widget/TextView.html
             */
            if (convertView == null){
                convertView = getLayoutInflater()
                        .inflate(R.layout.item_nav_drawer, parent, false);
            }
            TextView tv = (TextView) convertView;
            NavDrawerItem item = mNavDrawerItemList.get(position);
            tv.setText(item.getName());
            tv.setCompoundDrawablesWithIntrinsicBounds(item.getId(), 0, 0, 0);

            return tv;
        }
    }
}
