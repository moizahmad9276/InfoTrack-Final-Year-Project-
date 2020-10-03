package com.example.moiz.indoorts;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class BeaconNotificationsManager {

    private static final String TAG = "BeaconNotifications";

    private BeaconManager beaconManager;

    private List<BeaconRegion> regionsToMonitor = new ArrayList<>();
    private HashMap<String, String> enterMessages = new HashMap<>();
    private HashMap<String, String> exitMessages = new HashMap<>();

    private Context context;

    public static int notificationID = 0;
    public static int notificationID1 = 1;

    public static final int w = 1, g = 2, l = 3, d = 4;

    public BeaconNotificationsManager(Context context) {
        this.context = context;
        beaconManager = new BeaconManager(context);
        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {

            @Override
            public void onEnteredRegion(BeaconRegion region, List<Beacon> list) {
                Log.d(TAG, "onEnteredRegion: " + region.getIdentifier());
                String message = enterMessages.get(region.getIdentifier());
                if (message != null) {
                    showNotification(message);
                }
            }

            @Override
            public void onExitedRegion(BeaconRegion region) {
                Log.d(TAG, "onExitedRegion: " + region.getIdentifier());
                String message = exitMessages.get(region.getIdentifier());
                if (message != null) {
                    showNotification(message);
                }
            }
        });
    }

    public void addNotification(BeaconID beaconID, String enterMessage, String exitMessage) {
            BeaconRegion region = beaconID.toBeaconRegion();
            enterMessages.put(region.getIdentifier(), enterMessage);
            exitMessages.put(region.getIdentifier(), exitMessage);
            regionsToMonitor.add(region);
    }

    public void startMonitoring() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                for (BeaconRegion region : regionsToMonitor) {
                    beaconManager.startMonitoring(region);
                }
            }
        });
    }

    private void showNotification(String message) {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                if(w == 1){
                    Intent sunWhiteIntent = new Intent(context, WhiteSundayActivity.class);
                    sunWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent sunWhitePendingIntent = PendingIntent.getActivity(context, 0, sunWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder sunbuilder = new NotificationCompat.Builder(context)

                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(sunWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", sunWhitePendingIntent);

                    NotificationManager sunnotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    sunnotificationManager.notify(notificationID++, sunbuilder.build());
                   // break;
                }
                if(g == 2){
                    Intent sunWhiteIntent = new Intent(context, GreenSundayActivity.class);
                    sunWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent sunWhitePendingIntent = PendingIntent.getActivity(context, 0, sunWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder sunbuilder = new NotificationCompat.Builder(context)

                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(sunWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", sunWhitePendingIntent);

                    NotificationManager sunnotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    sunnotificationManager.notify(notificationID++, sunbuilder.build());
                    // break;
                }
                if(l == 3){
                    Intent sunWhiteIntent = new Intent(context, LightblueSundayActivity.class);
                    sunWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent sunWhitePendingIntent = PendingIntent.getActivity(context, 0, sunWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder sunbuilder = new NotificationCompat.Builder(context)

                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(sunWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", sunWhitePendingIntent);

                    NotificationManager sunnotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    sunnotificationManager.notify(notificationID++, sunbuilder.build());
                    // break;
                }
                if(d == 4){
                    Intent sunWhiteIntent = new Intent(context, DarkblueSundayActivity.class);
                    sunWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent sunWhitePendingIntent = PendingIntent.getActivity(context, 0, sunWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder sunbuilder = new NotificationCompat.Builder(context)

                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(sunWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", sunWhitePendingIntent);

                    NotificationManager sunnotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    sunnotificationManager.notify(notificationID++, sunbuilder.build());
                    break;
                }

            case Calendar.MONDAY:
                if(w == 1){
                    Intent monWhiteIntent = new Intent(context, WhiteMondayActivity.class);
                    monWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent monWhitePendingIntent = PendingIntent.getActivity(context, 0, monWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder monbuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(monWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", monWhitePendingIntent);

                    NotificationManager monnotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    monnotificationManager.notify(notificationID++, monbuilder.build());
                  //  break;
                }
                if(g == 2){
                    Intent monWhiteIntent = new Intent(context, GreenMondayActivity.class);
                    monWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent monWhitePendingIntent = PendingIntent.getActivity(context, 0, monWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder monbuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(monWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", monWhitePendingIntent);

                    NotificationManager monnotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    monnotificationManager.notify(notificationID++, monbuilder.build());
                    //  break;
                }
                if(l == 3){
                    Intent monWhiteIntent = new Intent(context, LightblueMondayActivity.class);
                    monWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent monWhitePendingIntent = PendingIntent.getActivity(context, 0, monWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder monbuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(monWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", monWhitePendingIntent);

                    NotificationManager monnotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    monnotificationManager.notify(notificationID++, monbuilder.build());
                    //  break;
                }
                if(d == 4){
                    Intent monWhiteIntent = new Intent(context, DarkblueMondayActivity.class);
                    monWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent monWhitePendingIntent = PendingIntent.getActivity(context, 0, monWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder monbuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(monWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", monWhitePendingIntent);

                    NotificationManager monnotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    monnotificationManager.notify(notificationID++, monbuilder.build());
                    break;
                }



            case Calendar.TUESDAY:
                if(w == 1){
                    Intent tueWhiteIntent = new Intent(context, WhiteTuesdayActivity.class);
                    tueWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent tueWhitePendingIntent = PendingIntent.getActivity(context, 0, tueWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder tuebuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(tueWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", tueWhitePendingIntent);

                    NotificationManager tuenotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    tuenotificationManager.notify(notificationID++, tuebuilder.build());
                    //break;
                }
                if(g == 2){
                    Intent tueWhiteIntent = new Intent(context, GreenTuesdayActivity.class);
                    tueWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent tueWhitePendingIntent = PendingIntent.getActivity(context, 0, tueWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder tuebuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(tueWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", tueWhitePendingIntent);

                    NotificationManager tuenotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    tuenotificationManager.notify(notificationID++, tuebuilder.build());
                    //break;
                }
                if(l == 3){
                    Intent tueWhiteIntent = new Intent(context, LightblueTuesdayActivity.class);
                    tueWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent tueWhitePendingIntent = PendingIntent.getActivity(context, 0, tueWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder tuebuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(tueWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", tueWhitePendingIntent);

                    NotificationManager tuenotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    tuenotificationManager.notify(notificationID++, tuebuilder.build());
                    //break;
                }
                if(d == 4){
                    Intent tueWhiteIntent = new Intent(context, DarkblueTuesdayActivity.class);
                    tueWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent tueWhitePendingIntent = PendingIntent.getActivity(context, 0, tueWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder tuebuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(tueWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", tueWhitePendingIntent);

                    NotificationManager tuenotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    tuenotificationManager.notify(notificationID++, tuebuilder.build());
                    break;
                }

            case Calendar.WEDNESDAY:
                if(w == 1){
                    Intent wedWhiteIntent = new Intent(context, WhiteWednesdayActivity.class);
                    wedWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent wedWhitePendingIntent = PendingIntent.getActivity(context, 0, wedWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder wedbuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(wedWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", wedWhitePendingIntent);

                    NotificationManager wednotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    wednotificationManager.notify(notificationID++, wedbuilder.build());
                    //break;
                }
                if(g == 2){
                    Intent wedWhiteIntent = new Intent(context, GreenWednesdayActivity.class);
                    wedWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent wedWhitePendingIntent = PendingIntent.getActivity(context, 0, wedWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder wedbuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(wedWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", wedWhitePendingIntent);

                    NotificationManager wednotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    wednotificationManager.notify(notificationID++, wedbuilder.build());
                    //break;
                }
                if(l == 3){
                    Intent wedWhiteIntent = new Intent(context, LightblueWednesdayActivity.class);
                    wedWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent wedWhitePendingIntent = PendingIntent.getActivity(context, 0, wedWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder wedbuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(wedWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", wedWhitePendingIntent);

                    NotificationManager wednotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    wednotificationManager.notify(notificationID++, wedbuilder.build());
                    //break;
                }
                if(d == 4){
                    Intent wedWhiteIntent = new Intent(context, DarkblueWednesdayActivity.class);
                    wedWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent wedWhitePendingIntent = PendingIntent.getActivity(context, 0, wedWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder wedbuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(wedWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", wedWhitePendingIntent);

                    NotificationManager wednotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    wednotificationManager.notify(notificationID++, wedbuilder.build());
                    break;
                }

            case Calendar.THURSDAY:
                if(w == 1){
                    Intent thuWhiteIntent = new Intent(context, WhiteThursdayActivity.class);
                    thuWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent thuWhitePendingIntent = PendingIntent.getActivity(context, 0, thuWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder thubuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(thuWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", thuWhitePendingIntent);

                    NotificationManager thunotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    thunotificationManager.notify(notificationID++, thubuilder.build());
                    //break;
                }
                if(g == 2){
                    Intent thuWhiteIntent = new Intent(context, GreenThursdayActivity.class);
                    thuWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent thuWhitePendingIntent = PendingIntent.getActivity(context, 0, thuWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder thubuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(thuWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", thuWhitePendingIntent);

                    NotificationManager thunotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    thunotificationManager.notify(notificationID++, thubuilder.build());
                    //break;
                }
                if(l == 3){
                    Intent thuWhiteIntent = new Intent(context, LightblueThursdayActivity.class);
                    thuWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent thuWhitePendingIntent = PendingIntent.getActivity(context, 0, thuWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder thubuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(thuWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", thuWhitePendingIntent);

                    NotificationManager thunotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    thunotificationManager.notify(notificationID++, thubuilder.build());
                    //break;
                }
                if(d == 4){
                    Intent thuWhiteIntent = new Intent(context, DarkblueThursdayActivity.class);
                    thuWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent thuWhitePendingIntent = PendingIntent.getActivity(context, 0, thuWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder thubuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(thuWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", thuWhitePendingIntent);

                    NotificationManager thunotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    thunotificationManager.notify(notificationID++, thubuilder.build());
                    break;
                }

            case Calendar.FRIDAY:
                if(w == 1){
                    Intent friWhiteIntent = new Intent(context, WhiteFridayActivity.class);
                    friWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent friWhitePendingIntent = PendingIntent.getActivity(context, 0, friWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder fribuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(friWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", friWhitePendingIntent);

                    NotificationManager frinotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    frinotificationManager.notify(notificationID++, fribuilder.build());
                    //break;
                }
                if(g == 2){
                    Intent friWhiteIntent = new Intent(context, GreenFridayActivity.class);
                    friWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent friWhitePendingIntent = PendingIntent.getActivity(context, 0, friWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder fribuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(friWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", friWhitePendingIntent);

                    NotificationManager frinotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    frinotificationManager.notify(notificationID++, fribuilder.build());
                    //break;
                }
                if(l == 3){
                    Intent friWhiteIntent = new Intent(context, LightblueFridayActivity.class);
                    friWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent friWhitePendingIntent = PendingIntent.getActivity(context, 0, friWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder fribuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(friWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", friWhitePendingIntent);

                    NotificationManager frinotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    frinotificationManager.notify(notificationID++, fribuilder.build());
                    //break;
                }
                if(d == 4){
                    Intent friWhiteIntent = new Intent(context, DarkblueFridayActivity.class);
                    friWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent friWhitePendingIntent = PendingIntent.getActivity(context, 0, friWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder fribuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(friWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", friWhitePendingIntent);

                    NotificationManager frinotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    frinotificationManager.notify(notificationID++, fribuilder.build());
                    break;
                }

            case Calendar.SATURDAY:
                if(w == 1){
                    Intent satWhiteIntent = new Intent(context, WhiteSaturdayActivity.class);
                    satWhiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent satWhitePendingIntent = PendingIntent.getActivity(context, 0, satWhiteIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder satbuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(satWhitePendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", satWhitePendingIntent);

                    NotificationManager satnotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    satnotificationManager.notify(notificationID++, satbuilder.build());
                  //  break;
                }
              if(g == 2){
                    Intent satGreenIntent = new Intent(context, GreenSaturdayActivity.class);
                    satGreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent satGreenPendingIntent = PendingIntent.getActivity(context, 0, satGreenIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder satbuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(satGreenPendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", satGreenPendingIntent);

                    NotificationManager satnotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    satnotificationManager.notify(notificationID++, satbuilder.build());
                 //   break;
                }
                if(l == 3){
                    Intent satGreenIntent = new Intent(context, LightblueSaturdayActivity.class);
                    satGreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent satGreenPendingIntent = PendingIntent.getActivity(context, 0, satGreenIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder satbuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(satGreenPendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", satGreenPendingIntent);

                    NotificationManager satnotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    satnotificationManager.notify(notificationID++, satbuilder.build());
                    //   break;
                }
                if(d == 4){
                    Intent satGreenIntent = new Intent(context, DarkblueSaturdayActivity.class);
                    satGreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent satGreenPendingIntent = PendingIntent.getActivity(context, 0, satGreenIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder satbuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(android.R.drawable.ic_dialog_info)
                            .setContentTitle("InfoTrack")
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setContentIntent(satGreenPendingIntent)
                            .addAction(R.drawable.ic_checkmark, "Click Here", satGreenPendingIntent);

                    NotificationManager satnotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    satnotificationManager.notify(notificationID++, satbuilder.build());
                    break;
                }
        }
    }
}
