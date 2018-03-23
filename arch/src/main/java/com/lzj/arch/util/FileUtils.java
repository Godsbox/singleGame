/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import timber.log.Timber;

/**
 * 文件系统工具类
 *
 * @author lihao
 */
public class FileUtils {

    /**
     * 计算文件夹的大小，采用递归文件夹方式。（单位：字节）
     *
     * @param directory 文件夹
     * @return 文件夹的大小
     */
    public static long sizeOfDirectory(File directory) {
        long size = 0L;

        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            return size;
        }

        final File[] files = directory.listFiles();
        if (files == null) // 权限受限制时为空
        {
            return size;
        }

        for (final File file : files) {
            try {
                if (!isSymlink(file)) {
                    size += sizeOf(file);
                    if (size < 0) {
                        break;
                    }
                }
            } catch (IOException ioe) {
                // Ignore exceptions caught when asking if a File is a symlink.
            }
        }

        return size;
    }

    /**
     * 删除文件或者目录
     * @param file
     */
    public  static void deleteFile(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                deleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * 计算文件的大小。（单位：字节）
     *
     * @param file 文件
     * @return 文件的大小
     */
    public static long sizeOf(File file) {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }

        if (!file.exists()) {
            return 0L;
        }

        if (file.isDirectory()) {
            return sizeOfDirectory(file);
        }
        return file.length();
    }

    /**
     * 判断指定的文件是否是符合链接。
     *
     * @param file 待判断的文件
     * @return true：是；false：不是。
     */
    public static boolean isSymlink(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }

        File fileInCanonicalDir = null;
        if (file.getParent() == null) {
            fileInCanonicalDir = file;
        } else {
            File canonicalDir = file.getParentFile().getCanonicalFile();
            fileInCanonicalDir = new File(canonicalDir, file.getName());
        }

        return !fileInCanonicalDir.getCanonicalFile().equals(fileInCanonicalDir.getAbsoluteFile());
    }

    /**
     * 将字节数组数据写入到文件中。
     *
     * @param filepath 文件绝对路径
     * @param data     字节数据数据
     * @return true：保存成功；false：保存失败
     */
    public static boolean write(String filepath, byte[] data) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filepath));
            try {
                bos.write(data);
            } finally {
                if (bos != null) {
                    bos.close();
                    bos = null;
                }
            }
            return true;
        } catch (IOException e) {
            Timber.e("Writes a byte array to a file error: " + e.getMessage());
        }
        return false;
    }

    /**
     * 判断给定的文件路径对应的文件是否存在。
     *
     * @param filepath 文件路径
     * @return true：存在；false：不存在。
     */
    public static boolean exists(String filepath) {
        if (filepath == null || filepath.trim().length() <= 0) {
            return false;
        }
        try {
            File f = new File(filepath);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     *  修改文件名
     * @param oldName
     * @param newName
     * @return
     */
    public static boolean renameFile(String oldName,String newName){
        File file = new File(oldName);
        File newFile = new File(newName);
        file.renameTo(newFile);
        return newFile.exists();
    }

    public static String getFileData(String dir,String key){
        File file = new File(dir + "/localStorage", key);
        String result = "";
        if (!file.exists()) {
            return result;
        }
        try {
            FileInputStream input = new FileInputStream(file);
            result = IoUtils.readFromFile(input);
        } catch (FileNotFoundException e) {
            return result;
        }
        return result;
    }

    public static boolean saveFileData(String dir,String key,String value){
        File file = new File(dir + "/localStorage");
        if(!file.exists()){
            if(!file.mkdir()){
                return false;
            }
        }
        file = new File(dir + "/localStorage" ,key);
        boolean result;
        try {
            FileOutputStream output = new FileOutputStream(file);
            result = IoUtils.writeToFile(value, output);
        } catch (FileNotFoundException e) {
            return false;
        }
        return result;
    }
}
