package com.example.moiz.indoorts;

class MyStatus {

    private String user_id;
    private String status;
    private String fullname;

    @Override
    public String toString() {
        return "MyStatus{" +
                "user_id='" + user_id + '\'' +
                ", status='" + status + '\'' +
                ", fullname='" + fullname + '\'' +
                '}';
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public MyStatus(String user_id, String fullname, String status) {
        this.user_id = user_id;
        this.status = status;
        this.fullname = fullname;

    }

    public MyStatus() {

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
