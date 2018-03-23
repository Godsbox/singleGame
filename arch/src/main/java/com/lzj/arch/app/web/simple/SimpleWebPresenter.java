/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.app.web.simple;

import com.lzj.arch.app.web.WebModel;
import com.lzj.arch.app.web.WebPresenter;
import com.lzj.arch.core.Contract.Router;

/**
 * 简单网页表现者。
 *
 * @author 吴吉林
 */
public class SimpleWebPresenter
        extends WebPresenter<SimpleWebContract.PassiveView, WebModel, Router>
        implements SimpleWebContract.Presenter {
}
