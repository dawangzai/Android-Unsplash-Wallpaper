package com.cleverzheng.wallpaper.network;

import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author：cleverzheng
 * @date：2017/2/15:14:37
 * @email：zhengwang043@gmail.com
 * @description：和照片集合有关的Api
 */

public interface CollectionApi {

    /**
     * 获取集合列表
     *
     * @return
     */
    @GET("collections/featured")
    Observable<List<CollectionBean>> getCollectionList(@Query("page") int page, @Query("per_page") int per_page);

    /**
     * 获取一个集合
     *
     * @param id 集合的id
     * @return
     */
    @GET("collections/{id}")
    Observable<CollectionBean> getSingleCollection(@Path("id") String id);

    /**
     * 获取集合中的照片列表
     *
     * @param id 集合的id
     * @return
     */
    @GET("collections/{id}/photos")
    Observable<List<PhotoBean>> getCollectionPhotoList(@Path("id") String id);
}
