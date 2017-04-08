package com.zhyan.gototongcheng.Main.BaiDuMapCommon;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.zhyan.gototongcheng.Main.BaiDuMapCommon.adapter.BaiduAddressSearchSuggestRecycleViewAdapter;
import com.zhyan.gototongcheng.Main.BaseController;
import com.zhyan.gototongcheng.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/3/24.
 */

public class BaiduAddressSearchSuggestController extends BaseController implements TextWatcher,OnGetPoiSearchResultListener,OnGetGeoCoderResultListener {


    @BindView(R.id.rly_baiduaddress_search_suggest_topbar_back)
    RelativeLayout rlyBaiduAddressSearchSuggestTopBarBack;
    @OnClick(R.id.rly_baiduaddress_search_suggest_topbar_back)
    public void rlyBaiduAddressSearchSuggestTopBarBackOnclick(){
        activity.finish();
    }

    @BindView(R.id.rlv_main_baidumap_common_baiduaddress_suggest_content)
    RecyclerView rlvMainBaiDuMapCommonBaiDuAddressSuggestContent;
    // 类PoiSearch继承poi检索接口
    private PoiSearch mpoiSearch;
    private List<PoiInfo> poiInfoList;
    private GeoCoder mSearch;//地理编码
    @BindView(R.id.et_baiduaddress_search_suggest_topbar_address)
    EditText etBaiduAddressSearchSuggestTopbarAddress;
    private String city,keyword;
    private BaiduAddressSearchSuggestRecycleViewAdapter baiduAddressSearchSuggestRecycleViewAdapter;
    public BaiduAddressSearchSuggestController(Activity activity1){
        activity = activity1;
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this,activity);
        initSuggestionSearch();
        initRecycleView();
    }
    private void initRecycleView(){

        /*建议地址数据填充*/
        baiduAddressSearchSuggestRecycleViewAdapter = new BaiduAddressSearchSuggestRecycleViewAdapter(activity,poiInfoList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
       /* layoutManager.setAutoMeasureEnabled(true);*/
        //设置布局管理器
        rlvMainBaiDuMapCommonBaiDuAddressSuggestContent.setLayoutManager(layoutManager);
        rlvMainBaiDuMapCommonBaiDuAddressSuggestContent.setAdapter(baiduAddressSearchSuggestRecycleViewAdapter);


    }
    private void initSuggestionSearch(){
        /*在线建议初始化*/
        /*mSuggestionSearch = SuggestionSearch.newInstance();*/
        /*监听在线建议*/
       /* mSuggestionSearch.setOnGetSuggestionResultListener(this);*/
        /*输入初始化*/
        etBaiduAddressSearchSuggestTopbarAddress.addTextChangedListener(this);
        /*建议地址初始化*/
        /*suggestionInfoList = new ArrayList<SuggestionInfo>();*/
        poiInfoList = new ArrayList<PoiInfo>();
        /*搜索参数初始化*/
       /* suggestionSearchOption = new SuggestionSearchOption();*/
        // 实例化PoiSearch
        mpoiSearch = PoiSearch.newInstance();
        // 注册搜索事件监听
        mpoiSearch.setOnGetPoiSearchResultListener(this);

        /*地理编码初始化*/
        mSearch = GeoCoder.newInstance();
        /*地理编码初始化*/
        /*设置编码监听者*/
        mSearch.setOnGetGeoCodeResultListener(this);
        /*设置编码监听者*/

    }

    private void suggestionSearchOnLine(String address){
        int indexProvince=address.indexOf("省");
        int indexCity=address.indexOf("市");
        if((address.length() >= 0)) {

            if((indexCity >= 0)){
            /*搜索全国*/
                city = address.substring(0,indexCity);
                mpoiSearch.searchInCity(new PoiCitySearchOption().pageNum(0).pageCapacity(30).city(city).keyword(address).isReturnAddr(true));
                mSearch.geocode(new GeoCodeOption()
                        .city(city)
                        .address(address));
                return;
            }
            /*默认搜索温州市*/
            // 分页编号
            mpoiSearch.searchInCity(new PoiCitySearchOption().pageNum(0).pageCapacity(30).city("温州市").isReturnAddr(true).keyword(address));
            mSearch.geocode(new GeoCodeOption()
                    .city("温州市")
                    .address(address));

        }


    }






    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        suggestionSearchOnLine(etBaiduAddressSearchSuggestTopbarAddress.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            //没有检索到结果
        }
        //获取地理编码结果
        PoiInfo poiInfo = new PoiInfo();
        poiInfo.address = result.getAddress();
        if(result.getLocation() !=null) {
            LatLng latLng = new LatLng(result.getLocation().latitude, result.getLocation().longitude);

            poiInfo.location = latLng;
            poiInfo.city = "";
            poiInfo.name = "";
            baiduAddressSearchSuggestRecycleViewAdapter.setHeadView(poiInfo);
        }
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            //没有找到检索结果
        }
        //获取反向地理编码结果
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        if ((result == null) || (result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND)) {
            return;
        }
        /*Toast.makeText(this,"size:"+suggestionResult.getAllSuggestions().size(),Toast.LENGTH_SHORT).show();*/
        /*if(result.isHasAddrInfo()) {
            Toast.makeText(this,"getpoiresult"+result.getAllAddr().size(),Toast.LENGTH_SHORT).show();
            baiduAddressSearchSuggestRecycleViewAdapter.setDataList(result.getAllAddr());
        }*/
        baiduAddressSearchSuggestRecycleViewAdapter.setDataList(result.getAllPoi());
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(activity, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
           /* Toast.makeText(this, "成功，查看详情页面", Toast.LENGTH_SHORT)
                    .show();*/

        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
}
