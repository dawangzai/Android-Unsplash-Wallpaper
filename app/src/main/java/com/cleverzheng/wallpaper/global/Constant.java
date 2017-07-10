package com.cleverzheng.wallpaper.global;

/**
 * Created by wangzai on 2017/2/18.
 */

public class Constant {

    public static final String TAG_LOG = "WallpaperLog";

    public final static int PER_PAGE = 10; //每页加载10条数据

    public static class Intent {
        public final static String INTENT_DATA_ONE = "INTENT_DATA_ONE";
        public final static String INTENT_DATA_TWO = "INTENT_DATA_TWO";
    }

    public static class Arguments {
        public final static String ARGUMENT_DATA_ONE = "ARGUMENT_DATA_ONE";
    }

    public static class PhotoListAdapterType {
        public final static int NEWEST = 0;
        public final static int PHOTO_DETAIL = 1;
        public final static int COLLECTION_DETAIL = 2;
    }
}
