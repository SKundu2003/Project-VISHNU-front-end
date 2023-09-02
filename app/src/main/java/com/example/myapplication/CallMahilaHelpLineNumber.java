package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;

public class CallMahilaHelpLineNumber {
    private Context context;
    private static final int CALL_PERMISSION_REQUEST_CODE = 1;

    public CallMahilaHelpLineNumber(Context context) {
        this.context = context;
    }

    public void makePhoneCall(String phoneNumber) {
        String number = "+91" + phoneNumber;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(number));

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            context.startActivity(callIntent);
        } else {
            // Request the CALL_PHONE permission
            if (context instanceof Activity) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
            } else {
                // Handle the case when the context is not an instance of Activity
                // You might want to provide feedback to the user or handle this differently
            }
        }
    }
}
