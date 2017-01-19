package com.smile.slidecalendar.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.smile.slidecalendar.ui.fragment.CalendarPagerFragment;

/**
 * @author Smile Wei
 * @since 2017/1/19.
 */

public class CalendarPagerAdapter extends FragmentStatePagerAdapter {
    public CalendarPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return CalendarPagerFragment.create(position);
    }

    @Override
    public int getCount() {
        return 1000;
    }
}
