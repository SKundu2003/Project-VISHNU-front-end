package com.example.myapplication;

import android.telephony.SmsManager;

public class SendSMStoHelpLine {
//    private Context context;
//
//    public SendSMStoHelpLine(Context context) {
//        this.context = context;
//    }

    public void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            // If you want to show a toast message when the SMS is sent successfully
//            Toast.makeText(context, "SMS sent!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            // If you want to show a toast message when the SMS sending fails
//            Toast.makeText(context, "SMS sending failed.", Toast.LENGTH_SHORT).show();
        }
    }
}
