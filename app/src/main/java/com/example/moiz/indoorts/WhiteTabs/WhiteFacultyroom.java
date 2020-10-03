package com.example.moiz.indoorts.WhiteTabs;

public class WhiteFacultyroom {
    private String faculty, designation;

    public WhiteFacultyroom(){

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

    public WhiteFacultyroom(String faculty, String designation) {
        this.faculty = faculty;
        this.designation = designation;
    }
}
