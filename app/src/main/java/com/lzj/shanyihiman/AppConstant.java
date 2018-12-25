/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyihiman;

import android.os.Build;
import android.os.Environment;

import static com.lzj.shanyihiman.BuildConfig.APP_VERSION;

/**
 * 应用全局常量类。
 *
 * @author wsy
 */
public final class AppConstant {


    /**
     * 作品id
     */
    public static final String GAME_ID = "95870";

    /**
     * 广告的 appkey
     */
    public static final String AD_APP_KEY = "307";

    /**
     * 广告位id
     */
    public static final String BLOCK_ID = "951";

    /**
     * 第一个json
     */
    public static final String GAME_UUID = "E49F429C-A0E6-46F2-09AD-5EA455460DD1";

    /**
     * 作者
     */
    public static final String AUTHOR_NAME = "作者: 印象凉烟雨";

    /**
     * 类型
     */
    public static final String WORK_TYPE = "类型: 逆袭、现代、言情";

    /**
     * 作品字数
     */
    public static final String WORK_COUNT = "字数: 35.6万";

    /**
     * 是否是竖屏 0横屏  1竖屏
     */
    public static final int vertical = 0;

    /**
     * 资源路径
     */
    public static final String FILE_DIR_HEAD = "file:///android_asset/";

    /**
     * 开始播放路径
     */
    public static final String GAME_PLAYURL = FILE_DIR_HEAD + "index.html?" +
            "uuid=" + GAME_UUID +
            "&client=android&gid="+ GAME_ID +
            "&type=0&v=1.0.0&app_version=1.5.0&single=1&offline=1&vertical=" + vertical;

    /**
     * 本地存储路径
     */
    public static final String GAME_DIE = BaWei.GAME_DIE == null ? Environment.getExternalStorageDirectory() + "/" + BaWei.PKG : BaWei.GAME_DIE;

    /**
     * 首选项文件名：应用。
     */
    public static final String PREFS_APP = "shanyi";

    public static final String SHANYI_PACKAGE = "com.lzj.shanyi";

    /**
     * 首选项：玩作品引导。<br /><br />
     *
     * 布尔值：true：已引导；false：未引导。
     */
    public static final String PREF_GAME_PLAY_GUIDE = "game.playGuide";

    /**
     * 首选项文件名：启动。
     */
    public static final String PREFS_LAUNCH = "shanyi_launch";

    /**
     * 播放作品
     */
    public static final String OFFLINE_SHOULDINTER_URL = "https://mapi.3000api.com/apis/soft/v1.0/game-info.html";

    /**
     * 播放章节
     */
    public static final String OFFLINE_SHOULDINTER_CHAPTER_URL = "https://mapi.3000api.com/apis/soft/v1.0/chapter-chapterInfo.html";

    /**
     * 键：本地游戏目录特殊规则。
     */
    public static final String GAME_DIR_PRE = "game";

    /**
     * 自定义协议。
     */
    public static final String SCHEME = "lzj3000";

    /**
     * 键：本地游戏目录特殊规则。
     */
    public static final String GAME_JSON = "json";

    private AppConstant() {
    }

    /**
     * 获取应用UA。
     *
     * @return UA
     */
    public static String getUserAgent() {
        String model = Build.MODEL;
        if ("　　".equals(model)) {
            model = "unResolve";
        }
        return new StringBuilder("jiansan/")
                .append(APP_VERSION)
                .append("(android;")
                .append(model).append(";")
                .append(Build.VERSION.RELEASE)
                .append(")")
                .toString();
    }
}
