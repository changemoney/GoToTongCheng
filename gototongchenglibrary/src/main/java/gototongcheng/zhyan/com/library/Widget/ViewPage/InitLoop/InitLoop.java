package gototongcheng.zhyan.com.library.Widget.ViewPage.InitLoop;


import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import gototongcheng.zhyan.com.library.R;
import gototongcheng.zhyan.com.library.Widget.ViewPage.InitLoop.listener.OnItemSelectedListener;

/**
 *
 * 最简单使用的轮播图片
 * http://download.csdn.net/detail/cjs1534717040/9421961
 * Created by admin on 2017/2/23.
 */

public class InitLoop {

    int[] pics = new int[] { R.drawable.activity_main_fragment_ad1, R.drawable.activity_main_fragment_ad2, R.drawable.activity_main_fragment_ad3, R.drawable.activity_main_fragment_ad4};
    //	static int[] pics = new int[] { R.drawable.pic_1, R.drawable.pic_2};
    long interval=3*1000;//广告切换间隔时间（毫秒）
    private ArrayList<View> views = new ArrayList<View>();

/*    @BindView(R.id.lvp_main_content)*/
    LoopViewPager mLoopViewPager;
/*    @BindView(R.id.lly_main_content)*/
    LinearLayout ll_dots;

    private View view;
    public InitLoop(View view, LoopViewPager mLoopViewPager1,LinearLayout ll_dots1){
        this.view = view;
        mLoopViewPager = mLoopViewPager1;
        ll_dots = ll_dots1;
    }


    public void init(){
/*
        ButterKnife.bind(this,view);*/
        cicleWheelInit();

    }
    public void setStop(){
        mLoopViewPager.setAutoChange(false);
    }
    public void setStart(){
        mLoopViewPager.setAutoChange(true);
    }

    /* 轮播初始化*/
    private void cicleWheelInit(){
        /*ll_board_viewpager= (LinearLayout) findViewById(R.id.ll_main_board_viewpager);*/

        /*ll_board_viewpager.addView(new MainPictureRotator(getActivity(),inflater,2500).initView(datas));*/

        for (int i = 0; i < pics.length; i++) {
            View v = View.inflate(view.getContext(), R.layout.view_page_initloop_item_pic, null);
            ImageView iv_pic = (ImageView) v.findViewById(R.id.iv_main_content_ad_item_pic);
            /*设置src*/
            iv_pic.setImageResource(pics[i]);
            /*iv_pic.setLayoutParams(params);*/
            /*设置background*/
           /* iv_pic.setBackgroundResource(pics[i]);*/
            /*设置background*/
            views.add(v);
            //动态生成小圆点
            ImageView iv_dot = new ImageView(view.getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(8,
                    8);
            lp.setMargins(4,0,4,0);

            /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);*/
            iv_dot.setLayoutParams(lp);


            if(i==0){
                iv_dot.setImageResource(R.drawable.point_skyblue);
            }else{
                iv_dot.setImageResource(R.drawable.point_wheat);
            }

            ll_dots.addView(iv_dot);
        }
        ll_dots.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        mLoopViewPager.setViewList(views);//给viewpager设置view列表
        mLoopViewPager.setAutoChange(true);//设置是否自动轮播
        mLoopViewPager.setAutoChangeTime(interval);//设置图片间隔时间
        mLoopViewPager.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void selected(int item, View view) {
                setDots(item);
            }
        });
    }
    private void setDots(int selectedPosition) {
        for (int i = 0; i < ll_dots.getChildCount(); i++) {
            if (i == selectedPosition) {
                ((ImageView) (ll_dots.getChildAt(i))).setImageResource(R.drawable.point_skyblue);
            } else {
                ((ImageView) (ll_dots.getChildAt(i))).setImageResource(R.drawable.point_wheat);
            }
        }
    }
}
