package com.cleverzheng.wallpaper.network;

import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author：cleverzheng
 * @date：2017/2/15:14:32
 * @email：zhengwang043@gmail.com
 * @description：和用户有关的Api
 */

public interface UserApi {

    /**
     * 查询用户的公开信息
     *
     * @param username 用户名
     * @return
     */
    @GET("users/{username}")
    Observable<UserBean> getUserInfo(@Path("username") String username);

    /**
     * 查询用户的照片列表
     *
     * @param username
     * @return
     */
    @GET("users/{username}/photos")
    Observable<List<PhotoBean>> getUserPhotoList(@Path("username") String username);

    /**
     * 查询用户的照片集合列表
     *
     * @param username
     * @return
     */
    @GET("users/{username}/collections")
    Observable<List<CollectionBean>> getUserCollectionList(@Path("username") String username);
}
