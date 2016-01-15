package com.bendaschel.lab2;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotSame;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    @Test
    public void testDrawerToggle(){

        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        DrawerLayout drawer = (DrawerLayout) mainActivity.findViewById(R.id.drawer_layout);
        CharSequence initialTitle = mainActivity.getSupportActionBar().getTitle();
        drawer.openDrawer(GravityCompat.START);
        CharSequence newTitle = mainActivity.getSupportActionBar().getTitle();
        assertThat(initialTitle, not(newTitle));
    }
}
