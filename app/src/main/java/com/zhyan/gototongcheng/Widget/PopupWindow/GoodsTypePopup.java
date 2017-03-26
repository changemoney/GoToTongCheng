package com.zhyan.gototongcheng.Widget.PopupWindow;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Widget.Dialog.ShouDongShuRuDialog;

/**
 * Created by admin on 2017/3/11.
 */

public class GoodsTypePopup extends PopupWindow {

    private Activity activity;
    private View mPopView;
    public OnSelectGoodsItemValueListener onSelectGoodsItemValueListener;
    public String selectItem;
    private ShouDongShuRuDialog shouDongShuRuDialog;
    /*午餐*/
    @BindView(R.id.cb_popupwindow_goodstype_lunch)
    CheckBox cbPopupWindowGoodsTypeLunch;
    @OnClick(R.id.cb_popupwindow_goodstype_lunch)
    public void cbPopupWindowGoodsTypeLunchOnclick(){
        selectItem = getSelectedItemValue("Lunch");
    }
    /*午餐*/
    /*衣服*/
    @BindView(R.id.cb_popupwindow_goodstype_clothes)
    CheckBox cbPopupWindowGoodsTypeClothes;
    @OnClick(R.id.cb_popupwindow_goodstype_clothes)
    public void cbPopupWindowGoodsTypeClothesOnclick(){
        selectItem = getSelectedItemValue("Clothes");
    }
    /*衣服*/
    /*鲜花*/
    @BindView(R.id.cb_popupwindow_goodstype_flower)
    CheckBox cbPopupWindowGoodsTypeFlower;
    @OnClick(R.id.cb_popupwindow_goodstype_flower)
    public void cbPopupWindowGoodsTypeFlowerOnclick(){
        selectItem = getSelectedItemValue("Flower");
    }
    /*鲜花*/
    /*水果*/
    @BindView(R.id.cb_popupwindow_goodstype_fruit)
    CheckBox cbPopupWindowGoodsTypeFruit;
    @OnClick(R.id.cb_popupwindow_goodstype_fruit)
    public void cbPopupWindowGoodsTypeFruitOnclick(){
        selectItem = getSelectedItemValue("Fruit");
    }
    /*水果*/
    /*下午茶*/
    @BindView(R.id.cb_popupwindow_goodstype_afternoon)
    CheckBox cbPopupWindowGoodsTypeAfternoon;
    @OnClick(R.id.cb_popupwindow_goodstype_afternoon)
    public void cbPopupWindowGoodsTypeAfternoonOnclick(){
        selectItem = getSelectedItemValue("Afternoon");
    }
    /*下午茶*/
    /*包裹*/
    @BindView(R.id.cb_popupwindow_goodstype_package)
    CheckBox cbPopupWindowGoodsTypePackage;
    @OnClick(R.id.cb_popupwindow_goodstype_package)
    public void cbPopupWindowGoodsTypePackageOnclick(){
        selectItem = getSelectedItemValue("Package");
    }
    /*包裹*/
    /*蛋糕*/
    @BindView(R.id.cb_popupwindow_goodstype_cake)
    CheckBox cbPopupWindowGoodsTypeCake;
    @OnClick(R.id.cb_popupwindow_goodstype_cake)
    public void cbPopupWindowGoodsTypeCakeOnclick(){
        selectItem = getSelectedItemValue("Cake");
    }
    /*蛋糕*/
    /*生活用品*/
    @BindView(R.id.cb_popupwindow_goodstype_articles)
    CheckBox cbPopupWindowGoodsTypeArticles;
    @OnClick(R.id.cb_popupwindow_goodstype_articles)
    public void cbPopupWindowGoodsTypeArticlesOnclick(){
        selectItem = getSelectedItemValue("Articles");
    }
    /*生活用品*/
    /*文件*/
    @BindView(R.id.cb_popupwindow_goodstype_file)
    CheckBox cbPopupWindowGoodsTypeFile;
    @OnClick(R.id.cb_popupwindow_goodstype_file)
    public void cbPopupWindowGoodsTypeFileOnclick(){
        selectItem = getSelectedItemValue("File");
    }
    /*文件*/
    /*手动输入*/
    @BindView(R.id.cb_popupwindow_goodstype_manualInput)
    public CheckBox cbPopupWindowGoodsTypeManualInput;
    @OnClick(R.id.cb_popupwindow_goodstype_manualInput)
    public void cbPopupWindowGoodsTypeManualInputOnclick(){
            dismiss();
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

                }
            }).build(activity);
        showDialog(mPopView);
    }
    public void showDialog(View view) {
        if (shouDongShuRuDialog != null && !shouDongShuRuDialog.isShowing())
            shouDongShuRuDialog.show();
    }

    public void dissmissDialog() {
        if (shouDongShuRuDialog != null && shouDongShuRuDialog.isShowing())
            shouDongShuRuDialog.dismiss();
    }
    /*手动输入*/

    /*CheckBox 管理器*/
    @BindView(R.id.lly_popupwindow_goodstype_cbmanage)
    LinearLayout rgPopupWindowGoodsTypecbManage;
    /*CheckBox 管理器*/










    /*确定 取消*/
    @BindView(R.id.rly_popupwindow_goodstype_query)
    RelativeLayout rlyPopupWindowGoodsTypeQuery;
    @OnClick(R.id.rly_popupwindow_goodstype_query)
    public void rlyPopupWindowGoodsTypeQueryOnclick(){
        if(onSelectGoodsItemValueListener != null){
            if(selectItem != null) {
                onSelectGoodsItemValueListener.OnClick(selectItem);
            }else{
                onSelectGoodsItemValueListener.OnClick("");
            }
        }
        dismiss();
    }

    @BindView(R.id.rly_popupwindow_goodstype_cancel)
    RelativeLayout rlyPopupWindowGoodsTypeCancel;
    @OnClick(R.id.rly_popupwindow_goodstype_cancel)
    public void rlyPopupWindowGoodsTypeCancelOnclick(){
           dismiss();
     }
    /*确定 取消*/



    public GoodsTypePopup(Activity activity1){
        activity = activity1;
        init();
    }
    private void init(){
        initInflateView();
        ButterKnife.bind(this,mPopView);


    }

    private void initInflateView(){
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopView= inflater.inflate(R.layout.popupwindow_goodstype_lly, null);
        this.setContentView(mPopView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点击外面的控件也可以使得PopUpWindow dimiss
        this.setOutsideTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
    }






    private String getSelectedItemValue(String selectItem){

        switch (selectItem){
            case "Afternoon":
                cbPopupWindowGoodsTypeAfternoon.setChecked(true);
                cbPopupWindowGoodsTypeAfternoon.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsTypeArticles.setChecked(false);
                cbPopupWindowGoodsTypeArticles.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeCake.setChecked(false);
                cbPopupWindowGoodsTypeCake.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeClothes.setChecked(false);
                cbPopupWindowGoodsTypeClothes.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeLunch.setChecked(false);
                cbPopupWindowGoodsTypeLunch.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFile.setChecked(false);
                cbPopupWindowGoodsTypeFile.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFlower.setChecked(false);
                cbPopupWindowGoodsTypeFlower.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFruit.setChecked(false);
                cbPopupWindowGoodsTypeFruit.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypePackage.setChecked(false);
                cbPopupWindowGoodsTypePackage.setTextColor(activity.getResources().getColor(R.color.black));
                selectItem = "下午茶";
                break;
            case "Articles":
                cbPopupWindowGoodsTypeAfternoon.setChecked(false);
                cbPopupWindowGoodsTypeAfternoon.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeArticles.setChecked(true);
                cbPopupWindowGoodsTypeArticles.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsTypeCake.setChecked(false);
                cbPopupWindowGoodsTypeCake.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeClothes.setChecked(false);
                cbPopupWindowGoodsTypeClothes.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeLunch.setChecked(false);
                cbPopupWindowGoodsTypeLunch.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFile.setChecked(false);
                cbPopupWindowGoodsTypeFile.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFlower.setChecked(false);
                cbPopupWindowGoodsTypeFlower.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFruit.setChecked(false);
                cbPopupWindowGoodsTypeFruit.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypePackage.setChecked(false);
                cbPopupWindowGoodsTypePackage.setTextColor(activity.getResources().getColor(R.color.black));
                selectItem = "生活用品";
                break;
            case "Cake":
                cbPopupWindowGoodsTypeAfternoon.setChecked(false);
                cbPopupWindowGoodsTypeAfternoon.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeArticles.setChecked(false);
                cbPopupWindowGoodsTypeArticles.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeCake.setChecked(true);
                cbPopupWindowGoodsTypeCake.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsTypeClothes.setChecked(false);
                cbPopupWindowGoodsTypeClothes.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeLunch.setChecked(false);
                cbPopupWindowGoodsTypeLunch.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFile.setChecked(false);
                cbPopupWindowGoodsTypeFile.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFlower.setChecked(false);
                cbPopupWindowGoodsTypeFlower.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFruit.setChecked(false);
                cbPopupWindowGoodsTypeFruit.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypePackage.setChecked(false);
                cbPopupWindowGoodsTypePackage.setTextColor(activity.getResources().getColor(R.color.black));
                selectItem = "蛋糕";
                break;
            case "Clothes":
                cbPopupWindowGoodsTypeAfternoon.setChecked(false);
                cbPopupWindowGoodsTypeAfternoon.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeArticles.setChecked(false);
                cbPopupWindowGoodsTypeArticles.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeCake.setChecked(false);
                cbPopupWindowGoodsTypeCake.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeClothes.setChecked(true);
                cbPopupWindowGoodsTypeClothes.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsTypeLunch.setChecked(false);
                cbPopupWindowGoodsTypeLunch.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFile.setChecked(false);
                cbPopupWindowGoodsTypeFile.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFlower.setChecked(false);
                cbPopupWindowGoodsTypeFlower.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFruit.setChecked(false);
                cbPopupWindowGoodsTypeFruit.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypePackage.setChecked(false);
                cbPopupWindowGoodsTypePackage.setTextColor(activity.getResources().getColor(R.color.black));
                selectItem = "衣服";
                break;

            case "Lunch":
                cbPopupWindowGoodsTypeAfternoon.setChecked(false);
                cbPopupWindowGoodsTypeAfternoon.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeArticles.setChecked(false);
                cbPopupWindowGoodsTypeArticles.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeCake.setChecked(false);
                cbPopupWindowGoodsTypeCake.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeClothes.setChecked(false);
                cbPopupWindowGoodsTypeClothes.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeLunch.setChecked(true);
                cbPopupWindowGoodsTypeLunch.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsTypeFile.setChecked(false);
                cbPopupWindowGoodsTypeFile.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFlower.setChecked(false);
                cbPopupWindowGoodsTypeFlower.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFruit.setChecked(false);
                cbPopupWindowGoodsTypeFruit.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypePackage.setChecked(false);
                cbPopupWindowGoodsTypePackage.setTextColor(activity.getResources().getColor(R.color.black));
                selectItem = "午餐";
                break;
            case "File":
                cbPopupWindowGoodsTypeAfternoon.setChecked(false);
                cbPopupWindowGoodsTypeAfternoon.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeArticles.setChecked(false);
                cbPopupWindowGoodsTypeArticles.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeCake.setChecked(false);
                cbPopupWindowGoodsTypeCake.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeClothes.setChecked(false);
                cbPopupWindowGoodsTypeClothes.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeLunch.setChecked(false);
                cbPopupWindowGoodsTypeLunch.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFile.setChecked(true);
                cbPopupWindowGoodsTypeFile.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsTypeFlower.setChecked(false);
                cbPopupWindowGoodsTypeFlower.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFruit.setChecked(false);
                cbPopupWindowGoodsTypeFruit.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypePackage.setChecked(false);
                cbPopupWindowGoodsTypePackage.setTextColor(activity.getResources().getColor(R.color.black));
                selectItem = "文件";
                break;
            case "Flower":
                cbPopupWindowGoodsTypeAfternoon.setChecked(false);
                cbPopupWindowGoodsTypeAfternoon.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeArticles.setChecked(false);
                cbPopupWindowGoodsTypeArticles.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeCake.setChecked(false);
                cbPopupWindowGoodsTypeCake.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeClothes.setChecked(false);
                cbPopupWindowGoodsTypeClothes.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeLunch.setChecked(false);
                cbPopupWindowGoodsTypeLunch.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFile.setChecked(false);
                cbPopupWindowGoodsTypeFile.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFlower.setChecked(true);
                cbPopupWindowGoodsTypeFlower.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsTypeFruit.setChecked(false);
                cbPopupWindowGoodsTypeFruit.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypePackage.setChecked(false);
                cbPopupWindowGoodsTypePackage.setTextColor(activity.getResources().getColor(R.color.black));
                selectItem = "鲜花";
                break;
            case "Fruit":
                cbPopupWindowGoodsTypeAfternoon.setChecked(false);
                cbPopupWindowGoodsTypeAfternoon.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeArticles.setChecked(false);
                cbPopupWindowGoodsTypeArticles.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeCake.setChecked(false);
                cbPopupWindowGoodsTypeCake.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeClothes.setChecked(false);
                cbPopupWindowGoodsTypeClothes.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeLunch.setChecked(false);
                cbPopupWindowGoodsTypeLunch.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFile.setChecked(false);
                cbPopupWindowGoodsTypeFile.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFlower.setChecked(false);
                cbPopupWindowGoodsTypeFlower.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFruit.setChecked(true);
                cbPopupWindowGoodsTypeFruit.setTextColor(activity.getResources().getColor(R.color.red));
                cbPopupWindowGoodsTypePackage.setChecked(false);
                cbPopupWindowGoodsTypePackage.setTextColor(activity.getResources().getColor(R.color.black));
                selectItem = "水果";
                break;
            case "Package":
                cbPopupWindowGoodsTypeAfternoon.setChecked(false);
                cbPopupWindowGoodsTypeAfternoon.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeArticles.setChecked(false);
                cbPopupWindowGoodsTypeArticles.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeCake.setChecked(false);
                cbPopupWindowGoodsTypeCake.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeClothes.setChecked(false);
                cbPopupWindowGoodsTypeClothes.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeLunch.setChecked(false);
                cbPopupWindowGoodsTypeLunch.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFile.setChecked(false);
                cbPopupWindowGoodsTypeFile.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFlower.setChecked(false);
                cbPopupWindowGoodsTypeFlower.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypeFruit.setChecked(false);
                cbPopupWindowGoodsTypeFruit.setTextColor(activity.getResources().getColor(R.color.black));
                cbPopupWindowGoodsTypePackage.setChecked(true);
                cbPopupWindowGoodsTypePackage.setTextColor(activity.getResources().getColor(R.color.red));
                selectItem = "包裹";
                break;
        }

        return selectItem;

    }









    public interface OnSelectGoodsItemValueListener{
        public void OnClick(String value);
    }
    public void setOnSelectGoodsItemValueListener(OnSelectGoodsItemValueListener onSelectGoodsItemValueListener1){
        this.onSelectGoodsItemValueListener = onSelectGoodsItemValueListener1;
    }
}
