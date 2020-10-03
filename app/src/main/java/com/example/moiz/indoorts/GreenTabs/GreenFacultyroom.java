package com.example.moiz.indoorts.GreenTabs;

public class GreenFacultyroom {
    private String faculty, designation;

    public GreenFacultyroom(){

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

    public GreenFacultyroom(String faculty, String designation) {
        this.faculty = faculty;
        this.designation = designation;
    }
}
