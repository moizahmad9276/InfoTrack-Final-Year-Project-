package com.example.moiz.indoorts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference myStorageReference;
    private String userID;
    private Context mContext;
    private double mPhotoUploadProgress = 0;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        myStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }


    public void addNewStudent(String email, String username, String designation, String address, String profile_photo,
                              String full_name, long phone_number, String registeration_id){
        Student student = new Student(FirebaseAuth.getInstance().getCurrentUser().getUid(), registeration_id, full_name, username, email,
                phone_number, address, designation, profile_photo);

        myRef.child(mContext.getString(R.string.dbname_students))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(student);

        StudentAccountSettings settings = new StudentAccountSettings(FirebaseAuth.getInstance().getCurrentUser().getUid(), full_name, username,
                email, phone_number, address,
                designation, profile_photo);

        myRef.child(mContext.getString(R.string.dbname_student_account_settings))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(settings);
    }

    public void addNewFaculty(String email, String username, String designation, String address, String profile_photo,
                              String full_name, long phone_number, String registeration_id){
        Faculty faculty = new Faculty(FirebaseAuth.getInstance().getCurrentUser().getUid(), registeration_id, full_name, username, email,
                phone_number, address, designation, profile_photo);

        myRef.child(mContext.getString(R.string.dbname_faculty))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(faculty);

        FacultyAccountSettings settings = new FacultyAccountSettings(FirebaseAuth.getInstance().getCurrentUser().getUid(), full_name, username,
                email, phone_number, address,
                designation, profile_photo);

        myRef.child(mContext.getString(R.string.dbname_faculty_account_settings))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(settings);
    }

    public void addMyStatus(String fullname, String status){
        MyStatus myStatus = new MyStatus(FirebaseAuth.getInstance().getCurrentUser().getUid(), fullname, status);

        myRef.child(mContext.getString(R.string.dbname_mystatus))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(myStatus);
    }

    public void uploadNewStudentPhoto(String photoType, int count, String imgUrl, Bitmap bm){

        FilePaths filePaths = new FilePaths();

        if(photoType.equals(mContext.getString(R.string.new_photo))){
            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = myStorageReference
                    .child(filePaths.FIREBASE_STUDENT_IMAGE_STORAGE + "/" + user_id + "/photo" + (count + 1));

            if(bm == null){
                bm = ImageManager.getBitmap(imgUrl);

            }
            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> firebaseUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                    Toast.makeText(mContext, "Photo uploaded successfully.", Toast.LENGTH_SHORT).show();

                    addStudentPhotoToDatabase(firebaseUrl.toString());

                    Intent intent = new Intent(mContext, StudentProfileActivity.class);
                    mContext.startActivity(intent);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mContext, "Photo upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    if(progress - 15 > mPhotoUploadProgress){
                        Toast.makeText(mContext, "Photo uploading progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }

                }
            });
        }

        else if(photoType.equals(mContext.getString(R.string.profile_photo))){

            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = myStorageReference
                    .child(filePaths.FIREBASE_STUDENT_IMAGE_STORAGE + "/" + user_id + "/profile_photo");

            if(bm == null){
                bm = ImageManager.getBitmap(imgUrl);

            }
            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> firebaseUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                    Toast.makeText(mContext, "Photo uploaded successfully.", Toast.LENGTH_SHORT).show();

                    setStudentProfilePhoto(firebaseUrl.toString());

                    ((StudentAccountSettingActivity)mContext).setViewPager(
                            ((StudentAccountSettingActivity)mContext).pagerAdapter
                                    .getFragmentNumber(mContext.getString(R.string.edit_profile_fragment))
                    );

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mContext, "Photo upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    if(progress - 15 > mPhotoUploadProgress){
                        Toast.makeText(mContext, "Photo uploading progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }
                }
            });
        }

    }

    public void uploadNewFacultyPhoto(String photoType, final int count, final String imgUrl, Bitmap bm){

        FilePaths filePaths = new FilePaths();

        if(photoType.equals(mContext.getString(R.string.new_photo))){
            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = myStorageReference
                    .child(filePaths.FIREBASE_FACULTY_IMAGE_STORAGE + "/" + user_id + "/photo" + (count + 1));

            if(bm == null){
                bm = ImageManager.getBitmap(imgUrl);

            }
            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> firebaseUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                    Toast.makeText(mContext, "Photo uploaded successfully.", Toast.LENGTH_SHORT).show();
                    addFacultyPhotoToDatabase(firebaseUrl.toString());

                    Intent intent = new Intent(mContext, FacultyProfileActivity.class);
                    mContext.startActivity(intent);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mContext, "Photo upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    if(progress - 15 > mPhotoUploadProgress){
                        Toast.makeText(mContext, "Photo uploading progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }
                }
            });
        }

        else if(photoType.equals(mContext.getString(R.string.profile_photo))){
            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = myStorageReference
                    .child(filePaths.FIREBASE_FACULTY_IMAGE_STORAGE + "/" + user_id + "/profile_photo");

            if(bm == null){
                bm = ImageManager.getBitmap(imgUrl);
            }
            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> firebaseUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                    Toast.makeText(mContext, "Photo uploaded successfully.", Toast.LENGTH_SHORT).show();

                    setFacultyProfilePhoto(firebaseUrl.toString());

                    ((FacultyAccountSettingActivity)mContext).setViewPager(
                            ((FacultyAccountSettingActivity)mContext).pagerAdapter
                                    .getFragmentNumber(mContext.getString(R.string.edit_profile_fragment))
                    );

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mContext, "Photo upload failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    if(progress - 15 > mPhotoUploadProgress){
                        Toast.makeText(mContext, "Photo uploading progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }
                }
            });
        }

    }

    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Karachi"));
        return sdf.format(new Date());
    }

    private void addStudentPhotoToDatabase(String url){

        String newPhotoKey = myRef.child(mContext.getString(R.string.dbname_all_student_photos)).push().getKey();
        StudentPhoto studentPhoto = new StudentPhoto();
        studentPhoto.setDate_created(getTimestamp());
        studentPhoto.setImage_path(url);
        studentPhoto.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        studentPhoto.setPhoto_id(newPhotoKey);

        myRef.child(mContext.getString(R.string.dbname_student_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser()
                        .getUid()).child(newPhotoKey).setValue(studentPhoto);
        myRef.child(mContext.getString(R.string.dbname_all_student_photos)).child(newPhotoKey).setValue(studentPhoto);

    }

    private void addFacultyPhotoToDatabase(String url){

        String newPhotoKey = myRef.child(mContext.getString(R.string.dbname_all_faculty_photos)).push().getKey();
        FacultyPhoto facultyPhoto = new FacultyPhoto();
        facultyPhoto.setDate_created(getTimestamp());
        facultyPhoto.setImage_path(url);
        facultyPhoto.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        facultyPhoto.setPhoto_id(newPhotoKey);

        myRef.child(mContext.getString(R.string.dbname_faculty_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser()
                        .getUid()).child(newPhotoKey).setValue(facultyPhoto);
        myRef.child(mContext.getString(R.string.dbname_all_faculty_photos)).child(newPhotoKey).setValue(facultyPhoto);
    }

    private void setStudentProfilePhoto(String url){
        myRef.child(mContext.getString(R.string.dbname_student_account_settings))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.profile_photo))
                .setValue(url);
    }

    private void setFacultyProfilePhoto(String url){

        myRef.child(mContext.getString(R.string.dbname_faculty_account_settings))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.profile_photo))
                .setValue(url);
    }

    public int getStudentImageCount(DataSnapshot dataSnapshot){
        int count = 0;
        for(DataSnapshot ds: dataSnapshot
                .child(mContext.getString(R.string.dbname_student_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()){
            count++;
        }
        return count;
    }

    public int getFacultyImageCount(DataSnapshot dataSnapshot){
        int count = 0;
        for(DataSnapshot ds: dataSnapshot
                .child(mContext.getString(R.string.dbname_faculty_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()){
            count++;
        }
        return count;
    }

    public StudentSettings getStudentSettings(DataSnapshot dataSnapshot) {
        StudentAccountSettings settings = new StudentAccountSettings();
        Student student = new Student();

        for(DataSnapshot ds: dataSnapshot.getChildren()){
            if(ds.getKey().equals(mContext.getString(R.string.dbname_student_account_settings))){

                try {
                    settings.setFull_name(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(StudentAccountSettings.class)
                                    .getFull_name()
                    );
                    settings.setUsername(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(StudentAccountSettings.class)
                                    .getUsername()
                    );

                    settings.setEmail(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(StudentAccountSettings.class)
                                    .getEmail()
                    );
                    settings.setAddress(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(StudentAccountSettings.class)
                                    .getAddress()
                    );
                    settings.setDesignation(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(StudentAccountSettings.class)
                                    .getDesignation()
                    );
                    settings.setPhone_number(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(StudentAccountSettings.class)
                                    .getPhone_number()
                    );
                    settings.setProfile_photo(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(StudentAccountSettings.class)
                                    .getProfile_photo()
                    );
                }catch (NullPointerException e){

                }

            }
            if(ds.getKey().equals(mContext.getString(R.string.dbname_students))){
                student.setProfile_photo(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Student.class)
                                .getProfile_photo()
                );
                student.setFull_name(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Student.class)
                                .getFull_name()
                );
                student.setUsername(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Student.class)
                                .getUsername()
                );
                student.setEmail(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Student.class)
                                .getEmail()
                );
                student.setDesignation(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Student.class)
                                .getDesignation()
                );
                student.setAddress(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Student.class)
                                .getAddress()
                );
                student.setPhone_number(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Student.class)
                                .getPhone_number()
                );
                student.setRegisteration_id(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Student.class)
                                .getRegisteration_id()
                );

            }
        }
        return new StudentSettings(student, settings);
    }


    public MyStatusSettings getStatusSettings(DataSnapshot dataSnapshot){
        MyStatus myStatus = new MyStatus();

        for(DataSnapshot ds: dataSnapshot.getChildren()){
            if(ds.getKey().equals(mContext.getString(R.string.dbname_mystatus))){
                try {
                    myStatus.setStatus(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(MyStatus.class)
                                    .getStatus()
                    );

                } catch (NullPointerException e) {
                    Log.e(TAG, "getFacultyAccountSettings: NullPointerException: " + e.getMessage());
                }
            }
        }
        return new MyStatusSettings(myStatus);
    }

    public FacultySettings getFacultySettings(DataSnapshot dataSnapshot) {
        FacultyAccountSettings settings = new FacultyAccountSettings();
        Faculty faculty = new Faculty();

        for(DataSnapshot ds: dataSnapshot.getChildren()){
            if(ds.getKey().equals(mContext.getString(R.string.dbname_faculty_account_settings))){

                try {
                    settings.setFull_name(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(FacultyAccountSettings.class)
                                    .getFull_name()
                    );
                    settings.setUsername(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(FacultyAccountSettings.class)
                                    .getUsername()
                    );

                    settings.setEmail(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(FacultyAccountSettings.class)
                                    .getEmail()
                    );
                    settings.setAddress(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(FacultyAccountSettings.class)
                                    .getAddress()
                    );
                    settings.setDesignation(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(FacultyAccountSettings.class)
                                    .getDesignation()
                    );
                    settings.setPhone_number(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(FacultyAccountSettings.class)
                                    .getPhone_number()
                    );
                    settings.setProfile_photo(
                            ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .getValue(FacultyAccountSettings.class)
                                    .getProfile_photo()
                    );

                    Log.d(TAG, "getFacultyAccountSettings: retrieved user_account_settings information: " + settings.toString());
                } catch (NullPointerException e) {
                    Log.e(TAG, "getFacultyAccountSettings: NullPointerException: " + e.getMessage());
                }

            }
            if(ds.getKey().equals(mContext.getString(R.string.dbname_faculty))){
                faculty.setProfile_photo(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Faculty.class)
                                .getProfile_photo()
                );
                faculty.setFull_name(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Faculty.class)
                                .getFull_name()
                );
                faculty.setUsername(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Faculty.class)
                                .getUsername()
                );
                faculty.setEmail(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Faculty.class)
                                .getEmail()
                );
                faculty.setDesignation(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Faculty.class)
                                .getDesignation()
                );
                faculty.setAddress(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Faculty.class)
                                .getAddress()
                );
                faculty.setPhone_number(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Faculty.class)
                                .getPhone_number()
                );
                faculty.setRegisteration_id(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Faculty.class)
                                .getRegisteration_id()
                );
                faculty.setUser_id(
                        ds.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .getValue(Faculty.class)
                                .getUser_id()
                );

            }
        }
        return new FacultySettings(faculty, settings);
    }

    public void updateStudentAccountSettings(String fullname, long phonenumber, String designation, String address){

        if(fullname != null){
            myRef.child(mContext.getString(R.string.dbname_student_account_settings))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_fullname))
                    .setValue(fullname);
            myRef.child(mContext.getString(R.string.dbname_students))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_fullname))
                    .setValue(fullname);
        }
        if(phonenumber != 0) {
            myRef.child(mContext.getString(R.string.dbname_student_account_settings))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_phonenumber))
                    .setValue(phonenumber);
            myRef.child(mContext.getString(R.string.dbname_students))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_phonenumber))
                    .setValue(phonenumber);
        }
        if(designation != null) {
            myRef.child(mContext.getString(R.string.dbname_student_account_settings))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_designation))
                    .setValue(designation);
            myRef.child(mContext.getString(R.string.dbname_students))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_designation))
                    .setValue(designation);
        }
        if(address != null) {
            myRef.child(mContext.getString(R.string.dbname_student_account_settings))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_address))
                    .setValue(address);
            myRef.child(mContext.getString(R.string.dbname_students))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_address))
                    .setValue(address);
        }
    }

    public void updateMyStatus(String status) {
        if (status != null) {
            myRef.child(mContext.getString(R.string.dbname_mystatus))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_status))
                    .setValue(status);

        }
    }

    public void updateFacultyAccountSettings(String fullname, long phonenumber, String designation, String address, CircleImageView pic){

        if(fullname != null){
            myRef.child(mContext.getString(R.string.dbname_faculty_account_settings))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_fullname))
                    .setValue(fullname);
            myRef.child(mContext.getString(R.string.dbname_faculty))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_fullname))
                    .setValue(fullname);
            myRef.child(mContext.getString(R.string.dbname_mystatus))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("fullname")
                    .setValue(fullname);
        }
        if(phonenumber != 0) {
            myRef.child(mContext.getString(R.string.dbname_faculty_account_settings))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_phonenumber))
                    .setValue(phonenumber);
            myRef.child(mContext.getString(R.string.dbname_faculty))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_phonenumber))
                    .setValue(phonenumber);
        }
        if(designation != null) {
            myRef.child(mContext.getString(R.string.dbname_faculty_account_settings))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_designation))
                    .setValue(designation);
            myRef.child(mContext.getString(R.string.dbname_faculty))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_designation))
                    .setValue(designation);
        }
        if(address != null) {
            myRef.child(mContext.getString(R.string.dbname_faculty_account_settings))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_address))
                    .setValue(address);
            myRef.child(mContext.getString(R.string.dbname_faculty))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(mContext.getString(R.string.field_address))
                    .setValue(address);
        }
    }

    public void updateStudentUsername(String username){
        myRef.child(mContext.getString(R.string.dbname_students))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.field_username))
                .setValue(username);

        myRef.child(mContext.getString(R.string.dbname_student_account_settings))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.field_username))
                .setValue(username);
    }

    public void updateFacultyUsername(String username){
        myRef.child(mContext.getString(R.string.dbname_faculty))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.field_username))
                .setValue(username);

        myRef.child(mContext.getString(R.string.dbname_faculty_account_settings))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.field_username))
                .setValue(username);
    }

    public void updateStudentEmail(String email){
        myRef.child(mContext.getString(R.string.dbname_students))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.field_email))
                .setValue(email);

        myRef.child(mContext.getString(R.string.dbname_student_account_settings))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.field_email))
                .setValue(email);
    }

    public void updateFacultyEmail(String email){
        myRef.child(mContext.getString(R.string.dbname_faculty))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.field_email))
                .setValue(email);

        myRef.child(mContext.getString(R.string.dbname_faculty_account_settings))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.field_email))
                .setValue(email);
    }
}
