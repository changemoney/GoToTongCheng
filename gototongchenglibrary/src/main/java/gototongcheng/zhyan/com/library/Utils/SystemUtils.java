package gototongcheng.zhyan.com.library.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by zhyan on 2017/2/16.
 */

public class SystemUtils {
    private Activity activity;
    WindowManager wm;
    int width;
    int height;
    public SystemUtils(Activity activity){
        this.activity = activity;
        wm = activity.getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }

    public int getWindowWidth(){
        return width;

    }
    public int getWindowHeight(){
        return height;
    }
}
