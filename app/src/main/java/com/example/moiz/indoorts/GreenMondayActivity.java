package com.example.moiz.indoorts;

import android.app.NotificationManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moiz.indoorts.GreenTabs.GreenFacultyroomFragment;
import com.example.moiz.indoorts.GreenTabs.GreenHappeningNowFragment;
import com.example.moiz.indoorts.GreenTabs.GreenMondayClassroomFragment;
import com.example.moiz.indoorts.GreenTabs.GreenOthersFragment;

public class GreenMondayActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_monday);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(BeaconNotificationsManager.notificationID1);

        tablayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout =(AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new GreenMondayClassroomFragment(), "CLASS ROOMS");
        viewPagerAdapter.AddFragment(new GreenFacultyroomFragment(), "FACULTY ROOM");
        viewPagerAdapter.AddFragment(new GreenHappeningNowFragment(), "HAPPENING NOW!");
        viewPagerAdapter.AddFragment(new GreenOthersFragment(), "OTHERS");

        viewPager.setAdapter(viewPagerAdapter);
        tablayout.setupWithViewPager(viewPager);
    }
}
