package com.wangzai.lovesy.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.bean.PhotoBean;
import com.wangzai.lovesy.bean.ProfileImageBean;
import com.wangzai.lovesy.bean.UrlsBean;
import com.wangzai.lovesy.bean.UserBean;
import com.wangzai.lovesy.operator.ImageLoaderOp;
import com.wangzai.lovesy.ui.newest.NewestFragment;
import com.wangzai.lovesy.utils.StringUtil;
import com.wangzai.lovesy.view.widget.DraweeImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：cleverzheng
 * @date：2017/2/18:11:22
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class PhotoListAdapter extends BaseAdapter {
    private Context context;
    private List<PhotoBean> photoList;
    private NewestFragment newestFragment;

    public PhotoListAdapter(NewestFragment newestFragment) {
        photoList = new ArrayList<>();
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
    }

    /**
     * 给适配器添加数据集合
     *
     * @param newestPhotoList
     */
    public void loadMoreData(List<PhotoBean> newestPhotoList) {
        photoList.addAll(newestPhotoList);
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_photo, parent, false);
            holder.dvPhoto = (DraweeImageView) convertView.findViewById(R.id.dvPhoto);
            holder.dvUserHead = (DraweeImageView) convertView.findViewById(R.id.dvUserHead);
            holder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

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

                username = user.getName();
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

        return convertView;
    }

    class ViewHolder {
        DraweeImageView dvPhoto;
        DraweeImageView dvUserHead;
        TextView tvUserName;
    }
}
