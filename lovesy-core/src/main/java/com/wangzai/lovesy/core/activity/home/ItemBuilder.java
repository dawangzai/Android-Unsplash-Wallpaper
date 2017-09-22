package com.wangzai.lovesy.core.activity.home;

import java.util.LinkedHashMap;

/**
 * Created by wangzai on 2017/9/21
 */

public class ItemBuilder {

    private final LinkedHashMap<BottomTabBean, BaseHomeFragment> items = new LinkedHashMap<>();

    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(BottomTabBean bean, BaseHomeFragment fragment) {
        items.put(bean, fragment);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, BaseHomeFragment> items) {
        this.items.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, BaseHomeFragment> build() {
        return items;
    }
}
