package com.wangzai.lovesy.home;

import android.graphics.Color;

import com.wangzai.lovesy.core.activity.home.BaseHomeActivity;
import com.wangzai.lovesy.core.activity.home.BaseHomeFragment;
import com.wangzai.lovesy.core.activity.home.BottomTabBean;
import com.wangzai.lovesy.core.activity.home.ItemBuilder;
import com.wangzai.lovesy.home.collection.CollectionFragment;
import com.wangzai.lovesy.home.index.IndexFragment;

import java.util.LinkedHashMap;

/**
 * Created by wangzai on 2017/9/21
 */

public class HomeActivity extends BaseHomeActivity {

    @Override
    protected LinkedHashMap<BottomTabBean, BaseHomeFragment> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BaseHomeFragment> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexFragment());
        items.put(new BottomTabBean("{fa-home}", "主页"), new CollectionFragment());
        return builder.addItems(items).build();
    }

    @Override
    protected int setIndexFragment() {
        return 0;
    }

    @Override
    protected int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
