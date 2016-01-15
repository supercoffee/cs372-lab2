package com.bendaschel.lab2;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.lv_drawer)
    RelativeLayout mDrawerListView;

    private ActionBarDrawerToggle mActionBarToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mDrawerLayout.setDrawerTitle(GravityCompat.END, "Navigation drawer");
        mActionBarToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
    }

//    @OnItemClick(R.id.lv_drawer)
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        mDrawerLayout.closeDrawers();
//
//    }
}
