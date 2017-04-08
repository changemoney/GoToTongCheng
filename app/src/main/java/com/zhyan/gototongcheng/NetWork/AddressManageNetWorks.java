package com.zhyan.gototongcheng.NetWork;

import com.zhyan.gototongcheng.NetWork.Base.BaseNetWork;

import java.util.List;

import gototongcheng.zhyan.com.library.Bean.BaseBean;
import gototongcheng.zhyan.com.library.Bean.ShopAddressListBean;
import gototongcheng.zhyan.com.library.Bean.UserAddressListBean;
import retrofit2.http.GET;
import retrofit2.http.Query;

import rx.Observable;
import rx.Observer;

/**
 * Created by admin on 2017/2/21.
 */

public class AddressManageNetWorks extends BaseNetWork {

    protected final NetService service = getRetrofit().create(NetService.class);

    private interface NetService {
        //设缓存有效期为1天
        final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
        //查询缓存的Cache-Control设置，使用缓存
        final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
        //查询网络的Cache-Control设置。不使用缓存
        final String CACHE_CONTROL_NETWORK = "max-age=0";
        /*用户查询订单列表*/

        /*商家地址添加*/
        @GET("clientaddr1/addrsave.do")
        Observable<BaseBean> addShopAddress(@Query("userUsid") String userUsid, @Query("clientaddr1Addr") String clientaddr1Addr, @Query("clientaddr1Lat") float clientaddr1Lat, @Query("clientaddr1Long") float clientaddr1Long, @Query("clientaddr1Isdefault") String clientaddr1Isdefault);

        /*商家地址添加*/
        /*商家地址列表查找*/
        @GET("clientaddr1/addrfind.do")
        Observable<List<ShopAddressListBean>> getShopAddrList(@Query("userUsid") String userUsid);
        /*商家地址列表查找*/

        /*商家地址删除*/
        @GET("clientaddr1/addrdelete.do")
        Observable<BaseBean> deleteShopAddress(@Query("userUsid") String usid, @Query("clientaddr1Things1") String clientaddr1Things1);
        /*商家地址删除*/

        /*商家地址修改*/
        @GET("clientaddr1/addrupdate.do")
        Observable<BaseBean> updateShopAddr(@Query("userUsid")String userUsid,@Query("clientaddr1Things1")String clientaddr1Things1,@Query("clientaddr1Addr")String clientaddr1Addr,@Query("clientaddr1Lat")float clientaddr1Lat,@Query("clientaddr1Long")float clientaddr1Long,@Query("clientaddr1Isdefault")String clientaddr1Isdefault);
        /*商家地址修改*/


        /*用户地址添加*/
        @GET("clientaddr/addrsave.do")
        Observable<BaseBean> addUserAddress(@Query("userUsid") String userUsid, @Query("clientaddrAddr") String clientaddrAddr, @Query("clientaddrLat") float clientaddrLat, @Query("clientaddrLong") float clientaddrLong, @Query("clientaddrIsdefault") String clientaddrIsdefault);
        /*用户地址添加*/
        /*用户地址列表查找*/
        @GET("clientaddr/addrfind.do")
        Observable<List<UserAddressListBean>> getUserAddress(@Query("userUsid") String userUsid);
        /*用户地址列表查找 484914c632db43e081b9a5eb75cc3971*/

        /*用户地址修改*/
        @GET("clientaddr/addrupdate.do")
        Observable<BaseBean> updateUserAddr(@Query("userUsid")String userUsid,@Query("clientaddrThings1")String clientaddrThings1,@Query("clientaddrAddr")String clientaddrAddr,@Query("clientaddrLat")float clientaddrLat,@Query("clientaddrLong")float clientaddrLong,@Query("clientaddrIsdefault")String clientaddrIsdefault);
        /*用户地址修改*/

        /*用户地址删除*/
        @GET("clientaddr/addrdelete.do")
        Observable<BaseBean> deleteUserAddress(@Query("userUsid") String usid, @Query("clientaddrThings1") String clientaddrThings1);
        /*用户地址删除*/
    }



    public void addShopAddress(String userUsid, String clientaddr1Addr, float clientaddr1Lat, float clientaddr1Long, String clientaddr1Isdefault, Observer<BaseBean> observer) {
        setSubscribe(service.addShopAddress(userUsid, clientaddr1Addr, clientaddr1Lat, clientaddr1Long, clientaddr1Isdefault), observer);
    }

    public void getShopAddrList(String userUsid,Observer<List<ShopAddressListBean>> observer) {
        setSubscribe( service.getShopAddrList(userUsid),observer);
    }
    public  void deleteShopAddress(String userUsid,String clientaddr1Things1,Observer<BaseBean> observable){
        setSubscribe(service.deleteShopAddress(userUsid,clientaddr1Things1),observable);
    }
    public  void updateShopAddr(String userUsid,String clientaddr1Things1,String clientaddr1Addr,float clientaddr1Lat,float clientaddr1Long,String clientaddr1Isdefault,Observer<BaseBean> observable){
        setSubscribe(service.updateShopAddr(userUsid,clientaddr1Things1,clientaddr1Addr,clientaddr1Lat,clientaddr1Long,clientaddr1Isdefault),observable);
    }
    public void addUserAddress(String userUsid, String clientaddrAddr, float clientaddrLat, float clientaddrLong, String clientaddrIsdefault, Observer<BaseBean> observer) {
        setSubscribe(service.addUserAddress(userUsid, clientaddrAddr, clientaddrLat, clientaddrLong, clientaddrIsdefault), observer);
    }
    public  void getUserAddress(String userUsid,Observer<List<UserAddressListBean>> observable){
        setSubscribe(service.getUserAddress(userUsid),observable);
    }
    public  void updateUserAddr(String userUsid,String clientaddrThings1,String clientaddrAddr,float clientaddrLat,float clientaddrLong,String clientaddrIsdefault,Observer<BaseBean> observable){
        setSubscribe(service.updateUserAddr(userUsid,clientaddrThings1,clientaddrAddr,clientaddrLat,clientaddrLong,clientaddrIsdefault),observable);
    }
    public  void deleteUserAddress(String userUsid,String clientaddrThings1,Observer<BaseBean> observable){
        setSubscribe(service.deleteUserAddress(userUsid,clientaddrThings1),observable);
    }
}
