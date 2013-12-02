package edu.andrazpencelj.parkinginljubljana;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by Andraž on 20.11.2013.
 */
public class ParkingLotPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public ParkingLotPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Seznam";
            case 1:
                return "Zemljevid";
        }
        return null;
    }
}
