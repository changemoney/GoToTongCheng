package com.zhyan.gototongcheng.Main.AddressManage.AddShop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.zhyan.gototongcheng.BaseActivity;
import com.zhyan.gototongcheng.Main.BaiDuMapCommon.BaiduAddressSearchSuggestActivity;
import com.zhyan.gototongcheng.NetWork.AddressManageNetWorks;
import com.zhyan.gototongcheng.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Bean.BaseBean;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import gototongcheng.zhyan.com.library.Utils.SystemUtils;
import gototongcheng.zhyan.com.library.Widget.RecycleView.XRecycleView.XRecyclerView;
import rx.Observer;

/**
 * 添加商家地址
 * Created by admin on 2017/3/24.
 */

public class AddressManageAddShopActivity extends BaseActivity{

    @BindView(R.id.rly_main_addressmanage_add_shop_tabbar_nearaddress)
    RelativeLayout rlyMmainAddressManageAddShopTabbarNearAddress;
    @OnClick(R.id.rly_main_addressmanage_add_shop_tabbar_nearaddress)
    public void rlyMmainAddressManageAddShopTabbarNearAddressOnclick(){
        vpMainAddressManageAddShopContent.setCurrentItem(0);
        currIndex = 1;
        InitTabBg(0);

    }
    @BindView(R.id.rly_main_addressmanage_add_shop_tabbar_history)
    RelativeLayout rlyMmainAddressManageAddShopTabbarHistory;
    @OnClick(R.id.rly_main_addressmanage_add_shop_tabbar_history)
    public void rlyMmainAddressManageAddShopTabbarHistoryOnclick(){
        vpMainAddressManageAddShopContent.setCurrentItem(1);
        currIndex = 0;
        InitTabBg(1);

    }


    /*上拉*/
    @BindView(R.id.rly_main_addressmanage_add_shop_content_up)
    RelativeLayout rlyMainAddressManageAddShopContentUp;
    @BindView(R.id.lly_main_addressmanage_add_shop_bottom)
    LinearLayout llyMainAddressmanageAddShopBottom;
    @OnClick(R.id.rly_main_addressmanage_add_shop_content_up)
    public void rlyMainAddressManageAddShopContentUpOnclick(){
        int tempHeight = 0;
        if(!isUp){
            isUp = true;
            ViewGroup.LayoutParams layoutParams = llyMainAddressmanageAddShopBottom.getLayoutParams();
            SystemUtils systemUtils = new SystemUtils(this);
            tempHeight = layoutParams.height;

            // 初始化需要加载的动画资源
            Animation animation = AnimationUtils
                    .loadAnimation(this, R.anim.pop_enter);
            animation.setDuration(1000);
            layoutParams.height = 0;
            llyMainAddressmanageAddShopBottom.setLayoutParams(layoutParams);
            llyMainAddressmanageAddShopBottom.startAnimation(animation);
            layoutParams.height += systemUtils.getWindowHeight()/2;
            llyMainAddressmanageAddShopBottom.setLayoutParams(layoutParams);
            ivMainAddressManageAddShopContentUpArrow.setBackgroundResource(R.mipmap.down_arrow);
            /*rlyHelpMeBuyAddShopDetailContentUp.setAnimation(R.style.PopupAnimation);;*/
        }else{
            ViewGroup.LayoutParams layoutParams = llyMainAddressmanageAddShopBottom.getLayoutParams();

            layoutParams.height =tempHeight ;

            // 初始化需要加载的动画资源
            Animation animation = AnimationUtils
                    .loadAnimation(this, R.anim.pop_exit);
            /*animation.setDuration(1000);*/
            llyMainAddressmanageAddShopBottom.startAnimation(animation);
            llyMainAddressmanageAddShopBottom.setLayoutParams(layoutParams);
            isUp = false;
            ivMainAddressManageAddShopContentUpArrow.setBackgroundResource(R.mipmap.up_arrow);
        }
    }
    @BindView(R.id.iv_main_addressmanage_add_shop_content_uparrow)
    ImageView ivMainAddressManageAddShopContentUpArrow;
    private boolean isUp = false;
    /*上拉*/


    /*商家地址经纬度*/
    private double blat,blon;
    /*商家地址经纬度*/

