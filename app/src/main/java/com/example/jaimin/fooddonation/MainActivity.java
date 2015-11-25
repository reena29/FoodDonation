package com.example.jaimin.fooddonation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();

    protected TextView mSignUpTextView;
    protected TextView mLoginTextView;
    protected TextView mHomeTextView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSignUpTextView=(TextView)findViewById(R.id.mainsignup);
        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
            }
        });
        mLoginTextView=(TextView)findViewById(R.id.mainlogin);
        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });
        mHomeTextView=(TextView)findViewById(R.id.mainhome);
        mHomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Home.class);
                startActivity(intent);
            }
        });
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            mLoginTextView.setVisibility(View.VISIBLE);
            mSignUpTextView.setVisibility(View.VISIBLE);
            mHomeTextView.setVisibility(View.INVISIBLE);
        }
        else {
            Log.i(TAG, currentUser.getUsername());
            mLoginTextView.setVisibility(View.INVISIBLE);
            mSignUpTextView.setVisibility(View.INVISIBLE);
        }
    }



}
