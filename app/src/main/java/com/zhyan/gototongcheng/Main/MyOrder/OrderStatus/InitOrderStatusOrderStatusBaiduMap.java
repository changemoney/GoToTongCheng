package com.zhyan.gototongcheng.Main.MyOrder.OrderStatus;

import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
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
import com.zhyan.gototongcheng.BaiDuMap.OverlayUtil.BikingRouteOverlay;
import com.zhyan.gototongcheng.BaiDuMap.OverlayUtil.OverlayManager;
import com.zhyan.gototongcheng.NetWork.OrderNetWorks;
import com.zhyan.gototongcheng.R;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import gototongcheng.zhyan.com.library.Bean.AngelAnidLocBean;
import gototongcheng.zhyan.com.library.Bean.MyOrderOrderStatusBean;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import rx.Observer;

/**
 * 初始化订单详情的地图状态
 * Created by admin on 2017/4/14.
 */

public class InitOrderStatusOrderStatusBaiduMap implements OnGetRoutePlanResultListener,OnGetGeoCoderResultListener {


    @BindView(R.id.mv_main_myorder_orderstatus_content_loc)
    MapView mvMainMyOrderOrderStatusContentLoc;
    @BindView(R.id.ib_main_myorder_orderstatus_contetn_gps_loc)
    ImageButton ibMainMyOrderOrderStatusContentGpsLoc;
    private View view;
    private BaiduMap mBaidumap;
    // 搜索相关
    RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
    private GeoCoder search=null;
    private String beginAddr,endAddr;

    private boolean isBeginAddrSearch = false;
    private LatLng beginLal = null,endLal = null;
    private TextView popupText = null; // 泡泡view
    OverlayManager routeOverlay = null;
    boolean useDefaultIcon = false;
    private String  angelAnid = null;
    private LatLng angelLal = null;
    private String angelLocTime = null;
    private MyOrderOrderStatusBean bean;
    private boolean isFirstLocSelf = true;
    private  Marker marker;
    public InitOrderStatusOrderStatusBaiduMap(View view1,MyOrderOrderStatusBean bean1){
        view = view1;
        bean = bean1;
        init();
    }
    private void init(){
        ButterKnife.bind(this,view);
        initBaiduMap();
    }

