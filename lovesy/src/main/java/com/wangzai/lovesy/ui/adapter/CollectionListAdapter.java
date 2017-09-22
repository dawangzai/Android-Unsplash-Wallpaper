package com.wangzai.lovesy.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.bean.CollectionBean;
import com.wangzai.lovesy.bean.CoverPhotoBean;
import com.wangzai.lovesy.bean.ProfileImageBean;
import com.wangzai.lovesy.bean.UrlsBean;
import com.wangzai.lovesy.bean.UserBean;
import com.wangzai.lovesy.mvp.operator.ImageLoaderOp;
import com.wangzai.lovesy.ui.collection.CollectionFragment;
import com.wangzai.lovesy.utils.ColorUtil;
import com.wangzai.lovesy.utils.StringUtil;
import com.wangzai.lovesy.view.widget.DraweeImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzai on 2017/2/24.
 */

public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.ViewHolder> {
    private List<CollectionBean> collectionBeanList;
    private CollectionFragment collectionFragment;

    public CollectionListAdapter(CollectionFragment collectionFragment) {
        this.collectionFragment = collectionFragment;
        if (collectionBeanList == null) {
            collectionBeanList = new ArrayList<>();
        }
    }

    /**
     * 刷新适配器数据
     *
     * @param collectionBeanList
     */
    public void refreshData(List<CollectionBean> collectionBeanList) {
        this.collectionBeanList.addAll(0, collectionBeanList);
        notifyDataSetChanged();
    }

    /**
     * 给适配器添加数据集合
     *
     * @param collectionBeanList
     */
    public void loadMoreData(List<CollectionBean> collectionBeanList) {
        this.collectionBeanList.addAll(collectionBeanList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(collectionFragment.getFragmentContext()).inflate(R.layout.recycleritem_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.llDesc.setBackgroundColor(Color.parseColor(ColorUtil.getRandomColor()));
        final CollectionBean collectionBean = collectionBeanList.get(position);
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
                holder.tvCollectionsName.setText(title);
            }

            int total_photos = collectionBean.getTotal_photos();
            if (total_photos > 0) {
                holder.tvCollectionsImages.setText(total_photos + "张");
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
            holder.cvCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    collectionFragment.clickCollectionDetail(collectionBean.getId());

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return collectionBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DraweeImageView dvPhoto;
        TextView tvCount;
        DraweeImageView dvUserHead;
        TextView tvUserName;
        TextView tvTitle;
        LinearLayout llDesc;
        TextView tvCollectionsName;
        TextView tvCollectionsImages;
        CardView cvCollection;

        public ViewHolder(View itemView) {
            super(itemView);
            dvPhoto = (DraweeImageView) itemView.findViewById(R.id.dvPhoto);
//            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            dvUserHead = (DraweeImageView) itemView.findViewById(R.id.dvUserHead);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            llDesc = (LinearLayout) itemView.findViewById(R.id.llDesc);

            tvCollectionsName = (TextView) itemView.findViewById(R.id.tvCollectionsName);
            tvCollectionsImages = (TextView) itemView.findViewById(R.id.tvCollectionsImages);
            cvCollection = (CardView) itemView.findViewById(R.id.cvCollection);
        }
    }
}
