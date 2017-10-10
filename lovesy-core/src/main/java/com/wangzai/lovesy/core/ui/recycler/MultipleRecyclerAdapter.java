package com.wangzai.lovesy.core.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangzai.lovesy.core.R;
import com.wangzai.lovesy.core.ui.imageloader.ImageLoader;
import com.wangzai.lovesy.core.widget.SimpleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzai on 2017/9/2
 */

public class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup {

    private MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create() {
        return new MultipleRecyclerAdapter(new ArrayList<MultipleItemEntity>());
    }

    private void init() {
        addItemType(ItemType.PHOTO, R.layout.item_photo);
        addItemType(ItemType.COLLECTION_PHOTO, R.layout.item_personal_collection);
        addItemType(ItemType.COLLECTION, R.layout.item_collection);
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity item) {

        final String photoUrl;
        final String avatarUrl;
        final String userName;
        final int totalLikes;
        final String collectionName;
        final int collectionPhotoCount;

        switch (holder.getItemViewType()) {
            case ItemType.PHOTO:
                photoUrl = item.getField(MultipleFields.PHOTO_URL);
                avatarUrl = item.getField(MultipleFields.AVATAR_URL);
                userName = item.getField(MultipleFields.USER_NAME);
                totalLikes = item.getField(MultipleFields.TOTAL_LIKES);

                ImageLoader.loaderImage((SimpleImageView) holder.getView(R.id.siv_photo), photoUrl);
                ImageLoader.loaderImage((SimpleImageView) holder.getView(R.id.siv_avatar), avatarUrl);
                holder.setText(R.id.tv_user_name, userName);
                holder.setText(R.id.tv_like, String.valueOf(totalLikes));
                holder.addOnClickListener(R.id.siv_photo)
                        .addOnClickListener(R.id.siv_avatar)
                        .addOnClickListener(R.id.tv_like)
                        .addOnClickListener(R.id.tv_user_name)
                        .addOnClickListener(R.id.iv_add_collect);
                break;
            case ItemType.COLLECTION_PHOTO:
                photoUrl = item.getField(MultipleFields.PHOTO_URL);
                avatarUrl = item.getField(MultipleFields.AVATAR_URL);
                userName = item.getField(MultipleFields.USER_NAME);

                ImageLoader.loaderImage((SimpleImageView) holder.getView(R.id.siv_photo), photoUrl);
                ImageLoader.loaderImage((SimpleImageView) holder.getView(R.id.siv_avatar), avatarUrl);
                holder.setText(R.id.tv_user_name, userName);
                break;
            case ItemType.COLLECTION:
                photoUrl = item.getField(MultipleFields.PHOTO_URL);
                avatarUrl = item.getField(MultipleFields.AVATAR_URL);
                userName = item.getField(MultipleFields.USER_NAME);
                collectionName = item.getField(MultipleFields.COLLECTION_NAME);
                collectionPhotoCount = item.getField(MultipleFields.COLLECTION_PHOTO_COUNT);

                ImageLoader.loaderImage((SimpleImageView) holder.getView(R.id.siv_avatar), avatarUrl);
                ImageLoader.loaderImage((SimpleImageView) holder.getView(R.id.siv_photo), photoUrl);
                holder.setText(R.id.tv_user_name, userName);
                holder.setText(R.id.tv_collection_name, collectionName);
                holder.setText(R.id.tv_collection_photo_count, String.valueOf(collectionPhotoCount));
                break;
            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }
}
