package com.zhyan.gototongcheng.Main.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;
import com.zhyan.gototongcheng.Main.BaseController;
import com.zhyan.gototongcheng.Main.Login.UserReg.UserRegActivity;
import com.zhyan.gototongcheng.NetWork.UserSettingNetWorks;
import com.zhyan.gototongcheng.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import gototongcheng.zhyan.com.library.Bean.UserLoginBean;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import rx.Observer;

/**
 * Created by admin on 2017/3/24.
 */

public class LoginActivityController extends BaseController  {




    private XCCacheManager mCacheManager;
    /*缓存*/

    /*注册页面*/
    @BindView(R.id.rly_login_content_reg)
    RelativeLayout rlyLoginContentReg;
    @OnClick(R.id.rly_login_content_reg)
    public void rlyLoginContentRegOnclick(){
        Intent intent = new Intent(activity,UserRegActivity.class);
        activity.startActivity(intent);
    }
    /*注册页面*/
    /*返回页面*/
    @BindView(R.id.rly_login_topbar_leftmenu_back)
    RelativeLayout rlyLoginTopBarLeftMenuBack;
    @OnClick(R.id.rly_login_topbar_leftmenu_back)
    public void rlyLoginTopBarLeftMenuBackOnclick(){
        activity.finish();
    }
    /*返回页面*/
    /*手机号码*/
    @BindView(R.id.et_login_content_tel)
    EditText etLoginContentTel;
    /*手机号码*/
    /*密码*/
    @BindView(R.id.et_login_content_pass)
    EditText etLoginContentPass;
    /*密码*/
    /*服务条款*/
    @BindView(R.id.tv_login_content_serviceitem)
    TextView tvLoginContentServiceItem;
    @OnClick(R.id.tv_login_content_serviceitem)
    public void tvLoginContentServiceItemOnclick(){
        /*Intent intent = new Intent(this,ServiceItemActivity.class);
        startActivity(intent);*/
    }
    /*服务条款*/
    /*登录*/
    /*正常登录*/
    @BindView(R.id.rly_login_content_loginsubmit)
    RelativeLayout rlyLoginContentLoginSubmit;
    @OnClick(R.id.rly_login_content_loginsubmit)
    public void rlyLoginContentLoginSubmitOnclick(){
        loginSubmit();
    }

      /*登录提交*/

    private void loginSubmit(){

        String tel = etLoginContentTel.getText().toString();
        String pass = etLoginContentPass.getText().toString();

        UserSettingNetWorks userSettingNetWorks = new UserSettingNetWorks();
        userSettingNetWorks.userLoginToNet(tel, pass, new Observer<UserLoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(activity,"网络君凯旋失败啦！！快检查你的账号和密码吧"+e,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(UserLoginBean userLogin) {

                /*Toast.makeText(getBaseContext(),"usid"+userLogin.getUserUsid(),Toast.LENGTH_LONG).show();*/
                if(userLogin.getUserName() != null){
                    XCCacheSavename xcCacheSavename = new XCCacheSavename();
                    mCacheManager.writeCache(xcCacheSavename.name,userLogin.getUserName());
                    mCacheManager.writeCache(xcCacheSavename.phone,userLogin.getUserName());
                    mCacheManager.writeCache(xcCacheSavename.usid,userLogin.getUserUsid());
                    System.out.println("usid:"+userLogin.getUserUsid());
                    mCacheManager.writeCache(xcCacheSavename.loginStatus,"yes");
                    Toast.makeText(activity,""+userLogin.getResult(),Toast.LENGTH_LONG).show();
                    activity.finish();
                }else{

                    Toast.makeText(activity,""+userLogin.getResult(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    /*正常登录*/


    public LoginActivityController(Activity activity1){
        activity = activity1;
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this,activity);
        mCacheManager = XCCacheManager.getInstance(activity);

    }



}
