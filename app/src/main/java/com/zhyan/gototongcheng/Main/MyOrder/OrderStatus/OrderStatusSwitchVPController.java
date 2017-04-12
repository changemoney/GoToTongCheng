package com.zhyan.gototongcheng.Main.MyOrder.OrderStatus;

import android.app.Activity;
import android.graphics.Matrix;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhyan.gototongcheng.Main.BaseController;
import com.zhyan.gototongcheng.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Utils.SystemUtils;

/**
 * Created by admin on 2017/4/5.
 */

public class OrderStatusSwitchVPController extends BaseController{













    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    public List<Fragment> listViews; // Tab页面列表
    @BindView(R.id.rly_main_myorder_orderstatus_topbar_leftmenu)
    RelativeLayout rlyMainMyOrderOrderStatusTopBarLeftMenu;
    @OnClick(R.id.rly_main_myorder_orderstatus_topbar_leftmenu)
    public void rlyMainMyOrderOrderStatusTopBarLeftMenuOnclick(){
        activity.finish();
    }
    @BindView(R.id.vp_main_myorder_orderstatus_content)
    ViewPager vpMainMyOrderOrderStatusContent;
    @BindView(R.id.rly_main_myorder_orderstatus_tabbar_orderstatus)
    RelativeLayout rlyMainMyOrderOrderStatusTabBarOrderStatus;;
    @OnClick(R.id.rly_main_myorder_orderstatus_tabbar_orderstatus)
    public void rlyMainMyOrderOrderStatusTabBarOrderStatusOnclick(){
            vpMainMyOrderOrderStatusContent.setCurrentItem(0);
            currIndex = 1;
            initTabBar(0);

    }
    @BindView(R.id.tv_main_myorder_orderstatus_tabbar_orderstatus)
    TextView tvMainMyOrderOrderStatusTabBarOrderStatus;
    @BindView(R.id.rly_main_myorder_orderstatus_tabbar_orderdetail)
    RelativeLayout rlyMainMyOrderOrderStatusTabBarOrderDetail;
    @OnClick(R.id.rly_main_myorder_orderstatus_tabbar_orderdetail)
    public void rlyMainMyOrderOrderStatusTabBarOrderDetailOnclick(){
            vpMainMyOrderOrderStatusContent.setCurrentItem(1);
            currIndex = 0;
            initTabBar(1);

    }
    @BindView(R.id.tv_main_myorder_orderstatus_tabbar_orderdetail)
    TextView tvMainMyOrderOrderStatusTabBarOrderDetail;
    @BindView(R.id.iv_main_myorder_orderstatus_tabbar_greenbottom_line)
    ImageView ivMainMyOrderOrderStatusTabBarGreenBottomLine;
    FragmentManager fragmentManager;
    public OrderStatusSwitchVPController(Activity activity1, FragmentManager fragmentManager1){
        activity = activity1;
        fragmentManager = fragmentManager1;
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
        ivMainMyOrderOrderStatusTabBarGreenBottomLine.setImageMatrix(matrix);
        ivMainMyOrderOrderStatusTabBarGreenBottomLine.setLayoutParams(params);
    }


    /**
     * 初始化ViewPager
     */
    private void InitViewPager() {
        listViews = new ArrayList<Fragment>();
        LayoutInflater mInflater = activity.getLayoutInflater();
        OrderStatusOrderStatusFragment orderStatusOrderStatusFragment = new OrderStatusOrderStatusFragment();
        OrderStatusOrderDetailFragment orderStatusOrderDetailFragment = new OrderStatusOrderDetailFragment();
        listViews.add(orderStatusOrderStatusFragment);
        listViews.add(orderStatusOrderDetailFragment);
        vpMainMyOrderOrderStatusContent.setAdapter(new MyOrderStatusMyFragmentPagerAdapter(fragmentManager,listViews));
        vpMainMyOrderOrderStatusContent.setCurrentItem(0);
      /*  initXRVItemView();*/


        vpMainMyOrderOrderStatusContent.addOnPageChangeListener(new MyOnPageChangeListener());

    }


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
                ivMainMyOrderOrderStatusTabBarGreenBottomLine.startAnimation(animation);
                if((listViews != null)&&(listViews.size() > 1)) {
                    tvMainMyOrderOrderStatusTabBarOrderStatus.setTextColor(activity.getResources().getColor(R.color.color_activity_main_myorder_orderstatus_tabar_switch_selected_green_bg));
                    tvMainMyOrderOrderStatusTabBarOrderDetail.setTextColor(activity.getResources().getColor(R.color.color_activity_main_myorder_orderstatus_tabar_switch_unselect_gray_bg));
                  /*  vpMainMyOrderOrderStatusContent.setCurrentItem(0);*/
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
                ivMainMyOrderOrderStatusTabBarGreenBottomLine.startAnimation(animation);
                if((listViews != null)&&(listViews.size() > 1)) {
                    tvMainMyOrderOrderStatusTabBarOrderStatus.setTextColor(activity.getResources().getColor(R.color.color_activity_main_myorder_orderstatus_tabar_switch_unselect_gray_bg));
                    tvMainMyOrderOrderStatusTabBarOrderDetail.setTextColor(activity.getResources().getColor(R.color.color_activity_main_myorder_orderstatus_tabar_switch_selected_green_bg));
                   /* vpMainMyOrderOrderStatusContent.setCurrentItem(1);*/
                }
                break;

        }
    }
}
