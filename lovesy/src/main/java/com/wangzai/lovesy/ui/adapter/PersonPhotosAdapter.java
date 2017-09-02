package com.wangzai.lovesy.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.bean.PhotoBean;
import com.wangzai.lovesy.bean.ProfileImageBean;
import com.wangzai.lovesy.bean.UrlsBean;
import com.wangzai.lovesy.bean.UserBean;
import com.wangzai.lovesy.operator.ImageLoaderOp;
import com.wangzai.lovesy.ui.persondetail.PersonPhotosFragment;
import com.wangzai.lovesy.utils.StringUtil;
import com.wangzai.lovesy.view.widget.DraweeImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：cleverzheng
 * @date：2017/3/7:15:14
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class PersonPhotosAdapter extends RecyclerView.Adapter<PersonPhotosAdapter.ViewHolder>{
    private Context context;
    private List<PhotoBean> photoList;
    private PersonPhotosFragment personPhotosFragment;

    public PersonPhotosAdapter(PersonPhotosFragment personPhotosFragment) {
        if (photoList == null) {
            photoList = new ArrayList<>();
        }
        this.context = personPhotosFragment.getFragmentContext();
        this.personPhotosFragment = personPhotosFragment;
    }

    /**
     * 刷新适配器数据
     *
     * @param personPhotos
     */
    public void refreshData(List<PhotoBean> personPhotos) {
        photoList.addAll(0, personPhotos);
        notifyDataSetChanged();
    }

    /**
     * 给适配器添加数据集合
     *
     * @param personPhotos
     */
    public void loadMoreData(List<PhotoBean> personPhotos) {
        photoList.addAll(personPhotos);
        notifyDataSetChanged();
    }

    @Override
    public PersonPhotosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_photo, parent, false);
        return new PersonPhotosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonPhotosAdapter.ViewHolder holder, int position) {
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
//                    newestFragment.clickPhotoDetail(finalPhotoId);
                }
            }
        });

        final String finalUsername = username;
        holder.dvUserHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtil.isEmpty(finalUsername)) {
//                    newestFragment.clickUserDetail(finalUsername);
                }
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

        public ViewHolder(View itemView) {
            super(itemView);
            dvPhoto = (DraweeImageView) itemView.findViewById(R.id.dvPhoto);
            dvUserHead = (DraweeImageView) itemView.findViewById(R.id.dvUserHead);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
        }
    }
}
