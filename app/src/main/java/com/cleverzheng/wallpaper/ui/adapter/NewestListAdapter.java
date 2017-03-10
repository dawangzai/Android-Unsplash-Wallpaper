package com.cleverzheng.wallpaper.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.bean.ProfileImageBean;
import com.cleverzheng.wallpaper.bean.UrlsBean;
import com.cleverzheng.wallpaper.bean.UserBean;
import com.cleverzheng.wallpaper.operator.ImageLoaderOp;
import com.cleverzheng.wallpaper.ui.newest.NewestFragment;
import com.cleverzheng.wallpaper.utils.StringUtil;
import com.cleverzheng.wallpaper.view.widget.MyDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：cleverzheng
 * @date：2017/2/28:18:09
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class NewestListAdapter extends RecyclerView.Adapter<NewestListAdapter.ViewHolder> {
    private Context context;
    private List<PhotoBean> photoList;
    private NewestFragment newestFragment;

    public NewestListAdapter(NewestFragment newestFragment) {
        if (photoList == null) {
            photoList = new ArrayList<>();
        }
        this.context = newestFragment.getFragmentContext();
        this.newestFragment = newestFragment;
    }

    /**
     * 刷新适配器数据
     *
     * @param newestPhotoList
     */
    public void refreshData(List<PhotoBean> newestPhotoList) {
        photoList.addAll(0, newestPhotoList);
        notifyDataSetChanged();
    }

    /**
     * 给适配器添加数据集合
     *
     * @param newestPhotoList
     */
    public void loadMoreData(List<PhotoBean> newestPhotoList) {
        photoList.addAll(newestPhotoList);
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
                    newestFragment.clickPhotoDetail(finalPhotoId);
                }
            }
        });

        final String finalUsername = username;
        holder.dvUserHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtil.isEmpty(finalUsername)) {
                    newestFragment.clickUserDetail(finalUsername);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MyDraweeView dvPhoto;
        MyDraweeView dvUserHead;
        TextView tvUserName;

        public ViewHolder(View itemView) {
            super(itemView);
            dvPhoto = (MyDraweeView) itemView.findViewById(R.id.dvPhoto);
            dvUserHead = (MyDraweeView) itemView.findViewById(R.id.dvUserHead);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
        }
    }
}
