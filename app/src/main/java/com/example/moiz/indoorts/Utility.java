package com.example.moiz.indoorts;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class Utility {

    public static final int EXTERNAL_PERMISSION = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context){
        int currentAPIversion = Build.VERSION.SDK_INT;
        if(currentAPIversion >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true).setTitle("Permission necessary").setMessage("External storage permission is necessary")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_PERMISSION);
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else{
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_PERMISSION);
                }
                return false;
            }
            else{
                return true;
            }
        }
        else{
            return true;
        }
    }
}
