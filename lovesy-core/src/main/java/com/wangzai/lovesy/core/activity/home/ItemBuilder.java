package com.wangzai.lovesy.core.activity.home;

import com.wangzai.lovesy.core.fragment.LoveSyFragment;

import java.util.LinkedHashMap;

/**
 * Created by wangzai on 2017/9/21
 */

public class ItemBuilder {

    private final LinkedHashMap<BottomTabBean, LoveSyFragment> items = new LinkedHashMap<>();

    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(BottomTabBean bean, LoveSyFragment fragment) {
        items.put(bean, fragment);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, LoveSyFragment> items) {
        this.items.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, LoveSyFragment> build() {
        return items;
    }
}
