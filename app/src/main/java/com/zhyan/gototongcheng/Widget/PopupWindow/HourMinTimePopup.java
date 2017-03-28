package com.zhyan.gototongcheng.Widget.PopupWindow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhyan.gototongcheng.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Utils.TimeUtil;
import gototongcheng.zhyan.com.library.Widget.WheelView.AbstractWheelTextAdapter;
import gototongcheng.zhyan.com.library.Widget.WheelView.OnWheelChangedListener;
import gototongcheng.zhyan.com.library.Widget.WheelView.OnWheelScrollListener;
import gototongcheng.zhyan.com.library.Widget.WheelView.WheelView;

/**
 * Created by admin on 2017/3/10.
 */

public class HourMinTimePopup extends PopupWindow {
    private View mPopView;
    private Activity activity;
    private boolean isInitData = false;
    private String selectHour,selectMin,selectDay1,selectAMPM;
    private int selectYear,selectMonth,selectDay,selectWeek;

 /*   private String ,;*/
    private int currentHour,currentMin,currentYear,currentMonth,currentDay,currentWeek;
    private String currentDay1,currentAMPM ;
    private int hour,min,year,month,day,week,ampm;
    private String day1;
    private List<String> yearList=new ArrayList<>(),monthList=new ArrayList<>(),dayList=new ArrayList<>(),day1List=new ArrayList<>(),AMPMList=new ArrayList<>(),hourList=new ArrayList<>(),minList=new ArrayList<>();
    private OnDateTimePopupListener onDateTimePopupListener;
    /*取消 确定*/
    @BindView(R.id.rly_popup_fourwheel_cancel)
    RelativeLayout rlyPopupFourWheelCancel;
    @OnClick(R.id.rly_popup_fourwheel_cancel)
    public void rlyPopupFourWheelCancelOnclick(){
        dismiss();
    }
    @BindView(R.id.rly_popup_fourwheel_query)
    RelativeLayout rlyPopupFourWheelQuery;

    @OnClick(R.id.rly_popup_fourwheel_query)
    public void rlyPopupFourWheelQueryOnclick(){
        if(onDateTimePopupListener != null){
            onDateTimePopupListener.onClick(selectDay1,selectAMPM,selectHour,selectMin);
            /*onDateTimePopupListener.onClick(selectDay,selectHour,selectMin);*/
        }
        dismiss();
    }

    /*取消 确定*/

    /*四个仿造ios的滚轮*/
    @BindView(R.id.wv_popup_fourwheel_day)
    WheelView wvPopupFourWheelDay;
    @BindView(R.id.wv_popup_fourwheel_ampm)
    WheelView wvPopupFourWheelAMPM;
    @BindView(R.id.wv_popup_fourwheel_hour)
    WheelView wvPopupFourWheelHour;
    @BindView(R.id.wv_popup_fourwheel_min)
    WheelView wvPopupFourWheelMin;
    /*三个仿造ios的滚轮*/

    /*数据填充器 adapter*/
    private CalendarTextAdapter mDayAdapter;
    private CalendarTextAdapter mAMPMAdapter;
    private CalendarTextAdapter hourAdapter;
    private CalendarTextAdapter minAdapter;
    /*数据填充器 adapter*/
    /*字体大小*/
    private int maxTextSize = 19;
    private int minTextSize = 9;
    /*字体大小*/

