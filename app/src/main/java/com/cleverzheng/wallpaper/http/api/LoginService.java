package com.cleverzheng.wallpaper.http.api;

import com.cleverzheng.wallpaper.bean.AccessToken;
import com.cleverzheng.wallpaper.bean.PhotoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wangzai on 2017/7/12.
 */

public interface LoginService {
    @POST("oauth/token")
    Observable<Response<AccessToken>> getAccessToken(@Query("client_id") String client_id,
                                                     @Query("client_secret") String client_secret,
                                                     @Query("redirect_uri") String redirect_uri,
                                                     @Query("code") String code,
                                                     @Query("grant_type") String grant_type);

    @GET("oauth/authorize")
    Observable<Response<AccessToken>> getAuthorizationCode(@Query("client_id") String client_id,
                                                           @Query("redirect_uri") String redirect_uri,
                                                           @Query("response_type") String response_type,
                                                           @Query("scope") String scope);
}
