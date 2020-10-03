package com.example.moiz.indoorts;

import android.app.NotificationManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moiz.indoorts.LightblueTabs.LightblueFacultyroomFragment;
import com.example.moiz.indoorts.LightblueTabs.LightblueHappeningNowFragment;
import com.example.moiz.indoorts.LightblueTabs.LightblueOthersFragment;
import com.example.moiz.indoorts.LightblueTabs.LightblueTuesdayClassroomFragment;

public class LightblueTuesdayActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightblue_tuesday);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(BeaconNotificationsManager.notificationID1);

        tablayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout =(AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new LightblueTuesdayClassroomFragment(), "CLASS ROOMS");
        viewPagerAdapter.AddFragment(new LightblueFacultyroomFragment(), "FACULTY ROOM");
        viewPagerAdapter.AddFragment(new LightblueHappeningNowFragment(), "HAPPENING NOW!");
        viewPagerAdapter.AddFragment(new LightblueOthersFragment(), "OTHERS");

        viewPager.setAdapter(viewPagerAdapter);
        tablayout.setupWithViewPager(viewPager);
    }
}
