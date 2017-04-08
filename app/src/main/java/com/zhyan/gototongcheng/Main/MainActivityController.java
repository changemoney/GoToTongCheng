package com.zhyan.gototongcheng.Main;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.Main.AddressManage.AddressManageActivity;
import com.zhyan.gototongcheng.Main.Login.LoginActivity;
import com.zhyan.gototongcheng.Main.MyOrder.MyOrderActivity;
import com.zhyan.gototongcheng.NetWork.UserSettingNetWorks;
import com.zhyan.gototongcheng.R;

import net.tsz.afinal.FinalBitmap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Bean.BaseBean;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import gototongcheng.zhyan.com.library.Utils.PhoneFormatCheckUtils;
import gototongcheng.zhyan.com.library.Widget.ImageView.CircleImageView;
import gototongcheng.zhyan.com.library.Widget.ImageView.RoundImageView;
import rx.Observer;

/**
 * Created by admin on 2017/3/23.
 */

public class MainActivityController extends BaseController{

    public MainActivityController(Activity activity1){
        activity = activity1;
        init();

    }
    @Override
    public void init(){
        ButterKnife.bind(this,activity);


      /*  initAfterLogin();*/
    }
    private LayoutInflater inflater;

    /*侧滑菜单设置*/
    @BindView(R.id.rly_main_leftmenu_setting)
    RelativeLayout rlyMainLeftMenuSetting;
    @OnClick(R.id.rly_main_leftmenu_setting)
    public void rlyMainLeftMenuSettingOnClick(){
/*        Intent intent = new Intent(this,SettingActivity.class);
        activity.startActivity(intent);*/
        Toast.makeText(activity,"开发中...",Toast.LENGTH_SHORT).show();
    }
    /*侧滑菜单设置*/

    /*活动页面*/
    @BindView(R.id.rly_main_leftmenu_activity)
    RelativeLayout rlyMainLeftMenuActivity;
    @OnClick(R.id.rly_main_leftmenu_activity)
    public void rlyMainLeftMenuActivityOnClick(){
/*        Intent intent = new Intent(this,MessageCenterActivity.class);
        activity.startActivity(intent);*/
        Toast.makeText(activity,"开发中...",Toast.LENGTH_SHORT).show();
    }
    /*活动页面*/
    /*我的订单*/
    @BindView(R.id.rly_main_leftmenu_myorder)
    RelativeLayout rlyMainLeftMenuMyOrder;
    @OnClick(R.id.rly_main_leftmenu_myorder)
    public void rlyMainLeftMenuMyOrderOnclick(){
        Intent intent = new Intent(activity,MyOrderActivity.class);
        activity.startActivity(intent);
    }
    /*我的订单*/
    /*我的钱包*/
    @BindView(R.id.rly_main_leftmenu_mywallet)
    RelativeLayout rlyMainLeftMenuMyWallet;
    @OnClick(R.id.rly_main_leftmenu_mywallet)
    public void rlyMainLeftMenuMyWalletOnclick(){
/*        Intent intent = new Intent(this,MessageCenterActivity.class);
        activity.startActivity(intent);*/
        Toast.makeText(activity,"开发中...",Toast.LENGTH_SHORT).show();
    }
    /*
    我的钱包*/
    /*地址管理*/
    @BindView(R.id.rly_main_leftmenu_addressmanage)
    RelativeLayout rlyMainLeftMenuAddressManage;
    @OnClick(R.id.rly_main_leftmenu_addressmanage)
    public void rlyMainLeftMenuAddressManageOnclick(){
        XCCacheSavename xcCacheSavename = new XCCacheSavename();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        xcCacheManager.writeCache(xcCacheSavename.addressManageType,"main");
        Intent intent = new Intent(activity,AddressManageActivity.class);
        activity.startActivity(intent);
    }

    /*地址管理*/

    /*消息中心*/

    @OnClick(R.id.rly_main_leftmenu_message)
    public void rlyMainLeftMenuMessageOnclick(){
/*        Intent intent = new Intent(this,MessageCenterActivity.class);
        activity.startActivity(intent);*/
        Toast.makeText(activity,"开发中...",Toast.LENGTH_SHORT).show();
    }

