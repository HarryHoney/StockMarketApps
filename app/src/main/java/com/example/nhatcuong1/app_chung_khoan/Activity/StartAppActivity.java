package com.example.nhatcuong1.app_chung_khoan.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.BaseAdapter;

import com.example.nhatcuong1.app_chung_khoan.Fragment.FragmentAdapter;
import com.example.nhatcuong1.app_chung_khoan.Fragment.HistoryFragment;
import com.example.nhatcuong1.app_chung_khoan.Fragment.StockMarketInfoFragment;
import com.example.nhatcuong1.app_chung_khoan.Fragment.UserInfoFragment;
import com.example.nhatcuong1.app_chung_khoan.Model.UserModel;
import com.example.nhatcuong1.app_chung_khoan.R;

import java.util.List;
import java.util.Vector;

/**
 * Created by nhatcuong1 on 11/17/15.
 */
public class StartAppActivity extends FragmentActivity {
    private static final String TAG = "StartAppActivity" ;
    private List<android.support.v4.app.Fragment> frags = new Vector<>();
    private StockMarketInfoFragment stockMarketInfoFragment ;
    private HistoryFragment historyFragment;
    private UserInfoFragment userInfoFragment;
    private ViewPager viewPager;
    private PagerTabStrip strip_status;
    private FragmentAdapter adapter;
    private static UserModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_app_activity);
        strip_status=(PagerTabStrip)findViewById(R.id.strip_status);
        strip_status.setTabIndicatorColor(Color.RED);
        Intent i = getIntent();
        user = (UserModel) i.getSerializableExtra("user");
        Log.i(TAG, "User trong main : " + user.getId());
        stockMarketInfoFragment = new StockMarketInfoFragment();
        historyFragment = new HistoryFragment();
        userInfoFragment = new UserInfoFragment();
        frags.add(stockMarketInfoFragment);
        frags.add(historyFragment);
        frags.add(userInfoFragment);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter =new FragmentAdapter(super.getSupportFragmentManager(),frags);
        viewPager.setAdapter(adapter);

    }

    public static UserModel getUser() {
        return user;
    }

    public static void setUser(UserModel user) {
        StartAppActivity.user = user;
    }
}
