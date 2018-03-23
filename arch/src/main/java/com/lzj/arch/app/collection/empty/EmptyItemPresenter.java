/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.app.collection.empty;

import com.lzj.arch.app.collection.ItemPresenter;
import com.lzj.arch.app.collection.empty.EmptyItemContract.PassiveView;
import com.lzj.arch.app.collection.empty.EmptyItemContract.Presenter;
import com.lzj.arch.core.Contract;

/**
 * 空项表现者。
 *
 * @author 吴吉林
 */
public class EmptyItemPresenter
        extends ItemPresenter<PassiveView, EmptyItemModel, Contract.Router>
        implements Presenter {

    @Override
    protected void onBind() {
        getView().showEmpty(getModel().getImage(), getModel().getMessage());
    }
}