    public HourMinTimePopup(final Activity activity1){
        activity = activity1;
        init();
    }
    private void init(){
        initInflateView();
        ButterKnife.bind(this,mPopView);
        initAMPM();//放下面就会出现null错误
        if(!isInitData){
            initData();
        }

       /* initDay();*/
        initDay1(12);

        initHour();
        initMin();
        initAdapter();
        initListener();
    }
    /*界面初始化*/
    private void initInflateView(){
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopView= inflater.inflate(R.layout.popupwindow_fourwheel_lly, null);
        this.setContentView(mPopView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点击外面的控件也可以使得PopUpWindow dimiss
        this.setOutsideTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);


    }
    /*界面初始化*/
    /*初始化选择天 小时 分钟*/
    private void initData(){
        setData(getYear(),getMonth(),getDay(),getWeek(),getAMPM(),getHour(),getMin());
    }
    private void setData(int year,int month,int day,int week,int ampm,int hour,int min){
        selectYear = year;
        selectMonth = month;
        selectDay = day;
        selectWeek = week;
        selectHour = ""+hour;
        selectMin = ""+min;
        selectDay1 = "今天";
        selectAMPM = AMPMList.get(ampm);
        this.currentYear = year;
        this.currentMonth = month;
        this.currentDay = day;
        this.currentWeek = week;
        this.currentHour = hour;
        this.currentMin = min;
        this.currentDay1 = "今天";
        this.currentAMPM = AMPMList.get(ampm);
    }
/*    private String getDay(){
        return "请选择";
    }*/
    public int getAMPM(){
        TimeUtil timeUtil = new TimeUtil();
        int hours = timeUtil.getHour(new Date());
        if(hours > 12){
            return 1;
        }else {
            return 0;
        }
    }
    public String getDay1(){
        String day1 = getMonth() +"月"+getDay()+"日"+"周"+getWeek();
        return day1;
    }

    public int getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public int getMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }

    public int getDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DATE);
    }
    public int getWeek(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_WEEK);
    }
    private int getHour(){
        return 0;
    }
    private int getMin(){
        return 0;
    }
