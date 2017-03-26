package com.zhyan.gototongcheng.Main.BaiDuMapCommon;

import android.os.Bundle;

import com.zhyan.gototongcheng.BaseActivity;
import com.zhyan.gototongcheng.R;

import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/24.
 */

public class BaiduAddressSearchSuggestActivity extends BaseActivity{


    BaiduAddressSearchSuggestController baiduAddressSearchSuggestController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_baidumap_common_baiduaddress_search_suggest_lly);
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        baiduAddressSearchSuggestController = new BaiduAddressSearchSuggestController(this);
    }
}
