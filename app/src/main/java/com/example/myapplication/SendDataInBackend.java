package com.example.myapplication;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class SendDataInBackend  extends AsyncTask<String, Void, String> {

    Context context;
    SendDataInBackend(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        String textToSend = params[1];
        String result = null;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            // Set the content type for the request
            httpPost.setHeader("Content-Type", "application/json");

            // Set the request body
            httpPost.setEntity(new StringEntity(textToSend));

            HttpResponse response = httpClient.execute(httpPost);

            // Check if the request was successful (status code 200)
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
//                Toast.makeText(context, "Success to send in backend", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(context, "Failed to send in backend", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
//            Toast.makeText(context, "Exception occurred before sending to backend", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        // Handle the response in the main thread
        if (result != null) {
            Log.d("HTTP Response", result);

            // Process the response data here
        } else {
            Log.e("HTTP Response", "No data received.");
        }
    }
}
