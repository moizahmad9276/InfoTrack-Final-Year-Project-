package com.example.moiz.indoorts;

import android.app.NotificationManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moiz.indoorts.DarkblueTabs.DarkblueFacultyroomFragment;
import com.example.moiz.indoorts.DarkblueTabs.DarkblueHappeningNowFragment;
import com.example.moiz.indoorts.DarkblueTabs.DarkblueMondayClassroomFragment;
import com.example.moiz.indoorts.DarkblueTabs.DarkblueOthersFragment;

public class DarkblueMondayActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_darkblue_monday);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(BeaconNotificationsManager.notificationID1);

        tablayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout =(AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new DarkblueMondayClassroomFragment(), "CLASS ROOMS");
        viewPagerAdapter.AddFragment(new DarkblueFacultyroomFragment(), "FACULTY ROOM");
        viewPagerAdapter.AddFragment(new DarkblueHappeningNowFragment(), "HAPPENING NOW!");
        viewPagerAdapter.AddFragment(new DarkblueOthersFragment(), "OTHERS");

        viewPager.setAdapter(viewPagerAdapter);
        tablayout.setupWithViewPager(viewPager);
    }
}
