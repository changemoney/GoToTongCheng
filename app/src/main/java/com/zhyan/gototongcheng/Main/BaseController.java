package com.zhyan.gototongcheng.Main;

import android.app.Activity;
import android.view.View;

/**
 * Created by admin on 2017/3/24.
 */

public abstract class BaseController {
    protected Activity activity;
    protected View view;
    public abstract void init();
}
