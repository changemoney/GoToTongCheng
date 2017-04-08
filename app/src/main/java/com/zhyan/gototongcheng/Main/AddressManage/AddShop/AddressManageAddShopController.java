package com.zhyan.gototongcheng.Main.AddressManage.AddShop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.zhyan.gototongcheng.Main.AddressManage.AddUser.AddressManageAddUserActivity;
import com.zhyan.gototongcheng.Main.AddressManage.AddShop.AddressManageAddShopActivity.InitRecycleView;
import com.zhyan.gototongcheng.Main.BaseController;
import com.zhyan.gototongcheng.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;

import static com.baidu.location.LocationClientOption.LocationMode.Hight_Accuracy;

/**
 * Created by admin on 2017/3/24.
 */

public class AddressManageAddShopController extends BaseController  implements OnGetGeoCoderResultListener,OnGetPoiSearchResultListener {


    /*商家地址经纬度*/
    public double blat,blon;
    /*商家地址经纬度*/

    private InitRecycleView initRecycleView;
    public BaiduMap mBaiduMap;
    @BindView(R.id.mv_main_addressmanage_add_shop_content)
    MapView mvMainAddressManageAddShopContent;
    @BindView(R.id.iv_main_addressmanage_add_shop_content_centerloc)
    ImageView ivMainAddressManageAddShopContentCenterLoc;

    private LocationClient locationClient=null;
    private BDLocationListener locationListener= new MyLocationListener();
    private BaiduMap.OnMapTouchListener mapTouchListener;
    /*地理编码检索*/
      /*关键字poi检索*/
    private PoiSearch poiSearch;
    /*关键字poi检索*/
    private GeoCoder mSearch;//地理编码 根据经纬度查找地址
    /*地理编码检索*/
    private Boolean isFirst = true;
    private  final int accuracyCircleFillColor = 0xAAFFFF88;
    private  final int accuracyCircleStrokeColor = 0xAA00FF00;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private LatLng currentPt;
 /*   private EditText etMainAddressManageAddShopContentAddress;*/
    private String addressLocation = "";
    public boolean isAddress = true;
    @BindView(R.id.et_main_addressmanage_add_shop_content_namecall)
    EditText etMainAddressManageAddShopContentNameCall;
    @BindView(R.id.et_main_addressmanage_add_shop_content_address)
    EditText etMainAddressManageAddShopContentAddress;


    double selfLat = 0,selfLon= 0;

    /*定位自己*/
    @BindView(R.id.ib_main_addressmanage_add_shop_gps_loc)
    ImageButton ibMainAddressManageAddUserGpsLoc;
    @OnClick(R.id.ib_main_addressmanage_add_shop_gps_loc)
    public void ibMainAddressManageAddUserGpsLocOnclick(){
        locMySelf();
    }
    /*定位自己*/
    private void locMySelf(){
        location(new LatLng(selfLat,selfLon));
    }










    public AddressManageAddShopController(Activity activity1,  InitRecycleView initRecycleView1){
        activity = activity1;
    /*    etMainAddressManageAddShopContentAddress = editText;*/
        initRecycleView = initRecycleView1;
        init();
    }
    private void isUpdateInit(LatLng latLng){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheSavename xcCacheSavename = new XCCacheSavename();
        String isAddressUpdate = xcCacheManager.readCache(xcCacheSavename.isAddressUpdate);
        if((isAddressUpdate != null)&&(isAddressUpdate.equals("yes"))){
            String lat = xcCacheManager.readCache(xcCacheSavename.addrShopLat);
            String lon = xcCacheManager.readCache(xcCacheSavename.addrShopLon);
            String userName = xcCacheManager.readCache(xcCacheSavename.addrShopName);
            String addr = xcCacheManager.readCache(xcCacheSavename.addrShopAddr);
            String clientaddrThings = xcCacheManager.readCache(xcCacheSavename.addrShopclientaddrThings);
            /*Toast.makeText(activity,"isUpdateInit:"+lat+" lon:"+lon+" userName:"+userName+" addr:"+addr+" clientaddrThings:"+clientaddrThings,Toast.LENGTH_SHORT).show();*/
            if((lat != null)&&(lon != null)){
                blat = Double.parseDouble(lat);
                blon = Double.parseDouble(lon);

            }
            if(userName != null){
                etMainAddressManageAddShopContentNameCall.setText(userName);

            }
            if(addr != null){
                etMainAddressManageAddShopContentAddress.setText(addr);
            }
            xcCacheManager.writeCache(xcCacheSavename.isAddressUpdate,"no");
            location(new LatLng(blat,blon));
        }else{
            /*Toast.makeText(activity,"this is add",Toast.LENGTH_SHORT).show();*/
        /*    LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());*/
            location(latLng);
        }


    }
    @Override
    public void init(){
        ButterKnife.bind(this,activity);

        initBaiDuMap();
    }
    private void initBaiDuMap(){
        /*监听输入框的变化*/
        /*监听输入框的变化*/
        initPoiSearch();
        mBaiduMap = mvMainAddressManageAddShopContent.getMap();
        mvMainAddressManageAddShopContent.showZoomControls(false);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        locationClient=new LocationClient(activity);
        locationClient.registerLocationListener(locationListener);
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        initOverlyWithMapView();

       /*地理编码初始化*/
        mSearch = GeoCoder.newInstance();
        /*地理编码初始化*/
        /*设置编码监听者*/
        mSearch.setOnGetGeoCodeResultListener(this);
        /*设置编码监听者*/
        initLocation1();
        locationClient.start();
    }