    /*返回上级*/
    @OnClick(R.id.rly_main_addressmanage_add_shop_topbar_leftmenu)
    public void rlyMainAddressManageAddShopTopBarLeftMenuOnclick(){
        finish();
    }
    /*返回上级*/

    /*提交商户信息*/
    @OnClick(R.id.rly_main_addressmanage_add_shop_topbar_rightmenu)
    public void rlyHelpMeBuyAddSellerAddressTopBarRightMenuOnclick(){

        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSavename xcCacheSavename = new  XCCacheSavename();
        String isAddressUpdate = xcCacheManager.readCache(xcCacheSavename.isAddressUpdate);
        if(addressManageAddShopController.isUpdate){
            updateShopAddressToNet();
            /*Toast.makeText(AddressManageAddShopActivity.this,"this is updateShopAddressToNet",Toast.LENGTH_LONG).show();*/
            xcCacheManager.writeCache(xcCacheSavename.isAddressUpdate,"no");
        }else {
            addShopAddressToNet();
        }
    }

    private void updateShopAddressToNet(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSavename xcCacheSavename = new  XCCacheSavename();
        String clientThing1 = xcCacheManager.readCache(xcCacheSavename.addrShopclientaddrThings);
        String usid = xcCacheManager.readCache("usid");
        if((clientThing1 != null)&&(usid != null)){
            AddressManageNetWorks addressManageNetWorks = new AddressManageNetWorks();
            addressManageNetWorks.updateShopAddr(usid, clientThing1,etMainAddressManageAddShopContentNameCall.getText().toString()+" "+etMainAddressManageAddShopContentAddress.getText().toString(),(float) blat,(float) blon, "0", new Observer<BaseBean>() {
                @Override
                public void onCompleted() {
                    /*Toast.makeText(getBaseContext(),"onCompleted",Toast.LENGTH_SHORT).show();*/
                }

                @Override
                public void onError(Throwable e) {
                    /*Toast.makeText(getBaseContext(),"onError"+e,Toast.LENGTH_SHORT).show();*/
                }

                @Override
                public void onNext(BaseBean baseBean) {
                    Toast.makeText(AddressManageAddShopActivity.this,baseBean.getResult(),Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }

    private void addShopAddressToNet(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String usid = xcCacheManager.readCache("usid");
        String name = etMainAddressManageAddShopContentNameCall.getText().toString();
        String address = etMainAddressManageAddShopContentAddress.getText().toString();
        String finalAddress = name+" "+ address;
        blat = addressManageAddShopController.blat;
        blon = addressManageAddShopController.blon;
        float clientaddr1Lat = (float) blat;
        float clientaddr1Long = (float) blon;
        String clientaddr1Isdefault = "0";

        if((usid != null)&&(finalAddress != null)&&(clientaddr1Isdefault != null)&&(!name.isEmpty())){
            AddressManageNetWorks addressManageNetWorks = new AddressManageNetWorks();
            addressManageNetWorks.addShopAddress(usid,finalAddress,clientaddr1Lat,clientaddr1Long,clientaddr1Isdefault, new Observer<BaseBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(BaseBean baseBean) {
                    Toast.makeText(getBaseContext(),baseBean.getResult(),Toast.LENGTH_LONG).show();
                    finish();
                }
            });

        }else{
            Toast.makeText(getBaseContext(),"请输入正确的名称和地址,不能为空",Toast.LENGTH_LONG).show();
        }

    }
    /*提交商户信息*/
    /*附近地址 历史记录*/
    private final int RESULT_SEARCH = 15;
    @OnClick(R.id.rly_main_addressmanage_add_shop_addresssearch)
    public void rlyMainAddressManageAddShopAddressSearchOnclick(){
        Intent intent = new Intent(this, BaiduAddressSearchSuggestActivity.class);
        startActivityForResult(intent,RESULT_SEARCH);
    }
    @BindView(R.id.tv_main_addressmanage_add_shop_tabbar_history)
    TextView tvAddressManageAddShopTabBarHistory;
    @BindView(R.id.iv_main_addressmanage_add_shop_tabbar_history)
    ImageView ivAddressManageAddShopTabBarHistory;
    @BindView(R.id.tv_main_addressmanage_add_shop_tabbar_nearaddress)
    TextView tvAddressManageAddShopTabBarNearAddress;
    @BindView(R.id.iv_main_addressmanage_add_shop_tabbar_nearaddress)
    ImageView ivAddressManageAddShopTabBarNearAddress;
    @BindView(R.id.vp_main_addressmanage_add_shop_content)
    ViewPager vpMainAddressManageAddShopContent;
    InitRecycleView initRecycleView;
    /*附近地址 历史记录*/
    /*商家名称*/
    @BindView(R.id.et_main_addressmanage_add_shop_content_namecall)
    EditText etMainAddressManageAddShopContentNameCall;
    /*商家名称*/
    /*商家地址*/
    @BindView(R.id.et_main_addressmanage_add_shop_content_address)
    EditText etMainAddressManageAddShopContentAddress;

    /*商家地址*/
    /*绿色转移叶卡*/
    @BindView(R.id.iv_main_addressmanage_add_shop_tab_greenbottom)
    ImageView ivMainAddressManageAddShopTabGreenBottom;
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private List<View> viewList; // Tab页面列表
    /*绿色转移叶卡*/
    private AddressManageAddShopController addressManageAddShopController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*initBaiDuSDK();*/
        setContentView(R.layout.activity_main_addressmanage_add_shop_lly);
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        initEditText();

        initSwitchContent();

    }

    private void initEditText(){

        etMainAddressManageAddShopContentAddress.setHorizontallyScrolling(false);
        etMainAddressManageAddShopContentAddress.setMaxLines(Integer.MAX_VALUE);
    }

    private void initSwitchContent(){
  /*      InitTabBg(true,true);*/
        InitImageView();
        InitViewPager();

        poiBeginSearch();
    }

    /**
     * 初始化动画
     */
    private void InitImageView() {

        SystemUtils systemUtils = new SystemUtils(this);
        int width = systemUtils.getWindowWidth();
        int height = systemUtils.getWindowHeight();
        int marginLeft = (((width/2)/2)/2) - width/30;
        int ivWidth = (width/2)/2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ivWidth,getResources().getDimensionPixelSize(R.dimen.activity_main_addressmanage_add_shop_tabbar_bottom_green_height));
        params.setMargins(marginLeft,0,0,0);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 2  ) / 2 +screenW/35;// 计算偏移量  screenW/有几个tab 就除以几
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        ivMainAddressManageAddShopTabGreenBottom.setImageMatrix(matrix);
        ivMainAddressManageAddShopTabGreenBottom.setLayoutParams(params);
    }

    /**
     * 初始化ViewPager
     */
    private void InitViewPager() {
        viewList = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
        viewList.add(mInflater.inflate(R.layout.activity_main_addressmanage_add_shop_content_vp_itemrv_lly, null));
        viewList.add(mInflater.inflate(R.layout.activity_main_addressmanage_add_shop_content_vp_itemrv_lly, null));
        initRecycleView = new InitRecycleView(viewList.get(0));
        initRecycleView.initXRV();
        addressManageAddShopController = new AddressManageAddShopController(this,initRecycleView);
        vpMainAddressManageAddShopContent.setAdapter(new AddShopMyPagerAdapter(viewList));
        vpMainAddressManageAddShopContent.setCurrentItem(0);
        vpMainAddressManageAddShopContent.addOnPageChangeListener(new MyOnPageChangeListener());


    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {



        public void onPageSelected(int arg0) {

            switch (arg0){
                case 0:
                    InitTabBg(arg0);
                    break;
                case 1:
                    InitTabBg(arg0);

                    break;
            }
        }
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }

    }

    /**
     * 初始化头标
     */
    private void InitTabBg(int ag0) {

            /*Toast.makeText(this,"arg:"+ag0,Toast.LENGTH_LONG).show();*/

        int one = offset * 2;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量
        Animation animation = null;
        switch (ag0) {
           /* if (isFirst) {*/
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }
                currIndex = 0;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(200);
                ivMainAddressManageAddShopTabGreenBottom.startAnimation(animation);
                tvAddressManageAddShopTabBarNearAddress.setTextColor(getResources().getColor(R.color.color_activity_main_addressmanage_add_shop_tabbar_green_bg));
                ivAddressManageAddShopTabBarNearAddress.setImageResource(R.drawable.activity_main_addressmanage_add_shop_nearby_select);
                ivAddressManageAddShopTabBarHistory.setImageResource(R.drawable.activity_main_addressmanage_add_shop_historyrecord_normal);
                tvAddressManageAddShopTabBarHistory.setTextColor(getResources().getColor(R.color.color_activity_main_addressmanage_add_shop_tabbar_gray_bg));
                addressManageAddShopController.beginSearchLalByAddress(etMainAddressManageAddShopContentAddress.getText().toString());
                break;
         /*   } else {*/
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                currIndex = 1;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(200);
                ivMainAddressManageAddShopTabGreenBottom.startAnimation(animation);
                tvAddressManageAddShopTabBarHistory.setTextColor(getResources().getColor(R.color.color_activity_main_addressmanage_add_shop_tabbar_green_bg));
                ivAddressManageAddShopTabBarHistory.setImageResource(R.drawable.activity_main_addressmanage_add_shop_historyrecord_select);
                tvAddressManageAddShopTabBarNearAddress.setTextColor(getResources().getColor(R.color.color_activity_main_addressmanage_add_shop_tabbar_gray_bg));
                ivAddressManageAddShopTabBarNearAddress.setImageResource(R.drawable.activity_main_addressmanage_add_shop_nearby_normal);
                break;
          /*  }*/
        }
    }

