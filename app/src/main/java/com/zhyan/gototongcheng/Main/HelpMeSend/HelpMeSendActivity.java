package com.zhyan.gototongcheng.Main.HelpMeSend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.BaseActivity;
import com.zhyan.gototongcheng.Main.AddressManage.AddressManageActivity;
import com.zhyan.gototongcheng.R;
import com.zhyan.gototongcheng.Widget.PopupOnClickEvents;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Bean.OrderDetailBean;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import gototongcheng.zhyan.com.library.Utils.TimeUtil;

/**
 * Created by admin on 2017/3/26.
 */

public class HelpMeSendActivity extends BaseActivity {

    @BindView(R.id.lly_main_helpmesend_total)
    LinearLayout llyMainHelpMeSendTotal;
    private final int RESULT_SENDER = 10;
    private final int RESULT_RECEIVER = 11;
    private double blat=0.0;
    private double rlat=0.0;
    private double blon=0.0;
    private double rlon=0.0;
    /*发件人*/
    @BindView(R.id.tv_main_helpmesend_content_sendername)
    TextView tvMainHelpMeSendContentSenderName;
    @BindView(R.id.tv_main_helpmesend_content_sendertel)
    TextView tvMainHelpMeSendContentSenderTel;
    @BindView(R.id.tv_main_helpmesend_content_senderaddr)
    TextView tvMainHelpMeSendContentSenderAddr;
    @BindView(R.id.lly_main_helpmesend_content_senderdata)
    LinearLayout llyMainHelpMeSendContentSenderData;
    @OnClick(R.id.lly_main_helpmesend_content_senderdata)
    public void llyMainHelpMeSendContentSenderDataOnclick(){
        xcCacheManager.writeCache("addressManageType","send");
        Bundle bundle = new Bundle();
        bundle.putString("sender",""+RESULT_SENDER);
        Intent intent = new Intent(this,AddressManageActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,RESULT_SENDER);
    }
    /*发件人*/

    /*收件人*/
    @BindView(R.id.tv_main_helpmesend_content_receivername)
    TextView tvMainHelpMeSendContentReceiverName;
    @BindView(R.id.tv_main_helpmesend_content_receivertel)
    TextView tvMainHelpMeSendContentReceiverTel;
    @BindView(R.id.tv_main_helpmesend_content_receiveraddr)
    TextView tvMainHelpMeSendContentReceiverAddr;
    @BindView(R.id.lly_main_helpmesend_content_receiverdata)
    LinearLayout llyMainHelpMeSendContentReceiverData;
    @OnClick(R.id.lly_main_helpmesend_content_receiverdata)
    public void llyMainHelpMeSendContentReceiverDataOnclick(){
        xcCacheManager.writeCache("addressManageType","send");
        Bundle bundle = new Bundle();
        bundle.putString("receiver",""+RESULT_RECEIVER);
        Intent intent = new Intent(this,AddressManageActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,RESULT_RECEIVER);
    }
    /*收件人*/
    /*备注*/
    @BindView(R.id.et_main_helpmesend_content_remark)
    EditText etMainHelpMeSendContentRemark;
    /*备注*/




    /*底部栏支付*/
    @BindView(R.id.rly_main_helpmesend_bottombar_payconfirm)
    RelativeLayout rlyMainHelpMeSendBottomBarPayConfirm;
    @OnClick(R.id.rly_main_helpmesend_bottombar_payconfirm)
    public void rlyMainHelpMeSendBottomBarPayConfirmOnclick(){
        try {
            initOrderDetail();
           /* Toast.makeText(this, ""+orderDetailBean.getUserUsid()+" "+orderDetailBean.getClientaddrAddr()+" "+orderDetailBean.getClientaddrAddr1()+" "+orderDetailBean.getOrderTimeliness(), Toast.LENGTH_LONG).show();*/
            if ((orderDetailBean.getUserUsid().isEmpty()) || (orderDetailBean.getClientaddrAddr().isEmpty()) || (orderDetailBean.getClientaddrAddr1().isEmpty())  || (orderDetailBean.getOrderTimeliness().isEmpty())||(helpMeSendActivityController.tvMainHelpMeSendContentSendDis.getText().toString().isEmpty())||(helpMeSendActivityController.price.isEmpty())) {
                Toast.makeText(this, "信息输入不全", Toast.LENGTH_LONG).show();
                return;
            }

            PopupOnClickEvents popupOnClickEvents = new PopupOnClickEvents(this);

            popupOnClickEvents.PayConfirm(llyMainHelpMeSendTotal, orderDetailBean);
        }catch (Exception e){

        }
    }



