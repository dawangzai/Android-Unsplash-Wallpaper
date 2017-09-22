package com.wangzai.lovesy.home.collection;

import com.alibaba.fastjson.JSON;
import com.wangzai.lovesy.bean.CollectionBean;
import com.wangzai.lovesy.bean.CoverPhotoBean;
import com.wangzai.lovesy.bean.UrlsBean;
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
            CollectionBean collectionBean = collectionList.get(i);
            String collectionName = collectionBean.getTitle();
            int collectionCount = collectionBean.getTotal_photos();
            CoverPhotoBean cover_photo = collectionBean.getCover_photo();
            UrlsBean urls = cover_photo.getUrls();
            String photoUrl = urls.getRegular();
            int type = ItemType.COLLECTION;

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(type)
                    .setField(MultipleFields.SPAN_SIZE, 1)
                    .setField(MultipleFields.PHOTO_URL, photoUrl)
                    .setField(MultipleFields.COLLECTION_NAME, collectionName)
                    .setField(MultipleFields.COLLECTION_COUNT, collectionCount)
                    .build();

            itemEntities.add(entity);
        }
        return itemEntities;
    }
}
