/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.arch.app.web.simple;

import com.lzj.arch.app.web.WebContract;

/**
 * 简单网页接口。
 *
 * @author 吴吉林
 */
public interface SimpleWebContract {

    /**
     * 视图接口。
     */
    interface PassiveView extends WebContract.PassiveView {

    }

    /**
     * 表现者接口。
     */
    interface Presenter extends WebContract.Presenter {

    }
}