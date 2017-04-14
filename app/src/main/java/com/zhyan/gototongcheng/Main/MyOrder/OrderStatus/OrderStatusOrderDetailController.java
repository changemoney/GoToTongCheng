package com.zhyan.gototongcheng.Main.MyOrder.OrderStatus;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
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
 * Created by admin on 2017/4/13.
 */

public class OrderStatusOrderDetailController extends BaseController {

    @BindView(R.id.tv_main_myorder_orderstatus_orderdetail_content_getname)
    TextView tvMainMyOrderOrderStatusOrderDetailContentGetName;
    @BindView(R.id.tv_main_myorder_orderstatus_orderdetail_content_gettel)
    TextView tvMainMyOrderOrderStatusOrderDetailContentGetTel;
    @BindView(R.id.tv_main_myorder_orderstatus_orderdetail_content_getaddr)
    TextView tvMainMyOrderOrderStatusOrderDetailContentGetAddr;
    @BindView(R.id.tv_main_myorder_orderstatus_orderdetail_content_sendername)
    TextView tvMainMyOrderOrderStatusOrderDetailContentSenderName;
    @BindView(R.id.tv_main_myorder_orderstatus_orderdetail_content_sendertel)
    TextView tvMainMyOrderOrderStatusOrderDetailContentSenderTel;
    @BindView(R.id.tv_main_myorder_orderstatus_orderdetail_content_senderaddr)
    TextView tvMainMyOrderOrderStatusOrderDetailContentSenderAddr;
    @BindView(R.id.tv_main_myorder_orderstatus_orderdetail_content_orderno)
    TextView tvMainMyOrderOrderStatusOrderDetailContentOrderNo;
    @BindView(R.id.tv_main_myorder_orderstatus_orderdetail_content_sendman)
    TextView tvMainMyOrderOrderStatusOrderDetailContentSendMan;
    @BindView(R.id.tv_main_myorder_orderstatus_orderdetail_content_ordertime)
    TextView tvMainMyOrderOrderStatusOrderDetailContentOrderTime;
    @BindView(R.id.tv_main_myorder_orderstatus_orderdetail_content_paymethod)
    TextView tvMainMyOrderOrderStatusOrderDetailContentPayMethod;
    @BindView(R.id.et_main_myorder_orderstatus_orderdetail_content_remark)
    EditText etMainMyOrderOrderStatusOrderDetailContentRemark;
    public OrderStatusOrderDetailController(View view1){
        view = view1;
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this,view);
        getOrderDetailDataFromNet();
    }



    private void getOrderDetailDataFromNet(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheSavename xcCacheSavename = new XCCacheSavename();
        String orderNo = xcCacheManager.readCache(xcCacheSavename.myOrderOrderStatusOrderNo);
        String userUsid = xcCacheManager.readCache(xcCacheSavename.usid);
        if((orderNo != null)&&(userUsid != null)) {
            OrderNetWorks orderNetWorks = new OrderNetWorks();
            /*Toast.makeText(view.getContext(),"this is orderstatus:4",Toast.LENGTH_SHORT).show();*/
            System.out.println("\nuserUsid:" + userUsid + " orderNo:" + orderNo);

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
                    /*Toast.makeText(view.getContext(), "this is orderstatus:" + myOrderOrderStatusBeen.get(0).getOrderstatusOrderstatus(), Toast.LENGTH_SHORT).show();*/
                    if (myOrderOrderStatusBeen == null) {
                        return;
                    }
                    initOrderDetail(myOrderOrderStatusBeen.get(0));
                }
            });
        }
    }
    private void initOrderDetail(MyOrderOrderStatusBean bean){
        tvMainMyOrderOrderStatusOrderDetailContentSenderName.setText(bean.getClientaddrName());
        tvMainMyOrderOrderStatusOrderDetailContentSenderTel.setText(bean.getClientaddrTel());
        tvMainMyOrderOrderStatusOrderDetailContentSenderAddr.setText(bean.getClientaddrAddr());
        tvMainMyOrderOrderStatusOrderDetailContentGetName.setText(bean.getClientaddr1Name());
        tvMainMyOrderOrderStatusOrderDetailContentGetTel.setText(bean.getLientaddr1Tel());
        tvMainMyOrderOrderStatusOrderDetailContentGetAddr.setText(bean.getClientaddrAddr1());
        tvMainMyOrderOrderStatusOrderDetailContentOrderNo.setText(bean.getOrderNo());
        tvMainMyOrderOrderStatusOrderDetailContentOrderTime.setText(bean.getOrderOrdertime());
        etMainMyOrderOrderStatusOrderDetailContentRemark.setText(bean.getOrderRemark());
      /*  tvMainMyOrderOrderStatusOrderDetailContentPayMethod.setText(bean.gep);*/
        /*tvMainMyOrderOrderStatusOrderDetailContentSendMan.setText();*/

    }

}
