package com.wangzai.lovesy.core.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by wangzai on 2017/10/12
 */

public enum LoveSyIcons implements Icon {
    icon_sousuo('\ue62f'),
    icon_xiazai('\ue608'),
    icon_xihuan('\ue62c'),
    icon_album('\ue61d'),
    icon_refresh('\ue652');

    private char character;

    LoveSyIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
