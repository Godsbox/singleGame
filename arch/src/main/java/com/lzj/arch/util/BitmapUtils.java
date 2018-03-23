/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.arch.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.graphics.Bitmap.Config.RGB_565;
import static com.lzj.arch.util.ContextUtils.getAppContext;

/**
 * 关于位图相关操作方法的工具类。
 *
 * @author wsy
 */
public final class BitmapUtils {

    private static final int MAX_SIZE = 380 * 1024;

    /**
     * 私有构造方法。
     */
    private BitmapUtils() {
    }

    /**
     * 获取低内存开销的图片位图。
     *
     * @param resId 图片资源 ID
     * @return 位图
     */
    public static Bitmap getLowMemoryBitmap(int resId) {
        InputStream is = getAppContext().getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, getLowMemoryOptions());
    }

    /**
     * 获取低内存开销的图片位图。
     *
     * @param filepath 图片文件路径
     * @return 位图
     */
    public static Bitmap getLowMemoryBitmap(String filepath) {
        return BitmapFactory.decodeFile(filepath, getLowMemoryOptions());
    }

    /**
     * 获取低内存开销选项。
     *
     * @return 低内存开销选项
     */
    private static BitmapFactory.Options getLowMemoryOptions() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return options;
    }

    /**
     * 压缩图片 并保存
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, DisplayUtils.getDisplayWidth(), DisplayUtils.getDisplayHeight());

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        Bitmap newBitmap = compressImage(bitmap);
        if (bitmap != null){
            bitmap.recycle();
        }
        return newBitmap;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int newWidth, int newHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, newWidth, newHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        Bitmap newBitmap = compressImage(bitmap);
        if (bitmap != null){
            bitmap.recycle();
        }
        return newBitmap;
    }


    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * 质量压缩
     * @param image
     */
    public static Bitmap compressImage(Bitmap image){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 80;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        int y = 10;
        while ( os.toByteArray().length > MAX_SIZE) {
            // Clean up os
            os.reset();
            // interval 10
            options -= y;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
            if(options == 10){
                y = 2;
            }
            if(options == 6){
                break;
            }
        }

        Bitmap bitmap = null;
        byte[] b = os.toByteArray();
        if (b.length != 0) {
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return bitmap;
    }

    public static String getBase64(String filePath, int newWidth, int newHeight) {
        Bitmap bitmap = getSmallBitmap(filePath, newWidth, newHeight);
        String base64 = bitmapToBase64(getSmallBitmap(filePath, newWidth, newHeight));
        bitmap.recycle();
        return base64;
    }


        /**
         * bitmap 转base64 String
         * @param bitmap
         * @return
         */
    public static String bitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream baoStr = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baoStr);
        byte[] datas = baoStr.toByteArray();
        return Base64.encodeToString(datas, Base64.DEFAULT);
    }

    /**
     * 设置图片为灰色
     * @param view
     */
    public static void setImageGray(ImageView view){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);//饱和度 0灰色 100过度彩色，50正常
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        view.setColorFilter(filter);
    }

    /**
     * 设置图片为正常色
     * @param view
     */
    public static void setImageNormal(ImageView view){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(50);//饱和度 0灰色 100过度彩色，50正常
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        view.setColorFilter(filter);
    }


    /**
     * 加载本地资源
     */
    public static void loadLowMemoryBitmap(int resId, ImageView view){
        InputStream is = getAppContext().getResources().openRawResource(resId);
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, BitmapUtils.getLowMemoryOptions());
        view.setImageBitmap(bitmap);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
