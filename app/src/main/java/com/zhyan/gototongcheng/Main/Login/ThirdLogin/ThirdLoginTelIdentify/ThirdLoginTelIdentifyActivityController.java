package com.zhyan.gototongcheng.Main.Login.ThirdLogin.ThirdLoginTelIdentify;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.Main.BaseController;
import com.zhyan.gototongcheng.Main.Login.UserReg.UserRegActivity;
import com.zhyan.gototongcheng.NetWork.UserSettingNetWorks;
import com.zhyan.gototongcheng.R;
import com.zhyan.gototongcheng.Utils.MOBSMSSDKUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import gototongcheng.zhyan.com.library.Bean.ThirdLoginBean;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import gototongcheng.zhyan.com.library.Utils.PhoneFormatCheckUtils;
import rx.Observer;

/**
 * Created by admin on 2017/4/10.
 */

public class ThirdLoginTelIdentifyActivityController extends BaseController {
    /*验证码秒数*/
    int second = 0;
    /*验证码秒数*/
    @BindView(R.id.rly_main_login_thirdlogin_tel_identify_topbar_leftmenu_back)
    RelativeLayout rlyMainLoginThirdLoginTelIdentifyTopBarLeftMenuBack;
    @OnClick(R.id.rly_main_login_thirdlogin_tel_identify_topbar_leftmenu_back)
    public void rlyMainLoginThirdLoginTelIdentifyTopBarLeftMenuBackOnclick(){
        activity.finish();
    }


    @BindView(R.id.et_main_login_thirdlogin_tel_identify_userreg_content_identify)
    EditText etMainLoginThirdLoginTelUserRegContentIdentify;
    @BindView(R.id.rly_main_login_thirdlogin_tel_identify_userreg_content_getidentify)
    RelativeLayout rlyMainLoginThirdLoginTelIdentifyUserRegContentGetIdentity;
    @OnClick(R.id.rly_main_login_thirdlogin_tel_identify_userreg_content_getidentify)
    public void rlyMainLoginThirdLoginTelIdentifyUserRegContentGetIdentityOnclick(){
        getIdentify();
    }
    @BindView(R.id.rly_main_login_thirdlogin_tel_identify_reg_submit)
    RelativeLayout rlyMainLoginThirdLoginTelIdentifyRegSubmit;
    @OnClick(R.id.rly_main_login_thirdlogin_tel_identify_reg_submit)
    public void  rlyMainLoginThirdLoginTelIdentifyRegSubmitOnclick(){
        identifyVertify();
    }

    @BindView(R.id.tv_main_login_thirdlogin_tel_identify_content_smsec)
    TextView tvMainLoginThirdLoginTelIdentifyContentSmSec;

