package com.wangzai.lovesy.core.ui.recycler;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

/**
 * Created by wangzai on 2017/10/15
 */

public class DividerLookupImpl implements DividerItemDecoration.DividerLookup {

    private final int mColor;
    private final int mSize;

    public DividerLookupImpl(int color, int size) {
        this.mColor = color;
        this.mSize = size;
    }

    @Override
    public Divider getVerticalDivider(int position) {
        return new Divider.Builder()
                .size(mSize)
                .color(mColor)
                .build();
    }

    @Override
    public Divider getHorizontalDivider(int position) {
        return new Divider.Builder()
                .size(mSize)
                .color(mColor)
                .build();
    }
}
