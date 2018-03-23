/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.app.collection.empty;

import com.lzj.arch.R;
import com.lzj.arch.app.collection.ItemModel;

/**
 * 空项表现模型。
 *
 * @author 吴吉林
 */
public class EmptyItemModel extends ItemModel {

    {
        setItemType(R.layout.app_item_empty);
    }

    /**
     * 图片资源 ID。
     */
    private int image = R.mipmap.app_img_no_data;

    /**
     * 消息资源 ID。
     */
    private int message = R.string.no_data_message;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
