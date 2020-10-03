package com.example.moiz.indoorts.DarkblueTabs;

public class DarkblueFacultyroom {
    private String faculty, designation;

    public DarkblueFacultyroom(){

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

    public DarkblueFacultyroom(String faculty, String designation) {
        this.faculty = faculty;
        this.designation = designation;
    }
}
