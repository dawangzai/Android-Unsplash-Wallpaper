package com.wangzai.lovesy.ui.download;

import java.util.LinkedHashMap;

/**
 * Created by wangzai on 2018/2/5
 */

public class DataBuilder<K, V> {

    private final LinkedHashMap<K, V> mData = new LinkedHashMap<>();

    public void addData(K k, V v) {
        mData.put(k, v);
    }

    public final LinkedHashMap<K, V> build() {
        return mData;
    }
}
