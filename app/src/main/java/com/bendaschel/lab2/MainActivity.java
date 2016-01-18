package com.bendaschel.lab2;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.lv_drawer)
    ListView mDrawerListView;

    @Bind(R.id.imageView)
    ImageView mImageView;

    private ActionBarDrawerToggle mActionBarToggle;
    private CharSequence mActivityTitle;
    private int mCurrentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mActivityTitle = getTitle();
        mActionBarToggle = new ActionBarDrawerToggle(
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
        mDrawerLayout.setDrawerListener(mActionBarToggle);
        mDrawerListView.setOnItemClickListener(this);
        setupListView();

        if(savedInstanceState != null){
            int lastPosition = savedInstanceState.getInt(AppConstants.EXTRA_LIST_ITEM);
            setImageViewItem(lastPosition);
        }else{
            setImageViewItem(0);
        }
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

        switch (item.getItemId()){
            case R.id.action_about:
                Toast.makeText(this, R.string.toast_about, Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupListView(){
        mDrawerListView.setAdapter(
                new ArrayAdapter<ListItem>(this, R.layout.item_nav_drawer,
                        R.id.tv_nav_list_item, ListItem.values())
        );
    }

    private void setActionBarTitle(CharSequence title){
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
        ListItem item = ListItem.values()[position];
        mImageView.setImageResource(item.getId());
        mCurrentItem = position;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(AppConstants.EXTRA_LIST_ITEM, mCurrentItem);
    }

}
