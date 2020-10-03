package com.example.moiz.indoorts;

import android.os.Environment;

public class FilePaths {

    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();

    public String PICTURES = ROOT_DIR + "/Pictures";
    public String CAMERA = ROOT_DIR + "/DCIM/camera";

    public String FIREBASE_STUDENT_IMAGE_STORAGE = "student_photos/students/";
    public String FIREBASE_FACULTY_IMAGE_STORAGE = "faculty_photos/faculty/";

}
