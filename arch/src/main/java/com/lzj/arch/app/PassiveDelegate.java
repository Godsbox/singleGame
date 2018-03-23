/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.arch.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.lzj.arch.R;
import com.lzj.arch.app.PassiveContract.Presenter;
import com.lzj.arch.core.Contract;
import com.lzj.arch.util.ResourceUtils;
import com.lzj.arch.util.ViewUtils;
import com.lzj.arch.util.map.StringMap;
import com.lzj.arch.util.map.StringMapImpl;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

/**
 * 视图委托。
 *
 * @author 吴吉林
 */
public class PassiveDelegate<P extends Contract.Presenter> implements View.OnClickListener {

    /**
     * 视图配置。
     */
    private PassiveConfig config = new PassiveConfig();

    /**
     * 应用栏。
     */
    private Toolbar appbar;

    /**
     * 控制器。
     */
    private PassiveController<P> controller;

    public PassiveDelegate(PassiveController<P> controller) {
        this.controller = controller;
    }

    PassiveConfig getConfig() {
        return config;
    }

    void findView(Activity activity) {
        appbar = (Toolbar) activity.findViewById(R.id.appbar);
    }

    void findView(Fragment fragment) {
        appbar = ViewUtils.findView(fragment.getView(), R.id.appbar);
    }

    void initView() {
        if (appbar == null) {
            return;
        }
        if (config.getTitleResource() > 0) {
            appbar.setTitle(config.getTitleResource());
        }
        if (config.getNavigationIcon() > 0) {
            appbar.setNavigationIcon(config.getNavigationIcon());
            appbar.setNavigationOnClickListener(this);
        }
        if (config.getMenuResource() > 0) {
            appbar.inflateMenu(config.getMenuResource());
            appbar.setOnMenuItemClickListener(controller);
        }
    }

    public void setTitle(@StringRes int title) {
        if (appbar != null) {
            appbar.setTitle(title);
        }
    }

    public void setTitle(String title) {
        if (appbar != null) {
            appbar.setTitle(title);
        }
    }

    public void setAppbarColorRes(@ColorRes int color) {
        if (appbar != null) {
            setAppbarColor(ResourceUtils.getColor(color));
        }
    }

    public void setAppbarVisible(int visible) {
        if (appbar != null) {
            appbar.setVisibility(visible);
        }
    }

    public void setAppbarColor(@ColorInt int color) {
        if (appbar != null) {
            appbar.setBackgroundColor(color);
        }
        if (SDK_INT < LOLLIPOP) {
            return;
        }
        Activity activity = controller instanceof Activity
                ? (Activity) controller
                : ((Fragment) controller).getActivity();
        activity.getWindow().setStatusBarColor(color);
    }

    public int getAppbarColor(){
        if (SDK_INT < LOLLIPOP) {
            return 0;
        }
        Activity activity = controller instanceof Activity
                ? (Activity) controller
                : ((Fragment) controller).getActivity();
        return activity.getWindow().getStatusBarColor();
    }

    public void setFullScreen(boolean full){
        Activity activity = controller instanceof Activity
                ? (Activity) controller
                : ((Fragment) controller).getActivity();
        if(full){
            activity.getWindow().addFlags(FLAG_FULLSCREEN);
        } else {
            activity.getWindow().clearFlags(FLAG_FULLSCREEN);
        }
    }

    public Menu getMenu() {
        if (appbar == null) {
            throw new IllegalStateException("当前界面没有应用栏");
        }
        return appbar.getMenu();
    }

    @Override
    public void onClick(View v) {
        controller.onNavigationClick();
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        Contract.Presenter p = controller.getPresenter();
        if (!(p instanceof PassiveContract.Presenter)) {
            return;
        }
        Presenter presenter = (Presenter) controller.getPresenter();
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras == null) {
                extras = new Bundle();
            }
            extras.putString("data", data.getDataString());
            StringMap map = new StringMapImpl(extras);
            presenter.onOkResult(requestCode, map);
            return;
        }
        if (resultCode == RESULT_CANCELED) {
            presenter.onCanceledResult(requestCode);
        }
    }
}
