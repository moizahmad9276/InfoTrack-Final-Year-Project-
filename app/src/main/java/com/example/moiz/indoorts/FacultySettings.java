package com.example.moiz.indoorts;

public class FacultySettings {
    private Faculty faculty;
    private FacultyAccountSettings settings;

    public FacultySettings(Faculty faculty, FacultyAccountSettings settings) {
        this.faculty = faculty;
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "FacultySettings{" +
                "faculty=" + faculty +
                ", settings=" + settings +
                '}';
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public FacultyAccountSettings getSettings() {
        return settings;
    }

    public void setSettings(FacultyAccountSettings settings) {
        this.settings = settings;
    }

    public FacultySettings(){

    }
}
