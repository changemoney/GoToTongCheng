package com.zhyan.gototongcheng.Main.HelpMeBuy.Shopping.SwipeCardViewWidget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyan.gototongcheng.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTouch;
import gototongcheng.zhyan.com.library.Bean.GoodsBean;

/**
 * http://www.tuicool.com/articles/qMnAfen
 * Created by zhyan on 2017/3/1.
 *
 * http://www.jianshu.com/p/c090ec158fc5
 */

public class SwipFlingRecyclerViewAdapter extends RecyclerView.Adapter<SwipFlingRecyclerViewAdapter.ItemContentViewHolder> {
    private List<GoodsBean> goodsBeanList /*= new ArrayList<GoodsBean>()*/;
    private Activity context;
    private LayoutInflater inflater;
    /*private PostcardAdapter.ViewHolder viewHold;*/
 /*   private TextView textViewGoodsType;*/
    public SwipFlingRecyclerViewAdapter(Activity mContext,List<GoodsBean> goodsBeanList1/*,PostcardAdapter.ViewHolder viewHold1*/){
        /*this.goodsBeanList = new ArrayList<GoodsBean>();*/
        this.context = mContext;
        /*this.viewHold = viewHold1;*/
        /*textViewGoodsType = textView;*/
        this.goodsBeanList = goodsBeanList1;
        inflater = LayoutInflater.from(context);
        System.out.println(" SwipFlingRecyclerViewAdapter");

    }
    public void setDataList(Collection<GoodsBean> dataList){
        this.goodsBeanList.clear();
        this.goodsBeanList.addAll(dataList);
        this.notifyItemRangeChanged(0,goodsBeanList.size());
        System.out.println(" setDataList");
    }
    public void addPos(GoodsBean bean, int position) {
        goodsBeanList.add(position, bean);
        this.notifyItemInserted(position);
        System.out.println(" addPos");
    }
    public void addData(GoodsBean bean){


        this.goodsBeanList.add(bean);
        notifyDataSetChanged();
        /*
        if(goodsBeanList.size() == 0) {
            this.goodsBeanList.add(bean);
            notifyItemInserted(0);
            this.notifyItemRangeChanged(0,1);
        }else{
            this.goodsBeanList.add(bean);
            this.notifyItemRangeInserted(goodsBeanList.size()-1,1);
            this.notifyItemRangeChanged(goodsBeanList.size()-1,1);
        }
*/


        System.out.println("addData ");
    }
    public List<GoodsBean> getAllData(){
        System.out.println(" getAllData");
        return this.goodsBeanList;
    }


