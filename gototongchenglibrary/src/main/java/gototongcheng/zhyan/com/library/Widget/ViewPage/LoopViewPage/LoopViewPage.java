package gototongcheng.zhyan.com.library.Widget.ViewPage.LoopViewPage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.widget.LinearLayout.LayoutParams;
import gototongcheng.zhyan.com.library.R;

/**
 * Created by admin on 2017/4/1.
 */

public class LoopViewPage  implements ViewPager.OnPageChangeListener {

    private Context context;

    private List<ImageView> imageViewList;
  /*  private TextView tvDescription;*/
    private LinearLayout llPoints;
    private String[] imageDescriptions;
    private int previousSelectPosition = 0;
    private ViewPager mViewPager;
    private boolean isLoop = true;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
    };
    public LoopViewPage(Context context1,ViewPager mViewPager1,LinearLayout llPoints1){
        context = context1;
        mViewPager = mViewPager1;
        llPoints = llPoints1;
        initView();
        init();

    }
    private void init(){

        // 自动切换页面功能
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (isLoop) {
                    SystemClock.sleep(2000);
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    private void initView(){
  /*      mViewPager = (ViewPager) context.findViewById(R.id.viewpager);
        tvDescription = (TextView) context.findViewById(R.id.tv_image_description);
        llPoints = (LinearLayout) context.findViewById(R.id.ll_points);*/

        prepareData();

        ViewPagerAdapter adapter = new ViewPagerAdapter();
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);

        /*tvDescription.setText(imageDescriptions[previousSelectPosition]);*/
        llPoints.getChildAt(previousSelectPosition).setEnabled(true);

        /**
         * 2147483647 / 2 = 1073741820 - 1
         */
        int n = Integer.MAX_VALUE / 2 % imageViewList.size();
        int itemPosition = Integer.MAX_VALUE / 2 - n;

        mViewPager.setCurrentItem(itemPosition);
    }


    private void prepareData() {
        imageViewList = new ArrayList<ImageView>();
        int[] imageResIDs = getImageResIDs();
        imageDescriptions = getImageDescription();

        ImageView iv;
        View view;
        for (int i = 0; i < imageResIDs.length; i++) {
            iv = new ImageView(context);
            iv.setBackgroundResource(imageResIDs[i]);
            imageViewList.add(iv);

            // 添加点view对象
            view = new View(context);
            view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.loop_point_selector));
            LayoutParams lp = new LayoutParams(10, 10);
            lp.leftMargin = 10;
            view.setLayoutParams(lp);
            view.setEnabled(false);
            llPoints.addView(view);
        }
    }


    private int[] getImageResIDs() {
        return new int[]{
                R.drawable.activity_main_fragment_ad1,
                R.drawable.activity_main_fragment_ad2,
                R.drawable.activity_main_fragment_ad3,
                R.drawable.activity_main_fragment_ad4
        };
    }

    private String[] getImageDescription() {
        return new String[]{
                "第一个引导页面",
                "第二个引导页面",
                "第三个引导页面",
                "第四个引导页面"
        };
    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        /**
         * 判断出去的view是否等于进来的view 如果为true直接复用
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        /**
         * 销毁预加载以外的view对象, 会把需要销毁的对象的索引位置传进来就是position
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViewList.get(position % imageViewList.size()));
        }

        /**
         * 创建一个view
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            /*container.removeAllViews();*/
            container.addView(imageViewList.get(position % imageViewList.size()));
            return imageViewList.get(position % imageViewList.size());
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 改变图片的描述信息
       /* tvDescription.setText(imageDescriptions[position % imageViewList.size()]);*/
        // 切换选中的点
        llPoints.getChildAt(previousSelectPosition).setEnabled(false);	// 把前一个点置为normal状态
        llPoints.getChildAt(position % imageViewList.size()).setEnabled(true);		// 把当前选中的position对应的点置为enabled状态
        previousSelectPosition = position  % imageViewList.size();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    protected void onDestroy() {
        isLoop = false;
    }
}
