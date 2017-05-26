package com.cleverzheng.wallpaper.http.api;

import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wangzai on 2017/5/26.
 */

public interface CollectionService {
    /**
     * 获取集合列表
     *
     * @return
     */
    @GET("collections/featured")
    Observable<Response<List<CollectionBean>>> getCollectionList(@Query("page") int page, @Query("per_page") int per_page);

    /**
     * 获取一个集合
     *
     * @param id 集合的id
     * @return
     */
    @GET("collections/{id}")
    Observable<Response<CollectionBean>> getSingleCollection(@Path("id") String id);

    /**
     * 获取集合中的照片列表
     *
     * @param id 集合的id
     * @return
     */
    @GET("collections/{id}/photos")
    Observable<Response<List<PhotoBean>>> getCollectionPhotoList(@Path("id") String id);
}