    /*根据地名开始查找经纬度*/
    public class  InitRecycleView{
        @BindView(R.id.xrv_main_addressmanage_add_shop_content_vp_item)
        XRecyclerView xrvMainAddressManageAddShopContentVPItem;
        public MyRecycleViewAdapter adapter ;

        private List<PoiInfo> poiInfoList = new ArrayList<>();
        public InitRecycleView(View view){
            ButterKnife.bind(this,view);
            initXRV();
        }

        private void initXRV(){
            adapter = new MyRecycleViewAdapter(getBaseContext(),poiInfoList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
            xrvMainAddressManageAddShopContentVPItem.setLayoutManager(layoutManager);
            xrvMainAddressManageAddShopContentVPItem.setAdapter(adapter);
        }
    }
 /*RecycleView适配器*/

    public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ItemContentViewHolder>{

        private List<PoiInfo> testList;
        private Context context;
        private LayoutInflater inflater;
        public MyRecycleViewAdapter(Context context1,List<PoiInfo> stringList){
            testList = stringList;
            this.context = context1;
            inflater = LayoutInflater.from(context1);
        }
        public void setDataList(Collection<PoiInfo> dataList){
            int count = testList.size();
            testList.clear();
            if(dataList != null) {
                testList.addAll(dataList);
            }
            notifyDataSetChanged();//数据变动太快用notifyDataSetChanged();

        }


        @Override
        public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemContentViewHolder(inflater.inflate(R.layout.activity_main_addressmanage_add_shop_content_vp_itemrv_item_lly, parent, false));

        }

