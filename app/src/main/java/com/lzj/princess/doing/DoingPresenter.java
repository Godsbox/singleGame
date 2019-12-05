/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.princess.doing;

import com.lzj.arch.core.AbstractPresenter;
import com.lzj.arch.core.Contract;

import static com.lzj.princess.doing.DoingFragment.EXTRA_ONCE;

/**
 * 正在做表现者。
 *
 * @author 吴吉林
 */
public class DoingPresenter
        extends AbstractPresenter<DoingContract.PassiveView, DoingModel, Contract.Router>
        implements DoingContract.Presenter {

    @Override
    protected void onViewAttach(boolean reattach, boolean newView, boolean isVisibleToUser) {
        super.onViewAttach(reattach, newView, isVisibleToUser);
        getView().showMessage(getModel().getMessage());
    }

    @Override
    protected void onPause() {
        super.onPause();
        boolean once = getParams().getBoolean(EXTRA_ONCE, false);
        if (once) {
            getView().cancel();
        }
    }
}
