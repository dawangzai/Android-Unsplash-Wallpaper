package com.wangzai.lovesy.api;

import com.wangzai.lovesy.BuildConfig;

/**
 * Created by wangzai on 2017/10/17
 */

public class ApiService {
    public static final String BASE_URL = BuildConfig.BASE_URL;

    public static final class Photos {
        public static final String PHOTOS = "photos/";
    }

    public static final class Collections {
        public static final String COLLECTIONS = "collections/";
        public static final String COLLECTIONS_FEATURED = "collections/featured";
    }

    public static final class User {
        public static final String USERS = "https://api.unsplash.com/users/";
        public static final String USERS_LIKES = "likes/";
        public static final String USERS_COLLECTIONS = "collections/";

    }

    public static final class Me {
        public static final String ME = "me";
    }

    public static final class Oauth {
        public static final String OAUTH = "https://unsplash.com/oauth/";
        public static final String OAUTH_TOKEN = "https://unsplash.com/oauth/token";
        public static final String OAUTH_AUTHORIZE = "https://unsplash.com/oauth/authorize?client_id=b05bfc46a0de4842346cb5ce7c766b3a8c9da071ec77f3b5f719406829c2fb31&redirect_uri=http://dawangzai.com&response_type=code&scope=public+read_user+write_user+read_collections+write_collections";
    }

}
