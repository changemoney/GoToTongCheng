package com.zhyan.gototongcheng.Main;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.TextView;

import com.zhyan.gototongcheng.BaseActivity;
import com.zhyan.gototongcheng.R;

public class MainActivity extends BaseActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    MainActivityController mainActivityController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lly);
        init();
        // Example of a call to a native method
  /*      TextView tv = (TextView) findViewById(R.id.sample_text);*/
/*        tv.setText(stringFromJNI());*/
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void init() {
        mainActivityController = new MainActivityController(this);
        initFragment();
    }







    /*初始化fragment*/
    private void initFragment(){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        // 动态增加Fragment
        MainFragment mainFragment = new MainFragment();
    /*    if(mainFragment != null){
            transaction.show(mainFragment);
        }else {
            transaction.add(R.id.main_content_lly, mainFragment, "content");
        }*/
        transaction.add(R.id.main_content_lly, mainFragment, "content");
        transaction.commit();
    }

    /*初始化fragment*/






    protected void onResume(){
        super.onResume();
        mainActivityController.initAfterLogin();
/*        Thread1 thread = new Thread1();
        thread.start();*/

 /*       MemoryUtils memoryUtils = new MemoryUtils();
        memoryUtils.cleanMemoryNoText(this);*/
    }

    class Thread1 extends Thread{

        public void run() {
        /*    Toast.makeText(getBaseContext(),"threadBrun:",Toast.LENGTH_LONG).show();*/
            mainActivityController.initAfterLogin();

        }
    };
}
