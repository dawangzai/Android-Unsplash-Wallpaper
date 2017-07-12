package com.cleverzheng.wallpaper.http.api;

import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by wangzai on 2017/5/26.
 */

public interface UserService {
    /**
     * 查询用户的公开信息
     *
     * @param username 用户名
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("users/{username}")
    Observable<Response<UserBean>> getUserInfo(@Path("username") String username);

    /**
     * 查询用户的照片列表
     *
     * @param username
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("users/{username}/photos")
    Observable<Response<List<PhotoBean>>> getUserPhotoList(@Path("username") String username);

    /**
     * 查询用户的照片集合列表
     *
     * @param username
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("users/{username}/collections")
    Observable<Response<List<CollectionBean>>> getUserCollectionList(@Path("username") String username);
}
