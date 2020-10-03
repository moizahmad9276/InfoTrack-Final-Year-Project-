package com.example.moiz.indoorts.LightblueTabs;

public class LightblueFacultyroom {
    private String faculty, designation;

    public LightblueFacultyroom(){

    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LightblueFacultyroom(String faculty, String designation) {
        this.faculty = faculty;
        this.designation = designation;
    }
}
