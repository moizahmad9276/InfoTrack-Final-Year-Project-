package com.example.moiz.indoorts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

public class StudentAccountSettingActivity extends AppCompatActivity {

    private static final String TAG = "StudentAccountSetting";
    private ListView listview;
    private ImageView back;
    public SectionsStatePagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private RelativeLayout relativeLayout;
    private Context mContext;
    Animation fromBottom;
    private LinearLayout layouthome;
    ProgressBar progressBar;
    TextView tvview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_account_setting);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.fromnow);
        layouthome = (LinearLayout) findViewById(R.id.textstdacc);
        layouthome.startAnimation(fromBottom);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tvview = (TextView) findViewById(R.id.tvrandom);
        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);

        viewPager = (ViewPager) findViewById(R.id.container);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout1);

        accountSettingList();
        setupFragments();
        getIncomingIntent();

        mContext = StudentAccountSettingActivity.this;
    }

    public void setViewPager(int fragmentNumber){
        relativeLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        tvview.setVisibility(View.VISIBLE);
        Log.d(TAG, "setViewPager: navigating to Fragment #:" +fragmentNumber);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(fragmentNumber);
        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);
    }

    public void accountSettingList(){
        listview = (ListView) findViewById(R.id.lvaccountsettings);
        ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.edit_profile));
        options.add(getString((R.string.logout)));

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, options);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: navigating to fragment # " +position);
                setViewPager(position);
            }
        });
    }

    private void getIncomingIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.selected_image)) || intent.hasExtra(getString(R.string.selected_bitmap))) {
            if (intent.getStringExtra(getString(R.string.return_to_fragment)).equals(getString(R.string.edit_profile_fragment))) {
                if (intent.hasExtra(getString(R.string.selected_image))) {
                    FirebaseMethods firebaseMethods = new FirebaseMethods(StudentAccountSettingActivity.this);
                    firebaseMethods.uploadNewStudentPhoto(getString(R.string.profile_photo), 0,
                            intent.getStringExtra(getString(R.string.selected_image)), null);
                } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
                    FirebaseMethods firebaseMethods = new FirebaseMethods(StudentAccountSettingActivity.this);
                    firebaseMethods.uploadNewStudentPhoto(getString(R.string.profile_photo), 0, null,
                            (Bitmap) intent.getParcelableExtra(getString(R.string.selected_bitmap)));
                }
            }
        }
        if(intent.hasExtra(getString(R.string.calling_activity))){
            setViewPager(pagerAdapter.getFragmentNumber(getString(R.string.edit_profile_fragment)));
        }
    }

    private void setupFragments(){
        pagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new StudentEditProfileFragment(), getString(R.string.edit_profile));
        pagerAdapter.addFragment(new StudentLogoutFragment(), getString(R.string.logout));
    }
}
