/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyidogleg.play;

import com.lzj.arch.core.Model;

/**
 * 玩游戏表现模型。
 *
 * @author 吴吉林
 */
public class PlayGameModel extends Model {

    /**
     * 标识确认退出是否展示。
     */
    private boolean quitConfirmShown;

    /**
     * 是否收藏。
     */
    private boolean collectOrnot;

    /**
     * 标识退出引导是否展示。
     */
    private boolean quitGuideShown;


    /**播放时长
     * 单位：分钟
     * */
    private int playTime = 0;

    /**
     * 距上次点击屏幕的时间-秒
     * 未触摸屏幕超过3分钟，停止计时
     * */
    private int touchSecTime = 0;

    public boolean isQuitGuideShown() {
        return quitGuideShown;
    }

    public void setQuitGuideShown(boolean quitGuideShown) {
        this.quitGuideShown = quitGuideShown;
    }

    public boolean isQuitConfirmShown() {
        return quitConfirmShown;
    }

    public void setQuitConfirmShown(boolean quitConfirmShown) {
        this.quitConfirmShown = quitConfirmShown;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getTouchSecTime() {
        return touchSecTime;
    }

    public void setTouchSecTime(int touchSecTime) {
        this.touchSecTime = touchSecTime;
    }

    public boolean isCollectOrnot() {
        return collectOrnot;
    }

    public void setCollectOrnot(boolean collectOrnot) {
        this.collectOrnot = collectOrnot;
    }
}
