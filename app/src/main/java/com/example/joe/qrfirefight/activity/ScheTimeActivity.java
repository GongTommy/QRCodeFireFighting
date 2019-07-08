package com.example.joe.qrfirefight.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.example.joe.qrfirefight.R;
import com.example.joe.qrfirefight.adapter.SelPagerAdapter;
import com.example.joe.qrfirefight.fragment.ScheDetailFragment;
import com.example.joe.qrfirefight.fragment.ScheSubmitFragment;
import com.example.joe.qrfirefight.fragment.ScheTimeFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18145288 on 19/6/2019.
 */

public class ScheTimeActivity extends FragmentActivity{
    private ViewPager vpContent;
    private List<Fragment> fragments;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schetime_activity);

        initView();
        initData();
    }

    private void initView() {
        vpContent = findViewById(R.id.vpContent);
    }

    private void initData(){
        fragments = new ArrayList<>();
        ScheTimeFragment fragment1 = new ScheTimeFragment();
        ScheDetailFragment fragment2 = new ScheDetailFragment();
        ScheSubmitFragment fragment3 = new ScheSubmitFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        SelPagerAdapter selPagerAdapter = new SelPagerAdapter(getSupportFragmentManager(), fragments);
        vpContent.setAdapter(selPagerAdapter);
        vpContent.setCurrentItem(0);
        vpContent.setOffscreenPageLimit(3);
    }

    public ViewPager getViewPager(){
        return vpContent;
    }
}
