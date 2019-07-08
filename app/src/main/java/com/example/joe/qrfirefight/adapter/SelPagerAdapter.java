package com.example.joe.qrfirefight.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.joe.qrfirefight.fragment.ScheTimeFragment;
import java.util.List;

/**
 * Created by 18145288 on 2019/6/20.
 */

public class SelPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public SelPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments == null || fragments.get(position) == null){
            return null;
        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (fragments == null || fragments.size() == 0){
            return 0;
        }
        return fragments.size();
    }
}
