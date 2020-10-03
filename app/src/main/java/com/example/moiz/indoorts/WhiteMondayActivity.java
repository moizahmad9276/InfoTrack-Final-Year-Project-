package com.example.moiz.indoorts;

import android.app.NotificationManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moiz.indoorts.WhiteTabs.WhiteFacultyroomFragment;
import com.example.moiz.indoorts.WhiteTabs.WhiteHappeningNowFragment;
import com.example.moiz.indoorts.WhiteTabs.WhiteMondayClassroomFragment;
import com.example.moiz.indoorts.WhiteTabs.WhiteOthersFragment;

public class WhiteMondayActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_monday);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(BeaconNotificationsManager.notificationID1);

        tablayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout =(AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new WhiteMondayClassroomFragment(), "CLASS ROOMS");
        viewPagerAdapter.AddFragment(new WhiteFacultyroomFragment(), "FACULTY ROOM");
        viewPagerAdapter.AddFragment(new WhiteHappeningNowFragment(), "HAPPENING NOW!");
        viewPagerAdapter.AddFragment(new WhiteOthersFragment(), "OTHERS");

        viewPager.setAdapter(viewPagerAdapter);
        tablayout.setupWithViewPager(viewPager);
    }
}
