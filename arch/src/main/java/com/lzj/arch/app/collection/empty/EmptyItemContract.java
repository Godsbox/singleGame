/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.app.collection.empty;

import com.lzj.arch.app.collection.ItemContract;

/**
 * 空项契约。
 *
 * @author 吴吉林
 */
public interface EmptyItemContract {

    /**
     * 视图接口。
     */
    interface PassiveView extends ItemContract.PassiveView {

        /**
         * 显示空项。
         *
         * @param image 图片资源 ID
         * @param message 消息资源 ID
         */
        void showEmpty(int image, int message);
    }

    /**
     * 表现者接口。
     */
    interface Presenter extends ItemContract.Presenter {

    }
}
