/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyi_princess.browser;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

import com.gyf.barlibrary.ImmersionBar;
import com.lzj.arch.app.PassiveActivity;
import com.lzj.arch.app.web.WebConstant;
import com.lzj.arch.core.Contract;
import com.lzj.shanyi_princess.AppConstant;
import com.lzj.shanyi_princess.BaWei;
import com.lzj.shanyi_princess.R;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebStorage;

import static android.view.KeyEvent.KEYCODE_BACK;
import static com.gyf.barlibrary.BarHide.FLAG_HIDE_NAVIGATION_BAR;
import static com.lzj.arch.app.web.WebConstant.EXTRA_LAYOUT_ID;

/**
 * 浏览器屏幕。
 *
 * @author wsy
 */
public class BrowserActivity
        extends PassiveActivity<Contract.Presenter> {

    {
        setFullscreen(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        BaWei.getInstance().addActivity_(this);
        WebStorage.getInstance().deleteAllData();
        CookieSyncManager.createInstance(getApplicationContext());
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null);
            cookieManager.removeAllCookie();
            cookieManager.flush();
        } else {
            cookieManager.removeSessionCookies(null);
            cookieManager.removeAllCookie();
            CookieSyncManager.getInstance().sync();
        }
        ImmersionBar.with(this).hideBar(FLAG_HIDE_NAVIGATION_BAR).init();
    }

    @Override
    protected void onFragmentTransaction(FragmentTransaction transaction) {
        BrowserFragment fragment = new BrowserFragment();
        fragment.setArgument(EXTRA_LAYOUT_ID, R.layout.app_fragment_web_content);
        fragment.setArgument(WebConstant.EXTRA_URL, AppConstant.GAME_PLAYURL);
        setFragment(fragment);
        super.onFragmentTransaction(transaction);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            showBackTip();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaWei.getInstance().removeActivity_(this);
    }

    public void showBackTip() {
        new AlertDialog.Builder(this, R.style.AlertDialog)
                .setMessage(R.string.exit_game_tip)
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BaWei.getInstance().removeALLActivity_();
                        System.exit(0);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 继续观看
                    }
                })
                .show();
    }
}