package com.zhyan.gototongcheng.Widget.PopupWindow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.NetWork.HelpMeSendBuyNetWorks;
import com.zhyan.gototongcheng.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Bean.BaseBean;
import gototongcheng.zhyan.com.library.Bean.HelpMeBuyBean;
import gototongcheng.zhyan.com.library.Bean.MyOrderBean;
import gototongcheng.zhyan.com.library.Bean.OrderDetailBean;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import gototongcheng.zhyan.com.library.ThirdPay.WX.WeChatPayService;
import gototongcheng.zhyan.com.library.ThirdPay.ZFB.ZhiFuBaoUtil;
import rx.Observer;


/**
 * Created by zhyan on 2017/2/20.
 */

public class WaitToPayPayConfirmPopup extends PopupWindow {



    /*跑腿费用*/
    @BindView(R.id.tv_widget_popupwindow_payconfirmpopup_payconfirm_fee)
    TextView tvPopupThirdPayPayConfirmFee;
    /*跑腿费用*/

    /*费用计算公式*/
    @BindView(R.id.tv_widget_popupwindow_payconfirmpopup_payconfirm_feedescri)
    TextView tvPopupThirdPayPayConfirmFeeDescri;
    /*费用计算公式*/
    private String select = "";

    /*本地支付*/
    @BindView(R.id.lly_widget_popupwindow_payconfirmpopup_payconfirm_local)
    LinearLayout llyPopupPayConfirmLocal;
    @BindView(R.id.cb_widget_popupwindow_payconfirmpopup_payconfirm_local)
    CheckBox cbPopupPayConfirmLocal;
    @OnClick(R.id.lly_widget_popupwindow_payconfirmpopup_payconfirm_local)
    public void llyPopupPayConfirmLocalOnclick(){
        select =initPayMethod("local");

    }
    /*本地支付*/
    /*微信支付*/
    @BindView(R.id.lly_widget_popupwindow_payconfirmpopup_payconfirm_wx)
    LinearLayout llyPopupPayConfirmWX;
    @BindView(R.id.cb_widget_popupwindow_payconfirmpopup_payconfirm_wx)
    CheckBox cbPopupPayConfirmWX;
    @OnClick(R.id.lly_widget_popupwindow_payconfirmpopup_payconfirm_wx)
    public void llyPopupPayConfirmWXOnclick(){
        select = initPayMethod("wx");
    }
    /*微信支付*/
    /*支付宝支付*/
    @BindView(R.id.lly_widget_popupwindow_payconfirmpopup_payconfirm_zfb)
    LinearLayout llyPopupPayConfirmZFB;
    @BindView(R.id.cb_widget_popupwindow_payconfirmpopup_payconfirm_zfb)
    CheckBox cbPopupPayConfirmZFB;
    @OnClick(R.id.lly_widget_popupwindow_payconfirmpopup_payconfirm_zfb)
    public void llyPopupPayConfirmZFBOnclick(){
        select = initPayMethod("zfb");
    }

    /*支付宝支付*/
    /*确认支付*/
    @BindView(R.id.rly_widget_popupwindow_payconfirmpopup_payconfirm_querypay)
    RelativeLayout rlyPopupThirdPayPayConfirmQueryPay;
    @OnClick(R.id.rly_widget_popupwindow_payconfirmpopup_payconfirm_querypay)
    public void rlyPopupPayConfirmQueryPayOnclick(){
        /*zhiFuBaoUtil.authV2(mPopView);*/
        switch (select){
            case "local":

                break;
            case "wx":
                /*Toast.makeText(activity,"this is wx",Toast.LENGTH_SHORT).show();*/
                payMethod("wx");
                break;
            case "zfb":
                /*Toast.makeText(activity,"this is zfb",Toast.LENGTH_SHORT).show();*/
                payMethod("zfb");
                break;
            default:
                break;
        }

    }
    /*确认支付*/
    /*弹出窗口关闭*/
    @BindView(R.id.rly_widget_popupwindow_payconfirmpopup_payconfirm_closewin)
    RelativeLayout rlyPopupThirdPayPayConfirmCloseWin;
    @OnClick(R.id.rly_widget_popupwindow_payconfirmpopup_payconfirm_closewin)
    public void rlyPopupThirdPayPayConfirmCloseWinOnclick(){
        dismiss();
    }
    /*弹出窗口关闭*/


