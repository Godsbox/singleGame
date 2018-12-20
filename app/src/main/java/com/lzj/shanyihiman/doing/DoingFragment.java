/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.shanyihiman.doing;

import android.view.View;
import android.widget.TextView;

import com.lzj.arch.app.PassiveFragment;
import com.lzj.shanyihiman.R;

import static com.lzj.shanyihiman.BaWei.PKG;


/**
 * 正在做界面。
 *
 * @author 吴吉林
 */
public class DoingFragment extends PassiveFragment<DoingContract.Presenter> implements DoingContract.PassiveView {

    /**
     * 参数名称：提示消息。
     */
    public static final String EXTRA_MESSAGE = PKG + ".extra.DOING_MESSAGE";

    /**
     * 参数名称：显示一次。
     */
    public static final String EXTRA_ONCE = PKG + ".extra.DOING_ONCE";

    /**
     * 加载视图。
     */
    private View progress;

    /**
     * 提示消息。
     */
    private TextView message;

    {
        getConfig().setLayoutResource(R.layout.app_fragment_doing);
        getConfig().setDialogCancelable(false);
        getConfig().setWindowBackground(R.drawable.app_shape_rect_round_5dp_translucent);
        getConfig().setWrapDialogContent(true);
    }

    @Override
    public void onFindView() {
        super.onFindView();
        progress = findView(R.id.progress);
        message = findView(R.id.message);
    }

    @Override
    public void showMessage(String message) {
        this.message.setText(message);
    }

    @Override
    public void cancel() {
        dismiss();
    }

    /**
     * 创建一个正在做界面。
     *
     * @param message 提示消息
     * @param once 只显示一次。
     * @return 正在做界面
     */
    public static DoingFragment newInstance(String message, boolean once) {
        DoingFragment fragment = new DoingFragment();
        fragment.setArgument(EXTRA_MESSAGE, message);
        fragment.setArgument(EXTRA_ONCE, once);
        return fragment;
    }

    @Override
    public DoingContract.Presenter createPresenter() {
        DoingPresenter presenter = new DoingPresenter();
        presenter.getModel().setMessage(this.<String>getArgument(EXTRA_MESSAGE));
        return presenter;
    }
}
