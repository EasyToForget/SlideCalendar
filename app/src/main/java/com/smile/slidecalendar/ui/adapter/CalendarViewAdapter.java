package com.smile.slidecalendar.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smile.slidecalendar.R;
import com.smile.slidecalendar.model.ScheduleCalendar;
import com.smile.slidecalendar.utils.DateFormatUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarViewAdapter extends BaseAdapter {

    private Context context;
    private Calendar calendar;
    private LayoutInflater inflater;
    private GridView gridView;
    private ArrayList<Date> days;
    private List<ScheduleCalendar> list;
    /**
     * 当月的周数
     */
    private int weeksOfMonth = 6;
    /**
     * 日历年的当前年
     */
    private int year = 2015;
    /**
     * 日历月的当前月
     */
    private int month = 0;
    /**
     * 当前是几号，20151010
     */
    private int tvDay;
    /**
     * 当前年是哪一年
     */
    private int currentYear;
    /**
     * 当前月是几月
     */
    private int currentMonth;
    /**
     * 当前天在本月的第几周
     */
    private int currentWeekNumber;

    public CalendarViewAdapter(Context context, Calendar calendar, GridView gridView, List<ScheduleCalendar> list) {
        this.context = context;
        this.calendar = calendar;
        this.gridView = gridView;
        this.list = list;
        days = getDates();
        inflater = LayoutInflater.from(context);
    }

    private void UpdateStartDateForMonth() {
        Calendar currentCalendar = Calendar.getInstance();
        currentYear = currentCalendar.get(Calendar.YEAR);
        currentMonth = currentCalendar.get(Calendar.MONTH);
        tvDay = Integer.parseInt(DateFormatUtil.formatDate(DateFormatUtil.FORMAT_yyyyMMdd, System.currentTimeMillis()));
        currentWeekNumber = currentCalendar.get(Calendar.WEEK_OF_MONTH);
        weeksOfMonth = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
        calendar.set(Calendar.DATE, 1); // 设置成当月第一天
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);// 得到当前日历显示的月


        // 星期一是2 星期天是1 填充剩余天数
        int day = calendar.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
        calendar.add(Calendar.DAY_OF_WEEK, -day);

        calendar.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位

    }

    private ArrayList<Date> getDates() {

        UpdateStartDateForMonth();

        ArrayList<Date> list = new ArrayList<>();

        for (int i = 1; i <= weeksOfMonth * 7; i++) {
            list.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, gridView.getHeight() / weeksOfMonth);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_calendar, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setLayoutParams(params);
            convertView.setTag(holder);
        } else {
            convertView.setLayoutParams(params);
            holder = (ViewHolder) convertView.getTag();
        }

        Date myDate = (Date) getItem(position);
        int day = myDate.getDate(); // 日期
        holder.tvDay.setText(String.valueOf(day));
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        int iMonth = cal.get(Calendar.MONTH);

        // 判断是否是当前月
        if (iMonth == month) {
            holder.tvDay.setVisibility(View.VISIBLE);

            boolean isCurrentDay = false;
            int m = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getD() == day) {
                    isCurrentDay = true;
                    m = i;
                    break;
                }
            }

            if (!isCurrentDay) {
                holder.llMuti.setVisibility(View.GONE);
                holder.llSingle.setVisibility(View.GONE);
            } else {
                ScheduleCalendar scheduleCalendar = list.get(m);
                if (scheduleCalendar != null && scheduleCalendar.getD() == day) {
                    if (scheduleCalendar.getLoad_tasks() > 0 && scheduleCalendar.getUnload_tasks() > 0) {
                        holder.llMuti.setVisibility(View.VISIBLE);
                        holder.llSingle.setVisibility(View.GONE);
                    } else if (scheduleCalendar.getLoad_tasks() > 0 && scheduleCalendar.getUnload_tasks() == 0) {
                        holder.llMuti.setVisibility(View.GONE);
                        holder.llSingle.setVisibility(View.VISIBLE);
                        holder.viewSingle.setBackgroundResource(R.drawable.bg_calendar_green_oval);
                    } else if (scheduleCalendar.getLoad_tasks() == 0 && scheduleCalendar.getUnload_tasks() > 0) {
                        holder.llMuti.setVisibility(View.GONE);
                        holder.llSingle.setVisibility(View.VISIBLE);
                        holder.viewSingle.setBackgroundResource(R.drawable.bg_calendar_red_oval);
                    } else {
                        holder.llMuti.setVisibility(View.GONE);
                        holder.llSingle.setVisibility(View.GONE);
                    }
                } else {
                    holder.llMuti.setVisibility(View.GONE);
                    holder.llSingle.setVisibility(View.GONE);
                }
            }
        } else {
            holder.llMuti.setVisibility(View.GONE);
            holder.llSingle.setVisibility(View.GONE);
            holder.tvDay.setVisibility(View.GONE);
        }


        /**
         * 根据当前天判断日期的颜色，当前天：红色；当前天之前：灰色；当前天之后：黑色
         */
        int intDay = Integer.parseInt(DateFormatUtil.formatDate(DateFormatUtil.FORMAT_yyyyMMdd, myDate.getTime()));
        if (tvDay == intDay) {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_main_color));
            holder.tvDay.setTextColor(ContextCompat.getColor(context, R.color.bg_main_red));
        } else if (tvDay < intDay) {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_white_color));
            holder.tvDay.setTextColor(ContextCompat.getColor(context, R.color.bg_black_color));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_white_color));
            holder.tvDay.setTextColor(ContextCompat.getColor(context, R.color.font_main_color));
        }

        /**
         * 根据当前天判断本周分割线的颜色
         */
        if (currentYear != year || currentMonth != month) {
            holder.dividerView.setVisibility(View.VISIBLE);
            holder.dividerCurrentView.setVisibility(View.GONE);
        } else {
            if ((currentWeekNumber - 1) * 7 <= position && position < currentWeekNumber * 7) {
                holder.dividerCurrentView.setVisibility(View.VISIBLE);
                holder.dividerView.setVisibility(View.GONE);
            } else {
                holder.dividerView.setVisibility(View.VISIBLE);
                holder.dividerCurrentView.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_calendar.xml  * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.ll_muti)
        LinearLayout llMuti;
        @BindView(R.id.ll_single)
        LinearLayout llSingle;
        @BindView(R.id.view_single)
        View viewSingle;
        @BindView(R.id.divider_view)
        View dividerView;
        @BindView(R.id.divider_current_view)
        View dividerCurrentView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
