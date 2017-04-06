package com.zhyan.gototongcheng.Main.HelpMeBuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.BaseActivity;
import com.zhyan.gototongcheng.Main.AddressManage.AddressManageActivity;
import com.zhyan.gototongcheng.Main.Common.ShouFeiBiaoZhunActivity;
import com.zhyan.gototongcheng.Main.HelpMeBuy.Shopping.ShoppingListActivity;
import com.zhyan.gototongcheng.R;
import com.zhyan.gototongcheng.Widget.PopupOnClickEvents;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import gototongcheng.zhyan.com.library.Bean.GoodsBean;
import gototongcheng.zhyan.com.library.Bean.OrderDetailBean;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import gototongcheng.zhyan.com.library.Utils.PhoneFormatCheckUtils;
import gototongcheng.zhyan.com.library.Utils.TimeUtil;
import gototongcheng.zhyan.com.library.Widget.Dialog.ShouDongShuRuDialog;

/**
 *
 * 帮我买
 * Created by admin on 2017/3/25.
 */

public class HelpMeBuyActivity extends BaseActivity {

    private String goodsName="";
    @BindView(R.id.lly_main_helpmebuy_total)
    LinearLayout llyMainHelpMeBuyTotal;
    /*后退到主界面*/
    @OnClick(R.id.rly_main_helpmebuy_topbar_leftmenu)
    public void rlyMainHelpMeBuyTopBarLeftMenuOnclick(){
        /*Toast.makeText(this,"ok",Toast.LENGTH_LONG).show();*/
        finish();
    }
    /*后退到主界面*/
 /*   *//*手动输入*//*
    @BindView(R.id.cb_main_helpmebuy_content_manualinput)
    Button cbHelpMeBuyContentManualinput;*/
    /*价格计算方式*/
    @BindView(R.id.rly_main_helpmebuy_bottombar_fee)
    RelativeLayout rlyHelpMeBuyBottomBarFee;
    @OnClick(R.id.rly_main_helpmebuy_bottombar_fee)
    public void rlyHelpMeBuyBottomBarFeeOnclick(){
        Intent intent = new Intent(this,ShouFeiBiaoZhunActivity.class);
        startActivity(intent);
    }
    /*价格计算方式*/
    private OrderDetailBean orderDetailBean;
    XCCacheManager xcCacheManager;
    private String usid = "";
    private String clientaddrThings1="",clientaddr1Things1="";
    private double blat=0,rlat=0,blon=0,rlon=0;
    private final int RESULT_RECE = 11;//收件人信息
    private final int RESULT_FOODSMENU = 12;//菜单
    private final int RESULT_SHOP = 10;//购买地址

    /*商家名称*/
    @BindView(R.id.tv_main_helpmebuy_content_shop_name)
    TextView tvMainHelpMeBuyContentShopName;
    /*商家详细地址*/
    @BindView(R.id.tv_main_helpmebuy_content_shop_address)
    TextView tvMainHelpMeBuyContentShopAddress;
    @OnClick(R.id.lly_main_helpmebuy_shopdetail)
    public void llyMainHelpMeBuyShopDetailOnclick(){
        xcCacheManager.writeCache("addressManageType","shop");
        Intent intent = new Intent(this,AddressManageActivity.class);
        startActivityForResult(intent,RESULT_SHOP);
    }

    /*收件人姓名*/
    @BindView(R.id.tv_main_helpmebuy_content_username)
    TextView tvMainHelpMeBuyContentUserName;
    /*收件人电话*/
    @BindView(R.id.tv_main_helpmebuy_content_usertel)
    TextView tvMainHelpMeBuyContentUserTel;
    /*收件人地址*/
    @BindView(R.id.tv_main_helpmebuy_content_useraddressdetail)
    TextView tvMainHelpMeBuyContentUserAddressDetail;
    @OnClick(R.id.lly_main_helpmebuy_userdetail)
    public void llyMainHelpMeBuyUserDetailOnclick(){
        xcCacheManager.writeCache("addressManageType","user");
        Intent intent = new Intent(this,AddressManageActivity.class);
        startActivityForResult(intent,RESULT_RECE);
    }
    /*购物清单*/
    @BindView(R.id.rv_main_helpmebuy_content_shoppingmenu)
    RecyclerView rvMainHelpMeBuyContentShoppingMenu;