    /*消息中心*/
    @BindView(R.id.rly_main_leftmenu_message)
    RelativeLayout rlyMainLeftMenuMessage;
    @BindView(R.id.rly_main_topbar_leftmenu)
    RelativeLayout rlyLeftMenu;

    /*登录*/
    @BindView(R.id.lly_main_leftmenu_login)
    LinearLayout llyMainLeftMenuLogin;
    @OnClick(R.id.lly_main_leftmenu_login)
    public void llyMainLeftMenuLoginOnclick(){
        XCCacheSavename xcCacheSavename =new XCCacheSavename();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        String isLogin = xcCacheManager.readCache(xcCacheSavename.loginStatus);
        if((isLogin != null)&&(isLogin.equals("yes"))){

        }else {
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
        }
    }
    /*登录*/
    @BindView(R.id.dly_main_activity)
    DrawerLayout dlyMainActivity;
    /*退出登录*/
    @BindView(R.id.rly_main_leftmenu_exitlogin)
    RelativeLayout rlyMainLeftMenuExitLogin;
    @OnClick(R.id.rly_main_leftmenu_exitlogin)
    public void rlyMainLeftMenuExitLoginOnclick(){
        UserSettingNetWorks userSettingNetWorks = new UserSettingNetWorks();
        userSettingNetWorks.userExit(new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                Toast.makeText(activity,baseBean.getResult(),Toast.LENGTH_SHORT).show();
                exitLogin();
            }
        });

    }
    /*退出登录*/
   /* @BindView(R.id.civ_main_leftmenu_headimg)
    ImageView civMainLeftMenuHeadImg;*/
    /*登录头像 名称*/
    @BindView(R.id.riv_main_leftmenu_headimg)
    RoundImageView rivMainLeftMenuHeadImg;
    @BindView(R.id.tv_main_leftmenu_name)
    TextView tvMainLeftMenuName;
    /*登录头像 名称*/









    /*侧边栏*/

    @OnClick(R.id.rly_main_topbar_leftmenu)
    public void leftMenuOnClick(){
        dlyMainActivity.openDrawer(Gravity.LEFT);
        /*Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();*/

    }
    /*侧边栏*/


    /*登陆以后初始化名字*/
    public void initAfterLogin(){
        try{
            XCCacheSavename xcCacheSavename = new XCCacheSavename();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
            String loginStatus = xcCacheManager.readCache(xcCacheSavename.loginStatus);
            if(loginStatus != null) {
                if (loginStatus.equals("yes")) {

                    String userName = xcCacheManager.readCache(xcCacheSavename.name);

                    if (userName != null) {
                    /*Toast.makeText(this,"initAfterLogin:",Toast.LENGTH_LONG).show();*/
                    /*if()*/
                        PhoneFormatCheckUtils phoneFormatCheckUtils = new PhoneFormatCheckUtils();
                        if ((phoneFormatCheckUtils.isNumber(userName)) && (userName.length() > 9)) {
                             userName = phoneFormatCheckUtils.telReplaceMiddleByStar(userName);
                            tvMainLeftMenuName.setText(userName);
                        }

                        String headImgUrl = xcCacheManager.readCache(xcCacheSavename.headUrl);
                        if ((headImgUrl != null) && (!headImgUrl.isEmpty())) {

                            FinalBitmap finalBitMap = null;
                            finalBitMap = FinalBitmap.create(activity);
                            finalBitMap.display(rivMainLeftMenuHeadImg, headImgUrl);


                        }

                    }
                }
            }
        }catch (Exception e){

        }
    }
    /*登陆以后初始化名字*/

    /*退出登录后的名字和头像*/
    private void exitLogin(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheSavename xcCacheSavename = new XCCacheSavename();
        xcCacheManager.writeCache(xcCacheSavename.loginStatus,"no");
        xcCacheManager.writeCache(xcCacheSavename.usid,"");
        tvMainLeftMenuName.setText("请登录");
        rivMainLeftMenuHeadImg.setImageResource(R.drawable.activity_main_leftmenu_goto_head);
    }
    /*退出登录后的名字和头像*/
}
