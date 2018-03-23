/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import timber.log.Timber;

import static com.lzj.arch.util.ContextUtils.getAppContext;

/**
 * 关于显示器操作方法的工具类。
 *
 * @author 吴吉林
 */
public class DisplayUtils {

    static {
        Timber.d("display density:%s, width:%s, height:%s", getDensity(), getDisplayWidth(), getDisplayHeight());
    }

    /**
     * 获取屏幕显示器宽度。（单位：像素）
     *
     * @return 显示器宽度
     */
    public static int getDisplayWidth() {
        DisplayMetrics displayMetrics = getAppContext().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕显示器高度。（单位：像素）
     *
     * @return 显示器高度
     */
    public static int getDisplayHeight() {
        DisplayMetrics displayMetrics = getAppContext().getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕密度。
     *
     * @return 屏幕密度
     */
    public static float getDensity() {
        return getAppContext().getResources().getDisplayMetrics().density;
    }

    /**
     * 密度像素转像素。
     *
     * @param dp 密度像素
     * @return 像素
     */
    public static int dp2px(float dp) {
        float density = getDensity();
        if (dp < 0) {
            return (int) (dp * density - 0.5f);
        }
        else {
            return (int) (dp * density + 0.5f);
        }
    }

    /**
     * 沉浸式状态栏
     * */
    public static void setTransparentStatus(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏(huawei等手机的底部虚拟按键透明设置)
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public static void setTransparentNavigation(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //不透明状态栏
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }

    /**
     * 退出应用
     * 适合sdk7以上的版本
     * */
    public static void exitApp(Activity activity) {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(startMain);
        System.exit(0);
    }

}
