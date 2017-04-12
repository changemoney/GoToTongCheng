package com.zhyan.gototongcheng.Main.MyOrder.OrderStatus;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.zhyan.gototongcheng.BaseActivity;
import com.zhyan.gototongcheng.Main.MyOrder.MyOrderActivityController;
import com.zhyan.gototongcheng.R;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/4/5.
 */

public class OrderStatusActivity extends FragmentActivity {


    OrderStatusSwitchVPController orderStatusSwitchVPController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_myorder_orderstatus_lly);
        init();
    }

    public void init() {
        ButterKnife.bind(this);
        orderStatusSwitchVPController = new OrderStatusSwitchVPController(this,getSupportFragmentManager());

    }

}
