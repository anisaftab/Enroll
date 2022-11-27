package com.example.enroll.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.enroll.R;


public class RegistrationActivity<db> extends AppCompatActivity {

    EditText fullname,username,password;
    Button  register;
    RadioButton radioButton;
    RadioGroup radioGroup;
    MyDBHandler db = new MyDBHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fullname = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        final String[] account_type = {""};

        radioGroup = findViewById(R.id.user_type);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = fullname.getText().toString();
                String user = username.getText().toString();
                String pass = password.getText().toString();

                int radioId = radioGroup.getCheckedRadioButtonId();

                radioButton = findViewById(radioId);

                if(radioButton.getText().equals("Student")){
                    account_type[0] = "Student";
                }
                else{
                    account_type[0] = "Instructor";
                }
                String accountType = (account_type[0]);


                if((user.equals("")) || ( name.equals("")) || pass.equals("") || accountType.equals("")){
                    Toast.makeText(RegistrationActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean checkUser = db.checkUsername(user);
                    if(!checkUser){
                        boolean insert = db.createAccount(accountType,name, user,pass);
                        if(insert) {
                            Toast.makeText(RegistrationActivity.this, "Registration Successful!!", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(myIntent);
                        }
                        else{
                            Toast.makeText(RegistrationActivity.this, "Registration Failed!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(RegistrationActivity.this, "User already exists!!", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent( getApplicationContext(), LoginActivity.class);
                        startActivity(myIntent);
                    }
                }

            }


        });

    }
    public void onToggle(View view){
         int radioId = radioGroup.getCheckedRadioButtonId();

         radioButton = findViewById(radioId);
    }
}

