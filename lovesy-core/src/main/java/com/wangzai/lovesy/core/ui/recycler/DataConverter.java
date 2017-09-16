package com.wangzai.lovesy.core.ui.recycler;

import java.util.ArrayList;

/**
 * Created by wangzai on 2017/9/2
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> itemEntities = new ArrayList<>();
    private String mJsonData;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        return mJsonData;
    }

    public void clearData() {
        itemEntities.clear();
    }
}
