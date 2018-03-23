/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.arch.util;

import android.support.annotation.StringRes;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.lzj.arch.util.ContextUtils.getAppContext;

/**
 * 关于消息操作方法的工具类。
 *
 * @author 吴吉林
 */
public final class ToastUtils {

    /**
     * 显示短消息。
     *
     * @param message 消息内容
     */
    public static void showShort(String message) {
        if (StringUtils.isEmpty(message)) {
            return;
        }
        makeText(getAppContext(), message, LENGTH_SHORT).show();
    }

    /**
     * 显示短消息。
     *
     * @param message 消息资源 ID
     */
    public static void showShort(@StringRes int message) {
        if (message <= 0) {
            return;
        }
        makeText(getAppContext(), message, LENGTH_SHORT).show();
    }

    /**
     * 显示短消息。
     *
     * @param message 消息资源 ID
     * @param args 参数
     */
    public static void showShort(@StringRes int message, Object... args) {
        String text = ResourceUtils.getString(message, args);
        makeText(getAppContext(), text, LENGTH_SHORT).show();
    }

    /**
     * 显示长消息。
     *
     * @param message 消息内容
     */
    public static void showLong(String message) {
        if (StringUtils.isEmpty(message)) {
            return;
        }
        makeText(getAppContext(), message, LENGTH_LONG).show();
    }

    /**
     * 显示长消息。
     *
     * @param message 消息资源 ID
     */
    public static void showLong(@StringRes int message) {
        if (message <= 0) {
            return;
        }
        makeText(getAppContext(), message, LENGTH_LONG).show();
    }
}
