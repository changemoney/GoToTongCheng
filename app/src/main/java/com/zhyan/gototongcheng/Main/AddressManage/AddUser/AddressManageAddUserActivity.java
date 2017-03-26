package com.zhyan.gototongcheng.Main.AddressManage.AddUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Bean.BaseBean;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import gototongcheng.zhyan.com.library.Utils.SystemUtils;
import gototongcheng.zhyan.com.library.Widget.RecycleView.XRecycleView.XRecyclerView;
import rx.Observer;

import static com.baidu.mapapi.search.core.RouteNode.location;

/**
 * Created by admin on 2017/3/24.
 */

public class AddressManageAddUserActivity extends BaseActivity  {

    /*back 返回上级*/
    @BindView(R.id.rly_main_addressmanage_add_user_topbar_leftmenu)
    RelativeLayout rlyMainAddressManageAddUserTopBarLeftMenu;
    @OnClick(R.id.rly_main_addressmanage_add_user_topbar_leftmenu)
    public void rlyMainAddressManageAddUserTopBarLeftMenuOnclick(){
        finish();
    }
    /*back 返回上级*/

     /*附近地址 历史记录*/
    @BindView(R.id.tv_main_addressmanage_add_user_tabbar_nearby)
    TextView tvMainAddressManageAddUserTabBarNearBy;
    @BindView(R.id.iv_main_addressmanage_add_user_tabbar_nearby)
    ImageView ivMainAddressManageAddUserTabBarNearBy;
    @BindView(R.id.tv_main_addressmanage_add_user_tabbar_history)
    TextView tvMainAddressManageAddUserTabBarHistory;
    @BindView(R.id.iv_main_addressmanage_add_user_tabbar_history)
    ImageView ivMainAddressManageAddUserTabBarHistory;
    @BindView(R.id.iv_main_addressmanage_add_user_tab_greenbottom)
    ImageView ivMainAddressManageAddUserTabGreenBottom;
    @BindView(R.id.vp_main_addressmanage_add_user_content)
    ViewPager vpMainAddressManageAddUserContent;
    @BindView(R.id.rly_main_addressmanage_add_user_tabbar_nearby)
    RelativeLayout rlyMmainAddressManageAddUserTabbarNearBy;
    @OnClick(R.id.rly_main_addressmanage_add_user_tabbar_nearby)
    public void rlyMainAddressManageAddUserTabBarNearByOnclick(){
        vpMainAddressManageAddUserContent.setCurrentItem(0);
        currIndex = 1;
        InitTabBg(0);

    }
    @BindView(R.id.rly_main_addressmanage_add_user_tabbar_history)
    RelativeLayout rlyMmainAddressManageAddUserTabbarHistory;
    @OnClick(R.id.rly_main_addressmanage_add_user_tabbar_history)
    public void rlyMainAddressManageAddUserTabBarHistoryOnclick(){
        vpMainAddressManageAddUserContent.setCurrentItem(1);
        currIndex = 0;
        InitTabBg(1);

    }

    InitRecycleView initRecycleView;
    /*附近地址 历史记录*/
    @BindView(R.id.rly_main_addressmanage_add_user_addresssearch)
    RelativeLayout rlyMainAddressManageAddUserAddressSearch;
    @OnClick(R.id.rly_main_addressmanage_add_user_addresssearch)
    public void rlyMainAddressManageAddUserAddressSearchOnclick(){
        Intent intent = new Intent(this, BaiduAddressSearchSuggestActivity.class);
        startActivityForResult(intent,RESULT_SEARCH);
    }
    private final int RESULT_SEARCH = 15;
    private final int RESULT_CONTACTER = 12;
    /*tab 绿色叶卡*/
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private List<View> viewList; // Tab页面列表
    /*tab 绿色叶卡*/

    /*联系姓名 电话*/
    @BindView(R.id.et_main_addressmanage_add_user_content_name)
    EditText etMainAddressManageAddUserContentName;
    @BindView(R.id.et_main_addressmanage_add_user_content_tel)
    EditText etMainAddressManageAddUserContentTel;
    /*联系姓名 电话*/
    /*用户地址*/
    @BindView(R.id.et_main_addressmanage_add_user_contentaddress)
    EditText etMainAddressManageAddUserContentAddress;
    /*用户地址*/
    /*用户经纬度*/
    private double rlat,rlon;
    /*用户经纬度*/

