package com.wangzai.lovesy.home.collection;

import com.alibaba.fastjson.JSON;
import com.wangzai.lovesy.bean.CollectionBean;
import com.wangzai.lovesy.bean.CoverPhotoBean;
import com.wangzai.lovesy.bean.ProfileImageBean;
import com.wangzai.lovesy.bean.UrlsBean;
import com.wangzai.lovesy.bean.UserBean;
import com.wangzai.lovesy.core.ui.recycler.DataConverter;
import com.wangzai.lovesy.core.ui.recycler.ItemType;
import com.wangzai.lovesy.core.ui.recycler.MultipleFields;
import com.wangzai.lovesy.core.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzai on 2017/9/21
 */

public class CollectionDataConvert extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        String jsonData = getJsonData();
        if (jsonData == null) {
            return itemEntities;
        }

        List<CollectionBean> collectionList = JSON.parseArray(jsonData, CollectionBean.class);
        int size = collectionList.size();
        for (int i = 0; i < size; i++) {
            final CollectionBean collectionBean = collectionList.get(i);
            final UserBean user = collectionBean.getUser();
            final ProfileImageBean profileImage = user.getProfile_image();
            final CoverPhotoBean cover_photo = collectionBean.getCover_photo();

            String collectionName = collectionBean.getTitle();
            int collectionCount = collectionBean.getTotal_photos();
            String photoUrl = null;
            if (cover_photo != null) {
                UrlsBean urls = cover_photo.getUrls();
                photoUrl = urls.getRegular();
            }
            String userName = user.getUsername();
            String avatarUrl = profileImage.getLarge();

            int type = ItemType.COLLECTION;

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(type)
                    .setField(MultipleFields.SPAN_SIZE, 2)
                    .setField(MultipleFields.PHOTO_URL, photoUrl)
                    .setField(MultipleFields.USER_NAME, userName)
                    .setField(MultipleFields.AVATAR_URL, avatarUrl)
                    .setField(MultipleFields.COLLECTION_NAME, collectionName)
                    .setField(MultipleFields.COLLECTION_PHOTO_COUNT, collectionCount)
                    .build();

            itemEntities.add(entity);
        }
        return itemEntities;
    }
}
