package com.cleverzheng.wallpaper.view.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.utils.DensityUtil;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by wangzai on 2017/5/22.
 */

public class RefreshLayout extends PtrFrameLayout {
    private int[] colors = new int[]{Color.parseColor("#6E9AC1"), Color.parseColor("#6E9AC1"),
            Color.parseColor("#6E9AC1"), Color.parseColor("#6E9AC1")};

    public RefreshLayout(Context context) {
        super(context);

        this.initConfig();
        this.initHeader();
//        this.initFooter();
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.initConfig();
        this.initHeader();
//        this.initFooter();
    }

    private void initFooter() {

//        View view = LayoutInflater.from(getContext()).inflate(R.layout.loading, null);
        MaterialHeader footer = new MaterialHeader(getContext());
        footer.setColorSchemeColors(colors);
        footer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        footer.setPadding(0, 20, 0, 25);
        footer.setPtrFrameLayout(this);

        this.addPtrUIHandler(footer);
        this.setFooterView(footer);
    }

    private void initHeader() {
        MaterialHeader header = new MaterialHeader(getContext());
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setPadding(0, 25, 0, 20);
        header.setPtrFrameLayout(this);

        this.addPtrUIHandler(header);
        this.setHeaderView(header);
    }

    private void initConfig() {
        setOffsetToRefresh(DensityUtil.dp2px(getContext(), 100));
        /**
         * 阻尼系数
         * 默认: 1.7f，越大，感觉下拉时越吃力
         */
        setResistance(1.7f);

        /**
         * 触发刷新时移动的位置比例
         * 默认，1.2f，移动达到头部高度1.2倍时可触发刷新操作
         */
        setRatioOfHeaderHeightToRefresh(1.2f);

        /**
         * 回弹延时
         * 默认 200ms，回弹到刷新高度所用时间
         */
        setDurationToClose(200);

        /**
         * 头部回弹时间
         * 默认1000ms
         */
        setDurationToCloseHeader(1000);

        /**
         * 刷新是保持头部
         * 默认值 true
         */
        setKeepHeaderWhenRefresh(true);

        /**
         * 下拉刷新 / 释放刷新
         * 默认为释放刷新 false
         */
        setPullToRefresh(true);

        /**
         * 刷新时，保持内容不动，仅头部下移, 使用 Material Design 风格才好看一点
         * 默认 false
         */
        setPinContent(true);

        /**
         * 刷新模式
         * 默认 TOP：只支持下拉
         * Bottom：只支持上拉
         * BOTH：两种同时支持
         */
        setMode(Mode.BOTH);
    }
}
