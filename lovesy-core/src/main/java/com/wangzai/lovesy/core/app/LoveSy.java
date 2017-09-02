package com.wangzai.lovesy.core.app;

import android.content.Context;

/**
 * Created by wangzai on 2017/8/18
 */

public class LoveSy {

    public static Configurator init(Context context) {
        Configurator.getInstance().getLoveSyConfigs().put(ConfigKeys.APPLICATION_CONTEXT, context);
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }
}
