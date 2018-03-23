/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.arch.app;

import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;

import com.lzj.arch.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.view.WindowManager.LayoutParams;
import static com.lzj.arch.util.DisplayUtils.dp2px;

/**
 * 视图配置。
 *
 * @author 吴吉林
 */
public class PassiveConfig {

    /**
     * 名称。
     */
    private String name;

    /**
     * 布局资源  ID。
     */
    @LayoutRes
    private int layoutResource;

    /**
     * 标题资源 ID。
     */
    @StringRes
    private int titleResource;

    /**
     * 菜单资源 ID。
     */
    @MenuRes
    private int menuResource;

    /**
     * 导航图标资源 ID。
     */
    @DrawableRes
    private int navigationIcon = R.mipmap.app_icon_back_white;

    /**
     * 对话框界面是否可取消。
     */
    private boolean dialogCancelable = true;

    /**
     * 标识是否是底部
     */
    private boolean bottomSheet;

    /**
     * 标识是否是右边
     */
    private boolean right;

    /**
     * 对话框界面背景资源 ID。
     */
    @DrawableRes
    private int windowBackground;

    private int[] dialogDimension = new int[] {MATCH_PARENT, WRAP_CONTENT};

    /**
     * 是否包裹对话框内容。
     */
    private boolean wrapDialogContent;

    /**
     * 窗口 DIM 效果是否启用。
     */
    private boolean dimEnabled = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @LayoutRes
    public int getLayoutResource() {
        return layoutResource;
    }

    public void setLayoutResource(@LayoutRes int layoutResource) {
        this.layoutResource = layoutResource;
    }

    public int getTitleResource() {
        return titleResource;
    }

    public void setTitleResource(int titleResource) {
        this.titleResource = titleResource;
    }

    public int getMenuResource() {
        return menuResource;
    }

    public void setMenuResource(int menuResource) {
        this.menuResource = menuResource;
    }

    public int getNavigationIcon() {
        return navigationIcon;
    }

    public void setNavigationIcon(int navigationIcon) {
        this.navigationIcon = navigationIcon;
    }

    public boolean isDialogCancelable() {
        return dialogCancelable;
    }

    public void setDialogCancelable(boolean dialogCancelable) {
        this.dialogCancelable = dialogCancelable;
    }

    public int getWindowBackground() {
        return windowBackground;
    }

    public void setWindowBackground(int windowBackground) {
        this.windowBackground = windowBackground;
    }

    /**
     * 设置该对话框界面的窗口宽高。
     *
     * @param width 宽，dp或 {@link LayoutParams#MATCH_PARENT}或{@link LayoutParams#WRAP_CONTENT}
     * @param height 高，dp或 {@link LayoutParams#MATCH_PARENT}或{@link LayoutParams#WRAP_CONTENT}
     */
    public void setDialogDimension(int width, int height) {
        if (width > 0) {
            width = dp2px(width);
        }
        if (height > 0) {
            height = dp2px(height);
        }
        this.dialogDimension = new int[] {width, height};
    }

    public int[] getDialogDimension() {
        return dialogDimension;
    }

    public boolean isWrapDialogContent() {
        return wrapDialogContent;
    }

    public void setWrapDialogContent(boolean wrapDialogContent) {
        this.wrapDialogContent = wrapDialogContent;
    }

    public boolean isBottomSheet() {
        return bottomSheet;
    }

    public boolean isRight(){
        return right;
    }

    public void setBottomSheet(boolean bottomSheet) {
        this.bottomSheet = bottomSheet;
    }

    public boolean isDimEnabled() {
        return dimEnabled;
    }

    public void setDimEnabled(boolean dimEnabled) {
        this.dimEnabled = dimEnabled;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
