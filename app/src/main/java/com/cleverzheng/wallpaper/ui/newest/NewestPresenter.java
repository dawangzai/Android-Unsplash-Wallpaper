package com.cleverzheng.wallpaper.ui.newest;

import android.app.Activity;
import android.util.Log;

import com.cleverzheng.wallpaper.MainActivity;
import com.cleverzheng.wallpaper.api.ApiResponse;
import com.cleverzheng.wallpaper.api.HttpClient;
import com.cleverzheng.wallpaper.api.PhotoService;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.network.Network;
import com.cleverzheng.wallpaper.network.PhotoApi;
import com.cleverzheng.wallpaper.operator.OpenActivityOp;
import com.cleverzheng.wallpaper.utils.LogUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by wangzai on 2017/2/12.
 */
public class NewestPresenter implements NewestContract.Presenter {
    private NewestContract.View newestView;

    //    private PhotoApi photoApi;
    private PhotoService photoService;
    private MainActivity activity;

    public NewestPresenter(NewestContract.View newestView, Activity activity) {
        this.activity = (MainActivity) activity;
        this.newestView = newestView;
        newestView.setPresent(this);
    }

    @Override
    public void start() {
//        photoApi = Network.getInstance().getPhotoApi();
        photoService = HttpClient.getInstance().getPhotoService();
        refreshData(1, Constant.PER_PAGE);
    }

    @Override
    public void refreshData(int page, int per_page) {
//        if (photoApi != null) {
//            photoApi.getNewestPhotoList(page, per_page)
//                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<List<PhotoBean>>() {
//                        @Override
//                        public void onCompleted() {
//                            Log.i("abc", "onCompleted");
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.i("abc", e.toString());
//                        }
//
//                        @Override
//                        public void onNext(List<PhotoBean> photoBeen) {
////                            ((MainActivity) activity).dismissLoading();
//                            if (photoBeen != null && photoBeen.size() > 0) {
//                                newestView.refresh(photoBeen);
//                            }
//                        }
//                    });
//        }

        if (photoService != null) {
            photoService.getNewestPhotoList(page, per_page)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiResponse<List<PhotoBean>>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull ApiResponse<List<PhotoBean>> listResponse) {
                            int code = listResponse.code;
                            List<PhotoBean> body = listResponse.body;
//                            listResponse.body();
//                            Headers headers = listResponse.headers();
//                            ResponseBody responseBody = listResponse.errorBody();
//                            int code = listResponse.code();
//                            boolean successful = listResponse.isSuccessful();
//                            String message = listResponse.message();
//                            okhttp3.Response raw = listResponse.raw();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @Override
    public void loadMoreData(int page, int per_page) {
//        if (photoApi != null) {
//            photoApi.getNewestPhotoList(page, per_page)
//                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<List<PhotoBean>>() {
//                        @Override
//                        public void onCompleted() {
//                            Log.i("abc", "onCompleted");
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.i("abc", e.toString());
//                        }
//
//                        @Override
//                        public void onNext(List<PhotoBean> photoBeen) {
//                            if (photoBeen != null && photoBeen.size() > 0) {
//                                newestView.loadMore(photoBeen);
//                            }
//                        }
//                    });
//        }
    }

    @Override
    public void openPhotoDetail(String photoId) {
        OpenActivityOp.openPhotoDetailActivity(activity, photoId);
    }

    @Override
    public void openUserDetail(String username) {
        OpenActivityOp.openPersonDetailActivity(activity, username);
    }
}
