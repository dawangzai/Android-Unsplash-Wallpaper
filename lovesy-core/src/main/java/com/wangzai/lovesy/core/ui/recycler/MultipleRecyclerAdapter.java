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

    private static List<MultipleItemEntity> dataList = new ArrayList<>();

    private MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create() {
        return new MultipleRecyclerAdapter(dataList);
    }

    private void init() {
        addItemType(ItemType.PERSONAL_PHOTO, R.layout.item_personal_photo);
        addItemType(ItemType.PERSONAL_COLLECTION, R.layout.item_personal_collection);
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

        final String photoUrl = item.getField(MultipleFields.PHOTO_URL);
        final String avatarUrl = item.getField(MultipleFields.AVATAR_URL);
        final String userName;
        final int totalLikes;

        ImageLoader.loaderImage((SimpleImageView) holder.getView(R.id.img_photo), photoUrl);
        ImageLoader.loaderImage((SimpleImageView) holder.getView(R.id.img_avatar), avatarUrl);

        switch (holder.getItemViewType()) {
            case ItemType.PERSONAL_PHOTO:
                userName = item.getField(MultipleFields.USER_NAME);
                totalLikes = item.getField(MultipleFields.TOTAL_LIKES);
                holder.setText(R.id.tv_user_name, userName);
                holder.setText(R.id.tv_like, String.valueOf(totalLikes));
                break;
            case ItemType.PERSONAL_COLLECTION:
                userName = item.getField(MultipleFields.USER_NAME);
                holder.setText(R.id.tv_user_name, userName);
                break;
            case ItemType.COLLECTION_LIST:
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
