package com.zhyan.gototongcheng;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhyan.gototongcheng.Main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;

/**
 * Created by admin on 2017/3/23.
 */

public class SplashController {
    /*第一次过渡页*/

    @BindView(R.id.mvp_splash_content)
    ViewPager vpSplashContent;
    @BindView(R.id.lly_splash_content)
    LinearLayout lly_dots;
    List<View> viewList;
    private SplashViewPageAdapter adapter;
    private Activity activity;
    private XCCacheManager xcCacheManager;
    private int[] imgSize = {R.drawable.splash1,R.drawable.splash2,R.drawable.splash3};
    public SplashController(Activity activity1){
        activity = activity1;
        ButterKnife.bind(this,activity1);
        xcCacheManager = XCCacheManager.getInstance(activity);

    }

    /*判断是否第一次加载*/
    public void isFirstLoad(Handler handler){
        if(xcCacheManager.readCache("isNewApp")== null) {
            initViewPage();
        }else{
            closeSplash(handler,vpSplashContent,lly_dots);
        }
    }
    /*public void isFirstLoad(Handler handler,ViewPager vpSplashContent,LinearLayout lly_dots,XCCacheManager xcCacheManager,SplashViewPageAdapter adapter){
        if(xcCacheManager.readCache("isNewApp")== null) {
            initViewPage(vpSplashContent,lly_dots,adapter,xcCacheManager);
        }else{
            closeSplash(handler,vpSplashContent,lly_dots);
        }
    }*/
    /*判断是否第一次加载*/

    private void initViewPage( ){
        vpSplashContent.setVisibility(View.VISIBLE);
        lly_dots.setVisibility(View.VISIBLE);
        viewList= new ArrayList<View>();
        for(int i = 0;i<imgSize.length;i++) {

            ImageView iv = new ImageView(activity);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(imgSize[i]);
            viewList.add(iv);
            ImageView iv_dot = new ImageView(activity);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(8,
                    8);
            lp.setMargins(4, 0, 4, 0);
            iv_dot.setLayoutParams(lp);
            if (i == 0) {
                iv_dot.setImageResource(R.drawable.point_skyblue);
            } else {
                iv_dot.setImageResource(R.drawable.point_wheat);
            }
            lly_dots.addView(iv_dot);

        }
        lly_dots.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        adapter = new SplashViewPageAdapter(activity,viewList);
        vpSplashContent.setAdapter(adapter);
        vpSplashContent.addOnPageChangeListener(new MyPageChangeListener());
    }
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {




        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setDots(position);
            if(position == 2){
                viewList.get(position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        xcCacheManager.writeCache("isNewApp","yes");
                        startMainActivity();
                        activity.finish();
                    }
                });
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /*设置小圆点*/
    private void setDots(int selectedPosition) {
        for (int i = 0; i < lly_dots.getChildCount(); i++) {
            if (i == selectedPosition) {
                ((ImageView) (lly_dots.getChildAt(i))).setImageResource(R.drawable.point_skyblue);
            } else {
                ((ImageView) (lly_dots.getChildAt(i))).setImageResource(R.drawable.point_wheat);
            }
        }
    }

    //关闭过渡页
    private void closeSplash(Handler handler,ViewPager vpSplashContent,LinearLayout lly_dots){

        vpSplashContent.setVisibility(View.INVISIBLE);
        lly_dots.setVisibility(View.INVISIBLE);
        Message message = handler.obtainMessage();
        message.what = 0;
        handler.sendMessageDelayed(message,2500);
    }
    /*设置小圆点*/
    public void startMainActivity(){
        Intent intent = new Intent(activity,MainActivity.class);
        activity.startActivity(intent);
    };


}
