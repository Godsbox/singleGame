/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.app.collection.empty;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzj.arch.R;
import com.lzj.arch.app.collection.AbstractViewHolder;
import com.lzj.arch.app.collection.empty.EmptyItemContract.PassiveView;
import com.lzj.arch.app.collection.empty.EmptyItemContract.Presenter;

/**
 * 空项视图。
 *
 * @author 吴吉林
 */
public class EmptyViewHolder extends AbstractViewHolder<Presenter> implements PassiveView {

    /**
     * 图片。
     */
    private ImageView image;

    /**
     * 消息。
     */
    private TextView message;

    public EmptyViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onFindView() {
        super.onFindView();
        image = findView(R.id.image);
        message = findView(R.id.message);
    }

    @Override
    public void showEmpty(int image, int message) {
        this.image.setImageResource(image);
        this.message.setText(message);
    }
}
