package com.wangzai.lovesy.core.net.rx;

import java.util.HashMap;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by wangzai on 2017/8/18
 */

public interface RxHttpService {

    @GET
    Observable<String> get(@Url String url, @QueryMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap HashMap<String, Object> params);

    @POST
    Observable<String> postRaw(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap HashMap<String, Object> params);

    @PUT
    Observable<String> putRaw(@Url String url, @Body RequestBody body);

    @DELETE
    Observable<String> delete(@Url String url, @QueryMap HashMap<String, Object> params);
}
