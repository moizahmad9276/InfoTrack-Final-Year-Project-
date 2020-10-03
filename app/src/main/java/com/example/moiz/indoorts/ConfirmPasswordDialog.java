package com.example.moiz.indoorts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmPasswordDialog extends DialogFragment {

    EditText mPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_password, container, false);

        TextView cancel = (TextView) view.findViewById(R.id.dialogCancel);
        TextView confirm = (TextView) view.findViewById(R.id.dialogConfirm);
        mPassword = (EditText) view.findViewById(R.id.confirm_password);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = mPassword.getText().toString();
                if(!password.equals("")){
                    mOnConfirmPasswordListener.onConfirmPassword(password);
                    getDialog().dismiss();
                    Toast.makeText(getActivity(), "Verify your email to confirm your email address", Toast.LENGTH_LONG).show();
                }
               else{
                    Toast.makeText(getActivity(), "You must enter a password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    public interface onConfirmPasswordListener{
        public void onConfirmPassword(String password);
    }
    onConfirmPasswordListener mOnConfirmPasswordListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mOnConfirmPasswordListener = (onConfirmPasswordListener) getTargetFragment();

        }catch (ClassCastException e){

        }
    }
}
