package com.example.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    boolean locationFlag = false; // Flag to check if location is received and stored in file

    private static final int PERMISSION_REQUEST_CODE_SMS = 1;
    private static final int PERMISSION_REQUEST_CODE_LOCATION = 2;

    private LocationManager locationManager;
    private String locationDataFileName = "locationData.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start the service when the app is launched
        startService(new Intent(this, CallForHelpService.class));

        // Initialize the LocationManager
        locationManager = new LocationManager(this);

        // Check and request permissions
        requestPermissionsIfNeeded();

        // Starting receiving location updates
        startLocationUpdates();
    }

    private void requestPermissionsIfNeeded() {
        // Check SMS permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_REQUEST_CODE_SMS);
        }

        // Check location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE_LOCATION);
        }
    }

    private void startLocationUpdates() {
        locationManager.requestLocationUpdates(new LocationManager.LocationListener() {
            @Override
            public void onLocationReceived(String locationData) {
                // Show the location data as a toast
                Toast.makeText(MainActivity.this, "Current Location: " + locationData, Toast.LENGTH_SHORT).show();


                // Storing location data in static form
                StaticClassForLocationSharing.locationDataInStaticForm = locationData;

                // Store locationData in a file
                if(!locationFlag){
                    storeLocationDataToFile(locationData);
                    locationFlag = true;
                }

            }
        });
    }

    private void storeLocationDataToFile(String locationData) {
        try {
            File file = new File(getFilesDir(), locationDataFileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(locationData.getBytes());
            fos.close();
            Toast.makeText(MainActivity.this, "Location data stored in file.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Failed to store location data in file.", Toast.LENGTH_SHORT).show();
        }
    }

    private String readLocationDataFromFile() {
        try {
            File file = new File(getFilesDir(), locationDataFileName);
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            fis.read(buffer);
            fis.close();
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // SMS permission granted, you can send SMS here if needed
                } else {
                    // SMS permission denied, handle it accordingly
                    showPermissionDeniedDialog("SMS");
                }
                break;

            case PERMISSION_REQUEST_CODE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Location permission granted, start location updates
                    startLocationUpdates();
                } else {
                    // Location permission denied, handle it accordingly
                    showPermissionDeniedDialog("Location");
                }
                break;
        }
    }

    private void showPermissionDeniedDialog(String permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission Denied");
        builder.setMessage("This app requires " + permission + " permission to function properly. Please grant the permission from the app settings.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // You can redirect the user to the app settings to grant the permission manually
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Remove location updates when the activity is stopped to save battery
        locationManager.stopLocationUpdates();
    }

    // Update info intent call --> User Info
    public void goToUpdateInfoIntent(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}