    MOBSMSSDKUtil mobsmssdkUtil ;
    private String tel = "";
    public ThirdLoginTelIdentifyActivityController(Activity activity1){
        activity = activity1;
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this,activity);
        mobsmssdkUtil = new MOBSMSSDKUtil(activity);
        mobSMSRegister();
        initTel();
    }
    private void initTel(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheSavename xcCacheSavename = new XCCacheSavename();
        tel = xcCacheManager.readCache(xcCacheSavename.thirdLoginRegTel);
    }
    private void mobSMSRegister(){
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                smsSDKResultComplete(event,result,data);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }

    public void smsSDKResultComplete(int event, int result, Object data){
        //回调完成
        if (result == SMSSDK.RESULT_COMPLETE)
        {
            //验证码验证成功
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
            {
               /* Toast.makeText(activity,"验证码验证成功",Toast.LENGTH_LONG).show();*/
                regSubmit();

            }
            //已发送验证码
            else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE)
            {
                /*Toast.makeText(activity, "验证码已经发送", Toast.LENGTH_SHORT).show();*/

            } else
            {
                ((Throwable) data).printStackTrace();
               /* Toast.makeText(activity,"data",Toast.LENGTH_LONG).show();*/
            }
        }if(result==SMSSDK.RESULT_ERROR)
        {
            /*Toast.makeText(activity, "验证码输入错误", Toast.LENGTH_LONG).show();*/
            try {
             /*   Toast.makeText(activity,"error",Toast.LENGTH_LONG).show();*/
                Throwable throwable = (Throwable) data;
                throwable.printStackTrace();
                JSONObject object = new JSONObject(throwable.getMessage());
                String des = object.optString("detail");//错误描述
                int status = object.optInt("status");//错误代码
                if (status > 0 && !TextUtils.isEmpty(des)) {
                    Toast.makeText(activity, des, Toast.LENGTH_LONG).show();
                    return;
                }

            } catch (Exception e) {
                //do something
            }
        }
    }


    /*判断是否正确输入手机号码*/
    public boolean isPhoneNum(){


        if(tel == null){
            return false;
        }
        PhoneFormatCheckUtils phoneFormatCheckUtils = new PhoneFormatCheckUtils();
        return  phoneFormatCheckUtils.isPhoneLegal(tel);
    }
    /*判断是否正确输入手机号码*/


    private void getIdentify(){
        if(isPhoneNum()){
             /*第一次点击倒计时*/
            if(second == 0){
                second = 60;
                beginTimeing();
            }else{
                Toast.makeText(activity,"亲，验证码已发送，请耐心等待。。",Toast.LENGTH_LONG).show();
            }
        /*第一次点击倒计时*/
        }else{
            Toast.makeText(activity,"亲，请输入正确的手机号码。。",Toast.LENGTH_LONG).show();
        }
    }
    public void beginTimeing(){
        /*String identify = etMainLoginThirdLoginTelUserRegContentIdentify.getText().toString();*/
        mobsmssdkUtil.getVerificationCode(tel);
        ThreadShow threadShow = new ThreadShow();
        Thread thread = new Thread(threadShow);
        thread.start();
    }

    // 线程类
    class ThreadShow implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (second > 0) {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    System.out.println("send...");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("thread error...");
                }
            }
        }
    }

    /*多线程开始倒计时*/
    // handler类接收数据
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if(second > 0) {
                    tvMainLoginThirdLoginTelIdentifyContentSmSec.setText(Integer.toString(second));
                    second--;
                }else{
                    tvMainLoginThirdLoginTelIdentifyContentSmSec.setText("");
                    tvMainLoginThirdLoginTelIdentifyContentSmSec.setHint("获取验证码");
                }
               /* System.out.println("receive...."+second);*/
            }
        };
    };

    private void identifyVertify(){
        if(isPhoneNum()) {

            String identity = etMainLoginThirdLoginTelUserRegContentIdentify.getText().toString();
            if(identity.isEmpty()){
                Toast.makeText(activity,"请输入验证码",Toast.LENGTH_LONG).show();
            }
            try {
                mobsmssdkUtil.confirmVerifSubmit(tel, identity);
            }catch (Exception e){

            }
        }
    }


    private void regSubmit(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        XCCacheSavename xcCacheSavename = new XCCacheSavename();
        String regType = xcCacheManager.readCache(xcCacheSavename.thirdLoginRegType);
        String thirdLoginUsid = xcCacheManager.readCache(xcCacheSavename.thirdLoginUsid);
        UserSettingNetWorks userSettingNetWorks = new UserSettingNetWorks();
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        System.out.print("\n regType:"+regType+" thirdLoginUsid:"+thirdLoginUsid);
        if((regType != null)&&(regType.equals("qq"))&&(thirdLoginUsid != null)){
            userSettingNetWorks.thirdLoginQQReg(tel, thirdLoginUsid, new Observer<ThirdLoginBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                   /* Toast.makeText(activity,""+e,Toast.LENGTH_LONG).show();*/
                }

                @Override
                public void onNext(ThirdLoginBean thirdLoginBean) {
                    if(thirdLoginBean.getResult().equals("登录成功")){
                        XCCacheSavename xcCacheSavename = new XCCacheSavename();
                        XCCacheManager xcCacheManager1 = XCCacheManager.getInstance(activity);
                        xcCacheManager1.writeCache(xcCacheSavename.name,thirdLoginBean.getUserName());
                        xcCacheManager1.writeCache(xcCacheSavename.phone,thirdLoginBean.getUserName());
                        xcCacheManager1.writeCache(xcCacheSavename.usid,thirdLoginBean.getUserUsid());
                       /* System.out.println("usid:"+userLogin.getUserUsid());*/
                        xcCacheManager1.writeCache(xcCacheSavename.loginStatus,"yes");
                        Toast.makeText(activity,""+thirdLoginBean.getResult(),Toast.LENGTH_LONG).show();
                        activity.finish();

                    }else{
                        Toast.makeText(activity,""+thirdLoginBean.getResult(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else if((regType != null)&&(regType.equals("weixin"))&&(thirdLoginUsid != null)){
            userSettingNetWorks.thirdLoginWeiXinReg(tel, thirdLoginUsid, new Observer<ThirdLoginBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(activity,""+e,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNext(ThirdLoginBean thirdLoginBean) {
                    if(thirdLoginBean.getResult().equals("登录成功")){
                        XCCacheSavename xcCacheSavename = new XCCacheSavename();
                        XCCacheManager xcCacheManager1 = XCCacheManager.getInstance(activity);
                        xcCacheManager1.writeCache(xcCacheSavename.name,thirdLoginBean.getUserName());
                        xcCacheManager1.writeCache(xcCacheSavename.phone,thirdLoginBean.getUserName());
                        xcCacheManager1.writeCache(xcCacheSavename.usid,thirdLoginBean.getUserUsid());
                       /* System.out.println("usid:"+userLogin.getUserUsid());*/
                        xcCacheManager1.writeCache(xcCacheSavename.loginStatus,"yes");
                        Toast.makeText(activity,""+thirdLoginBean.getResult(),Toast.LENGTH_LONG).show();
                        activity.finish();

                    }else{
                        Toast.makeText(activity,""+thirdLoginBean.getResult(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
       /* Toast.makeText(activity,"fail",Toast.LENGTH_LONG).show();*/
    }



}
