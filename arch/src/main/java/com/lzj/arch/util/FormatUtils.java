/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.util;

import java.text.DecimalFormat;

/**
 * 关于格式化操作方法的工具类。
 *
 * @author 吴吉林
 */
public final class FormatUtils {

    private static final DecimalFormat FORMAT_INTEGER = new DecimalFormat("#,###");
    private static final DecimalFormat FORMAT_ONE_DECIMAL = new DecimalFormat("#,##0.#");
    private static final DecimalFormat FORMAT_TOW_DECIMAL = new DecimalFormat("#,###.##");

    /**
     * 将数值转化成保留两个小数位的字符串。
     *
     * @param value 数值
     * @return 浮点数字符串
     */
    public static String toTwoDecimal(double value) {
        return FORMAT_TOW_DECIMAL.format(value);
    }

    /**
     * 将数值转化成保留一个小数位的字符串。
     *
     * @param value 数值
     * @return 浮点数字符串
     */
    public static String toOneDecimal(double value) {
        return FORMAT_ONE_DECIMAL.format(value);
    }

    /**
     * 将整数格式化成以逗号分格的字符串。如：1,000。
     *
     * @param value 整数
     * @return 格式化过的整数
     */
    public static String format(long value) {
        return FORMAT_INTEGER.format(value);
    }

    /**
     * 格式化整数，支持以w为单位的格式。
     *
     * @param value 整数
     * @return 格式化过的整数
     */
    public static String inTenThousand(long value) {
        double div = value / 10000.0;
        return div >= 1.0
                ? toOneDecimal(div) + "万"
                : format(value);
    }

    /**
     * 格式化下载速度。
     *
     * @param speed 速度。（单位：kb）
     * @return 格式化后的速度字符串值
     */
    public static String formatSpeed(int speed) {
        float div = (float) (speed / 1024.0);
        return div >= 1.0
                ? toOneDecimal(div) + "m/s"
                : speed + "k/s";
    }

    /**
     * 将给定的值转换成MB为单位。
     *
     * @param value 值
     * @return 格式化后的字符串值
     */
    public static String inMegabyte(long value) {
        double mb = (double) value / (1024 * 1024);
        return toTwoDecimal(mb);
    }

    /**
     * 是否是纯数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
