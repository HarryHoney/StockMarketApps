package com.example.nhatcuong1.app_chung_khoan.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhatcuong1 on 11/17/15.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private final static String TASK1 = "Stock Market Info";
    private final static String TASK2 = "History";
    private final static String TASK3 = "User Info";

    private List<Fragment> myFragments;
    private static int pos=0;

    public FragmentAdapter(FragmentManager fm,List<Fragment> myFrags) {
        super(fm);
        this.myFragments=myFrags;
    }

    public List<Fragment> getMyFragments() {
        return myFragments;
    }


    @Override
    public Fragment getItem(int position) {
        return myFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        setPos(position);

        String PageTitle = "";

        switch(pos)
        {
            case 0:
                PageTitle = TASK1;
                break;
            case 1:
                PageTitle = TASK2;
                break;
            case 2:
                PageTitle = TASK3;
                break;

            default:    break;
        }
        return PageTitle;

    }

    @Override
    public int getCount() {
        return myFragments.size();
    }

    public static void setPos(int pos) {
        FragmentAdapter.pos = pos;
    }

    public static int getPos() {
        return pos;
    }


}
