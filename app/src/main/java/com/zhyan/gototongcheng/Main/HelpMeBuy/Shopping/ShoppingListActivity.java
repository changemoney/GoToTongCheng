package com.zhyan.gototongcheng.Main.HelpMeBuy.Shopping;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhyan.gototongcheng.BaseActivity;
import com.zhyan.gototongcheng.Main.HelpMeBuy.Shopping.SwipeCardViewWidget.Bean;
import com.zhyan.gototongcheng.Main.HelpMeBuy.Shopping.SwipeCardViewWidget.PostcardAdapter;
import com.zhyan.gototongcheng.Main.HelpMeBuy.Shopping.SwipeCardViewWidget.SwipePostcard;
import com.zhyan.gototongcheng.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Bean.GoodsBean;

/**http://www.apkbus.com/thread-272520-1-1.html
 * Created by admin on 2017/3/25.
 */

public class ShoppingListActivity extends BaseActivity {


    @BindView(R.id.rly_main_helpmebuy_shoppinglist_topbar_leftmenu)
    RelativeLayout rlyMainHelpMeBuyShoppinglistTopBarLeftMenu;
    @OnClick(R.id.rly_main_helpmebuy_shoppinglist_topbar_leftmenu)
    public void rlyMainHelpMeBuyShoppinglistTopBarLeftMenuOnclick(){
        finish();
    }
    private List<GoodsBean> goodsBeanList;
    PostcardAdapter adapter;
    @BindView(R.id.sp_main_helpmebuy_shoppinglist_content)
    SwipePostcard spMainHelpMeBuyShoppingListShoppingContent;

    @BindView(R.id.rly_main_helpmebuy_shoppinglist_i_know_click)
    RelativeLayout rlyMainHelpMeBuyShoppingListIKnowClick;
    @OnClick(R.id.rly_main_helpmebuy_shoppinglist_i_know_click)
    public void rlyMainHelpMeBuyShoppingListIKnowClickOnclick(){
        rlyMainHelpMeBuyShoppingListIKnow.setVisibility(View.GONE);
    }
    @BindView(R.id.rly_main_helpmebuy_shoppinglist_i_know)
    RelativeLayout rlyMainHelpMeBuyShoppingListIKnow;
    private final int RESULT_FOODSMENU = 12;//菜单

    /*确认订单*/
    @OnClick(R.id.rly_main_helpmebuy_shoppinglist_topbar_rightmenu)
    public void rlyShoppingListTopBarRightMenuOnclick(){
        try {
            Intent intent = new Intent();
            System.out.println("this is rlyShoppingListTopBarRightMenuBegin:" + goodsBeanList.size() + "goodsbean");
            if (adapter.getGoodsBeanList() != null) {
   System.out.println("this is rlyShoppingListTopBarRightMenumiddle:" + goodsBeanList.size() + "goodsbean");
                if ((adapter.getGoodsBeanList() != null)) {
                    System.out.println("this is rlyShoppingListTopBarRightMenuEnd:" + goodsBeanList.size() + "goodsbean");
                    Bundle b = new Bundle();
                    b.putParcelableArrayList("foodsMenu", (ArrayList<GoodsBean>) adapter.getGoodsBeanList());

                    intent.putExtras(b);
                }
            }
            setResult(RESULT_FOODSMENU, intent);
        }catch (Exception e){
            Log.i("shoppinglistact:",e+"");
        }
        finish();
    }

    /*确认订单*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_helpmebuy_shoppinglist_lly);

        init();
    }
    @Override
    public void init() {
        ButterKnife.bind(this);
        initGetFoodsMenu();
        initCard();
    }
    private void initGetFoodsMenu(){
        Bundle foodsMenu = getIntent().getExtras();
        if(foodsMenu != null) {
            goodsBeanList = foodsMenu.getParcelableArrayList("foodsList");
            System.out.println("this is ShoppingListActivity:"+goodsBeanList.size());
            if(goodsBeanList == null){
                goodsBeanList = new ArrayList<GoodsBean>();
            }
        }else
        {
            goodsBeanList = new ArrayList<GoodsBean>();
        }

    }

    /*初始化卡片*/
        /*卡片效果*/
    private void initCard(){
        List<Bean> data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            Bean bean = new Bean();
            data.add(bean);
        }
        adapter = new PostcardAdapter(this, data,goodsBeanList);
        System.out.println("this is initCard:"+goodsBeanList.size());
        /*adapter.recyclerViewAdapter.setDataList(goodsBeanList);*/
        if (spMainHelpMeBuyShoppingListShoppingContent != null) {
            /*卡片数据填充*/
            spMainHelpMeBuyShoppingListShoppingContent.setAdapter(adapter);
            spMainHelpMeBuyShoppingListShoppingContent.setMaxPostcardNum(3);
//            postcard.setOffsetY(67);
//            postcard.setMinDistance(200);
            spMainHelpMeBuyShoppingListShoppingContent.setOnPostcardRunOutListener(new SwipePostcard.OnPostcardRunOutListener() {
                @Override
                public void onPostcardRunOut() {
                    Toast.makeText(getBaseContext(), "Run out!", Toast.LENGTH_SHORT).show();
                }
            });
            spMainHelpMeBuyShoppingListShoppingContent.setOnPostcardDismissListener(new SwipePostcard.OnPostcardDismissListener() {
                @Override
                public void onPostcardDismiss(int direction) {
                    if (direction == SwipePostcard.DIRECTION_LEFT) {
                        /*Toast.makeText(getBaseContext(), "Left", Toast.LENGTH_SHORT).show();*/
                    } else if (direction == SwipePostcard.DIRECTION_RIGHT) {
                        /*Toast.makeText(getBaseContext(), "right", Toast.LENGTH_SHORT).show();*/
                    }
                }
            });
          /*  Toast.makeText(getBaseContext(), postcard.getMaxPostcardNum() + " ", Toast.LENGTH_SHORT).show();*/
        }
    }

        /*卡片效果*/
    /*初始化卡片*/
}
