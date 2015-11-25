package com.example.jaimin.fooddonation;



import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

/**
 * A {@link android.support.v13.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    protected Context mcontext;


    public SectionsPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        mcontext=context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position) {
            case 0:
                return new DonorFragment();
            case 1:
                return new RecipientFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mcontext.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return mcontext.getString(R.string.title_section2).toUpperCase(l);

        }
        return null;
    }
}