    @Override
    public ItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("onCreateViewHolder parent:"+parent +" viewtype:  "+viewType);
        /*if(viewType==-1){
            View v=inflater.inflate(R.layout.activity_shoppinglist_content_piper_card_item_rv_item_lly,parent,false);
            return new ItemContentViewHolder(v);
        }
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shoppinglist_content_piper_card_item_rv_item_lly,parent,false);
        ItemContentViewHolder itemContentViewHolder=new ItemContentViewHolder(v);
        return itemContentViewHolder;*/
        return new ItemContentViewHolder(inflater.inflate(R.layout.activity_main_helpmebuy_shoppinglist_content_piper_card_item_rv_item_lly, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position) {
        System.out.println(" onBindViewHolder size:"+goodsBeanList.size()+"pos:"+position);
        if(goodsBeanList.size() == 0){
            return;
        }
        if(goodsBeanList.get(position) != null){
            GoodsBean goodsBean = goodsBeanList.get(position);
            if(goodsBeanList.get(position).getName() != null) {
                holder.tvMainHelpmeBuyContentPiperCardItemRVItemGoodsName.setText(goodsBeanList.get(position).getName());
            }
            if(goodsBeanList.get(position).getNum() != null){
                holder.tvMainHelpmeBuyContentPiperCardItemRVItemGoodsNum.setText(goodsBeanList.get(position).getNum());
            }
            int i = position % 2;
            if(i == 0){
                holder.llyMainHelpmeBuyContentPiperCardItemRVItemTotal.setBackgroundColor(context.getResources().getColor(R.color.gray));
            }else{
                holder.llyMainHelpmeBuyContentPiperCardItemRVItemTotal.setBackgroundColor(context.getResources().getColor(R.color.white));
            }

            holder.rlyMainHelpmeBuyContentPiperCardItemRVItemDelete.setOnClickListener(new MyOnclickListener(position));

        }



     /*   holder.tvShoppingListContentPiperCardItemRVItemGoodsNum.setText(goodsBean.getNum());*/

    }
    public class MyOnclickListener implements View.OnClickListener {
        int pos;
        public MyOnclickListener(int pos){
            this.pos = pos;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rly_main_helpmebuy_shoppinglist_content_piper_card_item_rv_item_delete:
                    goodsBeanList.remove(pos);
                    notifyDataSetChanged();
                    /*
                    if((pos != goodsBeanList.size())&&(pos != (goodsBeanList.size() -1))) {
                        Log.i("sfrvaitemPos:", "" + pos);
                        goodsBeanList.remove(pos);
                        notifyItemRangeRemoved(pos, 1);
                        notifyItemRangeChanged(pos, goodsBeanList.size()-pos);
                        return;
                    }else if(pos == (goodsBeanList.size() -1)){
                        goodsBeanList.remove(pos);
                        notifyItemRangeRemoved(pos,1);
                        notifyItemRangeChanged(pos,1);
                    }
*/
                    break;
            }
        }
    }

    @Override
    public void onBindViewHolder(ItemContentViewHolder holder, int position,List<Object> payloads){
        System.out.println(" onBindViewHolder22");
        onBindViewHolder(holder,position);
    }
    @Override
    public int getItemViewType(int position) {
        if(goodsBeanList.size()<=0){
            return -1;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {

        return goodsBeanList.size();
    }



    public class ItemContentViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_main_helpmebuy_shoppinglist_content_piper_card_item_rv_item_goodsname)
        TextView tvMainHelpmeBuyContentPiperCardItemRVItemGoodsName;
        @BindView(R.id.tv_main_helpmebuy_shoppinglist_content_piper_card_item_rv_item_goodsnum)
        TextView tvMainHelpmeBuyContentPiperCardItemRVItemGoodsNum;
        @BindView(R.id.tv_main_helpmebuy_shoppinglist_content_piper_card_item_rv_item_goodsprice)
        TextView tvMainHelpmeBuyContentPiperCardItemRVItemGoodsPrice;
        @BindView(R.id.lly_main_helpmebuy_shoppinglist_content_piper_card_item_rv_item_total)
        LinearLayout llyMainHelpmeBuyContentPiperCardItemRVItemTotal;
        @OnClick(R.id.lly_main_helpmebuy_shoppinglist_content_piper_card_item_rv_item_total)
        public void llyMainHelpmeBuyContentPiperCardItemRVItemTotalOnclick(){
            Toast.makeText(context,"this is item",Toast.LENGTH_SHORT).show();
        }
        @OnTouch(R.id.lly_main_helpmebuy_shoppinglist_content_piper_card_item_rv_item_total)
        public boolean llyMainHelpmeBuyContentPiperCardItemRVItemTotalOnTouch(View v,MotionEvent event){
            Toast.makeText(context,"this is OnTouch item",Toast.LENGTH_SHORT).show();
            return false;
        }

        /*
        @BindView(R.id.as_shoppinglist_content_piper_card_item_rv_item_cehua)
        AndroidSwipeLayout asMainHelpmeBuyContentPiperCardItemRVItem;*/
        @BindView(R.id.rly_main_helpmebuy_shoppinglist_content_piper_card_item_rv_item_delete)
        RelativeLayout rlyMainHelpmeBuyContentPiperCardItemRVItemDelete;
        public ItemContentViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
           /* System.out.print("out"+AndroidSwipeLayout.ShowMode.PullOut+",right:"+AndroidSwipeLayout.DragEdge.Right);*/
        }
    }



}
