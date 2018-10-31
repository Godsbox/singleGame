/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyigzsqsj.browser;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

import com.lzj.arch.app.PassiveActivity;
import com.lzj.arch.core.Contract;
import com.lzj.shanyigzsqsj.BaWei;
import com.lzj.shanyigzsqsj.R;

import static android.view.KeyEvent.KEYCODE_BACK;
import static com.lzj.arch.app.web.WebConstant.EXTRA_LAYOUT_ID;
import static com.lzj.arch.util.ViewUtils.inflate;

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
    }

    @Override
    protected void onFragmentTransaction(FragmentTransaction transaction) {
        BrowserFragment fragment = new BrowserFragment();
        fragment.setArgument(EXTRA_LAYOUT_ID, R.layout.app_fragment_web_content);
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