    TimeUtil timeUtil = new TimeUtil();
    String timeBegin = "";
    float xBegin = 0;
    float yBegin = 0;
    float xEnd = 0;
    float yEnd = 0;
    /*进入购物清单*/
    @OnTouch(R.id.rv_main_helpmebuy_content_shoppingmenu)
    public boolean llyHelpMeBuyShoppingListOnTouch(View view, MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                timeBegin = timeUtil.getCurrentDateTime();
                xBegin = event.getRawX();
                yBegin = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP: {
                xEnd = event.getRawX();
                yEnd = event.getRawY();
                int absXBegin = (int) Math.abs(xBegin);
                int absYBegin = (int) Math.abs(yBegin);
                int absXEnd = (int) Math.abs(xEnd);
                int absYEnd = (int) Math.abs(yEnd);
                int disX = (absXEnd - absXBegin);
                int disY = (absYEnd - absYBegin);
                System.out.println("this is llyShoppingListContentPiperCardItemPaperThree down xBegin:" + xBegin + ",y:" + yBegin);
                System.out.println("this is llyShoppingListContentPiperCardItemPaperThree down xEnd:" + absXEnd + ",y:" + absYEnd);
                System.out.println("this is llyShoppingListContentPiperCardItemPaperThree disX:" + disX + ",disY:" + disY);
                if((disX == 0)&&(disY == 0)){
                    isOnclick();
                    return true;
                }
                break;
            }
        }
        return false;
    }
    private void isOnclick() {
        System.out.println("this is onclick");
            /*recyclerViewAdapter.addData(new GoodsBean());*/
        String currentTime = timeUtil.getCurrentDateTime();
        long timeGap = timeUtil.getSubTwoTimeBySeconds(currentTime, timeBegin);// 与现在时间相差秒数
            /*Toast.makeText(mContext,"dis:timeGap:"+timeGap,Toast.LENGTH_SHORT).show();*/
            /*时间为小于1秒则判断为点击事件不然就判断为触摸事件*/
        if (Math.abs(timeGap) < 1) {
            Intent intent = new Intent(this,ShoppingListActivity.class);
            if((goodsBeanList.size() > 0)){
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("foodsList",(ArrayList<GoodsBean>)helpMeBuyShoppingMenuRecyclerViewAdapter.getGoodsBeanList());
                intent.putExtras(bundle);
            }
            startActivityForResult(intent,RESULT_FOODSMENU);
        }
    }
