/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.app.web.simple;

import com.lzj.arch.app.web.WebFragment;

import static com.lzj.arch.app.web.WebConstant.EXTRA_URL;

/**
 * 简单网页界面。
 *
 * @author 吴吉林
 */
public class SimpleWebFragment
        extends WebFragment<SimpleWebContract.Presenter>
        implements SimpleWebContract.PassiveView {

    /**
     * 创建一个简单网页界面。
     *
     * @param url 网页 URL 地址
     * @return 简单网页界面
     */
    public static SimpleWebFragment newInstance(String url) {
        SimpleWebFragment fragment = new SimpleWebFragment();
        fragment.setArgument(EXTRA_URL, url);
        return fragment;
    }
}
