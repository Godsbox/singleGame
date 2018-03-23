/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.lzj.arch.util.ContextUtils.getAppContext;

/**
 * 关于进程操作方法的工具类。
 *
 * @author 吴吉林
 */
public class ProcessUtils {

    /**
     * 判断当前进程是否是应用进程。
     *
     * @param appName 应用名称
     * @return true：是；false：否。
     */
    public static boolean isAppProcess(String appName) {
        RunningAppProcessInfo info = getRunningProcess(appName);
        return info != null && Process.myPid() == info.pid;
    }

    /**
     * 获取指定名称的进程运行信息。
     *
     * @param processName 进程名称
     * @return 进程运行信息。
     */
    public static RunningAppProcessInfo getRunningProcess(String processName) {
        ActivityManager am = (ActivityManager) getAppContext().getSystemService(ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> apps = am.getRunningAppProcesses();
        for (RunningAppProcessInfo info : apps) {
            if (info.processName.equals(processName)) {
                return info;
            }
        }
        return null;
    }

    /**
     * 判断某个应用是否已安装。
     *
     * @param app     应用包名
     * @return true：已安装；false：未安装。
     */
    public static boolean isAppInstalled(String app) {
        final PackageManager packageManager = getAppContext().getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            String name = packages.get(i).packageName;
            if (name.equals(app)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 打开第三方app
     * @param appName 包名
     * */
    public static void startOtherApp(Context context, String appName) {
        final PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(appName);
        if (intent != null) {
            context.startActivity(intent);
        }
    }

}
