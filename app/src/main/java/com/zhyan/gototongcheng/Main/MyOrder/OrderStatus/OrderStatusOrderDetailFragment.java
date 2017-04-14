package com.zhyan.gototongcheng.Main.MyOrder.OrderStatus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhyan.gototongcheng.R;

/**
 * Created by admin on 2017/4/12.
 */

public class OrderStatusOrderDetailFragment extends Fragment {



    private OrderStatusOrderDetailController orderStatusOrderDetailController;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main_myorder_orderstatus_content_vp_item_second_lly, container, false);//关联布局文件
        view = rootView;
        init();
        return rootView;
    }

    private void init(){
        orderStatusOrderDetailController = new OrderStatusOrderDetailController(view);
    }
}