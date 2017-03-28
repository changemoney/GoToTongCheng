package com.zhyan.gototongcheng.Main.HelpMeBuy;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.bikenavi.params.BikeNaviLauchParam;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.zhyan.gototongcheng.Main.BaseController;
import com.zhyan.gototongcheng.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Utils.PriceUtil;
import gototongcheng.zhyan.com.library.Widget.Dialog.ShouDongShuRuDialog;

/**
 * Created by admin on 2017/3/25.
 */

public class HelpMeBuyActivityController extends BaseController {
    ShouDongShuRuDialog shouDongShuRuDialog;
    public List<String> checkList = new ArrayList<String>();


    /*不辣*/
    @BindView(R.id.cb_main_helpmebuy_content_nochilli)
    CheckBox cbMainHelpMeBuyContentNoChilli;
    @OnClick(R.id.cb_main_helpmebuy_content_nochilli)
    public void cbMainHelpMeBuyContentNoChilliOnclick(){
        if(cbMainHelpMeBuyContentNoChilli.isChecked()){
            cbMainHelpMeBuyContentNoChilli.setTextColor(activity.getResources().getColor(R.color.red));
            checkList.add("不辣");

            cbMainHelpMeBuyContentSpecialChilli.setChecked(false);
            cbMainHelpMeBuyContentSpecialChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            cbMainHelpMeBuyContentMediaChilli.setChecked(false);
            cbMainHelpMeBuyContentMediaChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            cbMainHelpMeBuyContentSmallChilli.setChecked(false);
            cbMainHelpMeBuyContentSmallChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
        }else{
            cbMainHelpMeBuyContentNoChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            checkList.remove("nochilli");
        }
    }
    /*不辣*/
    /*微辣*/
    @BindView(R.id.cb_main_helpmebuy_content_smallchilli)
    CheckBox cbMainHelpMeBuyContentSmallChilli;
    @OnClick(R.id.cb_main_helpmebuy_content_smallchilli)
    public void cbMainHelpMeBuyContentSmallChilliOnclick(){
        if(cbMainHelpMeBuyContentSmallChilli.isChecked()){
            cbMainHelpMeBuyContentSmallChilli.setTextColor(activity.getResources().getColor(R.color.red));
            checkList.add("微辣");
            cbMainHelpMeBuyContentSpecialChilli.setChecked(false);
            cbMainHelpMeBuyContentSpecialChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            cbMainHelpMeBuyContentMediaChilli.setChecked(false);
            cbMainHelpMeBuyContentMediaChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            cbMainHelpMeBuyContentNoChilli.setChecked(false);
            cbMainHelpMeBuyContentNoChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
        }else{
            cbMainHelpMeBuyContentSmallChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            checkList.remove("微辣");
        }
    }
    /*微辣*/
    /*中辣*/
    @BindView(R.id.cb_main_helpmebuy_content_mediachilli)
    CheckBox cbMainHelpMeBuyContentMediaChilli;
    @OnClick(R.id.cb_main_helpmebuy_content_mediachilli)
    public void cbMainHelpMeBuyContentMediaChilliOnclick(){
        if(cbMainHelpMeBuyContentMediaChilli.isChecked()){
            cbMainHelpMeBuyContentMediaChilli.setTextColor(activity.getResources().getColor(R.color.red));
            checkList.add("中辣");

            cbMainHelpMeBuyContentSpecialChilli.setChecked(false);
            cbMainHelpMeBuyContentSpecialChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            cbMainHelpMeBuyContentSmallChilli.setChecked(false);
            cbMainHelpMeBuyContentSmallChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            cbMainHelpMeBuyContentNoChilli.setChecked(false);
            cbMainHelpMeBuyContentNoChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
        }else{
            cbMainHelpMeBuyContentMediaChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            checkList.remove("中辣");
        }
    }
    /*中辣*/
    /*特辣*/
    @BindView(R.id.cb_main_helpmebuy_content_specialchilli)
    CheckBox cbMainHelpMeBuyContentSpecialChilli;
    @OnClick(R.id.cb_main_helpmebuy_content_specialchilli)
    public void cbMainHelpMeBuyContentSpecialChilliOnclick(){
        if(cbMainHelpMeBuyContentSpecialChilli.isChecked()){
            cbMainHelpMeBuyContentSpecialChilli.setTextColor(activity.getResources().getColor(R.color.red));
            checkList.add("特辣");
            cbMainHelpMeBuyContentMediaChilli.setChecked(false);
            cbMainHelpMeBuyContentMediaChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            cbMainHelpMeBuyContentSmallChilli.setChecked(false);
            cbMainHelpMeBuyContentSmallChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            cbMainHelpMeBuyContentNoChilli.setChecked(false);
            cbMainHelpMeBuyContentNoChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
        }else{
            cbMainHelpMeBuyContentSpecialChilli.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));

