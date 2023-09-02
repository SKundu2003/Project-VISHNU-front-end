package com.example.myapplication.POJO;

import com.google.gson.annotations.SerializedName;

public class UserInfo {

    public static String userNumber;
    @SerializedName("ownPhoneNumber")
    public String ownPhoneNumber;

    @SerializedName("userName")
    public String userName;

    @SerializedName("address")
    public String address;

    @SerializedName("relativePhoneNumber")
    public String relativePhoneNumber;

    @SerializedName("nonStaticLatitude")
    public String nonStaticLatitude;

    @SerializedName("nonStaticLongitude")
    public String nonStaticLongitude;

    @SerializedName("latitude")
    public static String latitude;

    @SerializedName("longitude")
    public static String longitude;

    // Constructors, getters, setters (if needed), etc.


    public String getOwnPhoneNumber() {
        return ownPhoneNumber;
    }

    public void setOwnPhoneNumber(String ownPhoneNumber) {
        this.ownPhoneNumber = ownPhoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRelativePhoneNumber() {
        return relativePhoneNumber;
    }

    public void setRelativePhoneNumber(String relativePhoneNumber) {
        this.relativePhoneNumber = relativePhoneNumber;
    }

    public static String getLatitude() {
        return latitude;
    }

    public static void setLatitude(String latitude) {
        UserInfo.latitude = latitude;
    }

    public static String getLongitude() {
        return longitude;
    }

    public static void setLongitude(String longitude) {
        UserInfo.longitude = longitude;
    }

    public String getNonStaticLatitude() {
        return nonStaticLatitude;
    }

    public void setNonStaticLatitude(String nonStaticLatitude) {
        this.nonStaticLatitude = nonStaticLatitude;
    }

    public String getNonStaticLongitude() {
        return nonStaticLongitude;
    }

    public void setNonStaticLongitude(String nonStaticLongitude) {
        this.nonStaticLongitude = nonStaticLongitude;
    }
}
