package com.smile.slidecalendar.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smile.slidecalendar.R;
import com.smile.slidecalendar.ui.adapter.CalendarPagerAdapter;
import com.smile.slidecalendar.utils.CalendarUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Smile Wei
 * @since 2017/1/19.
 */
public class CalendarFragment extends Fragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private Context context;

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_calendar, container, false);
        ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {
        context = getActivity().getApplicationContext();
        Calendar calendar = Calendar.getInstance();
        tvYear.setText(context.getString(R.string.calendar_year, calendar.get(Calendar.YEAR)));
        tvMonth.setText(context.getString(R.string.calendar_month, calendar.get(Calendar.MONTH) + 1));
        viewPager.setAdapter(new CalendarPagerAdapter(getChildFragmentManager()));
        viewPager.setCurrentItem(CalendarUtil.getMonthDiff() - 1);

        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewPager.removeOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Calendar calendar = CalendarUtil.getSelectCalendar(position);
        tvYear.setText(context.getString(R.string.calendar_year, calendar.get(Calendar.YEAR)));
        tvMonth.setText(context.getString(R.string.calendar_month, calendar.get(Calendar.MONTH) + 1));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}