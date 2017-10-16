package com.wangzai.lovesy.core.ui.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * Created by wangzai on 2017/10/15
 */

public class BaseDivider extends DividerItemDecoration {

    private BaseDivider(@ColorInt int color, int size) {
        setDividerLookup(new DividerLookupImpl(color, size));
    }

    public static BaseDivider create(@ColorInt int color, int size) {
        return new BaseDivider(color, size);
    }
}
