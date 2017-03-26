package com.zhyan.gototongcheng;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/13.
 */

public class SplashViewPageAdapter extends PagerAdapter {

    private List<View> dataList;
    private Activity activity;
    public SplashViewPageAdapter(Activity activity1,List<View> dataList1){
        activity = activity1;
        dataList=dataList1;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {

        parent.addView(dataList.get(position),0);
            return dataList.get(position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((dataList.get(position)));
    }


}