    OrderDetailBean orderDetailBean;
    private String  bclientaddrThings1 = "";
    private String  rclientaddrThings1 = "";
    XCCacheManager xcCacheManager;
    private double price = 0;
    String usid="";
    HelpMeSendActivityController helpMeSendActivityController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_helpmesend_lly);
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        xcCacheManager = XCCacheManager.getInstance(this);
        helpMeSendActivityController = new HelpMeSendActivityController(this);
        usid = xcCacheManager.readCache("usid");
    }





    public void initOrderDetail(){
        orderDetailBean = new OrderDetailBean();


        if(usid == null){
            usid = "";
        }
        /*Toast.makeText(this,"usid:"+usid,Toast.LENGTH_LONG).show();*/
        orderDetailBean.setUserUsid(usid);
        System.out.println(orderDetailBean.getUserUsid());
        orderDetailBean.setClientaddrAddr(tvMainHelpMeSendContentSenderName.getText().toString() + ";"+tvMainHelpMeSendContentSenderTel.getText().toString() + ";"+tvMainHelpMeSendContentSenderAddr.getText().toString());
        orderDetailBean.setClientaddrAddr1(tvMainHelpMeSendContentReceiverName.getText().toString()+";"+tvMainHelpMeSendContentReceiverTel.getText().toString()+";"+tvMainHelpMeSendContentReceiverAddr.getText().toString());

        if(!helpMeSendActivityController.price.isEmpty()) {
            price = Double.parseDouble(helpMeSendActivityController.price);
        }
        orderDetailBean.setOrderOrderprice(price);
        orderDetailBean.setOrderLong((float)blon);
        orderDetailBean.setOrderLat((float)blat);
        orderDetailBean.setOrderDlat((float)rlat);
        orderDetailBean.setOrderDlong((float)rlon);

        orderDetailBean.setOrderMileage((double)helpMeSendActivityController.dis);
        orderDetailBean.setClientaddrThings1(bclientaddrThings1);
        orderDetailBean.setClientaddr1Things1(rclientaddrThings1);

        orderDetailBean.setDetailsGoodsname("");
        orderDetailBean.setOrderName(helpMeSendActivityController.tvMainHelpMeSendContentGoodsType.getText().toString());
        String time = helpMeSendActivityController.tvMainHelpMeSendContentRecTime.getText().toString();
        TimeUtil timeUtil = new TimeUtil();
        if(time.equals("立即收件")){
            time = timeUtil.getCurrentDateTime();
            /*time = time.replace(" ","");*/
        }
        orderDetailBean.setOrderTimeliness(time);
        String remark = etMainHelpMeSendContentRemark.getText().toString();
        if((remark == null)||(remark.isEmpty())){
            remark = "";
        }
        orderDetailBean.setOrderRemark(remark);
        orderDetailBean.setClientaddrArea("");

    }




    /*百度地图定位结果*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_SENDER:
                getDataFromAddActivity(data,true);
                break;
            case RESULT_RECEIVER:
                getDataFromAddActivity(data,false);


                break;
            default:
                break;
        }
    }
    /*百度地图定位结果*/
    private void getDataFromAddActivity(Intent data,boolean isSender){
        if(data != null){
            Bundle b=data.getExtras(); //data为B中回传的Intent
            String nameCall=b.getString("nameCall");//str即为回传的值
            String address=b.getString("address");//str即为回传的值
            String telphone = b.getString("tel");
            String clientaddrThings1 = b.getString("clientaddrThings1");
            String lat = b.getString("lat");
            String lon = b.getString("lon");
            /*Toast.makeText(this,"lat:"+lat+" lon:"+lon+" clientaddrThing1:"+clientaddrThings1,Toast.LENGTH_LONG).show();*/
            if(lat.isEmpty()||lon.isEmpty()||clientaddrThings1.isEmpty()){
                return;
            }
            if(isSender){
                if((lat != null) && (lon != null)) {
                    blat = Double.parseDouble(lat);
                    blon = Double.parseDouble(lon);
                }
                bclientaddrThings1 = clientaddrThings1;
                tvMainHelpMeSendContentSenderName.setText(nameCall);
                tvMainHelpMeSendContentSenderAddr.setText(address);
                tvMainHelpMeSendContentSenderTel.setText(telphone);
            }else{
                rclientaddrThings1 = clientaddrThings1;
                tvMainHelpMeSendContentReceiverName.setText(nameCall);
                tvMainHelpMeSendContentReceiverAddr.setText(address);
                tvMainHelpMeSendContentReceiverTel.setText(telphone);
                if((lat != null) && (lon != null)) {
                    rlat = Double.parseDouble(lat);
                    rlon = Double.parseDouble(lon);
                }
            }
            /*Toast.makeText(getBaseContext(),"lat:"+lat+" blat:"+blat+" rlat:"+rlat,Toast.LENGTH_SHORT).show();*/
            /*Toast.makeText(this,"this is helpmesend:lat"+lat+",lon:"+lon,Toast.LENGTH_SHORT).show();*/
        }
    }
    protected void onResume(){
        super.onResume();
        /*Toast.makeText(this,"here is onResume",Toast.LENGTH_SHORT).show();*/
        helpMeSendActivityController.startBikeNaviSearch(blat,blon,rlat,rlon);
    }
    protected void onStart(){
        super.onStart();
        /*Toast.makeText(this,"here is onResume",Toast.LENGTH_SHORT).show();*/
        helpMeSendActivityController.startBikeNaviSearch(blat,blon,rlat,rlon);
    }
    protected void onDestroy(){
        super.onDestroy();
        helpMeSendActivityController.onDestroy();
    }
}
