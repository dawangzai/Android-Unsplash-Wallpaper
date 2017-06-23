package com.cleverzheng.wallpaper.http.api;

import com.cleverzheng.wallpaper.bean.LinksBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wangzai on 2017/5/25.
 */

public interface PhotoService {
    /**
     * 最新的图片列表
     *
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("photos/")
    Observable<Response<List<PhotoBean>>> getNewestPhotoList(@Query("page") int page, @Query("per_page") int per_page);

    /**
     * 获取单张图片
     *
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("photos/{id}")
    Observable<Response<PhotoBean>> getSinglePhoto(@Path("id") String id);

    @GET("photos/{id}/download")
    Observable<Response<LinksBean>> getPhotoDownload(@Path("id") String id);
}
