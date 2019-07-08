package com.example.joe.qrfirefight.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Joe on 2019-05-23.
 */

public class FirePagerAdapter extends PagerAdapter {
    private List<View> list_views;
    private List<String> list_titles;

    public FirePagerAdapter(List<View> list_views, List<String> list_titles){
        this.list_views = list_views;
        this.list_titles = list_titles;
    }

    @Override
    public int getCount() {
        return list_views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View item_view = list_views.get(position);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list_views.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_titles.get(position);
    }
}
