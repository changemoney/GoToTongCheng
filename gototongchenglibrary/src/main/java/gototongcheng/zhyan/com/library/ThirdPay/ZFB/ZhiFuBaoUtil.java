package gototongcheng.zhyan.com.library.ThirdPay.ZFB;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;

import java.util.Map;


/**
 * Created by zhyan on 2017/2/25.
 */

public class ZhiFuBaoUtil {
    private Activity activity;
    public ZhiFuBaoUtil(Activity activity){
        this.activity = activity;
    }
      /*支付*/

/*    private void zhiFuBaoInit(){
    }*/


    /** 支付宝支付业务：入参app_id */
    public  final String APPID = "2017021605701384";

    /** 支付宝账户登录授权业务：入参pid值  签约合作者身份ID */
    public  final String PID = "2088621212377360";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public  final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public  final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC9t3skcCmB2xL0b3ZeC9p+/yvZC2RVlc+jbHayyh7BNX2kDN5PVd7CQo7sCMqUt4AUt6AzJO8OCEI7z1Qzzqq1Hld5oAW25j31aXth1S+AcqriquJ1iqXlEa8i+amkd/Ygh21JOEhK6y8+RZ/P2NqGPu5Lj94MeQovAA8ajzpC0l9nTXF/1aOEHI8QuQy0FRnNDi2G4F48pW+esrlbifJe1Qg9+Ot5Y+RbiAkNOJmFmevPZqFiPq/3V35J+23kskY7h+veTgVzSXE2Ef2o2XL3nfg6ODCo+FqKVC65j3UIqxqHD+zm+1kEbKCbjMTDeODIgEH2B/2Sa+jhe99dSQpfAgMBAAECggEAfmPXcS4cKPvXQ+tiE0OYXBUJlD6HqJD21Ow0o+YN2TAWtQ0xqe8OSaLn7o3o0GzzslmnK4uGi3WrEoeMlDsx9Rnysv+N7wyy6GVOop3RWKSL/hhUaHRnExXV9NoZT43v9jni7+3EE81dGwJtdp4E73zLaqG9D9gvlaJXbZ1P9iVjNMdLaNEhM6DJtD4uZhSYouPxEqcGh7IyDe4zFtyh/houAB+rIzjQyJFivNi6UYVensnUPRoB33OzAY2s8K9aTscRFqu2e5hPC1Nk15bgmULda08BjCuXHINDMCH8N/PNVq3mb+GKPn0FhGXHXmQkmFyV7bjLgMUd1pBMQaFG2QKBgQD5RDYm6vho5YtVVKYNNS71FD3qSFzYKk4aPqqLm9OfU6rEzoTtQ4oHxv3QliZswa/N1F5Le0xefB5A2Q/XM11iknwBC1pZsxLjOIqer76H7p36CEpifxgjKo1ldrWAmNRQbD/Ve+Nh+jaG+9XgVxzWKTF3NJqxz9q1SRAhDQ/tmwKBgQDC13TkkfbqjoNzV4qx1wH3e7hGoTYIU+akKQm8/YT8L3bQlojIbqrO5JXhiJyGkSbCz3KV1KnFaK+o1rXCmGwetZ2KVpLZMKZpebrNqV76lX64NACHIkOvvgucrIIPoajrMde8kqFfPMf9+cearcklm73mok/NS7ydutmGKpJEjQKBgApeQfpgb7C9S4xjFv0ZpzhefSzPkb0UFWeWD7X651SLQGErTfAojQdBybDxbeu8KrnfCYUOPeD4PLHp1XTYGBxPh4Trmfh9MF3vLiTnnn8896k8ywtJyhE8ZeFAjNAqizpFJmqU2qJqq/X1Z7ILLZHi6MHSmFSpFqnPvjIBvxXdAoGAJDC68J3AHE8+UVVIWMIUBnD/iHb3P1K+O2239lestN7oFdRwMbKEnYtN36dtU31wkqzPns7yfc11G0pOVofTRVz8ly5/dkIYFu1aakr0bl0IPZTDKFaP9fu50m8kdVzUsGMArHEb8kGq8BzOG4f+HbMw8b7iTcKslSuYrdgIdR0CgYEA8kvu0N7h+mDxlWsFXlxyhLnYfXQ8uQkEgMWTcyYj/BaHB5Oq/GrhSR2liSOVp/x9Ad6Bpxv9vyjcxsqxUPIrr8RVFKgwyMpFlDQDQqmx5vESSP0Jm+uJhGU95sfiYrdftsZrzdIMBkjUN5tim7eTWY+7xWs5RxFYjLwon79Gmx0=";
    public  final String RSA_PRIVATE = "";

    private  final int SDK_PAY_FLAG = 1;
    private  final int SDK_AUTH_FLAG = 2;

    // 签约卖家支付宝账号
    String SELLER = "zttcapp@163.com";
    /*支付宝*/
    public OnPaySuccessfulListener onPaySuccessfulListener;

    public interface OnPaySuccessfulListener{
        public void isSuccessful(boolean isSuccessful);
    }
    public void setOnPaySuccessfulListener(OnPaySuccessfulListener onPaySuccessfulListener1){
        onPaySuccessfulListener = onPaySuccessfulListener1;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                        if(onPaySuccessfulListener != null){
                            onPaySuccessfulListener.isSuccessful(true);
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
                        if(onPaySuccessfulListener != null){
                            onPaySuccessfulListener.isSuccessful(false);
                        }
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(activity,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(activity,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };






    /**
     * 支付宝支付业务
     *
     * @param v
     */
    public void payV2(View v,String goodsName,String price,String outTradeNo) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            activity.finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        /*price = "0.01";*/
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,goodsName,price,outTradeNo);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        /*final String orderInfo = getOrderInfo("走兔商品名称","走兔商品详情","0.01");*/
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }









/*
    *//**
     * 支付宝账户授权业务
     *
     * @param v
     *//*
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        *//**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         *//*
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(activity);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }*/

    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(activity);
        String version = payTask.getVersion();
        Toast.makeText(activity, version, Toast.LENGTH_SHORT).show();
    }
/*
*//**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v*//*

    public void h5Pay(View v) {
        Intent intent = new Intent(activity, H5PayDemoActivity.class);
        Bundle extras = new Bundle();
   *//*     *
         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
         * 商户可以根据自己的需求来实现*//*

        String url = "http://www.gotogoto.com";
        // url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
        extras.putString("url", url);
        intent.putExtras(extras);
        activity.startActivity(intent);
    }*/
}
