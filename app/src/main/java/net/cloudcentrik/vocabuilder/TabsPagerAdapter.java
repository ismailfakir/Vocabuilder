package net.cloudcentrik.vocabuilder;

/**
 * Created by ismail on 2016-02-12.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new EttEnFragment();
            case 1:
                // Games fragment activity
                return new MeaningFragment();
            case 2:
                // Games fragment activity
                return new PartOfSpeachFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}