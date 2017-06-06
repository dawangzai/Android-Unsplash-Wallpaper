package com.cleverzheng.wallpaper.ui.photodetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseFragmentFragment;
import com.cleverzheng.wallpaper.operator.ImageLoaderOp;
import com.cleverzheng.wallpaper.view.widget.MyDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzai on 2017/2/21.
 */
public class PhotoDetailFragment extends BaseFragmentFragment implements PhotoDetailContract.View {
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
    public void initData() {
        super.initData();
    }

    @Override
    public void setPhoto(String photoUrl) {
        ImageLoaderOp.setImage(dvPhoto, photoUrl);
    }
}
