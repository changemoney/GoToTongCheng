package com.zhyan.gototongcheng.Main.HelpMeSend;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.zhyan.gototongcheng.Main.Common.ShouFeiBiaoZhunActivity;
import com.zhyan.gototongcheng.Main.HelpMeBuy.HelpMeBuyActivityController;
import com.zhyan.gototongcheng.R;
import com.zhyan.gototongcheng.Widget.PopupOnClickEvents;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Bean.OrderDetailBean;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import gototongcheng.zhyan.com.library.Utils.PriceUtil;
import gototongcheng.zhyan.com.library.Utils.TimeUtil;
import gototongcheng.zhyan.com.library.Widget.Dialog.AlertView;
import gototongcheng.zhyan.com.library.Widget.ItemClickListener.OnItemClickListener;

/**
 * Created by admin on 2017/3/26.
 */

public class HelpMeSendActivityController extends BaseController{


    /*费用说明*/
    @BindView(R.id.rly_main_helpmesend_bottombar_feecontent)
    RelativeLayout rlyMainHelpMeSendBottomBarFeeContent;
    @OnClick(R.id.rly_main_helpmesend_bottombar_feecontent)
    public void rlyMainHelpMeSendBottomBarFeeContentOnclick(){
        Intent intent = new Intent(activity,ShouFeiBiaoZhunActivity.class);
        activity.startActivity(intent);
    }
    /*费用说明*/

    @BindView(R.id.lly_main_helpmesend_total)
    LinearLayout llyMainHelpMeSendTotal;
    @BindView(R.id.rly_main_helpmesend_topbar_leftmenu)
    RelativeLayout rlyMainHelpMeSendTopBarLeftMenu;
    @OnClick(R.id.rly_main_helpmesend_topbar_leftmenu)
    public void rlyMainHelpMeSendTopBarLeftMenuOnclick(){
        activity.finish();
    }


    @BindView(R.id.tv_main_helpmesend_bottombar_price)
    TextView tvMainHelpMeSendBottomBarPrice;

        /*重量 距离 种类 时间*/
    @BindView(R.id.tv_main_helpmesend_content_goodsweight)
    TextView tvMainHelpMeSendContentGoodsWeight;
    @OnClick(R.id.lly_main_helpmesend_content_goodsweight)
    public void llyMainHelpMeSendContentGoodsWeightOnclick(){
        new AlertView.Builder().setContext(activity)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle("物品重量")
                .setMessage(null)
                .setCancelText("取消")
                .setDestructive("超出10公斤(超出部分需另收费)")
                .setDestructive1("10公斤以内")
                .setOthers(null)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        /*Toast.makeText(getBaseContext(),"pos"+position,Toast.LENGTH_SHORT).show();*/
                        switch (position){
                            case 0:
                                tvMainHelpMeSendContentGoodsWeight.setText("超出10公斤");

                                break;
                            case 1:
                                tvMainHelpMeSendContentGoodsWeight.setText("10公斤以内");
                                break;
                            case -1:
                                break;
                        }
                    }
                })
                .build()
                .show();
/*        startBikeNaviSearch();*/
    }

    @BindView(R.id.tv_main_helpmesend_content_senddis)
    TextView tvMainHelpMeSendContentSendDis;
    @BindView(R.id.tv_main_helpmesend_content_goodstype)
    TextView tvMainHelpMeSendContentGoodsType;
    @BindView(R.id.lly_main_helpmesend_content_goodstype)
    LinearLayout llyMainHelpMeSendContentGoodsType;
    @OnClick(R.id.lly_main_helpmesend_content_goodstype)
    public void llyHelpMeSendContentGoodsTypeOnclick(){
        PopupOnClickEvents popupOnClickEvents = new PopupOnClickEvents(activity);
        popupOnClickEvents.GoodsTypeSelect(llyMainHelpMeSendTotal,tvMainHelpMeSendContentGoodsType);
    }
    @BindView(R.id.tv_main_helpmesend_content_rectime)
    TextView tvMainHelpMeSendContentRecTime;
    @OnClick(R.id.lly_main_helpmesend_content_rectime)
    public void llyMainHelpMeSendContentRecTimeOnclick(){
        PopupOnClickEvents popupOnClickEvents = new PopupOnClickEvents(activity);
        popupOnClickEvents.TimeSelect(llyMainHelpMeSendTotal,tvMainHelpMeSendContentRecTime);
    }
    /*重量 距离 种类 时间*/

    private String usid="";
    /*距离*/
    public float dis = 0;
    /*距离*/
    /*百度骑行引擎*/
    private RoutePlanSearch mSearch;
    /*百度骑行引擎*/
    public String price = "";

    public HelpMeSendActivityController(Activity activity1){
        activity = activity1;
        init();
    }


    @Override
    public void init() {
        ButterKnife.bind(this,activity);

        initWeightAndReceiveTime();
        initRoutePlanDisNavi();
    }

    private void initWeightAndReceiveTime(){
        tvMainHelpMeSendContentGoodsWeight.setText("10公斤以内");
        tvMainHelpMeSendContentRecTime.setText("立即收件");
    }


    /*百度骑行导航初始化http://lbsyun.baidu.com/index.php?title=androidsdk/guide/bikenavi   http://wiki.lbsyun.baidu.com/cms/androidsdk/doc/v4_2_1/index.html
 * http://lbsyun.baidu.com/index.php?title=androidsdk/guide/tool里面的步行导航中有骑行导航  再加上doc文档即可解决
 * */
    private void initRoutePlanDisNavi(){
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
                        float min = bikingRouteLineList.get(0).getDistance();
                        for (int i = 0; i < count; i++) {
                            if (min > bikingRouteLineList.get(i).getDistance()) {
                                min = bikingRouteLineList.get(i).getDistance();
                            }
                            continue;
                        }
                     /*   Toast.makeText(activity,"两地骑行距离onGetBikingRouteResult:"+min,Toast.LENGTH_LONG).show();*/
                        dis = min/1000;
                        tvMainHelpMeSendContentSendDis.setText(""+dis+"km");
                        /*Toast.makeText(getBaseContext(),"两地骑行距离onGetBikingRouteResult:"+min,Toast.LENGTH_LONG).show();*/
                        getPrice();
                        /*Toast.makeText(getBaseContext(),"here is onGetBikingRouteResult:"+dis,Toast.LENGTH_SHORT).show();*/
                    }
                }
            }
        });


    }
    /*获取价格*/
    private void getPrice(){
        PriceUtil priceUtil = new PriceUtil(activity);


        price = priceUtil.gotoHelpMeSendlFee(dis);

        if(!price.isEmpty()) {
            tvMainHelpMeSendBottomBarPrice.setVisibility(View.VISIBLE);
        /*Toast.makeText(getBaseContext(),"here is getPrice tvHelpMeSendBottomBarPrice:"+price,Toast.LENGTH_SHORT).show();*/
            tvMainHelpMeSendBottomBarPrice.setText("￥" + price);
        }
    }

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
/*        locationClient.unRegisterLocationListener(locationListener);*/
        mSearch.destroy();
    }
}
