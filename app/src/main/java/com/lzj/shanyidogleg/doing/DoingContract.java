/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.shanyidogleg.doing;

import com.lzj.arch.core.Contract;

/**
 * 正在做契约。
 *
 * @author 吴吉林
 */
public interface DoingContract {

    /**
     * 视图接口。
     */
    interface PassiveView extends Contract.PassiveView {

        /**
         * 显示提示消息。
         *
         * @param message 提示消息
         */
        void showMessage(String message);

        /**
         * 取消
         */
        void cancel();
    }

    /**
     * 表现者接口。
     */
    interface Presenter extends Contract.Presenter {

    }
}