/*    public String getDay() {
        Calendar c = Calendar.getInstance();
        String month = (c.get(Calendar.MONTH) + 1)+"";
        String day = c.get(Calendar.DATE)+"";
        String week = c.get(Calendar.DAY_OF_WEEK_IN_MONTH)+"";
        return month+" " + day +" "+ week;
    }*/

    /**
     * 设置年份
     *
     * @param year
     */
    public int setYear(int year) {
        int yearIndex = 0;
        if (year != getYear()) {
            this.month = 12;
        } else {
            this.month = getMonth();
        }
/*
        for (int i = getYear()+100; i > 1950; i--) {
            if (i == year) {
                return yearIndex;
            }
            yearIndex++;
        }*/
        for (int i = 2100; i > 1950; i--) {
            if (i == year) {
                return yearIndex;
            }
            yearIndex++;
        }
        return yearIndex;
    }

    /**
     * 设置月份
     *
     * @param
     * @param month
     * @return
     */
    public int setMonth(int month) {
        int monthIndex = 0;
        calDays(currentYear, month);
        for (int i = 1; i < this.month; i++) {
            if (month == i) {
                return monthIndex;
            } else {
                monthIndex++;
            }
        }
        return monthIndex;
    }
    public void initYears() {

        for (int i = 2100; i > 1950; i--) {
            yearList.add(i + "");
        }
    }
    public void initMonths(int months) {
        monthList.clear();
        for (int i = 1; i <= months; i++) {
            monthList.add(i + "");
        }
    }
    public void initDays(int days) {
        dayList.clear();
        for (int i = 1; i <= days; i++) {
            dayList.add(i + "");
        }
    }
    public void initDay1(int months){
        day1List.clear();
        TimeUtil timeUtil = new TimeUtil();

        String monthDay = "";
        for(int i=1;i<= months;i++){
            monthDay = "";
            if(i < 10) {
                calDays(getYear(), i);
                monthDay += "0"+i + "月";

            }else{
                calDays(getYear(), i);
                monthDay += i + "月";
            }
            for(int j=1;j<=day;j++){

                if(j < 10){
                    monthDay += "0"+ j+"日";
                }else{
                    monthDay +=  j+"日";
                }

                String tempweekdate =""+getYear();
                if(i < 10){
                    tempweekdate +="-0"+i;
                    if(j < 10){
                        tempweekdate += "-0"+j;
                    }else{
                        tempweekdate += "-"+ j;
                    }
                }else{
                    tempweekdate += "-"+i;
                    if(j < 10){
                        tempweekdate += "-0"+j;
                    }else{
                        tempweekdate += "-"+ j;
                    }
                }
                String week = timeUtil.getDayofweek(tempweekdate)+"";
                monthDay += week ;
                if((i == getMonth())&&(j == getDay())){
                    monthDay = "今天";
                }
                day1List.add(monthDay);
                monthDay = "";
                if(i < 10) {
                    monthDay += "0"+i + "月";
                }else{
                    monthDay += i + "月";
                }

            }
        }
    }
    public int setDay1(String currentDay1){
        int indexDay1 = 0;
        for(int i=0;i<day1List.size();i++){
            if(day1List.get(i).equals(currentDay1)){
                return i;
            }else{
                i++;
            }
        }
        return indexDay1;
    }
    /*初始化 设置 小时 分钟*/
    private void initHour(){
        /*hourList = new ArrayList<String>();*/
        this.selectHour = "10";
        hourList.clear();
        for(int i=10;i<24;i++)
        {
            if(i <10)
            {
                hourList.add("0"+i);
            }else {
                hourList.add("" + i);
            }
        }
        hourList.add("00");
    }
    private void initMin(){
        /*minList = new ArrayList<String>();*/
        this.selectMin = "00";
        minList.clear();
        for(int i=0;i<60;i++)
        {
            if(i <10)
            {
                minList.add("0"+i);
            }else {
                minList.add("" + i);
            }
        }
    }
    private int setHour(int hour){
        int hourIndex = 0;
        for(int i=0;i<24;i++){
            if(i == hour){
                return hourIndex;
            }else {
                hourIndex++;
            }
        }
        return hourIndex;
    }

    private int setMin(int min){
        int minIndex = 0;
        for(int i=0;i<60;i++){
            if(min == i){
                return minIndex;
            }else{
                minIndex++;
                continue;
            }
        }
        return minIndex;
    }
    /*初始化 设置 小时 分钟*/



    /*初始化day的选择*/
    private void initDay(){
        dayList = new ArrayList<String>();
/*        dayList.add("请选择");
        dayList.add("今天");
        dayList.add("明天");*/
    }
    /*初始化day的选择*/
    /*初始化AMPM的选择*/
    private int setAMPM(String select){
        int index = 0;
        for(int i = 0;i<AMPMList.size();i++){
            if(AMPMList.get(i).equals(select)){
                return i;
            }
        }
        return index;
    }

    private void initAMPM(){
        AMPMList = new ArrayList<String>();
        AMPMList.add("上午");
        AMPMList.add("下午");
    }
    /*初始化AMPM的选择*/


    /**
     * 计算每月多少天
     *
     * @param month
     * @param year
     */
    public void calDays(int year, int month) {
        boolean leayyear = false;
        if (year % 4 == 0 && year % 100 != 0) {
            leayyear = true;
        } else {
            leayyear = false;
        }
        for (int i = 1; i <= 12; i++) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    this.day = 31;
                    break;
                case 2:
                    if (leayyear) {
                        this.day = 29;
                    } else {
                        this.day = 28;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    this.day = 30;
                    break;
            }
        }
      /*  if (year == getYear() && month == getMonth()) {
            this.day = getDay();
        }*/
    }


    /*数据填充初始化*/
    private void initAdapter(){

        mDayAdapter = new CalendarTextAdapter(activity,day1List,setDay1(currentDay1),maxTextSize,minTextSize);
        wvPopupFourWheelDay.setVisibleItems(10);
        wvPopupFourWheelDay.setViewAdapter(mDayAdapter);
        wvPopupFourWheelDay.setCurrentItem(setDay1(currentDay1));
        mAMPMAdapter = new CalendarTextAdapter(activity,AMPMList,setAMPM(currentAMPM),maxTextSize,minTextSize);
        wvPopupFourWheelAMPM.setVisibleItems(10);
        wvPopupFourWheelAMPM.setViewAdapter(mAMPMAdapter);
        wvPopupFourWheelAMPM.setCurrentItem(setAMPM(currentAMPM));
        hourAdapter = new CalendarTextAdapter(activity,hourList,setHour(currentHour),maxTextSize,minTextSize);
        wvPopupFourWheelHour.setVisibleItems(10);
        wvPopupFourWheelHour.setViewAdapter(hourAdapter);
        wvPopupFourWheelHour.setCurrentItem(0);
        minAdapter = new CalendarTextAdapter(activity,minList,setMin(currentMin),maxTextSize,minTextSize);
        wvPopupFourWheelMin.setVisibleItems(10);
        wvPopupFourWheelMin.setViewAdapter(minAdapter);
        wvPopupFourWheelMin.setCurrentItem(0);
    }
    /*数据填充初始化*/
    /*滚轮监听初始化*/
    private void initListener(){
      /*  wvPopupFourWheelDay.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentDay = mDayAdapter.getItemText(wheel.getCurrentItem()).toString();
                setTextviewSize(currentDay,mDayAdapter);
                selectDay = currentDay;
                setDay(currentDay);
                initHour();
                hourAdapter = new CalendarTextAdapter(activity,hourList,0,maxTextSize,minTextSize);
                wvPopupFourWheelHour.setVisibleItems(6);
                wvPopupFourWheelHour.setViewAdapter(hourAdapter);
                wvPopupFourWheelHour.setCurrentItem(setDay(currentDay));
            }
        });*/
       /* wvPopupFourWheelDay.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                String currentDay = (String)mDayAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentDay,mDayAdapter);
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {

            }
        });*/
        wvPopupFourWheelDay.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentDay1 = mDayAdapter.getItemText(wheel.getCurrentItem()).toString();
                if(currentDay1 != null) {
                    selectDay1 = currentDay1;
                    setTextviewSize(currentDay1, mDayAdapter);

                    setDay1(currentDay1);
                }
            }
        });
        wvPopupFourWheelDay.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                String currentDay1 = (String)mDayAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentDay1,mDayAdapter);
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {

            }
        });
        wvPopupFourWheelAMPM.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentAMPM = mAMPMAdapter.getItemText(wheel.getCurrentItem()).toString();
                if(currentAMPM != null) {
                    selectAMPM = currentAMPM;
                    setTextviewSize(currentAMPM, mAMPMAdapter);

                    setAMPM(currentAMPM);
                }
            }
        });
        wvPopupFourWheelAMPM.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                String currentAMPM = (String)mAMPMAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentAMPM,mAMPMAdapter);
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {

            }
        });
        wvPopupFourWheelHour.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentHour = hourAdapter.getItemText(wheel.getCurrentItem()).toString();
                if(currentHour != null) {
                    selectHour = currentHour;
                    setTextviewSize(currentHour, hourAdapter);

                    setHour(Integer.valueOf(currentHour));
                }
            }
        });
        wvPopupFourWheelHour.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                String currentHour = (String)hourAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentHour,hourAdapter);
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {

            }
        });



        wvPopupFourWheelMin.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentMin = minAdapter.getItemText(wheel.getCurrentItem()).toString();
                if(currentMin != null) {
                    selectMin = currentMin;
                    setTextviewSize(currentMin, minAdapter);

                    setMin(Integer.valueOf(selectMin));
                }
            }
        });
        wvPopupFourWheelMin.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                String currentMin = (String)minAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentMin,minAdapter);
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {

            }
        });
    }



    /*滚轮监听初始化*/

    public interface OnDateTimePopupListener{
        public void onClick(String day, String ampm, String hour, String min);
    }

    public void setOnDateTimePopupListener(OnDateTimePopupListener onDateTimePopupListener){
        this.onDateTimePopupListener = onDateTimePopupListener;
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }









































    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        List<String> dateList;

        protected CalendarTextAdapter(Context context, List<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.popupdialog_wheel_item, NO_RESOURCE, currentItem, maxsize, minsize);
            this.dateList = list;
            setItemTextResource(R.id.tv_dialogpopup_wheel_item);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return dateList.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return dateList.get(index) + "";
        }
    }
}
