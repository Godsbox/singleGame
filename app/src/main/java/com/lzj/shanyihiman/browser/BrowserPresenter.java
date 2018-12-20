/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyihiman.browser;

import com.lzj.arch.app.web.WebPresenter;
import com.lzj.arch.core.Contract;

public class BrowserPresenter
        extends WebPresenter<BrowserContract.PassiveView, BrowserModel, Contract.Router>
        implements BrowserContract.Presenter {

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    @Override
    public void workFinish() {
        getView().toggleQuitConfirm(true);
    }

    @Override
    public void lookMoreWorks() {
        getView().showDialog();
    }

    @Override
    public void playAdVideo() {
        getView().playAdsVideo();
    }
}