    private void initBaiduMap(){
        mBaidumap = mvMainMyOrderOrderStatusContentLoc.getMap();
        mvMainMyOrderOrderStatusContentLoc.showZoomControls(false);
        mBaidumap.setMyLocationEnabled(true);// 开启定位图层
        initAngelLoc();
        initRouteSearch();
        initGeoSearch();
        initBeginAddrEndAddr();
    }
    private void initAngelLoc(){

      /*  final OrderNetWorks orderNetWorks = new OrderNetWorks();
        Toast.makeText(view.getContext(),"this is angelloc:11",Toast.LENGTH_SHORT).show();
        Thread thread = new Thread(){
            int i = 0;

            @Override
            public void run(){
                SystemClock.sleep(1000);
                *//*Toast.makeText(view.getContext(),"this is angelloc:22",Toast.LENGTH_SHORT).show();*//*
                orderNetWorks.getAngelAnidLocFromNet(bean.getAngelAnid(), new Observer<AngelAnidLocBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AngelAnidLocBean angelAnidLocBean) {
                      *//*  Toast.makeText(view.getContext(),"this is angelloc:"+angelAnidLocBean.getStaffllUpdatetime(),Toast.LENGTH_SHORT).show();*//*
                      System.out.print("\n33333333333333333333333333333333333333333333333");

                    }
                });

            }
        };
        thread.start();*/
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(runnable, 1, 3, TimeUnit.SECONDS);
    }
    Runnable runnable = new Runnable() {

        public void run() {
            // task to run goes here
            System.out.println("\nHello !!");

            OrderNetWorks orderNetWorks = new OrderNetWorks();
            orderNetWorks.getAngelAnidLocFromNet(bean.getAngelAnid(), new Observer<List<AngelAnidLocBean>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(List<AngelAnidLocBean> angelAnidLocBeen) {
                    System.out.print("\n3222222:"+angelAnidLocBeen.get(0).getStaffllUpdatetime());
                    showSelfLoc(angelAnidLocBeen.get(0));

                }
            });
        }
    };

    private void showSelfLoc(final AngelAnidLocBean bean){
       /* mBaidumap.clear();*/
        if(isFirstLocSelf) {
            initBeginAddrEndAddr();
            TextView textView = new TextView(view.getContext());
            Drawable drawable1 = view.getContext().getResources().getDrawable(R.drawable.courier_logo);
            drawable1.setBounds(0, 0, 45, 45);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
            textView.setCompoundDrawables(drawable1, null, null, null);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(textView);
            final LatLng latLng = new LatLng(bean.getStaffllDlat(), bean.getStaffllDlong());

                /*BitmapDescriptor bitmap = null;*/
            //准备 marker option 添加 marker 使用
            MarkerOptions markerOptions = new MarkerOptions().icon(bitmapDescriptor).position(latLng);
            //获取添加的 marker 这样便于后续的操作

            marker = (Marker)mBaidumap.addOverlay(markerOptions);

            mBaidumap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    popupText = new TextView(view.getContext());
                    popupText.setBackgroundResource(R.drawable.baidumap_overly_gray_bg_radius);
                    popupText.setPadding(10, 5, 10, 5);
                    popupText.setTextColor(0xFF000000);
                    popupText.setTextSize(10);
                    popupText.setText(bean.getStaffllUpdatetime());
                    popupText.setGravity(Gravity.CENTER);
                    mBaidumap.showInfoWindow(new InfoWindow(popupText, latLng, 0));
                    popupText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mBaidumap.hideInfoWindow();
                        }
                    });
                    return false;
                }
            });
            isFirstLocSelf = false;
        }else{
            marker.setPosition(new LatLng(bean.getStaffllDlat(),bean.getStaffllDlong()));
        }
    }
    private void initBeginAddrEndAddr(){

        beginAddr = bean.getClientaddrAddr();
        endAddr = bean.getClientaddrAddr1();
        addrToLLSearch(beginAddr);
        isBeginAddrSearch = true;
    }
    private void addrToLLSearch(String addr){
        addr = addr.trim();
        int cityIndex = addr.indexOf("市");
        if(cityIndex > 0){
            String city = addr.substring(0,cityIndex+1);
            addr = addr.substring(cityIndex+1,addr.length());
            search.geocode(new GeoCodeOption().city(city).address(addr));
        }else{
            search.geocode(new GeoCodeOption().city("浙江省温州市").address(addr));
        }
    }
    private void initRouteSearch(){
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
    }
    private void initGeoSearch(){
        search= GeoCoder.newInstance();
        /**根据经纬度得到屏幕中心点地址**/
        search.setOnGetGeoCodeResultListener(this);
    }
    private void searchProcessRouteByLLG(LatLng begLlg ,LatLng endLlg){
        PlanNode stNode,enNode;
        stNode = PlanNode.withLocation(begLlg);
        enNode = PlanNode.withLocation(endLlg);
        mSearch.bikingSearch((new BikingRoutePlanOption())
                .from(stNode).to(enNode));
    }
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if(geoCodeResult == null){
            return;
        }
        if(isBeginAddrSearch){
            beginLal = geoCodeResult.getLocation();
            isBeginAddrSearch = false;
            addrToLLSearch(endAddr);
        }else{
            endLal = geoCodeResult.getLocation();
            isBeginAddrSearch = true;
            if((beginLal != null)&&(endLal != null)){
                searchProcessRouteByLLG(beginLal,endLal);
            }
        }
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }

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
    public void onGetBikingRouteResult(BikingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(view.getContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            /*Toast.makeText(activity, "2", Toast.LENGTH_SHORT).show();*/
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
           /* Toast.makeText(activity, "3", Toast.LENGTH_SHORT).show();*/
            return;
        }
        /*Toast.makeText(activity, "4", Toast.LENGTH_SHORT).show();*/
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            /*Toast.makeText(activity, "5", Toast.LENGTH_SHORT).show();*/
            if (result.getRouteLines().size() == 1){
                BikingRouteOverlay overlay = new MyBikingRouteOverlay(mBaidumap);
                routeOverlay = overlay;
                mBaidumap.setOnMarkerClickListener(overlay);
/*            Toast.makeText(activity,"result.getRouteLines():"+result.getRouteLines().get(0).getAllStep().size(),Toast.LENGTH_LONG).show();
            Toast.makeText(activity,"result.getRouteLines():"+result.getRouteLines().get(0).getAllStep().get(0).getInstructions(),Toast.LENGTH_LONG).show();
 */             overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();

                /*Toast.makeText(activity, "6", Toast.LENGTH_SHORT).show();*/
            }else {
                /*Toast.makeText(activity, "7", Toast.LENGTH_SHORT).show();*/
                Log.d("route result", "结果数<0" );
                return;
            }
            /*Toast.makeText(activity, "8", Toast.LENGTH_SHORT).show();*/
        }
    }






    private class MyBikingRouteOverlay extends BikingRouteOverlay {

        public  MyBikingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }
        @Override
        public boolean onRouteNodeClick(int i) {

            BikingRouteLine mRouteLine = getmRouteLine();
            if(mRouteLine == null){
                return false;
            }
 /*           Toast.makeText(activity,"this is node:"+mRouteLine.getAllStep().get(i).getWayPoints().size(),Toast.LENGTH_LONG).show();
            Toast.makeText(activity,"this is node:"+i,Toast.LENGTH_LONG).show();
*/
            if (getmRouteLine().getAllStep() != null
                    && mRouteLine.getAllStep().get(i) != null) {
                popupText = new TextView(view.getContext());
                popupText.setBackgroundResource(R.drawable.baidumap_overly_gray_bg_radius);
                popupText.setPadding(10,5,10,5);
                popupText.setTextColor(0xFF000000);
                popupText.setTextSize(10);
                popupText.setText(mRouteLine.getAllStep().get(i).getInstructions());
                popupText.setGravity(Gravity.CENTER);
                mBaidumap.showInfoWindow(new InfoWindow(popupText, mRouteLine.getAllStep().get(i).getEntrance().getLocation(), 0));
                popupText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBaidumap.hideInfoWindow();
                    }
                });


            }
            return false;
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }


    }


    public void onDestroy(){
        if(mvMainMyOrderOrderStatusContentLoc == null){
            return;
        }
        mvMainMyOrderOrderStatusContentLoc.onDestroy();
        if(mBaidumap == null){
            return;
        }
        mBaidumap.clear();
        mBaidumap = null;
        if(search == null){
            return;
        }
        search.destroy();
        if(mSearch == null){
            return;
        }
        mSearch.destroy();
    }
}
