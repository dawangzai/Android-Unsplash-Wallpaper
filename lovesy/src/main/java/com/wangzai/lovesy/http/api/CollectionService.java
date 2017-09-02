package com.wangzai.lovesy.http.api;

import com.wangzai.lovesy.bean.CollectionBean;
import com.wangzai.lovesy.bean.PhotoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
    @Headers("Cache-Control: public, max-age=3600")
    @GET("collections/featured")
    Observable<Response<List<CollectionBean>>> getCollectionList(@Query("page") int page, @Query("per_page") int per_page);

    /**
     * 获取一个集合
     *
     * @param id 集合的id
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("collections/{id}")
    Observable<Response<CollectionBean>> getSingleCollection(@Path("id") int id);

    /**
     * 获取集合中的照片列表
     *
     * @param id 集合的id
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("collections/{id}/photos")
    Observable<Response<List<PhotoBean>>> getCollectionPhotoList(@Path("id") int id);
}
