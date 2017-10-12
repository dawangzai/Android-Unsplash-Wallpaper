package com.wangzai.lovesy.core.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * Created by wangzai on 2017/10/12
 */

public class FontLoveSyModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return LoveSyIcons.values();
    }
}
