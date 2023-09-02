package com.example.myapplication.Service;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.myapplication.POJO.UserInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class AskHelpFromBackend extends AsyncTask<String, Void, String> {

//    URL need to be changed
    private static final String BASE_URL = "https://cardiac-spirits-intersection-eating.trycloudflare.com/mastermicroservicetosendlocation"; // Replace with your API base URL

    @Override
    protected String doInBackground(String... params) {
        String ownPhoneNumber = UserInfo.userNumber;

        OkHttpClient client = new OkHttpClient();

        // Build the URL with the path variable
        String apiUrl = BASE_URL + "/sendLocation/" + ownPhoneNumber;

//        String apiUrl = BASE_URL;

        Request request = new Request.Builder()
                .url(apiUrl)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                // Handle the error response here
                Log.e("API Request", "Failed with status code: " + response.code());
                return null;
            }
        } catch (Exception e) {
            // Handle exceptions here
            Log.e("API Request", "Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        // Handle the API response on the UI thread
        if (response != null) {
            Log.d("API Response", response);
            // Process the API response here
        } else {
            Log.e("API Response", "API request failed.");
        }
    }


}
