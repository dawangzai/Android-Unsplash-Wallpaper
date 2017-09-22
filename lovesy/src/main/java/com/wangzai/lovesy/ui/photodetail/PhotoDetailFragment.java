package com.wangzai.lovesy.ui.photodetail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.mvp.base.BaseFragmentFragment;
import com.wangzai.lovesy.view.widget.PhotoImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.OnViewTapListener;

/**
 * Created by wangzai on 2017/2/21.
 */
public class PhotoDetailFragment extends BaseFragmentFragment implements PhotoDetailContract.View, View.OnClickListener {
    //    @BindView(R.id.dvPhoto)
//    DraweeImageView dvPhoto;
//    @BindView(R.id.zoomableImageView)
//    ZoomableImageView zoomableImageView;
    @BindView(R.id.photoDraweeView)
    PhotoImageView photoDraweeView;
    @BindView(R.id.llRightTools)
    LinearLayout llRightTools;

    @BindView(R.id.ivYuantu)
    ImageView ivYuantu;
    @BindView(R.id.ivDownload)
    ImageView ivDownload;
    @BindView(R.id.ivShare)
    ImageView ivShare;
    private PhotoDetailContract.Presenter mPresenter;

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
            this.mPresenter = present;
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
        ivDownload.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivYuantu.setOnClickListener(this);
    }

    private boolean isTranslate = false;

    @Override
    public void setPhoto(String lowUrl, String highUrl) {

//        ImageLoaderOp.setDetailImage(dvPhoto, lowUrl, highUrl);
//        ImageLoaderOp.setImage(dvPhoto, photoUrl);
//        ImageLoaderOp.setZoomableImage(zoomableImageView, lowUrl, highUrl);
        photoDraweeView.setPhotoUri(Uri.parse(highUrl));
//        final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.photo_detail_ta);

        photoDraweeView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
//                LogUtil.i("onclick", "onPhotoTap");
//                llRightTools.startAnimation(animation);
            }
        });

        photoDraweeView.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
//                LogUtil.i("onclick", "onViewTap");
//                llRightTools.startAnimation(animation);
            }
        });
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivDownload:
                mPresenter.download();
                break;
            case R.id.ivYuantu:
                break;
            case R.id.ivShare:
                break;
        }
    }
}
