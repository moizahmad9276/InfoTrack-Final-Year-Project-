package com.example.moiz.indoorts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import static android.content.Context.CONNECTIVITY_SERVICE;

public class FacultyEditProfileFragment extends Fragment implements ConfirmPasswordDialog.onConfirmPasswordListener {

    @Override
    public void onConfirmPassword(String password) {
        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), password);
        mAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Log.d(TAG, "Faculty re-authenticated.");

                            mAuth.fetchProvidersForEmail(em.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<ProviderQueryResult> task) {

                                            if(task.isSuccessful()){
                                                try{
                                                    if(task.getResult().getProviders().size() == 1){
                                                        Toast.makeText(getActivity(), "The e-mail is already in use.", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else{

                                                        mAuth.getCurrentUser().updateEmail(em.getText().toString())
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            Log.d(TAG, "Faculty email address updated.");
                                                                            Toast.makeText(getActivity(), "E-mail is updated successfully.", Toast.LENGTH_SHORT).show();
                                                                            firebaseMethods.updateFacultyEmail(em.getText().toString());

                                                                        }
                                                                    }
                                                                });

                                                    }
                                                }
                                                catch (NullPointerException e){

                                                }
                                            }

                                        }
                                    });
                        }

                        else{

                        }

                    }
                });

    }

    private static final String TAG = "FacultyEditProfile";

    public static final int REQUEST_CAMERA = 1, SELECT_IMAGE = 0;
    private String userChoosesTask;
    public Bitmap bm;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods firebaseMethods;
    private Context mContext;
    private String regID;
    private FacultySettings mFacultySettings;

    private EditText fname, em, pno, add, dsg;
    private CircleImageView profilepic;
    private TextView cphoto;
    private Button checkmark;
    Animation fromBottom;
    private LinearLayout layouthome;
    ProgressBar progressBar;
    TextView tvview;

    private StorageReference mStorage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faculty_edit_profile, container, false);

        fromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.fromnow);
        layouthome = (LinearLayout) view.findViewById(R.id.textedit);
        layouthome.startAnimation(fromBottom);

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        tvview = (TextView) view.findViewById(R.id.tvrandom);
        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);

        profilepic = (CircleImageView) view.findViewById(R.id.profile_photo);
        fname = (EditText) view.findViewById(R.id.fullname);
        dsg = (EditText) view.findViewById(R.id.designation);
        em = (EditText) view.findViewById(R.id.email);
        pno = (EditText) view.findViewById(R.id.phno);
        add = (EditText) view.findViewById(R.id.address);
        cphoto = (TextView) view.findViewById(R.id.change_photo);
        checkmark = (Button) view.findViewById(R.id.savechanges);

        firebaseMethods = new FirebaseMethods(getActivity());

        mStorage = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){
                    regID = firebaseAuth.getCurrentUser().getUid();
                    Log.d(TAG, "signed in" +firebaseAuth.getCurrentUser().getUid());

                }
                else{
                    Log.d(TAG, "Signed out");

                }
            }
        };

        mContext = getActivity();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setProfileWidgets(firebaseMethods.getFacultySettings(dataSnapshot));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetwork()){
                    Toast.makeText(getActivity(), "Check your network connection", Toast.LENGTH_LONG).show();
                }
                else{
                    saveProfileSettings();
                }
            }
        });

        em.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!haveNetwork()){
                    Toast.makeText(getActivity(), "Check your network connection", Toast.LENGTH_LONG).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    tvview.setVisibility(View.VISIBLE);
                    EmailChangeDialog emailChangeDialog = new EmailChangeDialog();
                    emailChangeDialog.show(getFragmentManager(),"change dialog");
                    progressBar.setVisibility(View.GONE);
                    tvview.setVisibility(View.GONE);
                }

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void setProfileWidgets(FacultySettings facultySettings){

        mFacultySettings = facultySettings;
        FacultyAccountSettings settings = facultySettings.getSettings();
        UniversalImageLoader.setImage(settings.getProfile_photo(), profilepic, null,"");

        fname.setText(settings.getFull_name());
        em.setText(settings.getEmail());
        pno.setText(String.valueOf(settings.getPhone_number()));
        dsg.setText(settings.getDesignation());
        add.setText(settings.getAddress());

        cphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!haveNetwork()){
                    Toast.makeText(getActivity(), "Check your network connection", Toast.LENGTH_LONG).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    tvview.setVisibility(View.VISIBLE);
                    selectImage();
                    progressBar.setVisibility(View.GONE);
                    tvview.setVisibility(View.GONE);
                }
            }
        });

    }

    private void selectImage(){
        final CharSequence[] items = {"Open Camera", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean result = Utility.checkPermission(getActivity());

                if(items[which].equals("Open Camera")){
                    userChoosesTask = "Open Camera";
                    if(result)
                        cameraIntent();
                }
                else if(items[which].equals("Choose from Gallery")){
                    userChoosesTask = "Choose from Gallery";
                    if(result)
                        galleryIntent();
                }
                else if(items[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), SELECT_IMAGE);
    }

    public void onSelectFromGalleryResult(Intent data){
        bm = null;
        if(data != null){
            try{
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            }
            catch (IOException e){

            }
            profilepic.setImageBitmap(bm);
        }
    }

    public void onCaptureImageResult(Intent data){
        bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try{
            destination.createNewFile();
            fo= new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        profilepic.setImageBitmap(bm);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == SELECT_IMAGE)
                onSelectFromGalleryResult(data);
            else if(requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Utility.EXTERNAL_PERMISSION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(userChoosesTask.equals("Open Camera"))
                        cameraIntent();
                    else if(userChoosesTask.equals("Select from Gallery"))
                        galleryIntent();
                }
                else{

                }
                break;
        }
    }

    private void saveProfileSettings(){
        progressBar.setVisibility(View.VISIBLE);
        tvview.setVisibility(View.VISIBLE);
        final String fullname = fname.getText().toString();
        final String email = em.getText().toString();
        final long phoneno = Long.parseLong(pno.getText().toString());
        final String designation = dsg.getText().toString();
        final String address = add.getText().toString();

        if(!mFacultySettings.getSettings().getFull_name().equals(fullname)){
            firebaseMethods.updateFacultyAccountSettings(fullname, 0,null,null, null);
        }

        if(!mFacultySettings.getSettings().getProfile_photo().equals(phoneno)){
            firebaseMethods.updateFacultyAccountSettings(null, phoneno,null,null, null);
        }

        if(!mFacultySettings.getSettings().getAddress().equals(address)){
            firebaseMethods.updateFacultyAccountSettings(null, 0,null,address, null);
        }

        if(!mFacultySettings.getSettings().getDesignation().equals(designation)){
            firebaseMethods.updateFacultyAccountSettings(null, 0,designation,null, null);
        }

        FileOutputStream fileOutputStream = null;
        File file = getDisc();
        if(!file.exists() && !file.mkdir()){
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyymmsshhmmss");
        String date = simpleDateFormat.format(new Date());
        String name = "Img" +date+ ".jpg";
        String file_name = file.getAbsolutePath()+"/"+name;
        File new_file = new File(file_name);
        try{
            fileOutputStream = new FileOutputStream(new_file);
            Bitmap bitmap = viewToBitmap(profilepic, profilepic.getWidth(), profilepic.getHeight());
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        catch (FileNotFoundException e){

        }
        catch (IOException e){

        }
        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);
        getActivity().finish();
    }

    private File getDisc(){
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(file, "InfoTrack Images");
    }

    public static Bitmap viewToBitmap(View view, int width, int height){
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public boolean haveNetwork(){
        boolean haveWifi = false;
        boolean haveData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for(NetworkInfo info : networkInfos){
            if(info.getTypeName().equalsIgnoreCase("Wifi"))
                if(info.isConnected())
                    haveWifi = true;
            if(info.getTypeName().equalsIgnoreCase("Mobile"))
                if(info.isConnected())
                    haveData = true;
        }
        return haveData || haveWifi;
    }

}
