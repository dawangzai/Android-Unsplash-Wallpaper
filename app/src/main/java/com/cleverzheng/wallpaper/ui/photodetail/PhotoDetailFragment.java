package com.cleverzheng.wallpaper.ui.photodetail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseFragmentFragment;
import com.cleverzheng.wallpaper.view.widget.PhotoImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzai on 2017/2/21.
 */
public class PhotoDetailFragment extends BaseFragmentFragment implements PhotoDetailContract.View {
    //    @BindView(R.id.dvPhoto)
//    DraweeImageView dvPhoto;
//    @BindView(R.id.zoomableImageView)
//    ZoomableImageView zoomableImageView;
    @BindView(R.id.photoDraweeView)
    PhotoImageView photoDraweeView;
    private PhotoDetailContract.Presenter presenter;

    public static PhotoDetailFragment newInstance() {
        PhotoDetailFragment fragment = new PhotoDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photodetail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setPresent(PhotoDetailContract.Presenter present) {
        if (present != null) {
            this.presenter = present;
        }
        present.getSinglePhoto();
    }

    @Override
    public void loadDataSuccess() {

    }

    @Override
    public void loadDataFailed(String message) {

    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void setPhoto(String lowUrl, String highUrl) {

//        ImageLoaderOp.setDetailImage(dvPhoto, lowUrl, highUrl);
//        ImageLoaderOp.setImage(dvPhoto, photoUrl);
//        ImageLoaderOp.setZoomableImage(zoomableImageView, lowUrl, highUrl);
        photoDraweeView.setPhotoUri(Uri.parse(highUrl));
    }

    @Override
    public void setPhoto(String imageUrl) {
//        ImageLoaderOp.setImage(dvPhoto, imageUrl);
    }

    @Override
    public void setImageSize(int width, int height) {
//        if (zoomableImageView != null) {
//            ViewGroup.LayoutParams layoutParams = zoomableImageView.getLayoutParams();
//            layoutParams.width = width;
//            layoutParams.height = height;
//            zoomableImageView.setLayoutParams(layoutParams);
//        }
    }
}
