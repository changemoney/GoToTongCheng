package com.zhyan.gototongcheng.NetWork;

import com.zhyan.gototongcheng.NetWork.Base.BaseNetWork;

import gototongcheng.zhyan.com.library.Bean.BaseBean;
import gototongcheng.zhyan.com.library.Bean.ThirdLoginBean;
import gototongcheng.zhyan.com.library.Bean.UserLoginBean;
import gototongcheng.zhyan.com.library.Bean.UserRegBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
/**
 * Created by admin on 2017/2/21.
 */

public class UserSettingNetWorks extends BaseNetWork {

    protected  final NetService service = getRetrofit().create(NetService.class);
    private interface NetService{
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";
        /*用户注册*/
        //GET请求
        @GET("users/appreg.do")
        Observable<UserRegBean> userReg(@Query("userName") String tel, @Query("userPassword") String pass);

        //用户登录
        @GET("users/applogin.do")
        Observable<UserLoginBean> userLogin(@Query("userName") String tel, @Query("userPassword") String pass);

        /*用户退出*/
        @GET("users/appexit.do")
        Observable<BaseBean> userExit();
        /*用户退出*/

        /*第三方qq登陆*/
        @GET("users/applogin1.do")
        Observable<ThirdLoginBean> thirdLoginQQ(@Query("clientQqid") String clientQqid);
        /*第三方weixin登陆*/
        @GET("users/applogin1.do")
        Observable<ThirdLoginBean> thirdLoginWeiXin(@Query("clientWeixinid") String clientWeixinid);

        /*第三方登录qq注册*/
        @GET("users/applogin2.do")
        Observable<ThirdLoginBean> thirdLoginQQReg(@Query("userName")String userName,@Query("clientQqid") String clientQqid);
        /*第三方登录weixin注册*/
        @GET("users/applogin2.do")
        Observable<ThirdLoginBean> thirdLoginWeiXinReg(@Query("userName")String userName,@Query("clientWeixinid") String clientQqid);
    }

    public  void userRegToNet(String userName, String userPassword, Observer<UserRegBean> observer){
        setSubscribe(service.userReg(userName, userPassword),observer);
    }
    public  void userLoginToNet(String userName, String userPassword, Observer<UserLoginBean> observer){
        setSubscribe(service.userLogin(userName, userPassword),observer);
    }
    public void userExit(Observer<BaseBean> observer){
        setSubscribe(service.userExit(),observer);
    }
    public void thirdLoginQQ(String clientQqid,Observer<ThirdLoginBean> observer){
        setSubscribe(service.thirdLoginQQ(clientQqid),observer);
    }
    public void thirdLoginWeiXin(String clientWeixinid,Observer<ThirdLoginBean> observer){
        setSubscribe(service.thirdLoginWeiXin(clientWeixinid),observer);
    }
    public void thirdLoginQQReg(String userName,String clientWeixinid,Observer<ThirdLoginBean> observer){
        setSubscribe(service.thirdLoginQQReg(userName,clientWeixinid),observer);
    }
    public void thirdLoginWeiXinReg(String userName,String clientWeixinid,Observer<ThirdLoginBean> observer){
        setSubscribe(service.thirdLoginWeiXinReg(userName,clientWeixinid),observer);
    }
}
