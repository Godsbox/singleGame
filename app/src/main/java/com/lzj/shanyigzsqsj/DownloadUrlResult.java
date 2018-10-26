/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.shanyigzsqsj;

import com.google.gson.annotations.SerializedName;

/**
 * @author wsy
 */
public class DownloadUrlResult {

    /**
     * 话题id。
     */
    @SerializedName("download_url")
    private String url;

    public String getUrl() {
        return url;
    }
}
