package com.example.enroll.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.enroll.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button login, register;
    MyDBHandler db = new MyDBHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(myIntent);
            }
        });



        login.setOnClickListener(new View.OnClickListener() {

            public boolean checkEmptyFields(String user, String password){
                if((user.equals("")) || password.equals("")){
                    return true;
                }
                else{
                    return false;
                }
            }
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                //System.out.println("user: " +user+" pass: "+pass);

                if (this.checkEmptyFields(user,pass)) {
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkUser = db.checkUsernamePassword(user, pass);
                    if ((user.equals("admin") )&&(pass.equals("admin123"))){
                        Toast.makeText(LoginActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();
                        Intent mIntent;
                        mIntent = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(mIntent);
                    }
                    else if (checkUser) {

                        String account_type = db.checkAccountType(user);

                        Intent myIntent;

                        if(Objects.equals(account_type, "Instructor")){
                            myIntent = new Intent(getApplicationContext(), InstructorActivity.class);
                            myIntent.putExtra("user", user);
                            myIntent.putExtra("name", db.getName(user));

                            Toast.makeText(LoginActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();

                            startActivity(myIntent);
                        } else if(Objects.equals(account_type, "Student")){
                            myIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                            myIntent.putExtra("KEY",user);

                            Toast.makeText(LoginActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();

                            startActivity(myIntent);
                        }


                    } else {
                        Toast.makeText(LoginActivity.this, "Sign In Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
}



