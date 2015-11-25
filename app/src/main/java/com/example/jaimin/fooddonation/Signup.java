package com.example.jaimin.fooddonation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Signup extends Activity {

    protected EditText mUsername;
    protected EditText mPassword;
    protected EditText mConfirmPassword;
    protected EditText mEmail;
    protected Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_signup);



        mUsername = (EditText)findViewById(R.id.signup_username);
        mPassword = (EditText)findViewById(R.id.signup_password);
        mEmail = (EditText)findViewById(R.id.signup_email);
        mConfirmPassword = (EditText)findViewById(R.id.signup_confirm);
        mSignUpButton = (Button)findViewById(R.id.signup_button);
       mSignUpButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String username = mUsername.getText().toString();
               String password = mPassword.getText().toString();
               String email = mEmail.getText().toString();
               String confirmp =mConfirmPassword.getText().toString();
               username = username.trim();
               password = password.trim();
               email = email.trim();
               confirmp=confirmp.trim();

               if (username.isEmpty() || password.isEmpty() || email.isEmpty() || confirmp.isEmpty() ||
                       !confirmp.equals(password)) {
                   AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                   builder.setMessage(R.string.signup_error_message)
                           .setTitle(R.string.signup_error_title)
                           .setPositiveButton(android.R.string.ok, null);
                   AlertDialog dialog = builder.create();
                   dialog.show();
               }
               else {
                   // create the new user!
                   setProgressBarIndeterminateVisibility(true);

                   ParseUser newUser = new ParseUser();
                   newUser.setUsername(username);
                   newUser.setPassword(password);
                   newUser.setEmail(email);
                   newUser.signUpInBackground(new SignUpCallback() {
                       @Override
                       public void done(ParseException e) {
                           setProgressBarIndeterminateVisibility(false);

                           if (e == null) {
                               // Success!
                               Intent intent = new Intent(Signup.this, Home.class);
                               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                               startActivity(intent);
                           }
                           else {
                               AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                               builder.setMessage(e.getMessage())
                                       .setTitle(R.string.signup_error_title)
                                       .setPositiveButton(android.R.string.ok, null);
                               AlertDialog dialog = builder.create();
                               dialog.show();
                           }
                       }
                   });
               }
           }
       });
    }





}