            checkList.remove("特辣");
        }
    };
    /*特辣*/
    /*放葱*/
    @BindView(R.id.cb_main_helpmebuy_content_haveonion)
    CheckBox cbMainHelpMeBuyContentHaveOnion;
    @OnClick(R.id.cb_main_helpmebuy_content_haveonion)
    public void cbHelpMeBuyContentHaveOnionOnclick(){
        if(cbMainHelpMeBuyContentHaveOnion.isChecked()){
            cbMainHelpMeBuyContentHaveOnion.setTextColor(activity.getResources().getColor(R.color.red));
            checkList.add("放葱");

            cbMainHelpMeBuyContentNoOnion.setChecked(false);
            cbMainHelpMeBuyContentNoOnion.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
        }else{
            cbMainHelpMeBuyContentHaveOnion.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            checkList.remove("放葱");
        }
    }
    /*放葱*/
    /*不放葱*/
    @BindView(R.id.cb_main_helpmebuy_content_noonion)
    CheckBox cbMainHelpMeBuyContentNoOnion;
    @OnClick(R.id.cb_main_helpmebuy_content_noonion)
    public void cbMainHelpMeBuyContentNoOnionOnclick(){
        if(cbMainHelpMeBuyContentNoOnion.isChecked()){
            cbMainHelpMeBuyContentNoOnion.setTextColor(activity.getResources().getColor(R.color.red));
            checkList.add("不放葱");
            cbMainHelpMeBuyContentHaveOnion.setChecked(false);
            cbMainHelpMeBuyContentHaveOnion.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
        }else{
            cbMainHelpMeBuyContentNoOnion.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            checkList.remove("不放葱");
        }
    }
    /*不放葱*/
    /*放香菜*/
    @BindView(R.id.cb_main_helpmebuy_content_havecaraway)
    CheckBox cbMainHelpMeBuyContentHaveCaraway;
    @OnClick(R.id.cb_main_helpmebuy_content_havecaraway)
    public void cbMainHelpMeBuyContentHaveCarawayOnclick(){
        if(cbMainHelpMeBuyContentHaveCaraway.isChecked()){
            cbMainHelpMeBuyContentHaveCaraway.setTextColor(activity.getResources().getColor(R.color.red));
            checkList.add("放香菜");
            cbMainHelpMeBuyContentNoCaraway.setChecked(false);
            cbMainHelpMeBuyContentNoCaraway.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
        }else{
            cbMainHelpMeBuyContentHaveCaraway.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            checkList.remove("放香菜");
        }
    }
    /*放香菜*/

    /*不放香菜*/
    @BindView(R.id.cb_main_helpmebuy_content_nocaraway)
    CheckBox cbMainHelpMeBuyContentNoCaraway;
    @OnClick(R.id.cb_main_helpmebuy_content_nocaraway)
    public void cbMainHelpMeBuyContentNoCarawayOnclick(){
        if(cbMainHelpMeBuyContentNoCaraway.isChecked()){
            cbMainHelpMeBuyContentNoCaraway.setTextColor(activity.getResources().getColor(R.color.red));
            checkList.add("不放香菜");

            cbMainHelpMeBuyContentHaveCaraway.setChecked(false);
            cbMainHelpMeBuyContentHaveCaraway.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
        }else{
            cbMainHelpMeBuyContentNoCaraway.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            checkList.remove("不放香菜");
        }
    }
    /*不放香菜*/
    /*放醋*/
    @BindView(R.id.cb_main_helpmebuy_content_havevinegar)
    CheckBox cbMainHelpMeBuyContentHaveVinegar;
    @OnClick(R.id.cb_main_helpmebuy_content_havevinegar)
    public void cbMainHelpMeBuyContentHaveVinegarOnclick(){
        if(cbMainHelpMeBuyContentHaveVinegar.isChecked()){
            cbMainHelpMeBuyContentHaveVinegar.setTextColor(activity.getResources().getColor(R.color.red));
            checkList.add("放醋");
            cbMainHelpMeBuyContentNoVinegar.setChecked(false);
            cbMainHelpMeBuyContentNoVinegar.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
        }else{
            cbMainHelpMeBuyContentHaveVinegar.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            checkList.remove("放醋");
        }
    }
    /*放醋*/

    /*不放醋*/
    @BindView(R.id.cb_main_helpmebuy_content_novinegar)
    CheckBox cbMainHelpMeBuyContentNoVinegar;
    @OnClick(R.id.cb_main_helpmebuy_content_novinegar)
    public void cbMainHelpMeBuyContentNoVinegarOnclick(){
        if(cbMainHelpMeBuyContentNoVinegar.isChecked()){
            cbMainHelpMeBuyContentNoVinegar.setTextColor(activity.getResources().getColor(R.color.red));
            checkList.add("不放醋");
            cbMainHelpMeBuyContentHaveVinegar.setChecked(false);
            cbMainHelpMeBuyContentHaveVinegar.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
        }else{
            cbMainHelpMeBuyContentNoVinegar.setTextColor(activity.getResources().getColor(R.color.color_activity_main_helpmebuy_bottom_pay_black_word_bg));
            checkList.remove("不放醋");
        }
    }
    /*不放醋*/
    /*手动输入*/
    int i = 0;
    public List<TextView> addRemarkList = new ArrayList<TextView>();
    @BindView(R.id.lly_main_helpmebuy_content_remark)
    LinearLayout llyMainHelpMeBuyContentRemark;
    @BindView(R.id.lly_main_helpmebuy_content_remark_child)
    LinearLayout llyMainHelpMeBuyContentRemarkChild;
    @BindView(R.id.tv_main_helpmebuy_content_manualinput)
    TextView tvMainHelpMeBuyContentManualinput;
    @OnClick(R.id.tv_main_helpmebuy_content_manualinput)
    public void tvMainHelpMeBuyContentManualinputOnclick(){
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
                TextView textView = new TextView(activity);
                textView.setText(msgName);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(3,0,3,0);
                textView.setLayoutParams(layoutParams);
                textView.setPadding(2, 6, 2, 6);
                textView.setTextColor(activity.getResources().getColor(R.color.red));
                textView.setTextSize(12);

                textView.setBackgroundResource(R.drawable.activity_main_helpmebuy_gray_bg_half_round_radius);
                addRemarkList.add(textView);
                llyMainHelpMeBuyContentRemarkChild.addView(textView);
            }
        }).build(activity);
        showDialog();
    }
    public void showDialog() {
        if (shouDongShuRuDialog != null && !shouDongShuRuDialog.isShowing())
            shouDongShuRuDialog.show();
    }

    public void dissmissDialog() {
        if (shouDongShuRuDialog != null && shouDongShuRuDialog.isShowing())
            shouDongShuRuDialog.dismiss();
    }


    @BindView(R.id.tv_main_helpmebuy_bottombar_fee)
    TextView tvMainHelpMeBuyBottomBarFee;

    /*百度骑行引擎*/
    private RoutePlanSearch mSearch;
    BikeNaviLauchParam param;
    private final String LTAG = "BaiduQiXing导航引擎";
    private String usid = "";
    private String userName = "";
    public double dis = 0;

    /*百度骑行引擎*/
    public String price="";
    HelpMeBuyShoppingMenuRecyclerViewAdapter helpMeBuyShoppingMenuRecyclerViewAdapter;
    public HelpMeBuyActivityController(Activity activity1, HelpMeBuyShoppingMenuRecyclerViewAdapter helpMeBuyShoppingMenuRecyclerViewAdapter1){
        activity = activity1;
        helpMeBuyShoppingMenuRecyclerViewAdapter = helpMeBuyShoppingMenuRecyclerViewAdapter1;
        init();
    }
    @Override
    public void init() {
        ButterKnife.bind(this,activity);
        initRoutePlanDisNavi();
    }



    /*百度骑行导航初始化http://lbsyun.baidu.com/index.php?title=androidsdk/guide/bikenavi   http://wiki.lbsyun.baidu.com/cms/androidsdk/doc/v4_2_1/index.html
  * http://lbsyun.baidu.com/index.php?title=androidsdk/guide/tool里面的步行导航中有骑行导航  再加上doc文档即可解决
  * */
    public void initRoutePlanDisNavi(){
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener(){

            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
                if(bikingRouteResult != null){
                    List<BikingRouteLine> bikingRouteLineList = bikingRouteResult.getRouteLines();
                    if(bikingRouteLineList != null) {
                        int count = bikingRouteLineList.size();
                        int min = bikingRouteLineList.get(0).getDistance();
                        for (int i = 0; i < count; i++) {
                            if (min > bikingRouteLineList.get(i).getDistance()) {
                                min = bikingRouteLineList.get(i).getDistance();
                            }
                            continue;
                        }
                        dis = min;
                    /*Toast.makeText(getBaseContext(),"两地骑行距离onGetBikingRouteResult:"+min,Toast.LENGTH_LONG).show();*/
                        getPrice();
                    }
                }
            }
        });

    }
    /*获取价格*/
    public void getPrice(){
        PriceUtil priceUtil = new PriceUtil(activity);
        double dist = dis/1000;
        price = priceUtil.gotoHelpMeBuylFee((float) dist,helpMeBuyShoppingMenuRecyclerViewAdapter.getItemCount());
        if(!price.isEmpty()) {
            tvMainHelpMeBuyBottomBarFee.setVisibility(View.VISIBLE);
            tvMainHelpMeBuyBottomBarFee.setText("￥" + price);
        }
    }
    /*获取价格*/

    /*开始骑行路线规划*/
    public void startBikeNaviSearch(double blat,double blon,double rlat,double rlon){
        /*Toast.makeText(getBaseContext(),"两地骑行距离startBikeNaviSearchBegin:"+blat+" "+blon+" "+rlat+" "+rlon,Toast.LENGTH_SHORT).show();*/
        if((blat != 0) &&(blon != 0)&&(rlat !=0)&&(rlon != 0)) {
            /*Toast.makeText(getBaseContext(),"两地骑行距离startBikeNaviSearchMiddle",Toast.LENGTH_SHORT).show();*/
            LatLng blal = new LatLng(blat, blon);
            LatLng elal = new LatLng(rlat, rlon);
            PlanNode stNode = PlanNode.withLocation(blal);
            PlanNode enNode = PlanNode.withLocation(elal);
            mSearch.bikingSearch((new BikingRoutePlanOption())
                    .from(stNode)
                    .to(enNode));
        }
    }
    /*开始骑行路线规划*/

    /*百度骑行导航初始化http://lbsyun.baidu.com/index.php?title=androidsdk/guide/bikenavi   http://wiki.lbsyun.baidu.com/cms/androidsdk/doc/v4_2_1/index.html*/
    public void onDestroy(){
        BaiduMapNavigation.finish(activity);
        mSearch.destroy();
    }
}
