/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyisnnx.browser;

import java.io.File;

import com.lzj.arch.util.ClipboardUtils;
import com.lzj.arch.util.DeviceUtils;
import com.lzj.arch.util.FileUtils;
import com.lzj.arch.app.web.WebInterface;
import com.lzj.shanyisnnx.AppConstant;
import com.lzj.shanyisnnx.BuildConfig;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * 浏览器 WEB 接口。
 *
 * @author wsy
 */
public class BrowserWebInterface extends WebInterface<BrowserContract.Presenter> {

    {
        setName("app");
    }

    /**
     * 本地游戏数据缓存。
     *
     * @param key   键值
     * @param value 存档数据
     */
    @JavascriptInterface
    public boolean saveGameValue(String key, String value) {
        Log.d("wsy","缓存数据到手机本地~~~~~~");
        return FileUtils.saveFileData(AppConstant.GAME_DIE,key,value);
    }

    /**
     * 读取本地游戏数据缓存。
     * @param key 键值
     */
    @JavascriptInterface
    public String readGameValue(String key) {
        Log.d("wsy","读取缓存的数据~~~~~~");
        return FileUtils.getFileData(AppConstant.GAME_DIE,key);
    }

    /**
     * 本地 游戏存档。
     *
     * @param key   键值
     * @param value 存档数据
     */
    @JavascriptInterface
    public boolean localStorage_setItem(String key, String value) {
        return FileUtils.saveFileData(AppConstant.GAME_DIE,key,value);
    }

    /**
     * 读取本地游戏存档。
     * @param key 键值
     */
    @JavascriptInterface
    public String localStorage_getItem(String key) {
        return FileUtils.getFileData(AppConstant.GAME_DIE,key);
    }

    /**
     * 删除本地游戏存档。
     *
     * @param key 键值
     */
    @JavascriptInterface
    public boolean removeLocalStorage(String key) {
        File file = new File(AppConstant.GAME_DIE + "/localStorage", key);
        if (file.exists()) {
            FileUtils.deleteFile(file);
        }
        return !file.exists();
    }

    /**
     * 游戏时间开始计时
     * */
    @JavascriptInterface
    public void gameStart(Object object) {
        //废弃  但是不加导致不能加载游戏
    }


    /**
     * 获取用户token。
     */
    @JavascriptInterface
    public String getUserToken() {
        return "";
    }

    /**
     * 获取设备id。
     */
    @JavascriptInterface
    public String getDeviceId() {
        return DeviceUtils.getDeviceId();
    }

    /**
     * 获取服务器接口版本号。
     */
    @JavascriptInterface
    public String getV() {
        return "1.0.0";
    }

    /**
     * 获取服务器接口版本号。
     */
    @JavascriptInterface
    public void playAdVideo() {
        Log.d("wsy","开始播放广告~~~~~~");
        getPresenter().playAdVideo();
    }

    /**
     * 获取客户端标识。
     */
    @JavascriptInterface
    public String getClient() {
        return "android";
    }

    /**
     * 获取当前版本号。
     */
    @JavascriptInterface
    public String getAppVersion() {
        return BuildConfig.APP_VERSION;
    }

    /**
     * 获取用户id密文。
     */
    @JavascriptInterface
    public String getU() {
        return "";
    }

    /**
     * 初始化js接口方法。
     * @param methodName 方法名称
     * @param callbackName 回调的方法名
     */
    @JavascriptInterface
    public void bindEvent(String methodName, String callbackName) {
    }

    /**
     * 移除js接口方法。
     * @param methodName 方法名称
     * @param callbackName 要移除的回调的方法名
     */
    @JavascriptInterface
    public void unBindEvent(String methodName, String callbackName) {
    }

    /**
     * 作品播放结束接口方法。
     */
    @JavascriptInterface
    public void workFinished(String result) {
        getPresenter().workFinish();
    }

    /**
     * 弹窗跳闪艺。
     */
    @JavascriptInterface
    public void moreWorks() {
        getPresenter().lookMoreWorks();
    }

    /**
     * 复制。
     */
    @JavascriptInterface
    public void shareCopy(String content) {
        ClipboardUtils.copyString("String", content);
    }

}