        @Override
        public void onBindViewHolder(MyRecycleViewAdapter.ItemContentViewHolder holder, int position) {
            try {
                if (testList.size() != 0) {
                    holder.tvMainAddressManageAddShopContentVPItemRVItemAddr.setText(testList.get(position).city + testList.get(position).name);/*testList.get(position).address+testList.get(position).describeContents()*/
                    holder.lng = testList.get(position).location;
                }
            }catch (Exception e){

            }
        }

        @Override
        public int getItemCount() {
            return testList.size();
        }

        public class ItemContentViewHolder extends RecyclerView.ViewHolder{
            public LatLng lng;
            @BindView(R.id.rly_main_addressmanage_add_shop_content_vp_itemrv_item_addr)
            RelativeLayout rlyMainAddressManageAddShopContentVpItemRVItemAddr;
            @OnClick(R.id.rly_main_addressmanage_add_shop_content_vp_itemrv_item_addr)
            public void rlyMainAddressManageAddShopContentVpItemRVItemAddrOnclick(){
                etMainAddressManageAddShopContentAddress.setText(tvMainAddressManageAddShopContentVPItemRVItemAddr.getText().toString());
                if(lng != null){
                    blat = lng.latitude;
                    blon = lng.longitude;
                }
            }
            @BindView(R.id.tv_main_addressmanage_add_shop_content_vp_itemrv_item_addr)
            public TextView tvMainAddressManageAddShopContentVPItemRVItemAddr;