    /*经纬度转换为地址监听*/
    private void initPoiSearch(){
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);

    }
    /*经纬度转换为地址监听*/

    /*地图移动坐标不动*/
    private void initOverlyWithMapView(){
        mapTouchListener = new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                  /*滑动动作的时候设置为滑动状态*/

                /*滑动动作的时候设置为滑动状态*/
            /*Toast.makeText(getBaseContext(),"here is ontouch",Toast.LENGTH_SHORT).show();*/
                //http://blog.csdn.net/sjf0115/article/details/7306284 获取控件在屏幕上的坐标
               /* int[] location = new int[2];
                ivMainAddressManageAddShopContentCenterLoc.getLocationOnScreen(location);*/

             /*   int x = location[0];
                int y = location[1];*/
                int x = (int) ivMainAddressManageAddShopContentCenterLoc.getX();
                int y = (int) ivMainAddressManageAddShopContentCenterLoc.getY();
                Point point = new Point(x, y);
            /*Toast.makeText(getBaseContext(),"x:"+x+"y:"+y,Toast.LENGTH_SHORT).show();*/
                //http://blog.csdn.net/sjf0115/article/details/7306284 获取控件在屏幕上的坐标
                if(point != null) {
                    currentPt = mBaiduMap.getProjection().fromScreenLocation(point);

                    mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(currentPt));
                    blat = currentPt.latitude;
                    blon = currentPt.longitude;
                }
            }
        };
        mBaiduMap.setOnMapTouchListener(mapTouchListener);


    }
    /*地图移动坐标不动*/
    /**配置定位参数**/
    private void initLocation1(){
        LocationClientOption option=new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setLocationMode(Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);//返回地址
        option.setIsNeedLocationDescribe(true);//返回地址周边描述
        option.setEnableSimulateGps(false);
        locationClient.setLocOption(option);
    }

    /**接收异步返回的定位结果**/
    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            if (location == null || mvMainAddressManageAddShopContent == null) {
                return;
            }

            if(isFirst){
                showCurrentPosition(location);
                isFirst = false;
            }
            /*showCurrentPosition(location);*/
        }
    }
    /**定位**/
    private void showCurrentPosition(BDLocation location1){
        TextView textView = new TextView(activity);
        Drawable drawable1 = activity.getResources().getDrawable(R.drawable.arrow);
        drawable1.setBounds(0, 0, 40, 45);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        textView.setCompoundDrawables(drawable1,null,null,null);
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromView(textView);
        /*定位蓝色点*/
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location1.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location1.getLatitude())
                .longitude(location1.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));
        /*定位蓝色点*/
        /*LatLng latLng = new LatLng(location1.getLatitude(),location1.getLongitude());*/
        /*location(latLng);*/
    /*获取自己的坐标*/
        selfLat = location1.getLatitude();
        selfLon = location1.getLongitude();
        /*获取自己的坐标*/
        if((location1.getAddrStr()!= null)&&(location1.getLocationDescribe() != null)) {
            addressLocation = location1.getAddrStr() + " " + location1.getLocationDescribe();
            etMainAddressManageAddShopContentAddress.setText(addressLocation);
            beginSearchLalByAddress(addressLocation);
        }

    }


    /*根据地名开始查找经纬度*/
    public void beginSearchLalByAddress(String address){
       /* String address = etHelpMeBuyAddSellerAddressContentAddress.getText().toString();*/
        int indexBlank = address.indexOf(" ");
        if(indexBlank > 0) {
            address = address.substring(0, indexBlank+1);
        }
        int index = address.indexOf("市");
        try {
            if (index > 0) {
                String city = address.substring(0, index+1);
                address = address.substring(index+1,address.length());
                mSearch.geocode((new GeoCodeOption()).city(city).address(address));
            } else {
                mSearch.geocode((new GeoCodeOption()).city("浙江省温州市").address(address));
            }
        }catch (Exception e){

        }
    }

    public void poiSearchInCity(String keyword){
        String address = etMainAddressManageAddShopContentAddress.getText().toString();
        Log.i("poiSearchInCity","address");
        int indexOfBlank = address.indexOf(" ");
        if(indexOfBlank > 0){
            Log.i("poiSearchInCity","have blank");
            address = address.substring(0,indexOfBlank+1);
        }
        int indexOfCity = address.indexOf("市");
        if(indexOfCity > 0){
            Log.i("poiSearchInCity","city");
            String city = address.substring(0,indexOfCity+1);
            /*Toast.makeText(activity,"city:"+city+" keyword:"+keyword,Toast.LENGTH_SHORT).show();*/
            isAddress = false;
            poiSearch.searchInCity((new PoiCitySearchOption()).city(city).keyword(keyword).pageNum(0).pageCapacity(30));
        }else{
            Log.i("poiSearchInCity","default");
            /*Toast.makeText(activity," keyword:"+keyword,Toast.LENGTH_SHORT).show();*/
            isAddress = false;
            poiSearch.searchInCity((new PoiCitySearchOption()).city("浙江省温州市").keyword(keyword).pageNum(0).pageCapacity(30));
        }

    }
    /*poi附近检索*/
    public void poiSearchNearBy(String keyword,LatLng latLng){
        int indexBlank = keyword.indexOf(" ");
        if(indexBlank > 0){
            if((latLng != null)&&(keyword!=null)) {
                keyword = keyword.substring(0,indexBlank+1);
                int indexCity = keyword.indexOf("市");
                if(indexCity > 0){
                    keyword = keyword.substring(indexCity+1,keyword.length());
                }

                /*Toast.makeText(activity," keyword:"+keyword,Toast.LENGTH_SHORT).show();*/

                poiSearch.searchNearby((new PoiNearbySearchOption())
                        .location(latLng)
                        .radius(90000)
                        .keyword(keyword).pageNum(0).pageCapacity(30));
            }
        }else{
            if((latLng != null)&&(keyword!=null)) {
                /*Toast.makeText(activity," keyword:"+keyword,Toast.LENGTH_SHORT).show();*/

                poiSearch.searchNearby((new PoiNearbySearchOption())
                        .location(latLng)
                        .radius(90000)
                        .keyword(keyword)
                        .pageNum(0).pageCapacity(30));
            }
        }
    }
    /*地图移动到经纬度所表示的地方*/
    public void location(LatLng ll){

        /*无论哪个调用此动画 都将经纬度赋值*/
        blat = ll.latitude;
        blon = ll.longitude;
        /*无论哪个调用此动画 都将经纬度赋值*/
       /* mBaiduMap.clear();*/
        //定义地图状态
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

    }
    /*地图移动到经纬度所表示的地方*/

    /*搜索附近的关键词*/
    private void getPoisFromKeyWordSearch(final List<PoiInfo> poiInfoList){
        /*ArrayList<MarkerOptions> markerOptionsList = new ArrayList<MarkerOptions>();*/
        Log.i("getPois","begin");
        final List<Marker> markerList = new ArrayList<>();
        mBaiduMap.clear();
        if((poiInfoList != null)&&(poiInfoList.size() > 0)) {
            Log.i("getPois","poiInfoList not null");
            for(int i =0;i<poiInfoList.size();i++) {
                TextView textView = new TextView(activity);
                Drawable drawable1 = activity.getResources().getDrawable(R.drawable.activity_main_addressmanage_add_shop_nearby);
                drawable1.setBounds(0, 0, 45, 45);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
                textView.setCompoundDrawables(drawable1, null, null, null);
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(textView);
                LatLng ll = new LatLng(poiInfoList.get(i).location.latitude,poiInfoList.get(i).location.longitude);
                /*BitmapDescriptor bitmap = null;*/
                //准备 marker option 添加 marker 使用
                MarkerOptions markerOptions = new MarkerOptions().icon(bitmapDescriptor).position(ll);
                //获取添加的 marker 这样便于后续的操作

                markerList.add((Marker) mBaiduMap.addOverlay(markerOptions));

                /*markerOptionsList.add(markerOptions);*/
            }
            Log.i("getPois","poiInfoList not null");
            mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    int pos = 0;
                    for(int i=0;i<markerList.size();i++){
                        if(markerList.get(i) == marker) {
                            pos = i   ;
                            break;
                        }
                    }
                    Button button = new Button(activity);
                    button.setText(poiInfoList.get(pos).address);
                    LatLng ll = marker.getPosition();
                    button.setOnClickListener(new MyOnclickListener(poiInfoList.get(pos)));
                    InfoWindow mInfoWindow = new InfoWindow(button, ll, -47);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                   /* for(int i=0;i<markerList.size();i++) {
                        if(((Marker)markerList.get(i)) == marker) {
                            Button button = new Button(activity);
                            button.setText(poiInfoList.get(i).address);
                            LatLng ll = marker.getPosition();
                            button.setOnClickListener(new MyOnclickListener(poiInfoList.get(i)));
                            InfoWindow mInfoWindow = new InfoWindow(button, ll, -47);

                            mBaiduMap.showInfoWindow(mInfoWindow);
                        }
                    }*/
                    return false;
                }
            });
        }

    }

    public class MyOnclickListener implements View.OnClickListener{
        PoiInfo  poiInfo;
        public MyOnclickListener(PoiInfo poiInfo1){
            poiInfo = poiInfo1;
        }

        @Override
        public void onClick(View v) {
            if(poiInfo != null) {
                blat = poiInfo.location.latitude;
                blon = poiInfo.location.longitude;
                etMainAddressManageAddShopContentAddress.setText(poiInfo.address);
            }
            mBaiduMap.hideInfoWindow();
        }
    }/*
    public class MyOnclickListener implements View.OnClickListener{
        PoiInfo poiInfo;
        public MyOnclickListener(PoiInfo poiInfo1){
            poiInfo = poiInfo1;
        }

        @Override
        public void onClick(View v) {
            if(poiInfo != null) {
                blat = poiInfo.location.latitude;
                blon = poiInfo.location.longitude;
                etMainAddressManageAddShopContentAddress.setText(poiInfo.address);
            }
            mBaiduMap.hideInfoWindow();
        }
    }*/
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        Log.i("onGetGeoCodeResult","begin");
        if (geoCodeResult.getLocation() != null) {
            /*直接定位到具体地址*/
            /*location( geoCodeResult.getLocation());*/
            isUpdateInit(geoCodeResult.getLocation());
            /*直接定位到具体地址*/
  /*          addressLocation = geoCodeResult.getAddress();
            etMainAddressManageAddShopContentAddress.setText(addressLocation );*/
            /*搜索附近地址*/
            if(isAddress) {
                poiSearchNearBy(geoCodeResult.getAddress(), geoCodeResult.getLocation());
            }
            /*搜索附近地址*/
            /*Toast.makeText(getBaseContext(),"onGetGeoCodeResult",Toast.LENGTH_SHORT).show();*/
        }
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(activity, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        Log.i("onGetReverseGeo","begin");
        LatLng latLng = result.getLocation();
        addressLocation = result.getAddress()+ "  " + result.getSematicDescription();
        etMainAddressManageAddShopContentAddress.setText(addressLocation );
     /*   location(latLng);*/
        if(isAddress) {
            poiSearchNearBy(addressLocation, latLng);
        }
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        if ((result == null) || (result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND)) {
            return;
        }
        if(result.getAllPoi() != null) {
            Log.i("onGetPoiResult","begin");
            /*Toast.makeText(activity," isAddress:"+isAddress,Toast.LENGTH_SHORT).show();*/
            if(!isAddress) {
                Log.i("onGetPoiResult","isAddress");
                /*Toast.makeText(activity,"PoiNum:"+result.getTotalPoiNum(),Toast.LENGTH_SHORT).show();*/
            /*找到关键词所标注的地方*/
                getPoisFromKeyWordSearch(result.getAllPoi());
            /*找到关键词所标注的地方*/
            }
            isAddress = true;
            Log.i("onGetPoiResult","isAddress true");
/*
            Toast.makeText(this,"this is onGetPoiResult"+result.getAllAddr().get(0).address,Toast.LENGTH_LONG).show();*/
            initRecycleView.adapter.setDataList(result.getAllPoi());


        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }





    protected void onDestroy(){
        if(mBaiduMap != null) {
            mBaiduMap.clear();
        }
        if(mSearch != null) {
            mSearch.destroy();
        }
        isFirst = true;
        mvMainAddressManageAddShopContent.onDestroy();
        if(poiSearch != null) {
            poiSearch.destroy();
        }
        if(locationClient!=null){
            locationClient.unRegisterLocationListener(locationListener);
            locationClient.stop();
        }
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheSavename xcCacheSavename = new XCCacheSavename();
        xcCacheManager.writeCache(xcCacheSavename.isAddressUpdate,"no");

    }

    protected void onResume(){
        /*init();*/

        mvMainAddressManageAddShopContent.onResume();
    }
    protected void onPause(){

        mvMainAddressManageAddShopContent.onPause();

    }
}
