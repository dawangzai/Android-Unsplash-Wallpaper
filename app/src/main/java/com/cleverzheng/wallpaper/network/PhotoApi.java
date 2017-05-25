package com.cleverzheng.wallpaper.network;

import com.cleverzheng.wallpaper.api.ApiResponse;
import com.cleverzheng.wallpaper.bean.PhotoBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author：cleverzheng
 * @date：2017/1/17:17:06
 * @email：zhengwang043@gmail.com
 * @description：和照片有关的Api
 */

public interface PhotoApi {
    /**
     * 最新的图片列表
     *
     * @return
     */
    @GET("photos/")
    Observable<List<PhotoBean>> getNewestPhotoList(@Query("page") int page, @Query("per_page") int per_page);

    /**
     * 获取单张图片
     *
     * @return
     */
    @GET("photos/{id}")
    Observable<PhotoBean> getSinglePhoto(@Path("id") String id);
}
