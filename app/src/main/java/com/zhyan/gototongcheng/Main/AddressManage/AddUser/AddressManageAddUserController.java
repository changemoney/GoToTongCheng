package com.zhyan.gototongcheng.Main.AddressManage.AddUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
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
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.zhyan.gototongcheng.Main.BaseController;
import com.zhyan.gototongcheng.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Utils.SystemUtils;
import gototongcheng.zhyan.com.library.Widget.RecycleView.XRecycleView.XRecyclerView;
import com.zhyan.gototongcheng.Main.AddressManage.AddUser.AddressManageAddUserActivity.InitRecycleView;
/**
 * Created by admin on 2017/3/24.
 */

public class AddressManageAddUserController extends BaseController  implements OnGetGeoCoderResultListener,OnGetPoiSearchResultListener {
    /*百度地图*/
    private GeoCoder search=null;
    /*关键字poi检索*/
    private PoiSearch poiSearch;
    /*关键字poi检索*/
    /*百度地图*/
    @BindView(R.id.mv_main_addressmanage_add_user_content)
    MapView mvHelpMeBuyAddReceiverDetailContent;
    @BindView(R.id.iv_main_addressmanage_add_user_centerloc)
    ImageView ivMainAddressManageAddUserCenterLoc;
    public BaiduMap mBaiduMap;

    private final int RESULT_SEARCH = 15;
    private final int RESULT_CONTACTER = 12;
    private BaiduMap.OnMapTouchListener mapTouchListener;
    private BDLocationListener locationListener= new MyLocationListener();
    private LocationClient locationClient=null;
    private LatLng currentPt = new LatLng(0,0);
    /*用户经纬度*/
    public double rlat,rlon;
    /*用户经纬度*/
    private  final int accuracyCircleFillColor = 0xAAFFFF88;
    private  final int accuracyCircleStrokeColor = 0xAA00FF00;
    private String addressLocation = "";
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private Boolean isFirst = true;
    private EditText  etMainAddressManageAddUserContentAddress ;
    InitRecycleView initRecycleView;
    public AddressManageAddUserController(Activity activity1, EditText editText, InitRecycleView initRecycleView1){
        activity = activity1;
        etMainAddressManageAddUserContentAddress = editText;
        initRecycleView = initRecycleView1;
        init();
    }

