/**
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.arch.util;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.lzj.arch.util.ContextUtils.getAppContext;

/**
 * 字体操作方法的工具类。
 *
 * @author 郭香岫
 */
public final class TextUtils {

    /**
     * 私有构造器。
     */
    private TextUtils() {
    }

    /**
     * 文本加粗
     */
    public static void setBoldText(TextView view) {
        TextPaint tp = view.getPaint();
        tp.setFakeBoldText(true);
    }

}
