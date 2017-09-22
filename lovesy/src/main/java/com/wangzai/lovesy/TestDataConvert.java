package com.wangzai.lovesy;

import com.alibaba.fastjson.JSON;
import com.wangzai.lovesy.bean.PhotoBean;
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
 * Created by wangzai on 2017/9/7
 */

class TestDataConvert extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        String jsonData = getJsonData();
        if (jsonData == null) {
            return itemEntities;
        }
        List<PhotoBean> photoList = JSON.parseArray(jsonData, PhotoBean.class);
        int size = photoList.size();
        for (int i = 0; i < size; i++) {
            PhotoBean photoBean = photoList.get(i);
            UserBean user = photoBean.getUser();
            UrlsBean urls = photoBean.getUrls();
            ProfileImageBean profileImage = user.getProfile_image();

            String avatarUrl = profileImage.getMedium();
            String username = user.getUsername();
            String photoUrl = urls.getRegular();
            int likes = photoBean.getLikes();
            int type = ItemType.PHOTO;

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(type)
                    .setField(MultipleFields.AVATAR_URL, avatarUrl)
                    .setField(MultipleFields.PHOTO_URL, photoUrl)
                    .setField(MultipleFields.USER_NAME, username)
                    .setField(MultipleFields.TOTAL_LIKES, likes)
                    .build();

            itemEntities.add(entity);
        }
        return itemEntities;
    }
}
