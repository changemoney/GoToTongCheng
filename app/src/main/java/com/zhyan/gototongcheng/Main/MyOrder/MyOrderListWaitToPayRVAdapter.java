package com.zhyan.gototongcheng.Main.MyOrder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.Main.MyOrder.OrderStatus.OrderStatusActivity;
import com.zhyan.gototongcheng.NetWork.OrderNetWorks;
import com.zhyan.gototongcheng.R;
import com.zhyan.gototongcheng.Widget.PopupOnClickEvents;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import gototongcheng.zhyan.com.library.Bean.BaseBean;
import gototongcheng.zhyan.com.library.Bean.MyOrderBean;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import rx.Observer;

/**
 * Created by admin on 2017/3/17.
 */

public class MyOrderListWaitToPayRVAdapter extends RecyclerView.Adapter<MyOrderListWaitToPayRVAdapter.MyHoldView> {

    private List<MyOrderBean> myOrderBeanList;
    private Activity activity;

    public MyOrderListWaitToPayRVAdapter(Activity activity1, List<MyOrderBean> orderBeanList){
        activity = activity1;
        myOrderBeanList = orderBeanList;
    }

    public void addMyOrderBeanList(Collection<MyOrderBean> myOrderBeanList1){

        myOrderBeanList.clear();
        myOrderBeanList.addAll(myOrderBeanList1);
        notifyDataSetChanged();


       /* int tempCount = myOrderBeanList.size();
        myOrderBeanList.clear();
        notifyItemRangeChanged(0,tempCount);
        notifyItemRangeRemoved(0,tempCount);
        myOrderBeanList.addAll(myOrderBeanList1);
        notifyItemRangeInserted(0,myOrderBeanList1.size());
        notifyItemRangeChanged(0,myOrderBeanList1.size());*/
    }
    public void clean(){
        myOrderBeanList.clear();
        notifyDataSetChanged();
    }

    @Override
    public MyHoldView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHoldView(LayoutInflater.from(activity).inflate(R.layout.activity_main_myorder_content_tab_vp_item_rv_wait_to_pay_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHoldView holder, int position) {
        initHoldData(holder,position);
    }
    private void initHoldData(MyHoldView holdView,int pos){
        holdView.tvMyOrderContentTabVPWaitToPayItemRVItemBeginAddr.setText(myOrderBeanList.get(pos).getClientaddrAddr());
        holdView.tvMyOrderContentTabVPWaitToPayItemRVItemEndAddr.setText(myOrderBeanList.get(pos).getClientaddrAddr1());
        holdView.tvMyOrderContentTabVPWaitToPayItemRVItemOrderNo.setText(myOrderBeanList.get(pos).getOrderNo());
        holdView.pos = pos;
        holdView.tvMyOrderContentTabVPWaitToPayItemRVItemOrderTime.setText(myOrderBeanList.get(pos).getOrderOrdertime());
        holdView.tvMyOrderContentTabVPWaitToPayItemRVItemOrderMoney.setText("￥"+myOrderBeanList.get(pos).getOrderOrderprice());
    }


    @Override
    public int getItemCount() {
        return myOrderBeanList.size();
    }

    public class MyHoldView extends RecyclerView.ViewHolder{
        int pos = 0;
   /*     @BindView(R.id.lly_main_myorder_content_total)
        LinearLayout llyMainMyOrderContentTotal;*/
        float bx= 0,by=0;
        @OnTouch(R.id.sly_main_myorder_content_rv_wait_to_pay_item)
        public boolean llyMainMyOrderContentTotalTouch(View view, MotionEvent event){

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                        bx = event.getRawX();
                        by = event.getRawY();
                        break;
                case MotionEvent.ACTION_UP:
                        float ex = event.getRawX();
                        float ey = event.getRawY();
                        float disx = ex - bx;
                        float disy = ey - by;
                    /*Toast.makeText(activity,"i'm begin:"+disx+" "+disy,Toast.LENGTH_SHORT).show();*/
                        if((disx == 0)&&(disy ==0)){
                            Intent intent = new Intent(activity, OrderStatusActivity.class);
                          /*  activity.startActivity(intent);*/
                            /*Toast.makeText(activity,"i'm click",Toast.LENGTH_SHORT).show();*/
                        }
                        break;
            }

            return false;
        }
        LinearLayout llyMainMyOrderTotal = (LinearLayout)activity.findViewById(R.id.lly_main_myorder_total);
        @BindView(R.id.bt_myorder_content_tab_vp_wait_to_pay_item_rv_item_submit)
        Button btMyOrderContentTabVPWaitToPayItemRVItemSubmit;
        @OnClick(R.id.bt_myorder_content_tab_vp_wait_to_pay_item_rv_item_submit)
        public void btMyOrderContentTabVPWaitToPayItemRVItemSubmitOnclick(){

            PopupOnClickEvents popupOnClickEvents = new PopupOnClickEvents(activity);
            popupOnClickEvents.WaitToPayPayConfirm(llyMainMyOrderTotal,myOrderBeanList.get(pos));
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
            XCCacheSavename xcCacheSavename = new XCCacheSavename();
            xcCacheManager.writeCache(xcCacheSavename.isMyOrderPay,"yes");
        }
        @BindView(R.id.tv_myorder_content_tab_vp_wait_to_pay_item_rv_item_orderno)
        TextView tvMyOrderContentTabVPWaitToPayItemRVItemOrderNo;
        @BindView(R.id.tv_myorder_content_tab_vp_wait_to_pay_item_rv_item_beginaddr)
        TextView tvMyOrderContentTabVPWaitToPayItemRVItemBeginAddr;
        @BindView(R.id.tv_myorder_content_tab_vp_wait_to_pay_item_rv_item_endaddr)
        TextView tvMyOrderContentTabVPWaitToPayItemRVItemEndAddr;
        @BindView(R.id.tv_myorder_content_tab_vp_wait_to_pay_item_rv_item_ordertime)
        TextView tvMyOrderContentTabVPWaitToPayItemRVItemOrderTime;
        @BindView(R.id.tv_myorder_content_tab_vp_wait_to_pay_item_rv_item_ordermoney)
        TextView tvMyOrderContentTabVPWaitToPayItemRVItemOrderMoney;
        @BindView(R.id.rly_main_myorder_content_rv_wait_to_pay_item_delete)
        RelativeLayout rlyMainMyOrderContentShopRVItemDelete;
        @OnClick(R.id.rly_main_myorder_content_rv_wait_to_pay_item_delete)
        public void rlyMainMyOrderContentRVItemDeleteOnclick(){
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
            String usid = xcCacheManager.readCache("usid");
            if((usid != null)&&(!usid.isEmpty())) {
                /*Toast.makeText(getBaseContext(),"usid:"+usid,Toast.LENGTH_SHORT).show();*/
                String orderNum = tvMyOrderContentTabVPWaitToPayItemRVItemOrderNo.getText().toString();
                if(!orderNum.isEmpty()) {
                    OrderNetWorks orderNetWorks = new OrderNetWorks();
                    orderNetWorks.deleteOrder(usid, orderNum, 1.0, new Observer<BaseBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BaseBean baseBean) {
                            Toast.makeText(activity,baseBean.getResult(),Toast.LENGTH_SHORT).show();
                            myOrderBeanList.remove(pos);
                           notifyDataSetChanged();
                        }
                    });
                }
            }
        }
        public MyHoldView(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
