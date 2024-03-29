/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.arch.app.content.state;

import com.lzj.arch.app.content.ContentContract.PassiveView;
import com.lzj.arch.app.content.ContentModel;
import com.lzj.arch.core.State;

/**
 * 刷新失败状态。
 *
 * @author 吴吉林
 */
public class RefreshFailure implements State<PassiveView, ContentModel> {
    @Override
    public void render(PassiveView view, ContentModel model) {
        view.setRefreshing(false);
        view.showRefreshFailure(model.getErrorMessage());
    }
}
