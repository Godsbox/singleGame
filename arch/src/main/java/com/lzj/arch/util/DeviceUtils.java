/*
 * Copyright (c) 2017 3000.com All Rights Reserved.
 */
package com.lzj.arch.util;

import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.UUID;

import timber.log.Timber;

import static android.content.Context.TELEPHONY_SERVICE;
import static android.provider.Settings.Secure.ANDROID_ID;
import static com.lzj.arch.util.ContextUtils.getAppContext;
import static com.lzj.arch.util.ContextUtils.getSystemService;

/**
 * 关于设备操作方法的工具类。
 *
 * @author 吴吉林
 */
public final class DeviceUtils {

    /**
     * 私有构造器。
     */
    private DeviceUtils() {
    }

    /**
     * 获取唯一设备 ID。
     *
     * @return 唯一设备 ID  该方法现在处于测试实验阶段。
     */
    public static String getUniquePsuedoId() {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String most = "35"
                + (Build.BOARD.length() % 10)
                + (Build.BRAND.length() % 10)
                + (Build.CPU_ABI.length() % 10)
                + (Build.DEVICE.length() % 10)
                + (Build.MANUFACTURER.length() % 10)
                + (Build.MODEL.length() % 10)
                + (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        String serial = null;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
        } catch (Exception exception) {
            serial = "serial";
        }
        String deviceId = getDeviceId();
        String least = deviceId + serial;

        // Thanks @Joe!
        // http://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        String psuedoId = new UUID(most.hashCode(), least.hashCode()).toString();
        Timber.d("build:%s\nserial:%s\ndevice_id:%s\npsuedoId:%s\n", most, serial, deviceId, psuedoId);
        return psuedoId;
    }

    /**
     * 获取设备 ID。
     *
     * @return 设备 ID
     */
    public static String getDeviceId() {
        TelephonyManager manager = getSystemService(TELEPHONY_SERVICE);
        String deviceId = manager.getDeviceId();
        return deviceId == null ? "device_id" : deviceId;
    }

    /**
     * 获取 Android ID。
     *
     * @return Android ID
     */
    public static String getAndroidId() {
        return Settings.Secure.getString(getAppContext().getContentResolver(), ANDROID_ID);
    }
}
