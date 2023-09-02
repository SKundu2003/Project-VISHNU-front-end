package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.POJO.UserInfo;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class LogInActivity extends AppCompatActivity {

    private EditText name;
    private EditText address;
    private EditText IMPnumber;
    private EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        IMPnumber = findViewById(R.id.impNumber);
        number = findViewById(R.id.number);
    }

    public void saveInfo(View view) {
        // Get data from EditText
        String nameStr = name.getText().toString();
        String addressStr = address.getText().toString();
        String impNumberStr = IMPnumber.getText().toString();
        String numberStr = number.getText().toString();

        //Write the number only in a file
        UserInfo.userNumber = numberStr;

        // Read location data from the file
        String locationData = readLocationDataFromFile();
//        String locationData = StaticClassForLocationSharing.locationDataInStaticForm;
        if (locationData != null) {
            // Split location data into latitude and longitude
            String[] locationDataArray = locationData.split(",");
            String latitude = locationDataArray[0];
            String longitude = locationDataArray[1];
            Toast.makeText(this, "Location data is "+latitude, Toast.LENGTH_SHORT).show();

            // Store data in UserInfo class
            UserInfo userInfo = new UserInfo();
            userInfo.setOwnPhoneNumber(numberStr);
            userInfo.setUserName(nameStr);
            userInfo.setAddress(addressStr);
            userInfo.setRelativePhoneNumber(impNumberStr);
            userInfo.setNonStaticLatitude(latitude);
            userInfo.setNonStaticLongitude(longitude);

            // Serialize UserInfo to JSON using Gson
            Gson gson = new Gson();
            String json = gson.toJson(userInfo);

            // Save data to file
            if (saveDataToFile(json)) {
                Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show();

                // Sending data to backend
                SendDataInBackend sendDataInBackend = new SendDataInBackend(this);

                //URL need to me changes
                String apiUrl = " https://cardiac-spirits-intersection-eating.trycloudflare.com/saveinfomicroservice/saveUserInfo"; // Replace with your API endpoint URL

                
                String textToSend = json;
                try {
                    sendDataInBackend.execute(apiUrl, textToSend);
                    Toast.makeText(this, "Data passed to backend", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Error sending data to backend", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Location data not available", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean saveDataToFile(String data) {
        try {
            FileOutputStream fos = openFileOutput("userInfo.txt", Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String readLocationDataFromFile() {
        try {
            FileInputStream fis = openFileInput("locationData.txt");
            StringBuilder sb = new StringBuilder();
            int content;
            while ((content = fis.read()) != -1) {
                sb.append((char) content);
            }
            fis.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void writeNumberOnly(String number){
        String textToWrite = number;
        String fileName = "onlyNumber.txt";

        try {
            // Create or open the file for writing
            File file = new File(Environment.getExternalStorageDirectory(), fileName);
            FileOutputStream fos = new FileOutputStream(file);

            // Write the string to the file
            fos.write(textToWrite.getBytes());
            fos.close();

            // File successfully written
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}