    private View mPopView;
    private Activity activity;
    /*微信支付*/
    /**
     * 订单ID
     */
    private String orderId ="288324";

    /**
     * 最后需要支付的金额
     */
    private String fastAmount ="10";
    /**
     * 不同类型的订单
     */
    int type = 1;
    /*微信支付*/
    private ZhiFuBaoUtil zhiFuBaoUtil;
    private String goodsName;
    private Double dPrice =  0.0;
    String tempPrice ="";
    private MyOrderBean myOrderBean;
    public WaitToPayPayConfirmPopup(Activity activity, MyOrderBean myOrderBean1){

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopView= inflater.inflate(R.layout.popupwindow_payconfirm_lly, null);
        this.activity = activity;
        goodsName = "走兔";
        myOrderBean = myOrderBean1;
/*        Toast.makeText(activity,"goodsName",Toast.LENGTH_SHORT).show();*/
            /*price = orderDetail.getOrderOrderprice();*/
        dPrice = myOrderBean1.getOrderOrderprice();
        /*dPrice = 0.01;*/
    /*        Toast.makeText(activity,"dPrice"+dPrice,Toast.LENGTH_SHORT).show();*/

        init();

    }
    private void init(){
        ButterKnife.bind(this,mPopView);
        popWindowInit();
        zhiFuBaoUtil = new ZhiFuBaoUtil(activity);
    }
/*    private void initJiFeiGongShi(){
        if(myOrderBean.getDetailsGoodsname().isEmpty()){
            tvPopupThirdPayPayConfirmFeeDescri.setText("(基础跑腿费7元+额外里程2元/1km)");

        }else{
            tvPopupThirdPayPayConfirmFeeDescri.setText("(基础跑腿费10元+额外里程2元/1km)");
        }
    }*/
    /*付款方式*/

    private String initPayMethod(String method){
        switch (method){
            case "local":
                cbPopupPayConfirmLocal.setChecked(true);
                cbPopupPayConfirmLocal.setBackgroundResource(R.drawable.widget_popupwindow_payconfirmpopup_paywayselect);
                cbPopupPayConfirmWX.setChecked(false);
                cbPopupPayConfirmWX.setBackgroundResource(R.drawable.widget_popupwindow_payconfirmpopup_paywaynormal);
                cbPopupPayConfirmZFB.setChecked(false);
                cbPopupPayConfirmZFB.setBackgroundResource(R.drawable.widget_popupwindow_payconfirmpopup_paywaynormal);
                break;
            case "wx":
                cbPopupPayConfirmLocal.setChecked(false);
                cbPopupPayConfirmLocal.setBackgroundResource(R.drawable.widget_popupwindow_payconfirmpopup_paywaynormal);
                cbPopupPayConfirmWX.setChecked(true);
                cbPopupPayConfirmWX.setBackgroundResource(R.drawable.widget_popupwindow_payconfirmpopup_paywayselect);
                cbPopupPayConfirmZFB.setChecked(false);
                cbPopupPayConfirmZFB.setBackgroundResource(R.drawable.widget_popupwindow_payconfirmpopup_paywaynormal);
                break;
            case "zfb":
                cbPopupPayConfirmLocal.setChecked(false);
                cbPopupPayConfirmLocal.setBackgroundResource(R.drawable.widget_popupwindow_payconfirmpopup_paywaynormal);
                cbPopupPayConfirmWX.setChecked(false);
                cbPopupPayConfirmWX.setBackgroundResource(R.drawable.widget_popupwindow_payconfirmpopup_paywaynormal);
                cbPopupPayConfirmZFB.setChecked(true);
                cbPopupPayConfirmZFB.setBackgroundResource(R.drawable.widget_popupwindow_payconfirmpopup_paywayselect);
                break;

        }
        return method;
    }
    /*付款方式*/

    private void popWindowInit(){
        this.setContentView(mPopView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        tempPrice = dPrice+"";
        int indexof = tempPrice.indexOf(".");
        if(indexof > 0){
            tempPrice = tempPrice.substring(0,indexof);
        }
        tvPopupThirdPayPayConfirmFee.setText(tempPrice + "元");
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点击外面的控件也可以使得PopUpWindow dimiss
        this.setOutsideTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);


    }


