package com.zhyan.gototongcheng.Main.MyOrder;

import android.os.Bundle;
import android.widget.Toast;

import com.zhyan.gototongcheng.BaseActivity;
import com.zhyan.gototongcheng.R;

import butterknife.ButterKnife;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;

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

    protected void onResume(){
        super.onResume();
        /*Toast.makeText(this,"this is onResume",Toast.LENGTH_SHORT).show();*/
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSavename xcCacheSavename = new XCCacheSavename();
        String isMyOrderPay = xcCacheManager.readCache(xcCacheSavename.isMyOrderPay);
        if((isMyOrderPay == null)||(isMyOrderPay.isEmpty())||(isMyOrderPay.equals("no"))){
            /*Toast.makeText(this,"this is onResume return",Toast.LENGTH_SHORT).show();*/
            return;
        }
        if(isMyOrderPay.equals("yes")) {
            /*Toast.makeText(this,"this is onResume yes",Toast.LENGTH_SHORT).show();*/
            myOrderActivityController.initWaitToPayRVItemViews();
            xcCacheManager.writeCache(xcCacheSavename.isMyOrderPay,"no");
        }
    }
}
