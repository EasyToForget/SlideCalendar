package com.smile.slidecalendar.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.smile.slidecalendar.R;
import com.smile.slidecalendar.model.ScheduleCalendar;
import com.smile.slidecalendar.ui.adapter.CalendarViewAdapter;
import com.smile.slidecalendar.utils.CalendarUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarPagerFragment extends Fragment {
    public static final String ARG_PAGE = "page";
    @BindView(R.id.calendar_view)
    GridView gridView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private Context context;
    private Calendar calendar;
    private List<ScheduleCalendar> list = new ArrayList<>();

    public static Fragment create(int pageNumber) {
        CalendarPagerFragment fragment = new CalendarPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        int pageNumber = getArguments().getInt(ARG_PAGE);
        calendar = CalendarUtil.getSelectCalendar(pageNumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        CalendarViewAdapter adapter = new CalendarViewAdapter(context, calendar, gridView, list);
        gridView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });
        refreshLayout.setColorSchemeResources(R.color.bg_main_orange);

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView day = (TextView) view.findViewById(R.id.tv_day);
                if (day.getVisibility() == View.GONE) {
                    return;
                }
                for (int i = 0; i < parent.getCount(); i++) {
                    parent.getChildAt(i).setBackgroundColor(ContextCompat.getColor(context, R.color.bg_white_color));
                }
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_main_color));
            }
        });


        int month = calendar.get(Calendar.MONTH);
        if (month == 0) {
            month = 12;
        }

        if (month % 2 == 0) {
            for (int i = 1; i < 29; i++) {
                if (i % 2 == 0) {
                    if (i % 8 == 0) {
                        list.add(new ScheduleCalendar(i, 0, 1));
                    } else if (i % 4 == 0) {
                        list.add(new ScheduleCalendar(i, 1, 0));
                    } else {
                        list.add(new ScheduleCalendar(i, 1, 1));
                    }
                }
            }
        } else {
            for (int i = 1; i < 29; i++) {
                if (i % 2 != 0) {
                    if (i % 5 == 0) {
                        list.add(new ScheduleCalendar(i, 0, 1));
                    } else if (i % 3 == 0) {
                        list.add(new ScheduleCalendar(i, 1, 0));
                    } else {
                        list.add(new ScheduleCalendar(i, 1, 1));
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

}
