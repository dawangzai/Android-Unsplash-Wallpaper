package com.cleverzheng.wallpaper.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.CoverPhotoBean;
import com.cleverzheng.wallpaper.bean.ProfileImageBean;
import com.cleverzheng.wallpaper.bean.UrlsBean;
import com.cleverzheng.wallpaper.bean.UserBean;
import com.cleverzheng.wallpaper.operator.ImageLoaderOp;
import com.cleverzheng.wallpaper.ui.persondetail.PersonCollectionFragment;
import com.cleverzheng.wallpaper.utils.StringUtil;
import com.cleverzheng.wallpaper.view.widget.DraweeImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：cleverzheng
 * @date：2017/3/8:10:12
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class PersonCollectionsAdapter extends RecyclerView.Adapter<PersonCollectionsAdapter.ViewHolder> {
    private Context context;
    private List<CollectionBean> collectionList;
    private PersonCollectionFragment personCollectionFragment;

    public PersonCollectionsAdapter(PersonCollectionFragment personCollectionFragment) {
        if (collectionList == null) {
            collectionList = new ArrayList<>();
        }
        this.context = personCollectionFragment.getFragmentContext();
        this.personCollectionFragment = personCollectionFragment;
    }

    /**
     * 刷新适配器数据
     *
     * @param personCollections
     */
    public void refreshData(List<CollectionBean> personCollections) {
        collectionList.addAll(0, personCollections);
        notifyDataSetChanged();
    }

    /**
     * 给适配器添加数据集合
     *
     * @param personCollections
     */
    public void loadMoreData(List<CollectionBean> personCollections) {
        collectionList.addAll(personCollections);
        notifyDataSetChanged();
    }

    @Override
    public PersonCollectionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_person_collection, parent, false);
        return new PersonCollectionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonCollectionsAdapter.ViewHolder holder, int position) {
        CollectionBean collectionBean = collectionList.get(position);
        if (collectionBean != null) {
            CoverPhotoBean cover_photo = collectionBean.getCover_photo();
            if (cover_photo != null) {
                UrlsBean urls = cover_photo.getUrls();
                if (urls != null) {
                    String small = urls.getSmall();
                    if (!StringUtil.isEmpty(small)) {
                        ImageLoaderOp.setImage(holder.dvPhoto, small);
                    }
                }
            }

            String title = collectionBean.getTitle();
            if (!StringUtil.isEmpty(title)) {
                holder.tvTitle.setText(title);
            }

            UserBean user = collectionBean.getUser();
            if (user != null) {
                String name = user.getName();
                if (!StringUtil.isEmpty(name)) {
                    holder.tvUserName.setText(name);
                }
                ProfileImageBean profile_image = user.getProfile_image();
                if (profile_image != null) {
                    String medium = profile_image.getMedium();
                    if (!StringUtil.isEmpty(medium)) {
                        ImageLoaderOp.setRoundImage(holder.dvUserHead, medium);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DraweeImageView dvPhoto;
        TextView tvCount;
        DraweeImageView dvUserHead;
        TextView tvUserName;
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            dvPhoto = (DraweeImageView) itemView.findViewById(R.id.dvPhoto);
//            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            dvUserHead = (DraweeImageView) itemView.findViewById(R.id.dvUserHead);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}
