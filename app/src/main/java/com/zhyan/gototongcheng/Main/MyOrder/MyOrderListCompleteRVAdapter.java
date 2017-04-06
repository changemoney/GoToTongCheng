package com.zhyan.gototongcheng.Main.MyOrder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.Main.MyOrder.OrderStatus.OrderStatusActivity;
import com.zhyan.gototongcheng.NetWork.OrderNetWorks;
import com.zhyan.gototongcheng.R;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import gototongcheng.zhyan.com.library.Bean.BaseBean;
import gototongcheng.zhyan.com.library.Bean.MyOrderBean;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import rx.Observer;

/**
 * Created by admin on 2017/3/17.
 */

public class MyOrderListCompleteRVAdapter extends RecyclerView.Adapter<MyOrderListCompleteRVAdapter.MyHoldView> {

    private List<MyOrderBean> myOrderBeanList;
    private Context context;

    public MyOrderListCompleteRVAdapter(Context context1, List<MyOrderBean> orderBeanList){
        context = context1;
        myOrderBeanList = orderBeanList;
    }

    public void addMyOrderBeanList(Collection<MyOrderBean> myOrderBeanList1){
/*
        Toast.makeText(context,"InitCompleteRVItemViews:"+myOrderBeanList1.size(),Toast.LENGTH_SHORT).show();*/
        myOrderBeanList.clear();
        myOrderBeanList.addAll(myOrderBeanList1);
        /*Toast.makeText(context,"InitCompleteRVItemViews:"+myOrderBeanList.size(),Toast.LENGTH_SHORT).show();*/
        this.notifyDataSetChanged();

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
        this.notifyDataSetChanged();
    }

    @Override
    public MyHoldView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHoldView(LayoutInflater.from(context).inflate(R.layout.activity_main_myorder_content_tab_vp_item_rv_complete_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHoldView holder, int position) {
        initHoldData(holder,position);
    }
    private void initHoldData(MyHoldView holdView,int pos){
        holdView.tvMyOrderContentTabVPItemRVCompleteItemBeginAddr.setText(myOrderBeanList.get(pos).getClientaddrAddr1());
        holdView.tvMyOrderContentTabVPItemRVCompleteItemEndAddr.setText(myOrderBeanList.get(pos).getClientaddrAddr());
        holdView.tvMyOrderContentTabVPItemRVCompleteItemOrderNo.setText(myOrderBeanList.get(pos).getOrderNo());
        holdView.pos = pos;
        holdView.tvMyOrderContentTabVPItemRVCompleteItemOrderTime.setText(myOrderBeanList.get(pos).getOrderOrdertime());
        holdView.tvMyOrderContentTabVPItemRVCompleteItemOrderMoney.setText("￥"+myOrderBeanList.get(pos).getOrderOrderprice());
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
        @OnTouch(R.id.sly_main_myorder_content_rv_complete_item)
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
                    /*Toast.makeText(context,"i'm begin:"+disx+" "+disy,Toast.LENGTH_SHORT).show();*/
                        if((disx == 0)&&(disy ==0)){
                            Intent intent = new Intent(context, OrderStatusActivity.class);
                           /* context.startActivity(intent);*/
                            /*Toast.makeText(context,"i'm click",Toast.LENGTH_SHORT).show();*/
                        }
                        break;
            }

            return false;
        }
    /*    @BindView(R.id.sly_main_myorder_content_rv_complete_item)
        LinearLayout llyMainMyOrderContentRVCompleteItem;
        @OnClick(R.id.lly_main_myorder_content_rv_complete_item)
        public void llyMainMyOrderContentRVCompleteItemOnclick(){
            Toast.makeText(context,"i'm click",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, OrderStatusActivity.class);
            context.startActivity(intent);
        }*/
        @BindView(R.id.tv_myorder_content_tab_vp_item_rv_complete_item_orderno)
        TextView tvMyOrderContentTabVPItemRVCompleteItemOrderNo;
        @BindView(R.id.tv_myorder_content_tab_vp_item_rv_complete_item_beginaddr)
        TextView tvMyOrderContentTabVPItemRVCompleteItemBeginAddr;
        @BindView(R.id.tv_myorder_content_tab_vp_item_rv_complete_item_endaddr)
        TextView tvMyOrderContentTabVPItemRVCompleteItemEndAddr;
        @BindView(R.id.tv_myorder_content_tab_vp_item_rv_complete_item_ordertime)
        TextView tvMyOrderContentTabVPItemRVCompleteItemOrderTime;
        @BindView(R.id.tv_myorder_content_tab_vp_item_rv_complete_item_ordermoney)
        TextView tvMyOrderContentTabVPItemRVCompleteItemOrderMoney;
        @BindView(R.id.rly_main_myorder_content_rv_complete_item_delete)
        RelativeLayout rlyMainMyOrderContentShopRVCompleteItemDelete;
        @OnClick(R.id.rly_main_myorder_content_rv_complete_item_delete)
        public void rlyMainMyOrderContentShopRVItemDeleteOnclick(){
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(context);
            String usid = xcCacheManager.readCache("usid");
            if((usid != null)&&(!usid.isEmpty())) {
                /*Toast.makeText(getBaseContext(),"usid:"+usid,Toast.LENGTH_SHORT).show();*/
                String orderNum = tvMyOrderContentTabVPItemRVCompleteItemOrderNo.getText().toString();
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
                            Toast.makeText(context,baseBean.getResult(),Toast.LENGTH_SHORT).show();
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
