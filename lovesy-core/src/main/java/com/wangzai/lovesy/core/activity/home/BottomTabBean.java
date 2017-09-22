package com.wangzai.lovesy.core.activity.home;

/**
 * Created by wangzai on 2017/9/21
 */

public class BottomTabBean {

    private final CharSequence icon;
    private final CharSequence title;

    public BottomTabBean(CharSequence icon, CharSequence title) {
        this.icon = icon;
        this.title = title;
    }

    public CharSequence getIcon() {
        return icon;
    }

    public CharSequence getTitle() {
        return title;
    }
}
