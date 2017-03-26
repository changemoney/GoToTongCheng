package com.zhyan.gototongcheng.Widget;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhyan.gototongcheng.Widget.PopupWindow.GoodsTypePopup;
import com.zhyan.gototongcheng.Widget.PopupWindow.HourMinTimePopup;
import com.zhyan.gototongcheng.Widget.PopupWindow.PayConfirmPopup;

import java.util.Date;
import java.util.List;

import gototongcheng.zhyan.com.library.Bean.OrderDetailBean;
import gototongcheng.zhyan.com.library.Utils.TimeUtil;
import gototongcheng.zhyan.com.library.Widget.Dialog.ShouDongShuRuDialog;

/**
 * Created by zhyan on 2017/2/20.
 */

public class PopupOnClickEvents {
    private Activity activity;
    private ShouDongShuRuDialog shouDongShuRuDialog;

    public PopupOnClickEvents(Activity activity1){
        activity = activity1;
    }
    public void PayConfirm(LinearLayout layout, OrderDetailBean orderDetailBean){
        PayConfirmPopup payConfirmPopup = new PayConfirmPopup(activity,orderDetailBean);
        setBackgroundAlpha(0.5f);
/*        payConfirmPopup.setFocusable(true);*/
        payConfirmPopup.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
        payConfirmPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }
    public void TimeSelect(LinearLayout layout, final TextView textView){
        HourMinTimePopup hourMinTimePopup = new HourMinTimePopup(activity);
        setBackgroundAlpha(0.5f);
        hourMinTimePopup.setFocusable(true);
        hourMinTimePopup.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
        hourMinTimePopup.setOnDateTimePopupListener(new HourMinTimePopup.OnDateTimePopupListener(){
            @Override
            public void onClick(String day, String ampm, String hour, String min) {
                if(textView != null) {
                    TimeUtil timeUtil = new TimeUtil();
                    if(day.equals("今天")) {

                        day = timeUtil.getLongYMD();
                    }else{
                        day = timeUtil.getYear(new Date())+"-" + day;
                    }
                    int indexWeek = day.indexOf("周");
                    if(indexWeek > 0){
                        day = day.substring(0,indexWeek);
                    }
                    day.replace("-","月");
                    day.replace("-","日");
                    day = day.replace("月","-");
                    day = day.replace("日","-");
                    textView.setText(day + " "+hour+":"+min);
                }
            }
        });
        hourMinTimePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);

            }
        });
    }

  /*  物品种类选择*/
    public void GoodsTypeSelect(LinearLayout layout, final TextView textView){

        final GoodsTypePopup goodsTypePopup = new GoodsTypePopup(activity);
        setBackgroundAlpha(0.5f);
        goodsTypePopup.setFocusable(true);
        goodsTypePopup.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
        goodsTypePopup.setOnSelectGoodsItemValueListener(new GoodsTypePopup.OnSelectGoodsItemValueListener() {

            @Override
            public void OnClick(String value) {
                textView.setText(value);
            }
        });
        goodsTypePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);

            }
        });
        goodsTypePopup.cbPopupWindowGoodsTypeManualInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsTypePopup.dismiss();
                shouDongShuRuDialog = new ShouDongShuRuDialog(activity).Build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dissmissDialog();
                    }
                }).setPositiveButton("确认",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dissmissDialog();
                    }
                }).setCallBackListener(new ShouDongShuRuDialog.DialogCallBackListener() {
                    @Override
                    public void callBack(String msgName) {
                        textView.setText(msgName);
                    }
                }).build(activity);
                showDialog();
            }
        });

    }
 /*   物品种类选择*/
   /* 物品重量选择*/
   /* OnGoodsWeightSelectListener onGoodsWeightSelectListener;
    public interface OnGoodsWeightSelectListener{
        public void select();
    }
    public void setOnGoodsWeightSelectListener(OnGoodsWeightSelectListener onGoodsWeightSelectListener1){
        this.onGoodsWeightSelectListener = onGoodsWeightSelectListener1;
    }
    public void GoodsWeightSelect(LinearLayout layout, final TextView textView){


        final GoodsWeightPopup goodsWeightPopup = new GoodsWeightPopup(activity);
        setBackgroundAlpha(0.5f);
        hourMinTimePopup.setFocusable(true);
        goodsWeightPopup.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
        goodsWeightPopup.setOnSelectGoodsItemValueListener(new GoodsWeightPopup.OnSelectGoodsItemValueListener() {

            @Override
            public void OnClick(String value) {
                if(onGoodsWeightSelectListener != null){
                    onGoodsWeightSelectListener.select();
                }
                textView.setText(value);
            }
        });
        goodsWeightPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);

            }
        });
        goodsWeightPopup.cbPopupWindowGoodsWeightManualInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsWeightPopup.dismiss();
                shouDongShuRuDialog = new ShouDongShuRuDialog(activity).Build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dissmissDialog();
                    }
                }).setPositiveButton("确认",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dissmissDialog();
                    }
                }).setCallBackListener(new ShouDongShuRuDialog.DialogCallBackListener() {
                    @Override
                    public void callBack(String msgName) {
                        textView.setText(msgName);
                    }
                }).build(activity);
                showDialog();
            }
        });

    }*/
    public void showDialog() {
        if (shouDongShuRuDialog != null && !shouDongShuRuDialog.isShowing())
            shouDongShuRuDialog.show();
    }

    public void dissmissDialog() {
        if (shouDongShuRuDialog != null && shouDongShuRuDialog.isShowing())
            shouDongShuRuDialog.dismiss();
    }
    /*物品重量选择*/




    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) activity).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) activity).getWindow().setAttributes(lp);
    }
}
