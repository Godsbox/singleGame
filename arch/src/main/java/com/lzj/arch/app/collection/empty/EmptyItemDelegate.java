/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.app.collection.empty;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lzj.arch.R;
import com.lzj.arch.app.collection.AbstractItemDelegate;

/**
 * 空项代表。
 *
 * @author 吴吉林
 */
public class EmptyItemDelegate extends AbstractItemDelegate {

    {
        addLayoutId(R.layout.app_item_empty);
        addLayoutId(R.layout.app_item_empty_vertical);
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(View itemView, int itemType) {
        return new EmptyViewHolder(itemView);
    }
}
