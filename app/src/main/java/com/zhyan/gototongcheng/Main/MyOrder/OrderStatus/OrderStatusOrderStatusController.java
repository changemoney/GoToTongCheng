package com.zhyan.gototongcheng.Main.MyOrder.OrderStatus;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.Main.BaseController;
import com.zhyan.gototongcheng.NetWork.OrderNetWorks;
import com.zhyan.gototongcheng.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gototongcheng.zhyan.com.library.Bean.MyOrderOrderStatusBean;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import rx.Observer;

/**
 * Created by admin on 2017/4/12.
 */

public class OrderStatusOrderStatusController extends BaseController {
    /*首页控件*/
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_miles)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstMiles;
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_price)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstPrice;
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_time)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstTime;
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_paysuccess)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstPaySuccess;
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_paysuccess_time)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstPaySuccessTime;
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_bikerstatus)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstBikerStatus;
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_biker_tel)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstBikerTel;
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_biker_time)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstBikerTime;
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_biker_reachget)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstBikerReachGet;
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_biker_reachget_time)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstBikerReachGetTime;
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_biker_reachto)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstBikerReachTo;
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_biker_reachto_time)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstBikerReachToTime;
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_order_finish)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstOrderFinish;
    @BindView(R.id.tv_main_myorder_orderstatus_content_vp_item_first_order_finish_time)
    TextView tvMainMyOrderOrderStatusContentVPItemFirstOrderFinishTime;

    /*首页控件*/

    public OrderStatusOrderStatusController(View view1){
        view = view1;
        /*Toast.makeText(view.getContext(),"this is orderstatus:1",Toast.LENGTH_SHORT).show();*/
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this,view);
        /*tvMainMyOrderOrderStatusContentVPItemFirstMiles.setText("fffffffffffffffffffff");*/
        /*Toast.makeText(view.getContext(),"this is orderstatus:2",Toast.LENGTH_SHORT).show();*/
        getOrderStatusFromNet();
        /*Toast.makeText(view.getContext(),"this is orderstatus:3",Toast.LENGTH_SHORT).show();*/
    }

    /*初始化订单状态*/
    private void getOrderStatusFromNet(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheSavename xcCacheSavename = new XCCacheSavename();
        String orderNo = xcCacheManager.readCache(xcCacheSavename.myOrderOrderStatusOrderNo);
        String userUsid = xcCacheManager.readCache(xcCacheSavename.usid);
        if((orderNo != null)&&(userUsid != null)){
            OrderNetWorks orderNetWorks = new OrderNetWorks();
            /*Toast.makeText(view.getContext(),"this is orderstatus:4",Toast.LENGTH_SHORT).show();*/
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            System.out.println("\nuserUsid:"+userUsid+ " orderNo:"+orderNo);
            orderNetWorks.getOrderStatusFromNet(userUsid, orderNo, new Observer<List<MyOrderOrderStatusBean>>() {
                @Override
                public void onCompleted() {
                    /*Toast.makeText(view.getContext(),"this is orderstatus:5",Toast.LENGTH_SHORT).show();*/
                }

                @Override
                public void onError(Throwable e) {
                    /*Toast.makeText(view.getContext(),"this is orderstatus:6"+e,Toast.LENGTH_SHORT).show();*/
                }

                @Override
                public void onNext(List<MyOrderOrderStatusBean> myOrderOrderStatusBeen) {
                    Toast.makeText(view.getContext(),"this is orderstatus:"+myOrderOrderStatusBeen.get(0).getOrderstatusOrderstatus(),Toast.LENGTH_SHORT).show();
                    if(myOrderOrderStatusBeen == null){
                        return;
                    }
                    initOrderStatus(myOrderOrderStatusBeen);
                }
            });
        }
    }

    private void initOrderStatus(List<MyOrderOrderStatusBean> myOrderOrderStatusBean){
        tvMainMyOrderOrderStatusContentVPItemFirstMiles.setText(""+myOrderOrderStatusBean.get(0).getOrderMileage()+"km");
        tvMainMyOrderOrderStatusContentVPItemFirstPrice.setText(""+myOrderOrderStatusBean.get(0).getOrderOrderprice()+"元");
        String time = myOrderOrderStatusBean.get(0).getOrderOrdertime().split(" ")[1];
        tvMainMyOrderOrderStatusContentVPItemFirstTime.setText(""+time);
        tvMainMyOrderOrderStatusContentVPItemFirstPaySuccess.setText(""+myOrderOrderStatusBean.get(0).getPaystatusPaystatus());
        tvMainMyOrderOrderStatusContentVPItemFirstPaySuccessTime.setText(""+myOrderOrderStatusBean.get(0).getTransportationtime());
        tvMainMyOrderOrderStatusContentVPItemFirstBikerStatus.setText(""+myOrderOrderStatusBean.get(0).getTransportationBeginstatus());
        tvMainMyOrderOrderStatusContentVPItemFirstBikerTime.setText(""+myOrderOrderStatusBean.get(0).getTransportationBegintime());
        tvMainMyOrderOrderStatusContentVPItemFirstBikerTel.setText(""+myOrderOrderStatusBean.get(0).getLientaddr1Tel());
    }

    public void onResume(){
        getOrderStatusFromNet();
    }
}
