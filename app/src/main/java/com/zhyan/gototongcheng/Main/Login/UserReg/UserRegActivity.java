package com.zhyan.gototongcheng.Main.Login.UserReg;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.BaseActivity;
import com.zhyan.gototongcheng.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/3/24.
 */

public class UserRegActivity extends BaseActivity{
    /*验证码秒数*/
    int second = 0;
    /*验证码秒数*/
    /*手机号码*/
    @BindView(R.id.et_userreg_content_tel)
    EditText etUserRegContentTel;
    /*手机号码*/
    /*密码*/
    @BindView(R.id.et_userreg_content_pass)
    EditText etUserRegContentPass;
    /*密码*/
    /*验证码*/
    @BindView(R.id.et_userreg_content_identity)
    EditText etUserRegContentIdentity;
    /*验证码*/


    /*注册提交*/
    @BindView(R.id.rly_userreg_content_regsubmit)
    RelativeLayout rlyUserRegContentRegSubmit;
    /*注册提交点击事件*/
    @OnClick(R.id.rly_userreg_content_regsubmit)
    public void rlyUserRegContentRegSubmitOnclick(){
        if(userRegController.isPhoneNum()) {
            String tel = etUserRegContentTel.getText().toString();
            String identity = etUserRegContentIdentity.getText().toString();
            if(identity.isEmpty()){
                Toast.makeText(this,"请输入验证码",Toast.LENGTH_LONG).show();
            }
            try {
                userRegController.mobsmssdkUtil.confirmVerifSubmit(tel, identity);
            }catch (Exception e){

            }
        }
    }
    /*判断是否正确输入手机号码*/
/*    private boolean isPhoneNum(){
        String tel = etUserRegContentTel.getText().toString();
        PhoneFormatCheckUtils phoneFormatCheckUtils = new PhoneFormatCheckUtils();
        return  phoneFormatCheckUtils.isPhoneLegal(tel);
    }*/
    /*判断是否正确输入手机号码*/
    /*注册提交点击事件*/
    /*注册提交*/

    /*查看服务协议条款*/
    @BindView(R.id.tv_userreg_content_serviceitem)
    TextView tvUserRegContentServiceItem;

    /*查看服务协议条款*/

    /*获取验证码*/
    @BindView(R.id.rly_userreg_content_smsec)
    RelativeLayout rlyUserRegContentSMSec;
    /*点击获取验证码*/
    @OnClick(R.id.rly_userreg_content_smsec)
    public void rlyUserRegContentSMSecOnclick(){

        if(userRegController.isPhoneNum()){
             /*第一次点击倒计时*/
            if(second == 0){
                second = 60;
                beginTimeing();
            }else{
                Toast.makeText(this,"亲，验证码已发送，请耐心等待。。",Toast.LENGTH_LONG).show();
            }
        /*第一次点击倒计时*/
        }else{
            Toast.makeText(this,"亲，请输入正确的手机号码。。",Toast.LENGTH_LONG).show();
        }

    }
    /*点击获取验证码*/
    /*获取验证码*/
    /*验证码倒计时秒数*/
    @BindView(R.id.tv_userreg_content_smsec)
    TextView tvUserRegContentSMSec;
    /*验证码倒计时秒数*/

    /*回退上一页*/
    @BindView(R.id.rly_userreg_topbar_leftmenu_back)
    RelativeLayout rlyUserRegTopBarLeftMenuBack;
    @OnClick(R.id.rly_userreg_topbar_leftmenu_back)
    public void rlyUserRegTopBarLeftMenuBackOnclick(){
        finish();
    }

    /*回退上一页*/

    UserRegActivityController userRegController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_login_userreg_lly);
        init();
    }
    @Override
    public void init() {
        ButterKnife.bind(this);
        userRegController = new UserRegActivityController(this,etUserRegContentTel,etUserRegContentPass,tvUserRegContentSMSec);

    }

    public void beginTimeing(){
        String tel = etUserRegContentTel.getText().toString();
        userRegController.mobsmssdkUtil.getVerificationCode(tel);
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
                    tvUserRegContentSMSec.setText(Integer.toString(second));
                    second--;
                }else{
                    tvUserRegContentSMSec.setText("");
                    tvUserRegContentSMSec.setHint("获取验证码");
                }
                System.out.println("receive...."+second);
            }
        };
    };

    protected void onDestroy(){
        super.onDestroy();

        userRegController.onDestroy();
    }
}
