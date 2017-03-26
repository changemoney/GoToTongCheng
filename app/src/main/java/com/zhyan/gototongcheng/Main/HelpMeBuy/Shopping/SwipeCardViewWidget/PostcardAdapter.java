package com.zhyan.gototongcheng.Main.HelpMeBuy.Shopping.SwipeCardViewWidget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.zhyan.gototongcheng.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import gototongcheng.zhyan.com.library.Bean.GoodsBean;
import gototongcheng.zhyan.com.library.Utils.TimeUtil;
import gototongcheng.zhyan.com.library.Widget.Dialog.CustomDialog;
import gototongcheng.zhyan.com.library.Widget.RecycleView.XRecycleView.XRecyclerView;
import gototongcheng.zhyan.com.library.Widget.ScrollView.SpringScrollView;

/*
* https://github.com/wuapnjie/SwipePostcard
* 卡片滑动滚出效果
 * Created by Flying SnowBean on 2016/1/26.
 */
public class PostcardAdapter extends SwipePostcard.Adapter {
    private final String TAG = PostcardAdapter.class.getSimpleName();
    private Activity mContext;
    private List<Bean> mData;
    private LayoutInflater inflater;

    private List<GoodsBean> goodsBeanList ;
    public SwipFlingRecyclerViewAdapter recyclerViewAdapter;/* = new SwipFlingRecyclerViewAdapter(mContext,goodsBeanList)*/;
    public PostcardAdapter(Activity context, List<Bean> data,List<GoodsBean> goodsBeanList1) {
        mContext = context;
        mData = data;
        inflater = LayoutInflater.from(context);
        goodsBeanList = goodsBeanList1;
        recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(mContext,goodsBeanList/*,viewHold*/);
    }
    public void addAll(List<Bean> beanList){

        mData.addAll(beanList);

    }


