package com.example.guoxiaowen.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements LoginDailogFragment.LoginInputListener,View.OnClickListener{

    private Button login;
    private EditText name;
    private EditText password;
    private LoginDailogFragment fragment;
    private String userName;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.main_login);
        name = findViewById(R.id.main_name);
        password = findViewById(R.id.main_password);
        final String Name = name.getText().toString().trim();
        final String Password = password.getText().toString().trim();     //用以判断是否为空
        name.setFocusable(false);
        name.setOnClickListener(this);
        password.setFocusable(false);
        password.setOnClickListener(this);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new LoginDailogFragment();
                fragment.show(getSupportFragmentManager(), "login");
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new LoginDailogFragment();
                fragment.show(getSupportFragmentManager(), "login");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLoginInputComplete(String userName, String userPassword) {
        name.setText(userName);
        password.setText(userPassword);
    }


    @Override
    public void onClick(View v) {

    }
}
