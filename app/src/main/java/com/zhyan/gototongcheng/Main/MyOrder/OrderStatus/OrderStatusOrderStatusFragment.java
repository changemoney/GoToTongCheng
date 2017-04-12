package com.zhyan.gototongcheng.Main.MyOrder.OrderStatus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhyan.gototongcheng.R;

/**
 * Created by admin on 2017/4/12.
 */

public class OrderStatusOrderStatusFragment extends Fragment {


    private OrderStatusOrderStatusController orderStatusOrderStatusController;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main_myorder_orderstatus_content_vp_item_first_lly, container, false);//关联布局文件
        view = rootView;
        /*Toast.makeText(view.getContext(),"this is onCreateView",Toast.LENGTH_LONG).show();*/
        init();
        return rootView;
    }
    private void init(){
        orderStatusOrderStatusController = new OrderStatusOrderStatusController(view);
    }



    public void onResume(){
        super.onResume();
        /*Toast.makeText(view.getContext(),"this is onResume",Toast.LENGTH_LONG).show();*/
        orderStatusOrderStatusController.onResume();
    }
}