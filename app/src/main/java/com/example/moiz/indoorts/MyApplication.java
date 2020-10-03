package com.example.moiz.indoorts;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.estimote.coresdk.common.config.EstimoteSDK;

import io.fabric.sdk.android.Fabric;

public class MyApplication extends Application {

    public static final String PROXIMITY_API_KEY = "86be6f2e48b6bb014793b0599cf0fc52";
    public static final String PROXIMITY_APP_ID = "infotrack-g38";

    private boolean beaconNotificationsEnabled = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(getApplicationContext(), new Crashlytics());

        EstimoteSDK.initialize(getApplicationContext(), PROXIMITY_APP_ID, PROXIMITY_API_KEY);
        EstimoteSDK.enableDebugLogging(true);
    }

    public void enableBeaconNotifications() {
        if (beaconNotificationsEnabled) {
            return;
        }

        BeaconNotificationsManager beaconNotificationsManager = new BeaconNotificationsManager(getApplicationContext());
        beaconNotificationsManager.addNotification(
            // TODO: replace with UUID, major and minor of your own beacon
            //white
            new BeaconID("8053276E-F248-B499-6A3B-7C1B0145644C", 22296, 5974),
            "What's happening out here?",
            "");

//        beaconNotificationsManager.addNotification(
//                //darkblue
//                // TODO: replace with UUID, major and minor of your own beacon
//                new BeaconID("A44E70EA-2860-ED07-9537-5EF85F4CA905", 3528, 53114),
//                "What's happening out here?",
//                "");
//
//        beaconNotificationsManager.addNotification(
//                //lightblue
//                // TODO: replace with UUID, major and minor of your own beacon
//                new BeaconID("F44A68C5-396B-7BD8-30F5-131398B01562", 62346, 16393),
//                "What's happening out here?",
//                "");
//
//        beaconNotificationsManager.addNotification(
//            //green
//            // TODO: replace with UUID, major and minor of your own beacon
//            new BeaconID("10BEE323-FABE-2760-4398-9E35E1F68666", 49009, 26393),
//            "What's happening out here?",
//            "");

        beaconNotificationsManager.startMonitoring();
        beaconNotificationsEnabled = true;
    }

    public boolean isBeaconNotificationsEnabled() {
        return beaconNotificationsEnabled;
    }
}
