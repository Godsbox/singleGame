/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyihiman.play;

import com.lzj.arch.app.PassiveContract;

/**
 * 玩游戏契约。
 *
 * @author 吴吉林
 */
public interface PlayGameContract {

    /**
     * 视图接口。
     */
    interface PassiveView extends PassiveContract.PassiveView {

        /**
         * 切换退出引导显隐藏。
         *
         * @param show true：显示；false：隐藏。
         */
        void toggleQuitGuide(boolean show);

        /**
         * 切换确认退出显隐藏。
         *
         * @param show true：显示；false：隐藏。
         */
        void toggleQuitConfirm(boolean show);

    }

    /**
     * 表现者接口。
     */
    interface Presenter extends PassiveContract.Presenter {

        /**
         * 处理返回事件。
         */
        void onBackPressed();
    }
}