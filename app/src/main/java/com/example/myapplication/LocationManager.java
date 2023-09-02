package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.myapplication.POJO.UserInfo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.FileOutputStream;
import java.io.IOException;

public class LocationManager {

    public interface LocationListener {
        void onLocationReceived(String locationData);
    }

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private Context context;
    private String latestLocationData;

    public LocationManager(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    // Location permission related method
    public void requestLocationUpdates(LocationListener listener) {
        // Check for location permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Handle the case when permission is not granted.
            return;
        }

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    Location location = locationResult.getLastLocation();
                    String locationData = saveLocationToFile(location); // Save the location to a file and get the location data
                    latestLocationData = locationData;
                    // Sending location data
                    listener.onLocationReceived(locationData);
                }
            }
        };

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000); // Update location every 10 seconds

        // Request location updates
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private String saveLocationToFile(Location location) {
        String filename = "locationData.txt";
        String locationData = location.getLatitude() + "," + location.getLongitude();
        UserInfo.latitude = String.valueOf(location.getLatitude());
        UserInfo.longitude = String.valueOf(location.getLongitude());

        Toast.makeText(context, "LocationManager latitude "+UserInfo.longitude, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "LocationManager longitude"+UserInfo.latitude, Toast.LENGTH_SHORT).show();
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(locationData.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return locationData;
    }

    public void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}
