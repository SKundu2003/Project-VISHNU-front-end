package com.example.myapplication.Module;

public class UserDetailsModule {
    public String ownPhoneNumber;
    public String userName;

    public String address;

    public String relativePhoneNumber;
    public String latitude;
    public String longitude;
    public UserDetailsModule(String ownPhoneNumber, String userName, String address, String relativePhoneNumber, String latitude, String longitude) {
        this.ownPhoneNumber = ownPhoneNumber;
        this.userName = userName;
        this.address = address;
        this.relativePhoneNumber = relativePhoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }


}
