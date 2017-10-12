package com.wangzai.lovesy.home;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.core.activity.home.BaseHomeActivity;
import com.wangzai.lovesy.core.activity.home.BaseHomeFragment;
import com.wangzai.lovesy.core.activity.home.BottomTabBean;
import com.wangzai.lovesy.core.activity.home.ItemBuilder;
import com.wangzai.lovesy.home.collection.CollectionFragment;
import com.wangzai.lovesy.home.index.IndexFragment;
import com.wangzai.lovesy.home.user.UserFragment;

import java.util.LinkedHashMap;

/**
 * Created by wangzai on 2017/9/21
 */

public class HomeActivity extends BaseHomeActivity {

    @Override
    protected LinkedHashMap<BottomTabBean, BaseHomeFragment> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BaseHomeFragment> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{md-home}", getString(R.string.bottom_index_title)), new IndexFragment());
        items.put(new BottomTabBean("{md-photo-album}", getString(R.string.bottom_collection_title)), new CollectionFragment());
        items.put(new BottomTabBean("{md-person}", getString(R.string.bottom_personal_title)), new UserFragment());
        return builder.addItems(items).build();
    }

    @Override
    protected int setIndexFragment() {
        return 0;
    }

    @Override
    protected int setClickedColor() {
        return R.color.colorPrimary;
    }
}
