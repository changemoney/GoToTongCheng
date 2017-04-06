package com.zhyan.gototongcheng.Main.MyOrder;

import android.app.Activity;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.Main.BaseController;
import com.zhyan.gototongcheng.NetWork.OrderNetWorks;
import com.zhyan.gototongcheng.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Bean.MyOrderBean;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import gototongcheng.zhyan.com.library.Utils.SystemUtils;
import gototongcheng.zhyan.com.library.Widget.RecycleView.XRecycleView.XRecyclerView;
import rx.Observer;

/**
 * Created by admin on 2017/3/26.
 */

public class MyOrderActivityController extends BaseController{
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    public List<View> listViews; // Tab页面列表


    @OnClick(R.id.rly_main_myorder_topbar_leftmenu)
    public void rlyMainMyOrderTopBarLeftMenuOnclick(){
        activity.finish();
    }

    @BindView(R.id.iv_main_myorder_tab_greenbottom)
    ImageView ivMainMyOrderTabGreenBottom;
/*    @BindView(R.id.vp_main_myorder)
    ViewPager vpMainMyOrder;*/// 页卡头标 页卡内容
/*    @OnTouch(R.id.vp_main_myorder)
    public boolean vpMainMyOrderTouch(View view, MotionEvent event){
        return true;
    }*/


    @BindView(R.id.tv_main_myorder_allorder)
    TextView tvMainMyOrderAllOrder;
    @OnClick(R.id.rly_main_myorder_allorder)
    public void rlyMainMyOrderAllOrderOnclick(){
      /*  vpMainMyOrder.setCurrentItem(0);*/
            currIndex = 1;
            initTabBar(0);
    }
    @BindView(R.id.tv_main_myorder_waitforsay)
    TextView tvMainMyOrderWaitForSay;
    @OnClick(R.id.rly_main_myorder_waitforsay)
    public void rlyMainMyOrderWaitForSayOnclick(){
       /* vpMainMyOrder.setCurrentItem(1);*/

            currIndex = 0;
            initTabBar(1);

    }
    public MyOrderActivityController(Activity activity1){
        activity = activity1;
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this,activity);
        initSwitchContent();
    }

    private void initSwitchContent(){

        InitImageView();
        InitViewPager();
    }
    XCCacheManager xcCacheManager;
        /*初始化 订单查询 或者待评价*/
    /**
     * 初始化动画
     */
    private void InitImageView() {

        SystemUtils systemUtils = new SystemUtils(activity);
        int width = systemUtils.getWindowWidth();
        int height = systemUtils.getWindowHeight();
        int marginLeft = (((width/2)/2)/2);
        int ivWidth = (width/2)/2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ivWidth,activity.getResources().getDimensionPixelSize(R.dimen.activity_main_myorder_tabbar_bottom_green_height));
        params.setMargins(marginLeft,0,0,0);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 2 - bmpW) / 2;// 计算偏移量  screenW/有几个tab 就除以几
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        ivMainMyOrderTabGreenBottom.setImageMatrix(matrix);
        ivMainMyOrderTabGreenBottom.setLayoutParams(params);
    }

    /**
     * 初始化ViewPager
     */
    private void InitViewPager() {
        listViews = new ArrayList<View>();
        LayoutInflater mInflater = activity.getLayoutInflater();
        listViews.add(mInflater.inflate(R.layout.activity_main_myorder_content_tab_vp_item_rv, null));
        listViews.add(mInflater.inflate(R.layout.activity_main_myorder_content_tab_vp_item_rv, null));
/*        vpMainMyOrder.setAdapter(new MyOrderMyPagerAdapter(listViews));
        vpMainMyOrder.setCurrentItem(0);*/
      /*  initXRVItemView();*/
        InitCompleteRVItemViews initCompleteRVItemViews = new InitCompleteRVItemViews(activity);
        initCompleteRVItemViews.getMyOrderFromNet();

/*        vpMainMyOrder.addOnPageChangeListener(new MyOnPageChangeListener());*/

    }
