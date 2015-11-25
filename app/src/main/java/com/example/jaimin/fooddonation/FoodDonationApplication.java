package com.example.jaimin.fooddonation;

import android.app.Application;

import com.parse.Parse;


public class FoodDonationApplication extends Application {

    // Enable Local Datastore.
    @Override
    public void onCreate() {
    super.onCreate();

    Parse.enableLocalDatastore(this);

    Parse.initialize(this,"pNQqPN7YBMwfOqeKpPKxUNdQuPnkFXu6UYfPpolR","c98txTkvU5T8DwDvjhyuBAhfJFUDysE5TrQUeN9b");

}

}
