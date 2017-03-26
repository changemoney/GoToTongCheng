package gototongcheng.zhyan.com.library.Widget.TextView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 跑马灯效果
 * 出处http://mobile.51cto.com/design-377022.htm
 * Created by zhyan on 2017/2/11.
 * 其他效果的textview
 * https://github.com/hanks-zyh/HTextView
 * https://github.com/QQ986945193/DavidMarqueeControlProject
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 * @CSDN博客: http://blog.csdn.net/qq_21376985
 * @码云OsChina ：http://git.oschina.net/MCXIAOBING
 */

public class MarqueeText extends TextView implements Runnable {
    private int currentScrollX;// 当前滚动的位置
    private boolean isStop = false;
    private int textWidth;
    private boolean isMeasure = false;

    public MarqueeText(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MarqueeText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        if (!isMeasure) {// 文字宽度只需获取一次就可以了
            getTextWidth();
            isMeasure = true;
        }
    }

    /**
     * 获取文字宽度
     */
    private void getTextWidth() {
        Paint paint = this.getPaint();
        String str = this.getText().toString();
        textWidth = (int) paint.measureText(str);
    }
/*从左向右*/
/*    @Override
    public void run() {
        currentScrollX -= 1;// 滚动速度
        scrollTo(currentScrollX, 0);
        *//*scrollTo(0, currentScrollX);*//*
        if (isStop) {
            return;
        }
        if (getScrollX() <= -(this.getWidth())) {
            scrollTo(textWidth, 0);
            currentScrollX = textWidth;
//                        return;
        }
        postDelayed(this, 6);
    }*/
/*从左向右*/
    /*从右向左*/
    @Override
    public void run() {
        currentScrollX += 1;// 滚动速度
        scrollTo(currentScrollX, 0);
        if (isStop) {
            return;
        }
        if (getScrollX() >= textWidth) {
            scrollTo(-this.getWidth(), 0);
            currentScrollX = -this.getWidth();
        }
        postDelayed(this, 10);
    }


    /*从右向左*/
    // 开始滚动
    public void startScroll() {
        isStop = false;
        this.removeCallbacks(this);
        post(this);
    }

    // 停止滚动
    public void stopScroll() {
        isStop = true;
    }

    // 从头开始滚动
    public void startFor0() {
        /*currentScrollX = 0;*/
        currentScrollX = textWidth;
        startScroll();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        // TODO Auto-generated method stub
        super.setText(text, type);
        startScroll();
    }

    @Override
    public void destroyDrawingCache() {
        // TODO Auto-generated method stub
        super.destroyDrawingCache();

    }
}