/*
    private void initXRVItemView(){
        initCompleteRVItemViews = new InitCompleteRVItemViews(activity);
        initWaitToPayRVItemViews = new InitWaitToPayRVItemViews(activity);
    }
*/

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        public MyOnPageChangeListener(){
        }
        public void onPageSelected(int arg0) {
            initTabBar(arg0);
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    }
    public void initTabBar(int arg0){
        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量
        Animation animation = null;
        switch (arg0) {
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }
                currIndex = arg0;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(200);
                ivMainMyOrderTabGreenBottom.startAnimation(animation);
                if((listViews != null)&&(listViews.size() > 1)) {
                    tvMainMyOrderAllOrder.setTextColor(activity.getResources().getColor(R.color.color_activity_main_myorder_tabar_switch_selected_green_bg));
                    tvMainMyOrderWaitForSay.setTextColor(activity.getResources().getColor(R.color.color_activity_main_myorder_tabar_switch_unselect_gray_bg));
                    InitCompleteRVItemViews initCompleteRVItemViews = new InitCompleteRVItemViews(activity);
                    initCompleteRVItemViews.getMyOrderFromNet();
                }
                break;
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                currIndex = arg0;
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(200);
                ivMainMyOrderTabGreenBottom.startAnimation(animation);
                if((listViews != null)&&(listViews.size() > 1)) {
                    tvMainMyOrderAllOrder.setTextColor(activity.getResources().getColor(R.color.color_activity_main_myorder_tabar_switch_unselect_gray_bg));
                    tvMainMyOrderWaitForSay.setTextColor(activity.getResources().getColor(R.color.color_activity_main_myorder_tabar_switch_selected_green_bg));
                    initWaitToPayRVItemViews();
                }
                break;

        }
    }

    public void initWaitToPayRVItemViews(){
        InitWaitToPayRVItemViews initWaitToPayRVItemViews = new InitWaitToPayRVItemViews(activity);
        initWaitToPayRVItemViews.getMyOrderFromNet();
    }
    /*初始化 完成订单 查询 */
    public class InitCompleteRVItemViews{
        @BindView(R.id.xrv_main_myorder_content_tab_vp_item)
        XRecyclerView xrvMyOrderContentTabVPItem;

        public MyOrderListCompleteRVAdapter myOrderListCompleteRVAdapter;
        private List<MyOrderBean> myOrderBeanList = new ArrayList<>();
        private void initRV(Activity activity){
            /*myOrderBeanList.add(new MyOrderBean());*/
            myOrderListCompleteRVAdapter = new MyOrderListCompleteRVAdapter(activity,myOrderBeanList);
            xrvMyOrderContentTabVPItem.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
            xrvMyOrderContentTabVPItem.setAdapter(myOrderListCompleteRVAdapter);

            xrvMyOrderContentTabVPItem.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    xrvMyOrderContentTabVPItem.refreshComplete();
                }

                @Override
                public void onLoadMore() {
                    xrvMyOrderContentTabVPItem.loadMoreComplete();
                }
            });


        }
        public InitCompleteRVItemViews(Activity activity){
            ButterKnife.bind(this,activity);
            initRV(activity);

        }
        /*已完成订单*/
        public void getMyOrderFromNet(){
            xcCacheManager = XCCacheManager.getInstance(activity);
            String usid = xcCacheManager.readCache("usid");
            myOrderListCompleteRVAdapter.clean();
            if((usid != null)&&(!usid.isEmpty())) {
                /*Toast.makeText(activity,"InitCompleteRVItemViews",Toast.LENGTH_SHORT).show();*/
                /*Toast.makeText(getBaseContext(),"usid:"+usid,Toast.LENGTH_SHORT).show();*/

                OrderNetWorks orderNetWorks = new OrderNetWorks();
                orderNetWorks.getMyOrderList(usid, new Observer<List<MyOrderBean>>() {
                    @Override
                    public void onCompleted() {
                        /*Toast.makeText(getBaseContext(),"orderbeanlist:OK",Toast.LENGTH_SHORT).show();*/
                    }

                    @Override
                    public void onError(Throwable e) {
                        /*Toast.makeText(getBaseContext(),"orderbeanlist:"+e,Toast.LENGTH_SHORT).show();*/
                    }

                    @Override
                    public void onNext(List<MyOrderBean> orderBeanList1) {
                        /*Toast.makeText(getBaseContext(),"orderbeanlist:"+orderBeanList1.get(0).getClientaddrAddr(),Toast.LENGTH_SHORT).show();*/

                        List<MyOrderBean> myOrderBeanList = new ArrayList<MyOrderBean>();
                        for(int i = 0;i<orderBeanList1.size();i++){
                            /*Toast.makeText(activity,"InitCompleteRVItemViews:"+orderBeanList1.get(i).getPaystatusPaystatus(),Toast.LENGTH_SHORT).show();*/
                            if(orderBeanList1.get(i).getPaystatusPaystatus().equals("支付成功")){
                                myOrderBeanList.add(orderBeanList1.get(i));
                            }else{
                                continue;
                            }
                        }
                        /*Toast.makeText(activity,"InitCompleteRVItemViews:"+orderBeanList.size(),Toast.LENGTH_SHORT).show();*/
                        myOrderListCompleteRVAdapter.addMyOrderBeanList(myOrderBeanList);
                    }
                });
            }
        }
        /*已完成订单*/


    }

    /*初始化 完成订单 或者待评价*/

    /*初始化 完成订单 查询 */
    public class InitWaitToPayRVItemViews{
        @BindView(R.id.xrv_main_myorder_content_tab_vp_item)
        XRecyclerView xrvMyOrderContentTabVPItem;

        public MyOrderListWaitToPayRVAdapter myOrderListWaitToPayRVAdapter;
        private List<MyOrderBean> myOrderBeanList = new ArrayList<>();
        private void initRV(Activity activity){
            /*myOrderBeanList.add(new MyOrderBean());*/
            myOrderListWaitToPayRVAdapter = new MyOrderListWaitToPayRVAdapter(activity,myOrderBeanList);
            xrvMyOrderContentTabVPItem.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
            xrvMyOrderContentTabVPItem.setAdapter(myOrderListWaitToPayRVAdapter);

            xrvMyOrderContentTabVPItem.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    xrvMyOrderContentTabVPItem.refreshComplete();
                }

                @Override
                public void onLoadMore() {
                    xrvMyOrderContentTabVPItem.loadMoreComplete();
                }
            });


        }
        public InitWaitToPayRVItemViews(Activity activity){
            ButterKnife.bind(this,activity);
            initRV(activity);

        }
        /*已完成订单*/
        public void getMyOrderFromNet(){
            xcCacheManager = XCCacheManager.getInstance(activity);
            String usid = xcCacheManager.readCache("usid");
            myOrderListWaitToPayRVAdapter.clean();
            if((usid != null)&&(!usid.isEmpty())) {
                /*Toast.makeText(getBaseContext(),"usid:"+usid,Toast.LENGTH_SHORT).show();*/
                /*Toast.makeText(activity,"InitWaitToPayRVItemViews",Toast.LENGTH_SHORT).show();*/
                OrderNetWorks orderNetWorks = new OrderNetWorks();
                orderNetWorks.getMyOrderList(usid, new Observer<List<MyOrderBean>>() {
                    @Override
                    public void onCompleted() {
                        /*Toast.makeText(getBaseContext(),"orderbeanlist:OK",Toast.LENGTH_SHORT).show();*/
                    }

                    @Override
                    public void onError(Throwable e) {
                        /*Toast.makeText(getBaseContext(),"orderbeanlist:"+e,Toast.LENGTH_SHORT).show();*/
                    }

                    @Override
                    public void onNext(List<MyOrderBean> orderBeanList1) {
                        /*Toast.makeText(getBaseContext(),"orderbeanlist:"+orderBeanList1.get(0).getClientaddrAddr(),Toast.LENGTH_SHORT).show();*/
                        List<MyOrderBean> orderBeanList = new ArrayList<MyOrderBean>();
                        for(int i = 0;i<orderBeanList1.size();i++){
                            if(!orderBeanList1.get(i).getPaystatusPaystatus().equals("支付成功")){
                                orderBeanList.add(orderBeanList1.get(i));
                            }else{
                                continue;
                            }
                        }
                        myOrderListWaitToPayRVAdapter.addMyOrderBeanList(orderBeanList);
                    }
                });
            }
        }
        /*已完成订单*/


    }

    /*初始化 完成订单 或者待评价*/
}
