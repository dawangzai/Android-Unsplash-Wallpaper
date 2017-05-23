package com.cleverzheng.wallpaper.ui.photodetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseFragment;
import com.cleverzheng.wallpaper.operator.ImageLoaderOp;
import com.cleverzheng.wallpaper.view.widget.MyDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author：cleverzheng
 * @date：2017/2/21:17:14
 * @email：zhengwang043@gmail.com
 * @description：照片详情
 */

public class PhotoDetailFragment extends BaseFragment implements PhotoDetailContract.View {
    @BindView(R.id.dvPhoto)
    MyDraweeView dvPhoto;
    private PhotoDetailContract.Presenter presenter;

    public static PhotoDetailFragment newInstance() {
        PhotoDetailFragment fragment = new PhotoDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_photodetail);
//        ButterKnife.bind(this, getContentView());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_persondetail, container, false);
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
    public void initData() {
        super.initData();
        setBackToolbar("美图");
    }

    @Override
    public void setPhoto(String photoUrl) {
//        dismissLoading();
        ImageLoaderOp.setImage(dvPhoto, photoUrl);
    }
}
