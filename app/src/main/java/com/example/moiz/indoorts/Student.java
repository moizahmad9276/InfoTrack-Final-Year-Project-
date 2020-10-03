package com.example.moiz.indoorts;

public class Student {

    private String user_id;
    private String registeration_id;
    private String full_name;
    private String username;
    private String email;
    private long phone_number;
    private String address;
    private String designation;
    private String profile_photo;

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    @Override
    public String toString() {
        return "Student{" +
                "registeration_id='" + registeration_id + '\'' +
                ", full_name='" + full_name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone_number=" + phone_number +
                ", address='" + address + '\'' +
                ", designation='" + designation + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                '}';
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Student(String user_id, String registeration_id, String full_name, String username, String email, long phone_number, String address, String designation, String profile_photo) {
        this.full_name = full_name;
        this.username = username;
        this.email = email;
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.address = address;
        this.designation = designation;
        this.registeration_id = registeration_id;
        this.profile_photo = profile_photo;
    }

    public Student() {
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getRegisteration_id() {
        return registeration_id;
    }

    public void setRegisteration_id(String registeration_id) {
        this.registeration_id = registeration_id;
    }

}
