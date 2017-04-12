package com.zhyan.gototongcheng.Main.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;
import com.zhyan.gototongcheng.BaseActivity;
import com.zhyan.gototongcheng.Main.Login.ThirdLogin.ThirdLoginTelActivity;
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
import gototongcheng.zhyan.com.library.Bean.ThirdLoginBean;
import gototongcheng.zhyan.com.library.Bean.UserLoginBean;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import rx.Observer;

/**
 * 登录页面
 * Created by admin on 2017/3/23.
 * 友盟第三方登录
 * http://download.csdn.net/download/donkor_/9700844
 */

public class LoginActivity extends BaseActivity  implements Handler.Callback, PlatformActionListener{


    private  final int MSG_USERID_FOUND = 1;
    private  final int MSG_LOGIN = 2;
    private  final int MSG_AUTH_CANCEL = 3;
    private  final int MSG_AUTH_ERROR = 4;
    private  final int MSG_AUTH_COMPLETE = 5;
    private String thirdLoginType = "";
    /*第三方登录 qq 微信*/
    @BindView(R.id.rly_login_content_qqlogin)
    RelativeLayout rlyLoginContentQQLogin;
    @OnClick(R.id.rly_login_content_qqlogin)
    public void rlyLoginContentQQLoginOnclick(){
        //执行授权,获取用户信息
        thirdLoginType="qq";
        authorize(new QQ(this));
    }
    @BindView(R.id.rly_login_content_wxlogin)
    RelativeLayout rlyLoginContentWXLogin;
    @OnClick(R.id.rly_login_content_wxlogin)
    public void rlyLoginContentWXLoginOnclick(){
        thirdLoginType="weixin";
        authorize(new Wechat(this));
    }
    //执行授权,获取用户信息
    private void authorize(Platform plat) {
        /*Toast.makeText(activity,plat.getName()+" "+plat.getDb().getUserName()+" "+plat.getDb().getPlatformNname()+" "+plat.getDb().getUserIcon(),Toast.LENGTH_LONG).show();*/
        if (plat.isValid()) {
            String userId = plat.getDb().getUserId();
            if (!TextUtils.isEmpty(userId)) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                login(plat.getName(), userId, null);
                /*Toast.makeText(this,"this is in plat ",Toast.LENGTH_LONG).show();*/
                thirdLoginSuccessful(plat);
           /*     String name = mCacheManager.readCache("userName");
                String usid = mCacheManager.readCache("usid");
                String headUrl = mCacheManager.readCache("headUrl");
                String loginStatus = mCacheManager.readCache("loginStatus");

                Toast.makeText(this,name+" "+usid+" "+headUrl+" "+loginStatus,Toast.LENGTH_LONG).show();*/
                return;
            }
        }
 /*       String name = mCacheManager.readCache("userName");
        String usid = mCacheManager.readCache("usid");
        String headUrl = mCacheManager.readCache("headUrl");
        String loginStatus = mCacheManager.readCache("loginStatus");

        Toast.makeText(this,name+" "+usid+" "+headUrl+" "+loginStatus,Toast.LENGTH_LONG).show();*/
        /*Toast.makeText(this,"this is out plat ",Toast.LENGTH_LONG).show();*/
        plat.setPlatformActionListener(this);
        //true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(false);
        plat.showUser(null);
    }

    //发送登陆信息
    private void login(String plat, String userId, HashMap<String, Object> userInfo) {
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }
    /*第三方登录 qq 微信*/


    private XCCacheManager mCacheManager;
    LoginActivityController loginActivityController;
    private Handler handler;//定义handler进行登陆结果的回调
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
             /*友盟第三方登录*/
        ShareSDK.initSDK(this);
        /*友盟第三方登录*/
        setContentView(R.layout.activity_main_login_lly);
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        //注册handler
        handler = new Handler(this);
        mCacheManager = XCCacheManager.getInstance(this);
        loginActivityController = new LoginActivityController(this);

    }




    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND: {
                Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_LOGIN: {
                String text = getString(R.string.logining, msg.obj);
               /* Toast.makeText(this, text, Toast.LENGTH_SHORT).show();*/
            }
            break;
            case MSG_AUTH_CANCEL: {
                Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_ERROR: {
                Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_AUTH_COMPLETE: {
                Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
            }
            break;
        }

        return false;
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {

        /*Toast.makeText(this,"this is out complete"+action,Toast.LENGTH_LONG).show();*/
        if (action == Platform.ACTION_USER_INFOR) {
            //登录成功,获取需要的信息
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            login(platform.getName(), platform.getDb().getUserId(), res);
            /*finish();*/
            thirdLoginSuccessful(platform);
        }
    }

    @Override
    public void onError(Platform platform, int action, Throwable t) {
        Log.e("thirdLoginAction",""+action);
        Log.e("thirdLoginPlatform",""+platform);
        Log.e("thirdLoginError",""+t);
        if(action==Platform.ACTION_USER_INFOR){
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR,this);
        }
        t.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }


    }


    private void thirdLoginSuccessful(Platform platform){
        String usid = platform.getDb().getUserId();
        String openid = platform.getDb().getUserId() + "";
        String gender = platform.getDb().getUserGender();
        String head_url = platform.getDb().getUserIcon();
        String nickname = platform.getDb().getUserName();
        XCCacheSavename xcCacheSavename = new XCCacheSavename();
        mCacheManager.writeCache(xcCacheSavename.headUrl,head_url);
        mCacheManager.writeCache(xcCacheSavename.thirdLoginUsid,usid);
        UserSettingNetWorks userSettingNetWorks = new UserSettingNetWorks();
        if(thirdLoginType.equals("qq")) {
            userSettingNetWorks.thirdLoginQQ(usid, new Observer<ThirdLoginBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ThirdLoginBean thirdLoginBean) {
                    XCCacheSavename xcCacheSavename = new XCCacheSavename();
                    /*Toast.makeText(getBaseContext(), thirdLoginBean.getResult(), Toast.LENGTH_LONG).show();*/
                    if(thirdLoginBean.getResult().equals("需要绑定")){
                        Intent intent = new Intent(getBaseContext(), ThirdLoginTelActivity.class);
                        startActivity(intent);
                        mCacheManager.writeCache(xcCacheSavename.thirdLoginRegType,"qq");
                        finish();
                        return;
                    }

                    mCacheManager.writeCache(xcCacheSavename.name,thirdLoginBean.getUserName());
                    mCacheManager.writeCache(xcCacheSavename.usid,thirdLoginBean.getUserUsid());
                    mCacheManager.writeCache(xcCacheSavename.loginStatus,"yes");
                    finish();
                }
            });
        }else if(thirdLoginType.equals("weixin")){
            userSettingNetWorks.thirdLoginWeiXin(usid, new Observer<ThirdLoginBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ThirdLoginBean thirdLoginBean) {
                    /*Toast.makeText(getBaseContext(), thirdLoginBean.getResult(), Toast.LENGTH_LONG).show();*/
                    XCCacheSavename xcCacheSavename = new XCCacheSavename();
                    if(thirdLoginBean.getResult().equals("需要绑定")){
                        Intent intent = new Intent(getBaseContext(), ThirdLoginTelActivity.class);
                        startActivity(intent);
                        mCacheManager.writeCache(xcCacheSavename.thirdLoginRegType,"weixin");
                        finish();
                        return;
                    }

                    mCacheManager.writeCache(xcCacheSavename.name,thirdLoginBean.getUserName());
                    mCacheManager.writeCache(xcCacheSavename.usid,thirdLoginBean.getUserUsid());
                    mCacheManager.writeCache(xcCacheSavename.loginStatus,"yes");
                    finish();
                }
            });
        }


        /*Toast.makeText(this,openid+" "+gender+" "+head_url+" "+nickname,Toast.LENGTH_LONG).show();*/
        System.out.print("\n usid:"+usid+"openid:"+openid+" gender:"+gender+" head_url:"+head_url+" nickname:"+nickname);
        Log.i("third login",openid+" "+gender+" "+head_url+" "+nickname);
        System.out.println(openid+" "+gender+" "+head_url+" "+nickname);

    }


    @Override
    protected void onDestroy() {
//释放资源
        ShareSDK.stopSDK(this);
        Platform qq = ShareSDK.getPlatform(this, QQ.NAME);
        Platform wechat = ShareSDK.getPlatform(this, Wechat.NAME);
        Platform weibo = ShareSDK.getPlatform(this, SinaWeibo.NAME);
        if (qq.isValid()) {
            qq.removeAccount();
        }
        if (wechat.isValid()) {
            wechat.removeAccount();
        }
        if (weibo.isValid()) {
            weibo.removeAccount();
        }
        super.onDestroy();
    }

}