    /*微信支付*/
    public void wxPay(final MyOrderBean myOrderBean){
        /*Toast.makeText(activity,"this is wxpay",Toast.LENGTH_SHORT).show();*/
        String body = "测试商品不描述";
        WeChatPayService weChatPay = new WeChatPayService(activity,type, myOrderBean.getOrderNo(), goodsName, tempPrice);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheSavename xcCacheSavename = new XCCacheSavename();
        xcCacheManager.writeCache(xcCacheSavename.WXPayTempOrderNo,myOrderBean.getOrderNo());
        weChatPay.pay();
    }
    /*微信支付*/
    /*支付宝支付*/
    public void zhiFuBaoPay(final MyOrderBean myOrderBean){

/*Toast.makeText(activity, " onCompleted mPopView:"+goodsName+price, Toast.LENGTH_LONG).show();*/
        zhiFuBaoUtil.payV2(mPopView, goodsName, ""+dPrice);
                    /*去支付金钱*/
        zhiFuBaoUtil.setOnPaySuccessfulListener(new ZhiFuBaoUtil.OnPaySuccessfulListener() {
            @Override
            public void isSuccessful(boolean isSuccessful) {
                HelpMeSendBuyNetWorks helpMeSendBuyNetWorks = new HelpMeSendBuyNetWorks();
                    /*Toast.makeText(activity," 我成功啦 isSuccessful:"+isSuccessful,Toast.LENGTH_LONG).show();*/
                if (isSuccessful) {
                    helpMeSendBuyNetWorks.orderPay(1,myOrderBean.getOrderNo(), new Observer<BaseBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BaseBean baseBean) {

                            Toast.makeText(activity, "" + baseBean.getResult(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    /*if (helpMeBuyBean.getPaystatusPaystatus().equals("有待支付")) {
                        helpMeSendBuyNetWorks.orderPay(1,helpMeBuyBean.getOrderNo(), new Observer<BaseBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(BaseBean baseBean) {
                                Toast.makeText(activity, "" + baseBean.getResult(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (helpMeBuyBean.getPaystatusPaystatus().equals("支付失败")) {
                        helpMeSendBuyNetWorks.orderPay(1, helpMeBuyBean.getOrderNo(), new Observer<BaseBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(BaseBean baseBean) {
                                Toast.makeText(activity, "" + baseBean.getResult(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }*/
                }

            }
        });
                    /*去支付金钱*/
                    /*Toast.makeText(activity," 我成功啦111 isSuccessful:"+helpMeBuyBean.getOrderNo(),Toast.LENGTH_LONG).show();*/


    }
    /*支付宝支付*/

    private void payMethod(String method){
        if(myOrderBean == null){
            Toast.makeText(activity,"请登录",Toast.LENGTH_SHORT).show();
            return;
        }
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheSavename xcCacheSavename = new XCCacheSavename();
        String usid = xcCacheManager.readCache(xcCacheSavename.usid);
        if(usid == null || usid.isEmpty()  ){
            Toast.makeText(activity,"请登录",Toast.LENGTH_SHORT).show();
            return;
        }
        /*Toast.makeText(activity,"this is goodsName"+goodsName+" "+dPrice,Toast.LENGTH_SHORT).show();*/
        if((goodsName!= null)&&(dPrice != null)) {

            /*int index = price.indexOf("￥");*/
          /*  String first = price.substring(0,1);
            if(!phoneFormatCheckUtils.IsIntNumber(first)) {
                price = price.substring(1, price.length());
                dPrice = Double.parseDouble(price);
                *//*int index = price.indexOf(".");
                if (index > 0) {
                    price = price.substring(0, index);

                }*//*
            }*/
            pOrderToNet(method);
        }
    }



    /*下单给后台*/
    private void pOrderToNet(final String method){
        switch (method){
            case "wx":
                wxPay(myOrderBean);
                break;
            case "zfb":
                zhiFuBaoPay(myOrderBean);
                break;
        }

    }
    /*下单给后台*/


}