package com.example.moiz.indoorts.GreenTabs;

public class GreenTuesdayClassroom {

    private String teachername;
    private String coursename;
    private String time;
    private String room;

    public GreenTuesdayClassroom(){

    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public GreenTuesdayClassroom(String teachername, String coursename, String time, String room) {

        this.teachername = teachername;
        this.coursename = coursename;
        this.time = time;
        this.room = room;
    }

}
