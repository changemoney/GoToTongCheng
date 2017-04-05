package com.zhyan.gototongcheng.Main.MyOrder.OrderStatus;

import android.app.Activity;
import android.os.Bundle;

import com.zhyan.gototongcheng.BaseActivity;
import com.zhyan.gototongcheng.Main.MyOrder.MyOrderActivityController;
import com.zhyan.gototongcheng.R;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/5.
 */

public class OrderStatusActivity extends BaseActivity {


    OrderStatusController orderStatusController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_myorder_orderstatus_lly);
        init();
    }
    @Override
    public void init() {
        ButterKnife.bind(this);
        orderStatusController = new OrderStatusController(this);
    }
}
