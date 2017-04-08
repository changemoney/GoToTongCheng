package com.zhyan.gototongcheng.Main.AddressManage.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.Main.AddressManage.AddUser.AddressManageAddUserActivity;
import com.zhyan.gototongcheng.NetWork.AddressManageNetWorks;
import com.zhyan.gototongcheng.R;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import gototongcheng.zhyan.com.library.Bean.BaseBean;
import gototongcheng.zhyan.com.library.Bean.UserAddressListBean;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import rx.Observer;

/**
 * Created by admin on 2017/3/22.
 */

public class AddressManageAddUserRVAdapter extends RecyclerView.Adapter<AddressManageAddUserRVAdapter.ItemContentViewHolder>  {
    private XCCacheManager xcCacheManager;
    private Activity activity;
    private List<UserAddressListBean> userAddressListBeanList;
    private LayoutInflater inflater;
    private int RESULT_TYPE;
    public AddressManageAddUserRVAdapter(Activity activity1, List<UserAddressListBean> userAddressListBeanList1,int RESULT_TYPE1){
        this.activity = activity1;
        userAddressListBeanList = userAddressListBeanList1;
        inflater = LayoutInflater.from(activity1);
        xcCacheManager = XCCacheManager.getInstance(activity);
        RESULT_TYPE = RESULT_TYPE1;
    }
    public void setDataList(Collection<UserAddressListBean> userAddressList1){
        if(userAddressListBeanList == null){
            return;
        }
        int count = userAddressListBeanList.size();
        userAddressListBeanList.clear();
        userAddressListBeanList.addAll(userAddressList1);
        notifyDataSetChanged();
    }
    @Override
    public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new ItemContentViewHolder(inflater.inflate(R.layout.activity_main_addressmanage_content_user_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position) {
        if(holder != null){
            holder.pos = position;


            holder.tvAddressManageContentUserRVItemAddr.setText(userAddressListBeanList.get(position).getClientaddrAddr());
            holder.tvAddressManageContentUserRVItemName.setText(userAddressListBeanList.get(position).getClientaddrName());
            holder.tvAddressManageContentUserRVItemTel.setText(userAddressListBeanList.get(position).getClientaddrTel());
            if(userAddressListBeanList.get(position).getClientaddrIsdefault().equals("1")){
                holder.ivAddressManageContentUserRVItemDefault.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return userAddressListBeanList.size();
    }

    public class ItemContentViewHolder extends RecyclerView.ViewHolder{
        private final int RESULT_OK = 11;
        private void backResultForHelpActivity(){

            Bundle bundle = new Bundle();
            bundle.putString("nameCall",userAddressListBeanList.get(pos).getClientaddrName());
            bundle.putString("tel",userAddressListBeanList.get(pos).getClientaddrTel());
            bundle.putString("address",userAddressListBeanList.get(pos).getClientaddrAddr());
            bundle.putString("clientaddr1Things1",userAddressListBeanList.get(pos).getClientaddrThings1());
            bundle.putString("rlat", "" + userAddressListBeanList.get(pos).getClientaddrLat());
            bundle.putString("rlon", "" + userAddressListBeanList.get(pos).getClientaddrLong());
            Intent intent = new Intent();
            intent.putExtras(bundle);
            activity.setResult(RESULT_OK, intent);
            activity.finish();
        }
        private void backResultForSendActivity(){

            Bundle bundle = new Bundle();
            bundle.putString("nameCall",userAddressListBeanList.get(pos).getClientaddrName());
            bundle.putString("tel",userAddressListBeanList.get(pos).getClientaddrTel());
            bundle.putString("address",userAddressListBeanList.get(pos).getClientaddrAddr());
            bundle.putString("clientaddrThings1",userAddressListBeanList.get(pos).getClientaddrThings1());
            bundle.putString("lat", "" + userAddressListBeanList.get(pos).getClientaddrLat());
            bundle.putString("lon", "" + userAddressListBeanList.get(pos).getClientaddrLong());
            Intent intent = new Intent();
            intent.putExtras(bundle);
            activity.setResult(RESULT_TYPE, intent);
            activity.finish();
        }
        float xBegin = 0;
        float yBegin = 0;
        float xEnd = 0;
        float yEnd = 0;
        @OnTouch(R.id.sly_addressmanage_content_user_rv_item)
        public boolean llyAddressManageContentRVItemTotalOnTouch(View view, MotionEvent event){

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    xBegin = event.getRawX();
                    yBegin = event.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                    xEnd = event.getRawX();
                    yEnd = event.getRawY();
                    int absXBegin = (int) Math.abs(xBegin);
                    int absYBegin = (int) Math.abs(yBegin);
                    int absXEnd = (int) Math.abs(xEnd);
                    int absYEnd = (int) Math.abs(yEnd);
                    int disX = (absXEnd - absXBegin);
                    int disY = (absYEnd - absYBegin);

                    if((disX == 0)&&(disY == 0)){
                        String type = xcCacheManager.readCache("addressManageType");
                        /*Toast.makeText(activity,"this is type:"+type,Toast.LENGTH_SHORT).show();*/
                        if((type != null)&&(!type.equals("main"))) {
                            if(type.equals("send")){
                                backResultForSendActivity();
                            }else {
                                if(type.equals("user")) {
                                    backResultForHelpActivity();
                                }
                            }
                        }
                        return true;
                    }
                    break;
            }
            return false;
        }


        @BindView(R.id.rly_addressmanage_content_user_rv_item_update)
        RelativeLayout rlyAddressManageContentUserRVItemUpdate;
        @OnClick(R.id.rly_addressmanage_content_user_rv_item_update)
        public void rlyAddressManageContentUserRVItemUpdateOnclick(){/*
            Toast.makeText(activity,"this is onclick",Toast.LENGTH_SHORT).show();*/
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
            XCCacheSavename xcCacheSavename = new XCCacheSavename();
            xcCacheManager.writeCache(xcCacheSavename.isAddressUpdate,"yes");
            xcCacheManager.writeCache(xcCacheSavename.addrUserUserName,userAddressListBeanList.get(pos).getClientaddrName());
            xcCacheManager.writeCache(xcCacheSavename.addrUserTel,userAddressListBeanList.get(pos).getClientaddrTel());
            xcCacheManager.writeCache(xcCacheSavename.addrUserAddr,userAddressListBeanList.get(pos).getClientaddrAddr());
            xcCacheManager.writeCache(xcCacheSavename.addrUserclientaddrThings,userAddressListBeanList.get(pos).getClientaddrThings1());
            xcCacheManager.writeCache(xcCacheSavename.addrUserLat,""+userAddressListBeanList.get(pos).getClientaddrLat());
            xcCacheManager.writeCache(xcCacheSavename.addrUserLon,""+userAddressListBeanList.get(pos).getClientaddrLong());
            System.out.print("\n this is addressmanage lat:"+userAddressListBeanList.get(pos).getClientaddrLat()+" lon:"+userAddressListBeanList.get(pos).getClientaddrLong());
            System.out.print("\n this is addressmanage lat:"+userAddressListBeanList.get(pos).getClientaddrLat()+" lon:"+userAddressListBeanList.get(pos).getClientaddrLong());
            System.out.print("\n this is addressmanage lat:"+userAddressListBeanList.get(pos).getClientaddrLat()+" lon:"+userAddressListBeanList.get(pos).getClientaddrLong());
            System.out.print("\n this is addressmanage lat:"+userAddressListBeanList.get(pos).getClientaddrLat()+" lon:"+userAddressListBeanList.get(pos).getClientaddrLong());
            System.out.print("\n this is addressmanage lat:"+userAddressListBeanList.get(pos).getClientaddrLat()+" lon:"+userAddressListBeanList.get(pos).getClientaddrLong());
            System.out.print("\n this is addressmanage lat:"+userAddressListBeanList.get(pos).getClientaddrLat()+" lon:"+userAddressListBeanList.get(pos).getClientaddrLong());
            System.out.print("\n this is addressmanage lat:"+userAddressListBeanList.get(pos).getClientaddrLat()+" lon:"+userAddressListBeanList.get(pos).getClientaddrLong());
            System.out.print("\n this is addressmanage lat:"+userAddressListBeanList.get(pos).getClientaddrLat()+" lon:"+userAddressListBeanList.get(pos).getClientaddrLong());
            System.out.print("\n this is addressmanage lat:"+userAddressListBeanList.get(pos).getClientaddrLat()+" lon:"+userAddressListBeanList.get(pos).getClientaddrLong());
            System.out.print("\n this is addressmanage lat:"+userAddressListBeanList.get(pos).getClientaddrLat()+" lon:"+userAddressListBeanList.get(pos).getClientaddrLong());
            System.out.print("\n this is addressmanage lat:"+userAddressListBeanList.get(pos).getClientaddrLat()+" lon:"+userAddressListBeanList.get(pos).getClientaddrLong());
            System.out.print("\n this is addressmanage lat:"+userAddressListBeanList.get(pos).getClientaddrLat()+" lon:"+userAddressListBeanList.get(pos).getClientaddrLong());
            System.out.print("\n this is addressmanage lat:"+userAddressListBeanList.get(pos).getClientaddrLat()+" lon:"+userAddressListBeanList.get(pos).getClientaddrLong());
            Intent intent = new Intent(activity, AddressManageAddUserActivity.class);
            activity.startActivity(intent);
        }

        public int pos = 0;
        @BindView(R.id.lly_addressmanage_content_user_rv_item_total)
        LinearLayout llyAddressManageContentRVItemTotal;

        @BindView(R.id.tv_addressmanage_content_user_rv_item_name)
        TextView tvAddressManageContentUserRVItemName;
        @BindView(R.id.tv_addressmanage_content_user_rv_item_tel)
        TextView tvAddressManageContentUserRVItemTel;
        @BindView(R.id.tv_addressmanage_content_user_rv_item_addr)
        TextView tvAddressManageContentUserRVItemAddr;
        @BindView(R.id.fly_addressmanage_content_user_rv_item)
        FrameLayout flyAddressManageContentRVItem;

        @BindView(R.id.iv_addressmanage_content_user_rv_item_default)
        ImageView ivAddressManageContentUserRVItemDefault;
        @BindView(R.id.rly_addressmanage_content_user_rv_item_delete)
        RelativeLayout rlyAddressManageContentUserRVItemDelete;
        @OnClick(R.id.rly_addressmanage_content_user_rv_item_delete)
        public void rlyAddressManageContentUserRVItemDeleteOnclick(){
            final String usid = xcCacheManager.readCache("usid");
            String clientaddrThings1 = userAddressListBeanList.get(pos).getClientaddrThings1();
            if((usid != null)&&(!usid.isEmpty())&&(clientaddrThings1 != null)) {
                final AddressManageNetWorks addressManageNetWorks = new AddressManageNetWorks();
                addressManageNetWorks.deleteUserAddress(usid, clientaddrThings1, new Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        Toast.makeText(activity,baseBean.getResult(),Toast.LENGTH_LONG).show();
                        addressManageNetWorks.getUserAddress(usid, new Observer<List<UserAddressListBean>>() {
                            @Override
                            public void onCompleted() {
                            }
                            @Override
                            public void onError(Throwable e) {
                            }
                            @Override
                            public void onNext(List<UserAddressListBean> userAddressListBeen) {
                                setDataList(userAddressListBeen);
                            }
                        });
                    }
                });
            }
        }
        public ItemContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

}