/*进入购物清单*/



    /*支付*/
    @BindView(R.id.rly_main_helpmebuy_bottom_payconfirm)
    RelativeLayout rlyMainHelpMeBuyBottomPayConfirm;
    @OnClick(R.id.rly_main_helpmebuy_bottom_payconfirm)
    public void rlyHelpMeBuyBottomPayConfirmOnclick(){
       /* Intent intent = new Intent(this, PayConfirmPopup.class);
        startActivity(intent);*/

            initOrderDetail();
            /*Toast.makeText(this,"initOrderOk",Toast.LENGTH_SHORT).show();*/
     /*       String addr = tvMainHelpMeBuyContentShopAddress.getText().toString();
            String addr1 = tvMainHelpMeBuyContentUserAddressDetail.getText().toString();*/
         Toast.makeText(this, "信息输入不全", Toast.LENGTH_LONG).show();
            if ((orderDetailBean.getUserUsid().isEmpty()) || (tvMainHelpMeBuyContentShopAddress.getText().toString().isEmpty()) || (tvMainHelpMeBuyContentUserAddressDetail.getText().toString().isEmpty()) || (orderDetailBean.getDetailsGoodsname().isEmpty())) {
                Toast.makeText(this, "信息输入不全", Toast.LENGTH_LONG).show();
                return;
            }
            PopupOnClickEvents popupOnClickEvents = new PopupOnClickEvents(this);
           /* goodsName = "走兔订单号";*/
            /*Toast.makeText(this,"click",Toast.LENGTH_SHORT).show();*/
            popupOnClickEvents.PayConfirm(llyMainHelpMeBuyTotal, orderDetailBean);

    }
     /*支付*/

    List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>();
    HelpMeBuyShoppingMenuRecyclerViewAdapter helpMeBuyShoppingMenuRecyclerViewAdapter;
    HelpMeBuyActivityController helpMeBuyActivityController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_helpmebuy_lly);
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);

        xcCacheManager = XCCacheManager.getInstance(this);
        usid = xcCacheManager.readCache("usid");
        initShoppingMenu(goodsBeanList);
        if(helpMeBuyShoppingMenuRecyclerViewAdapter != null) {
            helpMeBuyActivityController = new HelpMeBuyActivityController(this, helpMeBuyShoppingMenuRecyclerViewAdapter);
        }
    }



    /*初始化购物清单*/
    private void initShoppingMenu(List<GoodsBean> goodsBeanLists){
        if(goodsBeanLists != null) {
            helpMeBuyShoppingMenuRecyclerViewAdapter = new HelpMeBuyShoppingMenuRecyclerViewAdapter(this,goodsBeanList);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
            rvMainHelpMeBuyContentShoppingMenu.setLayoutManager(gridLayoutManager);
            rvMainHelpMeBuyContentShoppingMenu.setAdapter(helpMeBuyShoppingMenuRecyclerViewAdapter);
        }
    }
    /*初始化购物清单*/
    private void initOrderDetail(){
        orderDetailBean = new OrderDetailBean();
        /*Toast.makeText(this,"usid:"+usid,Toast.LENGTH_LONG).show();*/
        if(usid == null){
            usid = "";
        }
        orderDetailBean.setUserUsid(usid);
        orderDetailBean.setClientaddrThings1(clientaddrThings1);
        orderDetailBean.setClientaddr1Things1(clientaddr1Things1);
        orderDetailBean.setOrderLat((float)blat);
        orderDetailBean.setOrderLong((float)blon);
        orderDetailBean.setOrderDlat((float)rlat);
        orderDetailBean.setOrderDlong((float)rlon);
        System.out.println(orderDetailBean.getUserUsid());
        orderDetailBean.setClientaddrAddr(tvMainHelpMeBuyContentShopName.getText().toString() + ";"+tvMainHelpMeBuyContentShopAddress.getText().toString());
        orderDetailBean.setClientaddrAddr1(tvMainHelpMeBuyContentUserName.getText().toString()+";"+tvMainHelpMeBuyContentUserTel.getText().toString()+";"+tvMainHelpMeBuyContentUserAddressDetail.getText().toString());
        String remark = "";
        if((helpMeBuyActivityController.addRemarkList != null)&&(helpMeBuyActivityController.addRemarkList.size() > 0)) {
            for (int i=0; i<helpMeBuyActivityController.addRemarkList.size();i++){
                remark += helpMeBuyActivityController.addRemarkList.get(i).getText().toString()+";";
            }
        }
        if((helpMeBuyActivityController.checkList != null)&&(helpMeBuyActivityController.checkList.size() > 0)){
            for(int i=0;i<helpMeBuyActivityController.checkList.size();i++){
                remark += helpMeBuyActivityController.checkList.get(i)+";";
            }
        }
        orderDetailBean.setOrderRemark(remark);

            /*Toast.makeText(this,"price:"+helpMeBuyActivityController.price,Toast.LENGTH_SHORT).show();*/
        PhoneFormatCheckUtils phoneFormatCheckUtils = new PhoneFormatCheckUtils();
        if(phoneFormatCheckUtils.isDouble(helpMeBuyActivityController.price)) {
            orderDetailBean.setOrderOrderprice(Double.parseDouble(helpMeBuyActivityController.price));
        }
        orderDetailBean.setOrderMileage(helpMeBuyActivityController.dis);
        List<GoodsBean> goodsBeanList1 =  helpMeBuyShoppingMenuRecyclerViewAdapter.getGoodsBeanList();
        String menu = "";
        if((goodsBeanList1 != null)&&(goodsBeanList1.size() > 0)){
            for(int i=0;i<goodsBeanList1.size();i++){
                menu += goodsBeanList1.get(i).getName()+"x"+goodsBeanList1.get(i).getNum()+";";
            }
        }
        orderDetailBean.setDetailsGoodsname(menu);
    }












    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_SHOP:
                Bundle b=data.getExtras(); //data为B中回传的Intent
                String nameCall=b.getString("nameCall");//str即为回传的值
                String address=b.getString("address");//str即为回传的值
                clientaddr1Things1 = b.getString("clientaddr1Things1");
                String lat = b.getString("blat");
                String lon = b.getString("blon");
                /*Toast.makeText(getBaseContext(),"RESULT_BUY:"+lat+" "+lon+" ",Toast.LENGTH_SHORT).show();*/

                if((lat != null) && (lon != null)) {
                    blat = Double.parseDouble(lat);
                    blon = Double.parseDouble(lon);

                }
                tvMainHelpMeBuyContentShopName.setText(nameCall);
                tvMainHelpMeBuyContentShopAddress.setText(address);

                break;
            case RESULT_RECE:
                Bundle r=data.getExtras(); //data为B中回传的Intent
                String name=r.getString("nameCall");//str即为回传的值
                String addr=r.getString("address");//str即为回传的值
                clientaddrThings1 = r.getString("clientaddrThings1");
                String tel =r.getString("tel");
                String latt = r.getString("rlat");
                String lonn = r.getString("rlon");
                /*Toast.makeText(getBaseContext(),"RESULT_RECE:"+name+" "+addr+" "+tel+" "+latt+" "+lonn,Toast.LENGTH_SHORT).show();*/
               /* Toast.makeText(getBaseContext(),"RESULT_BUY:"+latt+" "+lonn+" ",Toast.LENGTH_SHORT).show();*/
                if((latt != null) && (lonn != null)) {
                    rlat = Double.parseDouble(latt);
                    rlon = Double.parseDouble(lonn);

                }

                tvMainHelpMeBuyContentUserName.setText(name);
                tvMainHelpMeBuyContentUserTel.setText(tel);
                tvMainHelpMeBuyContentUserAddressDetail.setText(addr);
                break;

            case RESULT_FOODSMENU:
                Bundle foodsb = data.getExtras();
                if(foodsb != null) {
                    List<GoodsBean> goodsBeanArrayList = foodsb.getParcelableArrayList("foodsMenu");

                    if((goodsBeanArrayList != null)) {
                        Log.i("helpmebuy,goodsBeanArr",goodsBeanArrayList.size()+"");
                        /*initShoppingMenu(goodsBeanArrayList);*/
                       /* goodsBeanList.clear();*/
                        helpMeBuyShoppingMenuRecyclerViewAdapter.setGoodsBeanList(goodsBeanArrayList);
                       /* goodsBeanList.clear();
                        goodsBeanList.addAll(goodsBeanArrayList);*/
                    }
                }
                break;
            default:
                break;
        }
    }


    protected void onResume(){
        super.onResume();
        /*Toast.makeText(this.getBaseContext(),"blat:"+blat+" blon:"+blon+" rlat:"+rlat+" rlon:"+rlon,Toast.LENGTH_SHORT).show();*/
        helpMeBuyActivityController.startBikeNaviSearch(blat,blon,rlat,rlon);
    }
    protected void onPause(){
        super.onPause();

    }
    protected void onStop(){
        super.onStop();

    }
    protected void onDestroy(){
        super.onDestroy();
   /*     if(!usid.isEmpty()) {
            locationClient.unRegisterLocationListener(locationListener);
        }*/
        helpMeBuyActivityController.onDestroy();
    }
}
