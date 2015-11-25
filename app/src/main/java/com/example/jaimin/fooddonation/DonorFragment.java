package com.example.jaimin.fooddonation;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DonorFragment extends ListFragment {
    public static final String TAG = DonorFragment.class.getSimpleName();
    protected List<ParseObject> mDonors;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_donor, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("foodtable");
        query.whereEqualTo("dorR","donor");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> posts, ParseException e) {
                getActivity().setProgressBarIndeterminateVisibility(false);

                if (e == null) {

                    mDonors = posts;

                    String[] donors = new String[mDonors.size()];
                    int i = 0;

                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss aa");//("MM/dd/yyyy hh:mm:ss aa");
                    Date convertedDatepost = new Date();
                    Date convertedDatecurrent = new Date();
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    try {
                         convertedDatecurrent = dateFormat.parse(currentDateTimeString);
                    } catch (java.text.ParseException e1) {
                        e1.printStackTrace();
                    }
                    Log.d(TAG,"todays date="+currentDateTimeString);

                    for (ParseObject postingdata : mDonors) {
                        String dt=postingdata.getString("postdate");
                        try {
                             convertedDatepost = dateFormat.parse(dt);
                        } catch (java.text.ParseException e1) {
                            e1.printStackTrace();
                        }

                        Log.d(TAG,"postdate="+ dt);


                        long diff = convertedDatecurrent.getTime() - convertedDatepost.getTime();
                        Log.d(TAG,"datediff="+ Long.toString(diff));
                        long seconds = diff / 1000;
                        Log.d(TAG,"seconddiff="+ Long.toString(seconds));

                        long minutes = seconds / 60;
                        Log.d(TAG,"minutediff="+ Long.toString(minutes));

                        long hours = minutes / 60;
                        Log.d(TAG,"hourdiff="+ Long.toString(hours));

                        if(hours>=24){
                            postingdata.deleteInBackground();
                        }

                    }

                    for (ParseObject postingdata : mDonors) {

                            donors[i]=postingdata.getString("full");
                            i++;

                    }

                    ArrayAdapter<String> adapter= new ArrayAdapter<String>(
                            getListView().getContext(),
                            android.R.layout.simple_list_item_1,
                            donors);
                    setListAdapter(adapter);



                } else {
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getListView().getContext());
                    builder.setMessage(e.getMessage())
                            .setTitle(R.string.login_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
}

