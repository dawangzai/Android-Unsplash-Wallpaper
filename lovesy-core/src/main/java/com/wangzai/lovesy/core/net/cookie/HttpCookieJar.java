package com.wangzai.lovesy.core.net.cookie;

import com.wangzai.lovesy.core.net.HttpCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by wangzai on 2017/8/24
 */

public class HttpCookieJar implements CookieJar {

    private static final HashMap<HttpUrl, List<Cookie>> COOKIES = HttpCreator.getCookies();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        COOKIES.put(url, cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = COOKIES.get(url);
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }
}