  /*  @Override
    public void bindView(View view, final int position) {
        Bean bean = mData.get(position);
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

    }*/

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder viewHold;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_main_helpmebuy_shoppinglist_content_piper_card_item_lly, parent, false);
          /*  TextView textView =(TextView) convertView.findViewById(R.id.tv_shoppinglist_content_piper_card_item_goodstypenum);*/
            viewHold = new ViewHolder(convertView);
            /*viewHold.tvShoppingListContentPiperCardItemGoodsTypeNum.setText(""+ recyclerViewAdapter.getItemCount());*/
            convertView.setTag(viewHold);
        }else{
            /*TextView textView =(TextView) convertView.findViewById(R.id.tv_shoppinglist_content_piper_card_item_goodstypenum);*/
            /*recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(mContext,goodsBeanList,textView);*/
            viewHold = (ViewHolder) convertView.getTag();

        }

        viewHold.tvShoppingListContentPiperCardItemGoodsTypeNum.setText(""+ recyclerViewAdapter.getItemCount());

        /*convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_shoppinglist_content_piper_card_item_lly, parent, false);*/
        return convertView;
    }
    public List<GoodsBean> getGoodsBeanList(){

        return this.goodsBeanList;
    }
    public class ViewHolder {

        /*商品种类*/
        @BindView(R.id.tv_shoppinglist_content_piper_card_item_goodstypenum)
        TextView tvShoppingListContentPiperCardItemGoodsTypeNum;
        /*商品种类*/

        @BindView(R.id.xrv_shoppinglist_content_piper_card_item_goods)
        XRecyclerView xrvShoppingListContentPiperCardItemGoods ;
/*        @BindView(R.id.ssv_shoppinglist_content_piper_card_item_scrol)
        SpringScrollView ssvShoppingListContentPiperCardItemPaperThree;*/

        CustomDialog customDialog;
        View view;


        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
            this.view = view;
            initRecyclerView();

        }

        private void initRecyclerView(){
            /*goodsBeanList.add(new GoodsBean());
            recyclerViewAdapter = new SwipFlingRecyclerViewAdapter(mContext,goodsBeanList);*/
            /*ervShoppingListContentPiperCardItemGoods.setHasFixedSize(true);*/
            /*位置不一样会导致刷新不了的bug*/
            xrvShoppingListContentPiperCardItemGoods.addHeaderView(inflater.inflate(R.layout.activity_main_helpmebuy_shoppinglist_content_piper_card_item_rv_headview_lly,null));
            xrvShoppingListContentPiperCardItemGoods.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));

            xrvShoppingListContentPiperCardItemGoods.addItemDecoration(new DividerItemDecoration(mContext,
                    DividerItemDecoration.VERTICAL));
            xrvShoppingListContentPiperCardItemGoods.setAdapter(recyclerViewAdapter);

            xrvShoppingListContentPiperCardItemGoods.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    xrvShoppingListContentPiperCardItemGoods.refreshComplete();
                }

                @Override
                public void onLoadMore() {
                    xrvShoppingListContentPiperCardItemGoods.loadMoreComplete();
                }
            });
        }



        /* 判断是点击事件 不是滑动事件的解决方法http://www.cnblogs.com/wader2011/archive/2011/12/02/2271981.html
    添加商品*/
        TimeUtil timeUtil = new TimeUtil();
        String timeBegin = "";
        float xBegin = 0;
        float yBegin = 0;
        float xEnd = 0;
        float yEnd = 0;
        @OnTouch(R.id.xrv_shoppinglist_content_piper_card_item_goods)
        public boolean llyShoppingListContentPiperCardItemParentRVOnTouch(View view, MotionEvent event){

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
                            /*isOnclick();*/
                            /*return true;*/

                    }
                }
                System.out.println("this is touch llyShoppingListContentPiperCardItemPaperThree:");
                return false;
        }
        /*@OnTouch(R.id.erv_shoppinglist_content_piper_card_item_goods)
        public boolean ervShoppingListContentPiperCardItemGoodsOnTouch(View v,MotionEvent event){


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    timeBegin = timeUtil.getCurrentDateTime();
                    xBegin = event.getRawX();
                    yBegin = event.getRawY();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
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
                    System.out.println("this is touch down xBegin:" + xBegin + ",y:" + yBegin);
                    System.out.println("this is touch down xEnd:" + absXEnd + ",y:" + absYEnd);
                    System.out.println("this is touch disX:" + disX + ",disY:" + disY);

                    if((disX == 0)&&(disY == 0)){
                        isOnclick();
                        return true;
                    }
                    break;
                            *//*isOnclick();*//*
                            *//*return true;*//*

                }
            }

            return false;
        }
*/

        private void isOnclick(){
            System.out.println("this is onclick");
            /*recyclerViewAdapter.addData(new GoodsBean());*/
            String currentTime = timeUtil.getCurrentDateTime();
            long timeGap = timeUtil.getSubTwoTimeBySeconds(currentTime,timeBegin);// 与现在时间相差秒数
            /*Toast.makeText(mContext,"dis:timeGap:"+timeGap,Toast.LENGTH_SHORT).show();*/
            /*时间为小于1秒则判断为点击事件不然就判断为触摸事件*/
            if(Math.abs(timeGap) <1) {
            /*弹出窗口来输入商品*/
                customDialog = new CustomDialog(mContext).Build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dissmissDialog();
                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dissmissDialog();
                    }
                }).setCallBackListener(new CustomDialog.DialogCallBackListener() {
                    @Override
                    public void callBack(String msgName, String msgNum) {
                        GoodsBean goodsBean = new GoodsBean();
                        goodsBean.setName(msgName);
                        goodsBean.setNum(msgNum);
                        if(!msgName.isEmpty()) {
                            recyclerViewAdapter.addData(goodsBean);
                       /* goodsBeanList.clear();
                        goodsBeanList.addAll(recyclerViewAdapter.getAllData());*/
                            tvShoppingListContentPiperCardItemGoodsTypeNum.setText("" + recyclerViewAdapter.getItemCount());
                            /*Toast.makeText(mContext, "name:" + goodsBean.getName() + ",num:" + goodsBean.getNum(), Toast.LENGTH_SHORT).show();*/
                        }

                    }
                }).build(mContext);
                showDialog(view);
            /*弹出窗口来输入商品*/
            }
        }

        /*时间为小于2秒则判断为点击事件不然就判断为触摸事件*/
        /* 判断是点击事件 不是滑动事件的解决方法http://www.cnblogs.com/wader2011/archive/2011/12/02/2271981.html
    添加商品*/
        public void showDialog(View view) {
            if (customDialog != null && !customDialog.isShowing())
                customDialog.show();
        }

        public void dissmissDialog() {
            if (customDialog != null && customDialog.isShowing())
                customDialog.dismiss();
        }






    }
}
