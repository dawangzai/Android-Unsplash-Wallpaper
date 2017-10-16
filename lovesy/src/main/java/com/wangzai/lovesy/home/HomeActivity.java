package com.wangzai.lovesy.home;

import android.view.Menu;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.core.activity.home.BaseHomeActivity;
import com.wangzai.lovesy.core.activity.home.BaseHomeFragment;
import com.wangzai.lovesy.core.activity.home.BottomTabBean;
import com.wangzai.lovesy.core.activity.home.ItemBuilder;
import com.wangzai.lovesy.core.fragment.LoveSyFragment;
import com.wangzai.lovesy.core.util.LogUtil;
import com.wangzai.lovesy.home.collection.CollectionFragment;
import com.wangzai.lovesy.home.index.IndexFragment;
import com.wangzai.lovesy.home.user.UserFragment;

import java.util.LinkedHashMap;

/**
 * Created by wangzai on 2017/9/21
 */

public class HomeActivity extends BaseHomeActivity {

    @Override
    protected LinkedHashMap<BottomTabBean, LoveSyFragment> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, LoveSyFragment> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{md-photo-camera}", getString(R.string.bottom_index_title)), new IndexFragment());
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
        return R.color.colorAccent;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (mCurrentPage) {
            case 0:
                menu.getItem(0).setVisible(true);
                menu.getItem(1).setVisible(true);
                menu.getItem(2).setVisible(false);
                break;
            case 1:
                menu.getItem(0).setVisible(true);
                menu.getItem(1).setVisible(false);
                menu.getItem(2).setVisible(false);
                break;
            case 2:
                menu.getItem(0).setVisible(true);
                menu.getItem(1).setVisible(false);
                menu.getItem(2).setVisible(true);
                break;
            default:
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

}