    public void init(){
        ButterKnife.bind(this,activity);
        initBaiDuMap();
    }
    /*打开通讯录*/
    @OnClick(R.id.rly_main_addressmanage_add_user_addcontacter)
    public void rlyHelpMeBuyAddReceiverDetailAddContacterOnclick(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);//vnd.android.cursor.dir/contact
        activity.startActivityForResult(intent, RESULT_CONTACTER);
 /*       getPhoneContracts(this);*/
    }
    /*打开通讯录*/
        /*搜索*/
    @OnClick(R.id.rly_main_addressmanage_add_user_addresssearch)
    public void llyHelpMeBuyAddReceiverDetailSearchAddressOnclick(){
/*        Intent intent = new Intent(this,BaiduAddressSearchSuggestActivity.class);
        activity.startActivityForResult(intent,RESULT_SEARCH);*/
    }


    /*根据地名开始查找经纬度*/
    public void beginSearchLalByAddress(String address){
       /* String address = etHelpMeBuyAddSellerAddressContentAddress.getText().toString();*/
        int index = address.indexOf("市");
        try {
            if (index > 0) {
                String city = address.substring(0, index);
                address = address.substring(index, address.length());
                search.geocode(new GeoCodeOption().city(city).address(address));
            } else {
                search.geocode(new GeoCodeOption().city("温州市").address(address));
            }
        }catch (Exception e){

        }
    }
    /*根据地名开始查找经纬度*/

    private void initBaiDuMap(){
        initPoiSearch();
        mBaiduMap = mvHelpMeBuyAddReceiverDetailContent.getMap();

        initOverlyWithMapView();
        //设置缩放级别，默认级别为12
        mBaiduMap.setMyLocationEnabled(true);
        locationClient=new LocationClient(activity);
        locationClient.registerLocationListener(locationListener);
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        search= GeoCoder.newInstance();
        /**根据经纬度得到屏幕中心点地址**/
        search.setOnGetGeoCodeResultListener(this);
        initLocation();
        locationClient.start();


    }

    private void initPoiSearch(){
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);

    }
    /*地图移动坐标不动*/
    private void initOverlyWithMapView(){

        mapTouchListener = new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                  /*滑动动作的时候设置为滑动状态*/

                /*滑动动作的时候设置为滑动状态*/
            /*Toast.makeText(getBaseContext(),"here is ontouch",Toast.LENGTH_SHORT).show();*/
                //http://blog.csdn.net/sjf0115/article/details/7306284 获取控件在屏幕上的坐标
                int[] location = new int[2];
                ivMainAddressManageAddUserCenterLoc.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                Point point = new Point(x,y);
                /*Toast.makeText(getBaseContext(),"x:"+x+"y:"+y,Toast.LENGTH_SHORT).show();*/
                //http://blog.csdn.net/sjf0115/article/details/7306284 获取控件在屏幕上的坐标
                currentPt = mBaiduMap.getProjection().fromScreenLocation(point);
                search.reverseGeoCode(new ReverseGeoCodeOption().location(currentPt));
                rlat = currentPt.latitude;
                rlon = currentPt.longitude;
            }
        };
        mBaiduMap.setOnMapTouchListener(mapTouchListener);



    }

  /*地图移动坐标不动*/
    /**配置定位SDK参数**/
    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setOpenGps(true); // 打开gps
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
            if (location == null || mvHelpMeBuyAddReceiverDetailContent == null) {
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
    private void showCurrentPosition(BDLocation location){

        TextView textView = new TextView(activity);
        Drawable drawable1 = activity.getResources().getDrawable(R.drawable.arrow);
        drawable1.setBounds(0, 0,40, 45);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        textView.setCompoundDrawables(drawable1,null,null,null);
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromView(textView);
        /*定位蓝色点*/
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));
        /*定位蓝色点*/
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        location(latLng);
        /*etHelpMeBuyAddSellerAddressContentAddress.setText(location.getAddrStr() + location.getBuildingName() +location.getFloor()+location.getStreet()+location.getStreetNumber());*/
        if((location.getAddrStr()!= null)&&(location.getLocationDescribe() != null)) {
            addressLocation = location.getAddrStr() + " " + location.getLocationDescribe();
            etMainAddressManageAddUserContentAddress.setText(addressLocation);
            beginSearchLalByAddress(addressLocation);
        }
    }

    /**经纬度地址动画显示在屏幕中间  有关mark网站的出处http://blog.csdn.net/callmesen/article/details/40540895**/
    private void location(LatLng latLng){
        /*只要调用画面 就能赋值*/
        rlat = latLng.latitude;
        rlon = latLng.longitude;

        /*无论哪个调用此动画 都将经纬度赋值*/
       /* mBaiduMap.clear();*/
        //定义地图状态
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

    }

    /*poi附近检索*/
    public void poiSearchNearBy(String keyword,LatLng latLng){
        int indexBlank = keyword.indexOf(" ");
        if(indexBlank > 0){
            if((latLng != null)&&(keyword!=null)) {
                keyword = keyword.substring(0, indexBlank);
                poiSearch.searchNearby((new PoiNearbySearchOption())
                        .location(latLng)
                        .radius(9000)
                        .keyword(keyword)
                        .pageNum(0).pageCapacity(30));
            }
        }else{
            if((latLng != null)&&(keyword!=null)) {

                poiSearch.searchNearby((new PoiNearbySearchOption())
                        .location(latLng)
                        .radius(9000)
                        .keyword(keyword)
                        .pageNum(0).pageCapacity(30));
            }
        }

    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (geoCodeResult.getLocation() != null) {
            location(geoCodeResult.getLocation());
            /*搜索附近地址*/
            poiSearchNearBy(geoCodeResult.getAddress(),geoCodeResult.getLocation());
            /*搜索附近地址*/
        }
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(activity, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
     /*   mBaiduMap.clear();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));*/
        LatLng latLng = result.getLocation();
        addressLocation = result.getAddress();
        /*if(isFirst) {*/
      /*  location(latLng);*/
        etMainAddressManageAddUserContentAddress.setText(addressLocation+"  "+result.getSematicDescription());
        poiSearchNearBy(addressLocation,latLng);
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        if(result.getAllPoi() != null) {
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
        mBaiduMap.clear();
        mBaiduMap.setMyLocationEnabled(false);
        search.destroy();
        isFirst = true;
        mvHelpMeBuyAddReceiverDetailContent.onDestroy();
        locationClient.unRegisterLocationListener(locationListener);
        if(locationClient!=null){
            locationClient.stop();
        }


    }

    protected void onResume() {
        mvHelpMeBuyAddReceiverDetailContent.onResume();

    }



    protected void onPause() {
        // TODO Auto-generated method stub

        mvHelpMeBuyAddReceiverDetailContent.onPause();
    }

}
