package com.zhyan.gototongcheng.Main.Login.UserReg;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.Main.BaseController;
import com.zhyan.gototongcheng.NetWork.UserSettingNetWorks;
import com.zhyan.gototongcheng.Utils.MOBSMSSDKUtil;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import gototongcheng.zhyan.com.library.Bean.UserRegBean;
import gototongcheng.zhyan.com.library.Utils.PhoneFormatCheckUtils;
import rx.Observer;

/**
 * Created by admin on 2017/3/24.
 */

public class UserRegActivityController extends BaseController {
    /*验证码秒数*/
    int second = 0;
    /*验证码秒数*/

    MOBSMSSDKUtil mobsmssdkUtil ;
    private EditText etUserRegContentTel,etUserRegContentPass;
    private TextView tvUserRegContentSMSec;
    public UserRegActivityController(Activity activity1, EditText tel, EditText pass, TextView textView){
        activity = activity1;
        etUserRegContentTel = tel;
        etUserRegContentPass = pass;
        tvUserRegContentSMSec = textView;
        init();

    }
    public void init(){
        mobsmssdkUtil = new MOBSMSSDKUtil(activity);
        mobSMSRegister();
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
                regSubmit();

            }
            //已发送验证码
            else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE)
            {
                /*Toast.makeText(activity, "验证码已经发送", Toast.LENGTH_SHORT).show();*/

            } else
            {
                ((Throwable) data).printStackTrace();

            }
        }if(result==SMSSDK.RESULT_ERROR)
        {
            Toast.makeText(activity, "验证码输入错误", Toast.LENGTH_LONG).show();
            try {
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
    /*注册信息*/
    private void regSubmit(){
        String tel = etUserRegContentTel.getText().toString();
        String pass = etUserRegContentPass.getText().toString();
        UserSettingNetWorks userSettingNetWorks = new UserSettingNetWorks();
        userSettingNetWorks.userRegToNet(tel,pass,new Observer<UserRegBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(activity,"网络君被孤立啦！！"+e,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNext(UserRegBean userReg) {

                Toast.makeText(activity,""+userReg.getResult(),Toast.LENGTH_LONG).show();
                activity.finish();
            }
        });
    }
    /*注册信息*/
    /*判断是否正确输入手机号码*/
    public boolean isPhoneNum(){
        String tel = etUserRegContentTel.getText().toString();

        if(tel == null){
            return false;
        }
        PhoneFormatCheckUtils phoneFormatCheckUtils = new PhoneFormatCheckUtils();
        return  phoneFormatCheckUtils.isPhoneLegal(tel);
    }
    /*判断是否正确输入手机号码*/


    public void onDestroy(){

        SMSSDK.unregisterAllEventHandler();


    }
}
