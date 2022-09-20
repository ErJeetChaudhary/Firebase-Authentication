package com.demo.auth.models;

/**
 * Created by Jitendra on 20/09/22.
 **/
public class UserInfo {

    private String name;
    private String mobileNo;
    private String gender;
    private String address;

    public UserInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return "Name: " + name + "\n\n" +
                "Cellular Number: " + mobileNo + "\n\n" +
                "Gender: " + gender + "\n\n" +
                "Address: " + address + "\n";
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
