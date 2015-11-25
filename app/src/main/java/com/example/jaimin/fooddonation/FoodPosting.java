package com.example.jaimin.fooddonation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.util.Date;

public class FoodPosting extends Activity {
    protected EditText mdrname;
    protected EditText mdraddress;
    protected EditText mdrzipcode;
    protected EditText mdrphone;
    protected EditText mdrnumpeople;
    protected Button mdrsubmitpost;
    protected RadioGroup mradiodr;
    protected TextView mdatetext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_food_posting);

        mdatetext=(TextView)findViewById(R.id.datetext);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        mdatetext.setText(currentDateTimeString);

        mradiodr=(RadioGroup)findViewById(R.id.radioGroupDR);
        mdrname = (EditText) findViewById(R.id.DRname);
        mdraddress = (EditText) findViewById(R.id.DRaddress);
        mdrzipcode = (EditText) findViewById(R.id.DRzipcode);
        mdrphone = (EditText) findViewById(R.id.DRphone);
        mdrnumpeople = (EditText) findViewById(R.id.peoplenum);
        mdrsubmitpost = (Button) findViewById(R.id.post_button);
        mdrsubmitpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String drname = mdrname.getText().toString();
                String draddress = mdraddress.getText().toString();
                String drzipcode = mdrzipcode.getText().toString();
                String drphone = mdrphone.getText().toString();
                String drnumpeople = mdrnumpeople.getText().toString();
                String DorR;
                String fulldetail;
                String postdate=mdatetext.getText().toString();
                drname = drname.trim();
                draddress = draddress.trim();
                drzipcode = drzipcode.trim();
                drphone = drphone.trim();
                drnumpeople = drnumpeople.trim();

                if(mradiodr.getCheckedRadioButtonId()==R.id.radioDonor){
                    DorR="donor";

                }else if(mradiodr.getCheckedRadioButtonId()==R.id.radioRecipient){
                    DorR="recipient";

                }else{
                    DorR=null;
                }


                if (drname.isEmpty() || draddress.isEmpty() || drzipcode.isEmpty() ||
                        drphone.isEmpty() || drnumpeople.isEmpty() || DorR==null ||
                        drzipcode.length()!=5 || drphone.length()!=10) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FoodPosting.this);
                    builder.setMessage(R.string.submit_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    //submit
                    fulldetail=drname+"\n"+draddress+"\n"+drzipcode+"\n"+"Phone: "+drphone+"\n"+
                            "Approximate People: "+drnumpeople+"\n"+"Post Time: "+postdate;
                    Log.d("fulldetail",fulldetail);
                    ParseObject postingdata = new ParseObject("foodtable");
                    postingdata.put("name", drname); //string
                    postingdata.put("address", draddress); //integer
                    postingdata.put("zipcode", drzipcode); //variable
                    postingdata.put("phone",drphone);
                    postingdata.put("numofpeople",drnumpeople);
                    postingdata.put("dorR",DorR);
                    postingdata.put("full",fulldetail);
                    postingdata.put("postdate",postdate);
                    setProgressBarIndeterminateVisibility(true);
                    postingdata.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            setProgressBarIndeterminateVisibility(false);

                            if (e == null) {
                                // Success!
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(FoodPosting.this);
                                builder1.setMessage(R.string.submit_success)
                                        .setTitle(R.string.submit_success_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog1 = builder1.create();
                                dialog1.show();
                                //mdrname.setText("",null);

                            }
                            else {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(FoodPosting.this);
                                builder2.setMessage(e.getMessage())
                                        .setTitle(R.string.signup_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog2 = builder2.create();
                                dialog2.show();
                            }
                           // Intent intent = new Intent(FoodPosting.this, Home.class);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //startActivity(intent);

                        }
                    });

                }


            }
        });
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.food_posting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();

        if (itemId == R.id.action_logout) {
            ParseUser.logOut();
            navigateToLogin();
        }
        else if (itemId == R.id.action_posting) {
            Intent intent = new Intent(this, FoodPosting.class);
            startActivity(intent);
        }
        else if (itemId == R.id.action_home) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
        else if (itemId == R.id.action_about) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
