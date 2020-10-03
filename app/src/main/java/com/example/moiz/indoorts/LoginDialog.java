package com.example.moiz.indoorts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class LoginDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Login As")
                .setMessage("Are you a Faculty member or a Student?")
                .setNegativeButton("Faculty", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), FacultyLoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setPositiveButton("Student", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), StudentLoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                });
        return builder.create();
    }
}

