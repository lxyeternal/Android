package com.example.guoxiaowen.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by lenovo on 2018/2/8.
 */

public class LoginDailogFragment extends DialogFragment implements View.OnClickListener {

    private EditText mUsername;
    private EditText mPassword;
    private Button btn;
    private ImageView iv;
    private TextView toReg;
    private ProgressBar pb;

    public interface LoginInputListener{
        void onLoginInputComplete(String userName, String password);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_login, null);
        iv= view.findViewById(R.id.login_iv);
        toReg= view.findViewById(R.id.login_register);
        mUsername= view.findViewById(R.id.login_et1);
        btn= view.findViewById(R.id.login_btn);
        mPassword= view.findViewById(R.id.login_et2);
        iv.setOnClickListener(this);
        toReg.setOnClickListener(this);
        btn.setOnClickListener(this);

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                LoginInputListener listener= (LoginInputListener) getActivity();
                listener.onLoginInputComplete(mUsername.getText().toString(), mPassword.getText().toString());
                dismiss();
                break;
        }
    }
}
