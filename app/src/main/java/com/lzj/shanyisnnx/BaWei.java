/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyisnnx;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArraySet;
import android.util.Log;

import com.lzj.arch.file.KeyValueCaches;
import com.lzj.arch.file.SharedPreferencesImpl;
import com.lzj.arch.util.ContextUtils;
import com.mobgi.MobgiAds;
import com.mobgi.common.utils.LogUtil;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import static com.lzj.arch.util.OsUtils.asOfMarshmallow;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.lzj.shanyisnnx.AppConstant.AD_APP_KEY;
import static com.lzj.shanyisnnx.BuildConfig.BUILD_TYPE;

/**
 * 单独游戏应用。
 *
 * @author wsy
 */
public class BaWei extends MultiDexApplication {

    /**
     * 应用报名。
     */
    public static final String PKG = BaWei.class.getPackage().getName();

    /**
     * 本地存储路径
     */
    public static String GAME_DIE;

    /**
     * 单例。
     */
    private static BaWei INSTANCE;

    private String DOWNLOAD_SHANYI_URL = "http://app.3000.com/azapk";

    /**
     * 标识启动权限是否均已授权。
     */
    private static boolean launchPermissionsGranted;

    /**
     * 启动权限数组。
     */
    public static final String[] LAUNCH_PERMISSIONS = new String[]{
            READ_PHONE_STATE,
            WRITE_EXTERNAL_STORAGE
    };

    private static List<Activity> oList = new ArrayList<>();

    /**
     * 被拒绝的权限。
     */
    private static Set<String> deniedLaunchPermissions = new ArraySet<>(LAUNCH_PERMISSIONS.length);

    /**
     * 分割 Dex 支持
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtils.setAppContext(this);
        LogUtil.setDebug(true);
        MobgiAds.init(this, AD_APP_KEY);
        //initApiClient();
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.d("wsy","初始化成功了没有啊 == "+b);
            }
        }); // 初始化x5WebView内核

        if (getApplicationContext().getExternalFilesDir(null) != null) {
            GAME_DIE = getApplicationContext().getExternalFilesDir(null).getPath();
        }

        INSTANCE = this;
        checkLaunchPermissions();
        Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> e) throws Exception {
                KeyValueCaches.add(new SharedPreferencesImpl(AppConstant.PREFS_LAUNCH));
                KeyValueCaches.add(new SharedPreferencesImpl(AppConstant.PREFS_APP));
            }
        }).subscribeOn(Schedulers.single()).subscribe();

        if (!"release".equals(BUILD_TYPE)) {
            CrashHandler.createCrashHandler();
        }
    }


    /**
     * 检查启动权限是否均已授权。
     *
     * @return true：均已授权；false：存在未授权。
     */
    public static boolean checkLaunchPermissions() {
        // 安卓 6.0 以下不需要授权运行时权限。
        if (!asOfMarshmallow()) {
            launchPermissionsGranted = true;
            return true;
        }
        // 检查运行时权限是否均已被授权
        boolean allGranted = true;
        for (String permission : LAUNCH_PERMISSIONS) {
            int flag = ContextCompat.checkSelfPermission(getInstance(), permission);
            if (flag != PERMISSION_GRANTED) {
                allGranted = false;
                deniedLaunchPermissions.add(permission);
            }
        }
        launchPermissionsGranted = allGranted;
        return launchPermissionsGranted;
    }

    /**
     * 获取应用单例。
     *
     * @return 应用单例
     */
    public static BaWei getInstance() {
        return INSTANCE;
    }


    /**
     * 获取页面名称。<br /><br />
     * <p>
     * 实现策略：将类名的“com.lzj.shanyi.feature.”去掉，作为页面名称。
     *
     * @param page 页面对象
     * @return 页面名称
     */
    public static String getPageName(Object page) {
        return page.getClass().getName().replace("com.lzj.shanyijiansan", "");
    }

    /**
     * 添加Activity
     */
    public void addActivity_(Activity activity) {
        // 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(Activity activity) {
        //判断当前集合中存在该Activity
        if (oList.contains(activity)) {
            oList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
            activity.finish();
        }
    }

    public String getDownloadShanyiUrl(){
        return DOWNLOAD_SHANYI_URL;
    }

    public void setUrl(String url){
        this.DOWNLOAD_SHANYI_URL = url;
    }

}