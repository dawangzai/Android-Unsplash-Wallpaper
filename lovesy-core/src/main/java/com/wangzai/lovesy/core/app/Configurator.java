package com.wangzai.lovesy.core.app;

import com.wangzai.lovesy.core.net.HttpCreator;
import com.wangzai.lovesy.core.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by wangzai on 2017/8/18
 */

public class Configurator {
    public static final HashMap<Object, Object> LOVESY_CONFIGS = new HashMap<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = HttpCreator.getInterceptors();

    private Configurator() {
        LOVESY_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public HashMap<Object, Object> getLoveSyConfigs() {
        return LOVESY_CONFIGS;
    }

    public final Configurator withApiHost(String host) {
        LOVESY_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    public final Configurator withLogEnable(boolean logEnable, String tag) {
        LOVESY_CONFIGS.put(ConfigKeys.LOG_ENABLE, logEnable);
        LogUtil.init(logEnable, tag);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        LOVESY_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final void configure() {
        LOVESY_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    @SuppressWarnings("unchecked")
    public <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = LOVESY_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + "IS NULL !");
        }
        return (T) LOVESY_CONFIGS.get(key);
    }

    private void checkConfiguration() {
        boolean isReady = (boolean) LOVESY_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready !");
        }
    }
}
