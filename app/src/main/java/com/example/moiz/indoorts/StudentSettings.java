package com.example.moiz.indoorts;

public class StudentSettings {
    private Student student;
    private StudentAccountSettings settings;

    public StudentSettings(Student student, StudentAccountSettings settings) {
        this.student = student;
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "StudentSettings{" +
                "student=" + student +
                ", settings=" + settings +
                '}';
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentAccountSettings getSettings() {
        return settings;
    }

    public void setSettings(StudentAccountSettings settings) {
        this.settings = settings;
    }

    public StudentSettings(){

    }
}
