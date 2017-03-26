package com.zhyan.gototongcheng.Main.AddressManage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.zhyan.gototongcheng.BaseActivity;
import com.zhyan.gototongcheng.Main.AddressManage.AddShop.AddressManageAddShopActivity;
import com.zhyan.gototongcheng.Main.AddressManage.AddUser.AddressManageAddUserActivity;
import com.zhyan.gototongcheng.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Widget.Dialog.AddAddressInAddressManageDialog;

/**
 * Created by admin on 2017/3/24.
 */

public class AddressManageActivity extends BaseActivity{
    /*返回上一页*/
    @BindView(R.id.rly_addressmanage_topbar_leftmenu)
    RelativeLayout rlyAddressManageTopBarLeftMenuBack;

    @OnClick(R.id.rly_addressmanage_topbar_leftmenu)
    public void rlyAddressManageTopBarLeftMenuBackOnclick() {
        this.finish();
    }
    /*返回上一页*/






    /*添加地址*/
    private AddAddressInAddressManageDialog addAddressInAddressManageDialog;
    @BindView(R.id.rly_addressmanage_topbar_addaddress)
    RelativeLayout rlyAddressManageTopBarAddAddress;
    @OnClick(R.id.rly_addressmanage_topbar_addaddress)
    public void rlyAddressManageTopBarAddAddressOnclick() {
        /*new AlertView("请选择要添加的地址类型", "添加联系人地址","添加商家地址", new String[]{"取消"}, null, this, AlertView.Style.Alert, this).show();*/
        addAddressInAddressManageDialog = new AddAddressInAddressManageDialog(this).Build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dissmissDialog();
            }
        }).setAddContacterButton("添加联系人", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dissmissDialog();
            }
        }).setAddShopButton("添加商家地址", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dissmissDialog();
            }
        }).setCallBackListener(new AddAddressInAddressManageDialog.DialogCallBackListener() {
            @Override
            public void callBack(String type) {
                gotoNextActivity(type);
            }
        }).build(this);
        showDialog();
    }


    private void gotoNextActivity(String type){
        if(type == null){
            return;
        }
        switch (type){
            case "user":
                Intent intent = new Intent(this,AddressManageAddUserActivity.class);
                startActivity(intent);
                break;
            case "shop":
                Intent intent1 = new Intent(this,AddressManageAddShopActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
    private void showDialog(){
        if((addAddressInAddressManageDialog != null)&&(!addAddressInAddressManageDialog.isShowing())){
            addAddressInAddressManageDialog.show();
        }
    }
    private void dissmissDialog(){
        if((addAddressInAddressManageDialog != null)&&(addAddressInAddressManageDialog.isShowing())) {
            addAddressInAddressManageDialog.dismiss();
        }
    }

/*    添加地址*/




    AddressManageActivityController addressManageActivityController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_addressmanage_lly);
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        addressManageActivityController = new AddressManageActivityController(this);
    }


    protected void onResume(){
        super.onResume();
        addressManageActivityController.onResume();
    }
}
