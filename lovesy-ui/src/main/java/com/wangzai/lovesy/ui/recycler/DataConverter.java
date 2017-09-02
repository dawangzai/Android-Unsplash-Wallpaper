package com.wangzai.lovesy.ui.recycler;

import java.util.ArrayList;

/**
 * Created by wangzai on 2017/9/2
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    public String getJsonData() {
        if (mJsonData.isEmpty()) {
            throw new NullPointerException("JSON DATA IS NULL !");
        }
        return mJsonData;
    }
}
