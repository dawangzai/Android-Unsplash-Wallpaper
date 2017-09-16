package com.wangzai.lovesy.core.fragment.web.Route;

import com.wangzai.lovesy.core.activity.LoveSyActivity;
import com.wangzai.lovesy.core.fragment.web.WebFragment;

/**
 * Created by wangzai on 2017/9/16
 */

public class Route {
    private Route() {
    }

    private static final class Holder {
        static final Route INSTANCE = new Route();
    }

    public static Route getInstance() {
        return Holder.INSTANCE;
    }

    public final boolean handleWebUrl(LoveSyActivity activity,String url) {

//        activity.loadFragment(fragment);
        return true;
    }
}
