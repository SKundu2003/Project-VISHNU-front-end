package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.POJO.UserInfo;
import com.example.myapplication.Service.AskHelpFromBackend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CallForHelpService extends Service {

    private SpeechRecognizer speechRecognizer;
    @Override
    public void onCreate() {
        super.onCreate();
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new HelpRecognitionListener());
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startListening();
        return START_STICKY;
    }

    private void startListening() {
        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something...");

        speechRecognizer.startListening(recognizerIntent);
    }

    private void stopListening() {
        speechRecognizer.stopListening();
    }

    private class HelpRecognitionListener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle params) {
            // Not used
        }

        @Override
        public void onBeginningOfSpeech() {
            // Not used
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // Not used
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // Not used
        }

        @Override
        public void onEndOfSpeech() {
            // Not used
        }

        @Override
        public void onError(int error) {
            // Handle speech recognition errors if needed
            startListening();
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (result != null && !result.isEmpty()) {
                String recognizedText = result.toString().toLowerCase();
                if (recognizedText.contains("vishnu help") || recognizedText.contains("bishnu help")) {
                    // Respond to the trigger phrase "hello app"
                    // Open your app or launch the main activity

//                    //Send SMS to help
                    SendSMStoHelpLine smStoHelpLine = new SendSMStoHelpLine();
//                    LocationManager locationManager = new LocationManager();
                    smStoHelpLine.sendSMS("+91 "+MahilaHelpLineNumberIndia.helpLineNumber, "Help me! I am in danger. My current location is: "+StaticClassForLocationSharing.locationDataInStaticForm);

                    //Send SMS to relative
                    smStoHelpLine.sendSMS("+91 "+MahilaHelpLineNumberIndia.policeNumber, "Help me! I am in danger. My current location is: "+StaticClassForLocationSharing.locationDataInStaticForm);

                    Toast.makeText(CallForHelpService.this, "VISHNU activated", Toast.LENGTH_SHORT).show();


                    //Asking for help from backend
                    try {
                        String ownPhoneNumber = UserInfo.userNumber;
                        AskHelpFromBackend askHelpFromBackend = new AskHelpFromBackend();
                        Toast.makeText(CallForHelpService.this, "Your number is "+ownPhoneNumber, Toast.LENGTH_SHORT).show();
                        askHelpFromBackend.execute(ownPhoneNumber);
                        Toast.makeText(CallForHelpService.this, "Help is on the way!", Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        Toast.makeText(CallForHelpService.this, "Exception occurred while asking for help from backend", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            startListening();
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            // Not used
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            // Not used
        }

        //To read the number
        public String readLocationDataFromFile() {
            String fileName = "onlyNumber.txt";

            try {
                // Create a File object representing the file you want to read
                File file = new File(Environment.getExternalStorageDirectory(), fileName);

                if (file.exists()) {
                    FileInputStream fis = new FileInputStream(file);

                    int length;
                    byte[] bytes = new byte[1024];
                    StringBuilder sb = new StringBuilder();

                    while ((length = fis.read(bytes)) != -1) {
                        sb.append(new String(bytes, 0, length));
                    }

                    String readString = sb.toString();
                    fis.close();
                    return readString;

                    // Use the readString as needed
                } else {
                    // Handle the case where the file doesn't exist
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy() {
        stopListening();
        super.onDestroy();
    }
}
