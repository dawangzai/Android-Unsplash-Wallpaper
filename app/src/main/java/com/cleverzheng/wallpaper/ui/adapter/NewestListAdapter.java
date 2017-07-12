package com.cleverzheng.wallpaper.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseFragmentFragment;
import com.cleverzheng.wallpaper.bean.LinksBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.bean.ProfileImageBean;
import com.cleverzheng.wallpaper.bean.UrlsBean;
import com.cleverzheng.wallpaper.bean.UserBean;
import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.operator.ImageLoaderOp;
import com.cleverzheng.wallpaper.ui.collectiondetail.CollectionDetailFragment;
import com.cleverzheng.wallpaper.ui.newest.NewestFragment;
import com.cleverzheng.wallpaper.ui.persondetail.PersonPhotosFragment;
import com.cleverzheng.wallpaper.utils.StringUtil;
import com.cleverzheng.wallpaper.utils.ToastUtil;
import com.cleverzheng.wallpaper.view.widget.DraweeImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzai on 2017/2/28.
 */

public class NewestListAdapter extends RecyclerView.Adapter<NewestListAdapter.ViewHolder> {
    private int adapterType = -1;
    private Context context;
    private List<PhotoBean> photoList;
    private NewestFragment newestFragment;
    private PersonPhotosFragment personPhotosFragment;
    private CollectionDetailFragment collectionDetailFragment;

    public <T extends BaseFragmentFragment> NewestListAdapter(int adapterType, T mFragment) {
        if (photoList == null) {
            photoList = new ArrayList<>();
        }
        if (mFragment != null) {
            this.context = mFragment.getFragmentContext();
            this.adapterType = adapterType;
            if (adapterType == Constant.PhotoListAdapterType.NEWEST) {
                this.newestFragment = (NewestFragment) mFragment;
            } else if (adapterType == Constant.PhotoListAdapterType.PHOTO_DETAIL) {
                this.personPhotosFragment = (PersonPhotosFragment) mFragment;
            } else if (adapterType == Constant.PhotoListAdapterType.COLLECTION_DETAIL) {
                this.collectionDetailFragment = (CollectionDetailFragment) mFragment;
            }
        }
    }

    /**
     * 刷新适配器数据
     *
     * @param photos
     */
    public void refreshData(List<PhotoBean> photos) {
        photoList.addAll(0, photos);
        notifyDataSetChanged();
    }

    /**
     * 给适配器添加数据集合
     *
     * @param photos
     */
    public void loadMoreData(List<PhotoBean> photos) {
        photoList.addAll(photos);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String photoId = "";
        String username = "";

        PhotoBean photoBean = photoList.get(position);
        if (photoBean != null) {
            photoId = photoBean.getId();
            UserBean user = photoBean.getUser();
            UrlsBean urls = photoBean.getUrls();
            if (user != null) {
                ProfileImageBean profileImage = user.getProfile_image();
                if (profileImage != null) {
                    String small = profileImage.getMedium();
                    if (!StringUtil.isEmpty(small)) {
                        ImageLoaderOp.setRoundImage(holder.dvUserHead, small);
                    }
                }

                username = user.getUsername();
                if (!StringUtil.isEmpty(username)) {
                    holder.tvUserName.setText(username);
                }
            }

            if (urls != null) {
                String regular = urls.getRegular();
                if (!StringUtil.isEmpty(regular)) {
                    ImageLoaderOp.setImage(holder.dvPhoto, regular);
                }
            }
        }

        final String finalPhotoId = photoId;
        holder.dvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtil.isEmpty(finalPhotoId)) {
                    if (newestFragment != null) {
                        newestFragment.clickPhotoDetail(finalPhotoId);
                    }

                    if (collectionDetailFragment != null) {

                    }
                }
            }
        });

        final String finalUsername = username;
        holder.dvUserHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtil.isEmpty(finalUsername)) {
                    if (newestFragment != null) {
                        newestFragment.clickUserDetail(finalUsername);
                    }
                    if (collectionDetailFragment != null) {

                    }
                }
            }
        });

        LinksBean links = photoBean.getLinks();
        final String download = links.getDownload();
        holder.ivAddCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                newestFragment.downloadPicture(finalPhotoId);
                ToastUtil.showShort(R.string.login_note);
            }
        });

        int likes = photoBean.getLikes();
        if (likes > 0) {
            holder.tvLike.setText(String.valueOf(likes));
        }
        holder.tvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(R.string.login_note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DraweeImageView dvPhoto;
        DraweeImageView dvUserHead;
        TextView tvUserName;
        ImageView ivAddCollect;
        TextView tvLike;

        public ViewHolder(View itemView) {
            super(itemView);
            dvPhoto = (DraweeImageView) itemView.findViewById(R.id.dvPhoto);
            dvUserHead = (DraweeImageView) itemView.findViewById(R.id.dvUserHead);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            ivAddCollect = (ImageView) itemView.findViewById(R.id.ivAddCollect);
            tvLike = (TextView) itemView.findViewById(R.id.tvLike);
        }
    }
}
