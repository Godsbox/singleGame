/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyijiansan.browser;

import com.lzj.arch.app.web.WebContract;

/**
 * 浏览器契约。
 *
 * @author wsy
 */
public interface BrowserContract {

    /**
     * 视图接口。
     */
    interface PassiveView extends WebContract.PassiveView {

        /**
         * 本地json资源丢失。
         */
        void showJsonError();

        /**
         * 播放结束
         * @param show
         */
        void toggleQuitConfirm(boolean show);

    }

    /**
     * 表现者接口。
     */
    interface Presenter extends WebContract.Presenter {

        /**
         * 作品播放结束
         */
        void workFinish();
    }
}