    /*上拉*/
     /*信息确认返回*/
    @OnClick(R.id.rly_main_addressmanage_add_user_topbar_rightmenu)
    public void rlyHelpMeBuyAddReceiverDetailTopBarRightMenuOnclick(){
        addUserAddressToNet();
    }
    /*信息确认返回*/
    private void addUserAddressToNet(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String usid = xcCacheManager.readCache("usid");
        String name = etMainAddressManageAddUserContentName.getText().toString();
        String tel = etMainAddressManageAddUserContentTel.getText().toString();
        String address = etMainAddressManageAddUserContentAddress.getText().toString();
        String finalAddress = name+" "+tel+" "+ address;
        rlat = addressManageAddUserController.rlat;
        rlon = addressManageAddUserController.rlon;
        float clientaddr1Lat = (float) rlat;
        float clientaddr1Long = (float) rlon;
        String clientaddr1Isdefault = "";

        if((usid != null)&&(finalAddress != null)&&(clientaddr1Isdefault != null)&&(!name.isEmpty())){
            AddressManageNetWorks addressManageNetWorks = new AddressManageNetWorks();
            addressManageNetWorks.addUserAddress(usid,finalAddress,clientaddr1Lat,clientaddr1Long,clientaddr1Isdefault, new Observer<BaseBean>() {
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
            Toast.makeText(getBaseContext(),"请输入正确联系方式和地址,不能为空",Toast.LENGTH_LONG).show();
        }
    }
    /*信息确认返回*/
    @BindView(R.id.rly_main_addressmanage_add_user_tabbar_up)
    RelativeLayout rlyMainAddressManageAddUserTabBarUp;
    @BindView(R.id.lly_main_addressmanage_add_user_tabbar_bottom)
    LinearLayout llyMainAddressManageAddUserTarBarBottom;
    @OnClick(R.id.rly_main_addressmanage_add_user_tabbar_up)
    public void rlyHelpMeBuyAddReceiverDetailTabBarUpOnclick(){
        int tempHeight = 0;
        if(!isUp){
            isUp = true;
            ViewGroup.LayoutParams layoutParams = llyMainAddressManageAddUserTarBarBottom.getLayoutParams();
            SystemUtils systemUtils = new SystemUtils(this);
            tempHeight = layoutParams.height;

            // 初始化需要加载的动画资源
            Animation animation = AnimationUtils
                    .loadAnimation(this, R.anim.pop_enter);
            animation.setDuration(1000);
            layoutParams.height = 0;
            llyMainAddressManageAddUserTarBarBottom.setLayoutParams(layoutParams);
            llyMainAddressManageAddUserTarBarBottom.startAnimation(animation);
            layoutParams.height += systemUtils.getWindowHeight()/2;
            llyMainAddressManageAddUserTarBarBottom.setLayoutParams(layoutParams);
            ivMainAddressManageAddUserTabBarUpArrow.setBackgroundResource(R.mipmap.down_arrow);
            /*rlyHelpMeBuyAddShopDetailContentUp.setAnimation(R.style.PopupAnimation);;*/
        }else{
            ViewGroup.LayoutParams layoutParams = llyMainAddressManageAddUserTarBarBottom.getLayoutParams();

            layoutParams.height =tempHeight ;

            // 初始化需要加载的动画资源
            Animation animation = AnimationUtils
                    .loadAnimation(this, R.anim.pop_exit);
            /*animation.setDuration(1000);*/
            llyMainAddressManageAddUserTarBarBottom.startAnimation(animation);
            llyMainAddressManageAddUserTarBarBottom.setLayoutParams(layoutParams);
            isUp = false;
            ivMainAddressManageAddUserTabBarUpArrow.setBackgroundResource(R.mipmap.up_arrow);
        }
    }
    @BindView(R.id.iv_main_addressmanage_add_user_tabbar_uparrow)
    ImageView ivMainAddressManageAddUserTabBarUpArrow;
    private boolean isUp = false;
    /*上拉*/
    private AddressManageAddUserController addressManageAddUserController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* initBaiDuSDK();*/
        setContentView(R.layout.activity_main_addressmanage_add_user_lly);
        init();
    }


    @Override
    public void init() {
        ButterKnife.bind(this);

        initEditText();
        initSwitchContent();
    }
    private void initEditText(){

        etMainAddressManageAddUserContentAddress.setHorizontallyScrolling(false);
        etMainAddressManageAddUserContentAddress.setMaxLines(Integer.MAX_VALUE);
    }
    /*附近地址 历史记录*/
    private void initSwitchContent(){
        /*InitTabBg(true);*/
        InitImageView();
        InitViewPager();

        poiBeginSearch();
        /*initEditAddress();*/
    }
    /*附近地址 历史记录*/

    /**
     * 初始化动画
     */
    private void InitImageView() {

        SystemUtils systemUtils = new SystemUtils(this);
        int width = systemUtils.getWindowWidth();
        int height = systemUtils.getWindowHeight();
        int marginLeft = (((width/2)/2)/2)-width/30;
        int ivWidth = (width/2)/2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ivWidth,getResources().getDimensionPixelSize(R.dimen.activity_main_addressmanage_add_user_tabbar_bottom_green_height));
        params.setMargins(marginLeft,0,0,0);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 2  )/2+screenW/35 ;// 计算偏移量  screenW/有几个tab 就除以几
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        ivMainAddressManageAddUserTabGreenBottom.setImageMatrix(matrix);
        ivMainAddressManageAddUserTabGreenBottom.setLayoutParams(params);
    }


    /**
     * 初始化ViewPager
     */
    private void InitViewPager() {
        viewList = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
        viewList.add(mInflater.inflate(R.layout.activity_main_addressmanage_add_user_content_vp_itemrv_lly, null));
        viewList.add(mInflater.inflate(R.layout.activity_main_addressmanage_add_user_content_vp_itemrv_lly, null));
        initRecycleView = new InitRecycleView(viewList.get(0));
        initRecycleView.initXRV();
        addressManageAddUserController = new AddressManageAddUserController(this,etMainAddressManageAddUserContentAddress,initRecycleView);
        vpMainAddressManageAddUserContent.setAdapter(new AddUserMyPagerAdapter(viewList));
        vpMainAddressManageAddUserContent.setCurrentItem(0);
        vpMainAddressManageAddUserContent.addOnPageChangeListener(new MyOnPageChangeListener());

    }



    public class  InitRecycleView {
        @BindView(R.id.xrv_main_addressmanage_add_user_content_vp_item)
        XRecyclerView xrvMainAddressManageAddUserContentVPItem;
        public MyRecycleViewAdapter adapter;

        private List<PoiInfo> poiInfoList = new ArrayList<>();

        public InitRecycleView(View view) {
            ButterKnife.bind(this, view);
            initXRV();

        }

        private void initXRV() {
            adapter = new MyRecycleViewAdapter(getBaseContext(), poiInfoList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
            xrvMainAddressManageAddUserContentVPItem.setLayoutManager(layoutManager);
            xrvMainAddressManageAddUserContentVPItem.setAdapter(adapter);
        }

    }
 /*RecycleView适配器*/

    public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ItemContentViewHolder>{
        private List<PoiInfo> testList;
        private Context context;
        private LayoutInflater inflater;
        public MyRecycleViewAdapter(Context context,List<PoiInfo> stringList){
            testList = stringList;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }
        public void setDataList(List<PoiInfo> dataList){
            this.testList = dataList;
            this.notifyDataSetChanged();
        }
        @Override
        public MyRecycleViewAdapter.ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyRecycleViewAdapter.ItemContentViewHolder(inflater.inflate(R.layout.activity_main_addressmanage_add_user_content_vp_itemrv_item_lly, parent, false));

        }
        @Override
        public void onBindViewHolder(MyRecycleViewAdapter.ItemContentViewHolder holder, int position) {
            try {
                if (testList.size() != 0) {
                    holder.tvHelpMeBuyAddReceiverDetailContentVPItemRVItemAddr.setText(testList.get(position).city + testList.get(position).name);/*testList.get(position).address+testList.get(position).describeContents()*/
                    holder.lng = testList.get(position).location;
                }
            }catch (Exception e){

            }
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return testList.size();
        }


        public class ItemContentViewHolder extends RecyclerView.ViewHolder{
            public LatLng lng;
            @BindView(R.id.lly_helpmebuyadd_receiverdetail_content_vp_itemrv_item_total)
            LinearLayout llyHelpMeBuyAddReceiverDetailContentVPItemRVItemTotal;
            @OnClick(R.id.lly_helpmebuyadd_receiverdetail_content_vp_itemrv_item_total)
            public void llyHelpMeBuyAddReceiverDetailContentVPItemRVItemTotalOnclick(){
                etMainAddressManageAddUserContentAddress.setText(tvHelpMeBuyAddReceiverDetailContentVPItemRVItemAddr.getText().toString());
                if(lng != null){
                    rlat = lng.latitude;
                    rlon = lng.longitude;
                }

            }
            @BindView(R.id.tv_helpmebuyadd_receiverdetail_content_vp_itemrv_item_addr)
            TextView tvHelpMeBuyAddReceiverDetailContentVPItemRVItemAddr;

            public ItemContentViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }

    /*RecycleView适配器*/
    /*
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
                ivMainAddressManageAddUserTabGreenBottom.startAnimation(animation);
                tvMainAddressManageAddUserTabBarNearBy.setTextColor(getResources().getColor(R.color.color_activity_main_addressmanage_add_user_tabbar_green_bg));
                ivMainAddressManageAddUserTabBarNearBy.setImageResource(R.drawable.activity_main_addressmanage_add_user_nearby_select);
                tvMainAddressManageAddUserTabBarHistory.setTextColor(getResources().getColor(R.color.color_activity_main_addressmanage_add_user_tabbar_gray_bg));
                ivMainAddressManageAddUserTabBarHistory.setImageResource(R.drawable.activity_main_addressmanage_add_user_historyrecord_normal);
                addressManageAddUserController.beginSearchLalByAddress(etMainAddressManageAddUserContentAddress.getText().toString());
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
                ivMainAddressManageAddUserTabGreenBottom.startAnimation(animation);
                tvMainAddressManageAddUserTabBarNearBy.setTextColor(getResources().getColor(R.color.color_activity_main_addressmanage_add_user_tabbar_gray_bg));
                ivMainAddressManageAddUserTabBarNearBy.setImageResource(R.drawable.activity_main_addressmanage_add_user_nearby_normal);
                tvMainAddressManageAddUserTabBarHistory.setTextColor(getResources().getColor(R.color.color_activity_main_addressmanage_add_user_tabbar_green_bg));
                ivMainAddressManageAddUserTabBarHistory.setImageResource(R.drawable.activity_main_addressmanage_add_user_historyrecord_select);
                break;
          /*  }*/
        }
    }

    private void poiBeginSearch(){
        /*Toast.makeText(getBaseContext(),"poiBeginSearch",Toast.LENGTH_SHORT).show();*/
        etMainAddressManageAddUserContentAddress.setOnEditorActionListener(new MyEditorActionListener());

    }

    /*软键盘监听*/
    public class MyEditorActionListener implements TextView.OnEditorActionListener {


        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                String keyword = "";
                keyword = v.getText().toString();
                if(!keyword.isEmpty()) {
                    addressManageAddUserController.beginSearchLalByAddress(keyword);
                }else {
                    addressManageAddUserController.mBaiduMap.clear();
                }
                //写你要做的事情
                    /*Toast.makeText(getBaseContext(), "" + keyword, Toast.LENGTH_SHORT).show();*/
                    /*poiSearch.searchNearby((new PoiNearbySearchOption())
                            .location(latLng)
                            .radius(600000)
                            .keyword(keyword)
                            .pageNum(0).pageCapacity(30));*/
                hideInput(AddressManageAddUserActivity.this);//隐藏软键盘

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



    /*搜索*/
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        /*Toast.makeText(this,"resultcode"+resultCode,Toast.LENGTH_LONG).show();*/
        switch (reqCode) {
            case (RESULT_CONTACTER) :
                if (resultCode == Activity.RESULT_OK) {
                    getPhoneContracts(data);
                }
                break;
            case RESULT_SEARCH:
                getAddressData(data);
                break;
            default:
                break;
        }
    }

    /*通讯录  http://www.2cto.com/kf/201203/122494.html
  *http://5200415.blog.51cto.com/3851969/969821
  * */

    public void getPhoneContracts(Intent data){
        if(data != null) {
            Uri contactData = data.getData();
            Cursor c = managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String phoneNumber = null;
                if (hasPhone.equalsIgnoreCase("1")) {
                    hasPhone = "true";
                } else {
                    hasPhone = "false";
                }
                etMainAddressManageAddUserContentName.setText(name);
                if (Boolean.parseBoolean(hasPhone)) {
                    int contactId = c.getInt(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if (phoneNumber != null) {
                            etMainAddressManageAddUserContentTel.setText(phoneNumber);
                        }
                    }
                    phones.close();
                }
            }
        }

    }
    /*通讯录*/
    /*查找返回地址*/
    private void getAddressData(Intent data){
        if(data != null) {
            Bundle b = data.getExtras(); //data为B中回传的Intent
            String address = b.getString("address");//str即为回传的值
            String lat = b.getString("lat");
            String lon = b.getString("lon");
            if ((lat != null) && (lon != null)) {
                LatLng latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
                location(latLng);
                etMainAddressManageAddUserContentAddress.setText(address);
            }
        }
    }
    /*查找返回地址*/
    protected void onDestroy(){
        addressManageAddUserController.onDestroy();
        super.onDestroy();

    }
    @Override
    protected void onResume() {
        addressManageAddUserController.onResume();
        super.onResume();
    }


    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        addressManageAddUserController.onPause();
    }
}
