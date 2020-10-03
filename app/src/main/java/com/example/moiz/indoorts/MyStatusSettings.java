package com.example.moiz.indoorts;

public class MyStatusSettings {
    private MyStatus myStatus;

    public MyStatusSettings(MyStatus myStatus) {
        this.myStatus = myStatus;
    }

    public MyStatusSettings() {

    }

    @Override
    public String toString() {
        return "MyStatusSettings{" +
                "myStatus=" + myStatus +
                '}';
    }

    public MyStatus getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(MyStatus myStatus) {
        this.myStatus = myStatus;
    }
}