            public ItemContentViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }

    /*RecycleView适配器*/


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_SEARCH:
                Bundle b=data.getExtras(); //data为B中回传的Intent
                String address=b.getString("address");//str即为回传的值
                String lat = b.getString("lat");
                String lon = b.getString("lon");
                /*Toast.makeText(getBaseContext(),"RESULT_SEARCH:"+lat+" "+lon,Toast.LENGTH_SHORT).show();*/
                if((lat != null) && (lon != null)) {
                     /*blat = Double.parseDouble(lat);
                     blon = Double.parseDouble(lon);*/
                    LatLng ll = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                    addressManageAddShopController.location(ll);
                    etMainAddressManageAddShopContentAddress.setText(address);
                }


                break;
            default:
                break;
        }
    }






    /*输入地址poi附近检索*/
    private void poiBeginSearch(){
        /*Toast.makeText(getBaseContext(),"poiBeginSearch",Toast.LENGTH_SHORT).show();*/
        etMainAddressManageAddShopContentNameCall.setOnEditorActionListener(new MyEditorActionListener(true));
        etMainAddressManageAddShopContentAddress.setOnEditorActionListener(new MyEditorActionListener(false));


    }

    /*软键盘监听*/
    public class MyEditorActionListener implements TextView.OnEditorActionListener {
        private boolean isNameCall;

        public MyEditorActionListener (Boolean isName){

            isNameCall = isName;

        }
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            String keyword = "";
            keyword = v.getText().toString();
            Log.i("editlistener","begin");
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.i("editlistener","search");
                if(isNameCall) {
                    Log.i("editlistener","isNameCall");
                    blat = addressManageAddShopController.blat;
                    blon = addressManageAddShopController.blon;
                    LatLng latLng = new LatLng(blat,blon);

                    if((!keyword.isEmpty())&&(latLng != null)) {
                        /*Toast.makeText(getBaseContext(),"keyword"+keyword,Toast.LENGTH_SHORT).show();*/
                        addressManageAddShopController.isAddress = false;
                        addressManageAddShopController.poiSearchNearBy(keyword, latLng);
                        /*addressManageAddShopController.poiSearchInCity(keyword);*/
                        Log.i("editlistener","key have value");
                    }else {
                        addressManageAddShopController.mBaiduMap.clear();
                        Log.i("editlistener","key is empty");
                    }
                    //写你要做的事情
                    /*Toast.makeText(getBaseContext(), "" + keyword, Toast.LENGTH_SHORT).show();*/
                    /*poiSearch.searchNearby((new PoiNearbySearchOption())
                            .location(latLng)
                            .radius(600000)
                            .keyword(keyword)
                            .pageNum(0).pageCapacity(30));*/

                }else{
                    addressManageAddShopController.isAddress = true;
                    Log.i("editlistener","address");
                    /*String address = "";
                    address = v.getText().toString();*/
                    if(addressManageAddShopController.isAddress) {
                        Log.i("editlistener","beginSearchLalByAddress");
                        addressManageAddShopController.beginSearchLalByAddress(keyword);
                    }

                    /*beginSearchLalByAddress(address);*/
                }
                Log.i("editlistener","hideInput");
                hideInput(AddressManageAddShopActivity.this);//隐藏软键盘
                return true;
            }
            return false;
        }
    }
    private InputMethodManager manager;
    private void hideInput(Activity activity) {
        // 输入法管理器 用户隐藏软键盘
        if(manager==null){
            manager = ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE));
        }

        manager.hideSoftInputFromWindow(( activity)
                        .getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }

    /*软键盘监听*/


    protected void onDestroy(){
        super.onDestroy();
        addressManageAddShopController.onDestroy();


    }

    protected void onResume(){
        /*init();*/

        super.onResume();
        addressManageAddShopController.onResume();
    }
    protected void onPause(){

        super.onPause();
        addressManageAddShopController.onPause();

    }


}
