/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.arch.bus;


/**
 * 基本事件。
 *
 * @author 王盛钰
 */
public class BaseMessageEvent {

    /**
     * 播放器下载完成通知。
     */
    public static final int PLAYER_DOWNLOADED = 5;

    /**
     * 礼物缓存通知。
     */
    public static final int GIFT_DOWNLOADING = 6;

    /**
     * 礼物缓存完成通知。
     */
    public static final int GIFT_DOWNLOADED = 7;

    /**
     * 刷新数据，单选。
     */
    public static final int RECYCLE_UPDATE = 8;

    /**
     * 是否刷新游戏下载列表页。
     */
    public static final int DOWNLAOD_TAB_UPDATE = 9;

    /**
     * 礼物赠送成功通知。
     */
    public static final int GIFT_SEND_SUCCESS = 10;

    /**
     * 退出菜单事件。
     */
    public static final int PLAYER_MENU = 11;

    /**
     * 礼物缓存出错通知。
     */
    public static final int GIFT_DOWNLOAD_ERROR = 12;

    /**
     * 删除游戏事件。
     */
    public static final int DELETE_GAME_EVENT = 13;

    /**
     * 下载小红点有变化。
     */
    public static final int DOWNLOAD_COUNT_CHANGE = 14;

    /**
     * 是否刷新游戏下载列表页。
     */
    public static final int DOWNLOADING_LIST_UPDATE = 16;

    /**
     * 下载小红点提示  作品是否有更新判断处理 事件。
     */
    public static final int DOWNLOAD_RED_TIP_AND_UPDATE = 17;

    /**
     * 充值成功通知。
     */
    public static final int PAYMENT_SUCCESS = 18;

    /**
     * 成功获得星星通知。
     */
    public static final int RECEIVE_STAR_NOTIFY = 19;

    /**
     * 带输入软键盘的弹窗 关闭事件
     */
    public static final int DIALOG_CLOSE_EVENT = 20;


    private int type;

    private String itemId;

    private boolean isFirst;

    private boolean result;

    private boolean onlyUpdate;

    private boolean onlyRefresh;

    public BaseMessageEvent(int type, boolean onlyRefresh , boolean onlyUpdate){
        this.type = type;
        this.onlyUpdate = onlyUpdate;
        this.onlyRefresh = onlyRefresh;
    }

    public BaseMessageEvent(int type){
        this.type = type;
    }

    public BaseMessageEvent(int type, String itemId){
        this.type = type;
        this.itemId = itemId;
    }

    public BaseMessageEvent(int type, boolean result){
        this.type = type;
        this.result = result;
    }

    public int getType() {
        return type;
    }

    public boolean isOnlyUpdate() {
        return onlyUpdate;
    }

    public boolean isOnlyRefresh(){ return onlyRefresh; }

    public String getItemId() {
        return itemId;
    }

    public boolean isResult() {
        return result;
    }

    public boolean isFirst(){
        return isFirst;
    }

    public void setIsFirst(boolean first) {
        isFirst = first;
    }
}
