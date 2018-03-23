/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.app.content;

/**
 * 内容配置类。
 *
 * @author 吴吉林
 */
public class ContentConfig {

    /**
     * 空内容标题资源 ID。
     */
    int emptyTitle;

    /**
     * 空内容消息资源 ID。
     */
    int emptyMessage;

    /**
     * 空内容图片资源 ID。
     */
    int emptyImage;

    /**
     * 空内容操作文案资源 ID。
     */
    int emptyAction;

    /**
     * 需要登录跳转入口
     */
    boolean needLogin;

    public void setNeedLogin(boolean need) {
        this.needLogin = need;
    }

    public void setEmptyTitle(int emptyTitle) {
        this.emptyTitle = emptyTitle;
    }

    public void setEmptyMessage(int emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    public void setEmptyAction(int emptyAction) {
        this.emptyAction = emptyAction;
    }

    public void setEmptyImage(int emptyImage) {
        this.emptyImage = emptyImage;
    }
}
