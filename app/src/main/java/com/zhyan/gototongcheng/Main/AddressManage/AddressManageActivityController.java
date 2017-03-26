package com.zhyan.gototongcheng.Main.AddressManage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RadioButton;

import com.zhyan.gototongcheng.Main.AddressManage.adapter.AddressManageAddShopRVAdapter;
import com.zhyan.gototongcheng.Main.AddressManage.adapter.AddressManageAddUserRVAdapter;
import com.zhyan.gototongcheng.Main.BaseController;
import com.zhyan.gototongcheng.NetWork.AddressManageNetWorks;
import com.zhyan.gototongcheng.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Bean.ShopAddressListBean;
import gototongcheng.zhyan.com.library.Bean.UserAddressListBean;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import gototongcheng.zhyan.com.library.Widget.RecycleView.XRecycleView.XRecyclerView;
import rx.Observer;

/**
 * Created by admin on 2017/3/24.
 */

public class AddressManageActivityController extends BaseController {


    /*用户或者商家地址*/
    @BindView(R.id.xrv_addressmanage_useraddrlist)
    XRecyclerView xrvAddressManageUserAddrList;
    @BindView(R.id.xrv_addressmanage_shopaddrlist)
    XRecyclerView xrvAddressManageShopAddrList;

    /*从互联网获取商家地址*/
    @BindView(R.id.rb_addressmanage_addshop)
    RadioButton rbAddressManageAddShop;
    @OnClick(R.id.rb_addressmanage_addshop)
    public void rbAddressManageAddShopOnclick()
    {
        getDetailDataFromNet("shop");
        /*getUserAddressFromNet();*/
    }
    /*从互联网获取商家地址*/
    /*从互联网获取联系人地址*/
    @BindView(R.id.rb_addressmanage_adduser)
    RadioButton rbAddressManageAddUser;
    @OnClick(R.id.rb_addressmanage_adduser)
    public void rbAddressManageAddUserOnclick()
    {
        getDetailDataFromNet("user");
  /*      getUserAddressFromNet();*/
    }
    /*从互联网获取联系人地址*/


    /*用户或者商家地址*/
    private  int RESULT_TYPE = 0;
    XCCacheManager xcCacheManager;
    private List<ShopAddressListBean> shopAddressListBeanList;
    private List<UserAddressListBean> userAddressListBeanList;
    private AddressManageAddShopRVAdapter shopRVAdapter;
    private AddressManageAddUserRVAdapter userRVAdapter;
    public AddressManageActivityController(Activity activity1){
        activity = activity1;
        init();
    }
    public void init(){
        ButterKnife.bind(this,activity);
        xcCacheManager = XCCacheManager.getInstance(activity);
        initResultType();
        initXRV();
    }
    /*判别 从哪里来访*/
    private void initResultType(){
        xcCacheManager = XCCacheManager.getInstance(activity);
        String type = xcCacheManager.readCache("addressManageType");
        if(type.equals("send")) {
            Bundle bundle = activity.getIntent().getExtras();
            String sender = bundle.getString("sender");
            String receiver = bundle.getString("receiver");
            if (sender != null) {
                RESULT_TYPE = Integer.valueOf(sender);
            }
            if (receiver != null) {
                RESULT_TYPE = Integer.valueOf(receiver);
            }
        }
    }
    /*判别 从哪里来访*/

    private void initXRV(){
        shopAddressListBeanList = new ArrayList<>();
        userAddressListBeanList = new ArrayList<>();
        shopRVAdapter = new AddressManageAddShopRVAdapter(activity,shopAddressListBeanList);
        userRVAdapter = new AddressManageAddUserRVAdapter(activity,userAddressListBeanList,RESULT_TYPE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(activity);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        xrvAddressManageShopAddrList.setAdapter(shopRVAdapter);
        xrvAddressManageUserAddrList.setAdapter(userRVAdapter);
        xrvAddressManageShopAddrList.setLayoutManager(layoutManager);
        xrvAddressManageUserAddrList.setLayoutManager(layoutManager1);
        xrvAddressManageShopAddrList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                xrvAddressManageShopAddrList.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                xrvAddressManageShopAddrList.loadMoreComplete();
            }
        });
        xrvAddressManageUserAddrList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                xrvAddressManageUserAddrList.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                xrvAddressManageUserAddrList.loadMoreComplete();
            }
        });
    }


    public void getDetailDataFromNet(String type) {

        String usid = xcCacheManager.readCache("usid");
        if ((usid != null) && (!usid.isEmpty())) {
            AddressManageNetWorks addressManageNetWorks = new AddressManageNetWorks();
            System.out.println("usid:" + usid);
            switch (type) {
                case "user":
                    xrvAddressManageUserAddrList.setVisibility(View.VISIBLE);
                    xrvAddressManageShopAddrList.setVisibility(View.GONE);
                    rbAddressManageAddUser.setChecked(true);
                    rbAddressManageAddShop.setChecked(false);
                    addressManageNetWorks.getUserAddress(usid, new Observer<List<UserAddressListBean>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<UserAddressListBean> userAddressListBeen) {
                            /*Toast.makeText(getBaseContext(),"this is userlist:"+userAddressListBeen.size(),Toast.LENGTH_SHORT).show();*/
                            userRVAdapter.setDataList(userAddressListBeen);

                        }
                    });

                    break;
                case "shop":
                    xrvAddressManageUserAddrList.setVisibility(View.GONE);
                    xrvAddressManageShopAddrList.setVisibility(View.VISIBLE);
                    rbAddressManageAddShop.setChecked(true);
                    rbAddressManageAddUser.setChecked(false);
                    addressManageNetWorks.getShopAddrList(usid, new Observer<List<ShopAddressListBean>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<ShopAddressListBean> shopAddressListBeen) {
                            shopRVAdapter.setDataList(shopAddressListBeen);

                        }
                    });
                    break;

            }


        }
    }
    public void onResume(){
        getDetailDataFromNet("user");
    }
}
