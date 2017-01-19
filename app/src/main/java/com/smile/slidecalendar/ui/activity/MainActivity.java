package com.smile.slidecalendar.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.smile.slidecalendar.R;
import com.smile.slidecalendar.ui.adapter.BaseFragmentAdapter;
import com.smile.slidecalendar.ui.fragment.CalendarFragment;
import com.smile.slidecalendar.ui.fragment.HomeFragment;
import com.smile.slidecalendar.ui.fragment.MessageFragment;
import com.smile.slidecalendar.ui.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();

    private int currentPosition; //index of current fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    /**
     * init fragment, add the first fragment
     */
    private void initView() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.navigation_tab_homepage, R.drawable.home_toolbar, R.color.bg_white_color);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.navigation_tab_schedule, R.drawable.calendar_toolbar, R.color.bg_white_color);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.navigation_tab_message, R.drawable.message_toolbar, R.color.bg_white_color);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.navigation_tab_profile, R.drawable.people_toolbar, R.color.bg_white_color);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);

        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setAccentColor(Color.parseColor("#1E8CF0"));
        bottomNavigation.setInactiveColor(Color.parseColor("#949494"));
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#FE5652"));
        bottomNavigation.setNotificationTextColor(Color.parseColor("#FE5652"));
        bottomNavigation.setColored(false);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (position != currentPosition) {
                    currentPosition = position;
                }
                viewPager.setCurrentItem(position, false);
                return true;
            }
        });

        List<Fragment> fragments = new ArrayList<>();

        fragments.add(HomeFragment.newInstance());
        fragments.add(CalendarFragment.newInstance());
        fragments.add(MessageFragment.newInstance());
        fragments.add(MyFragment.newInstance());

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), fragments));

    }
}
