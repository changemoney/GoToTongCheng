package com.zhyan.gototongcheng.Main.MyOrder;

import android.os.Bundle;

import com.zhyan.gototongcheng.BaseActivity;
import com.zhyan.gototongcheng.R;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/26.
 */

public class MyOrderActivity extends BaseActivity{


    MyOrderActivityController myOrderActivityController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_myorder_lly);
        init();
    }
    @Override
    public void init() {
        ButterKnife.bind(this);
        myOrderActivityController = new MyOrderActivityController(this);
    }
}
