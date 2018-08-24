/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyirose;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;

import com.lzj.arch.network.Api;
import com.lzj.arch.network.CacheConfig;
import com.lzj.arch.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * API 接口构造器。
 *
 * @author 吴吉林
 */
public class ApiBuilder {

    /**
     * API 接口。
     */
    private ApiImpl api = new ApiImpl();

    /**
     * 接口 URL 地址。
     */
    private String url;

    /**
     * 标识post()方法是否被调用。
     */
    private boolean postCalled;

    /**
     * 设置 URL 地址。
     *
     * @param url URL 地址
     * @return API 接口构造器
     */
    public ApiBuilder url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 设置上传文件。
     *
     * @param name 参数名
     * @param filepath 本地文件路径
     * @return API 构造器
     */
    public ApiBuilder upload(String name, String filepath) {
        api.files.add(Pair.create(name, filepath));
        return this;
    }

    /**
     * 设置接口参数。
     *
     * @param api 接口参数
     * @return API 接口构造器
     */
    public ApiBuilder api(String api) {
        url = api;
        return this;
    }
    /**
     * 设置是否开启硬盘缓存功能。
     *
     * @param enable true：开启；false：关闭。
     * @return API 构造器
     */
    public ApiBuilder enableCache(boolean enable) {
        api.cacheConfig.setCacheEnabled(enable);
        return this;
    }

    /**
     * 设置缓存的键。
     *
     * @param key 键
     * @return API 构造器
     */
    public ApiBuilder cacheKey(String key) {
        api.cacheConfig.setKey(key);
        return this;
    }

    /**
     * 设置是否忽略本地缓存。
     *
     * @param ignore true：忽略；false：使用本地缓存
     * @return API 构造器
     */
    public ApiBuilder ignoreCache(boolean ignore) {
        api.cacheConfig.setIgnoreCache(ignore);
        return this;
    }

    /**
     * 构造 API 接口。
     *
     * @return API 接口
     */
    public Api build() {
        api.url = url;
        api.method = postCalled ? "POST" : "GET";
        postCalled = false;
        return api;
    }

    /**
     * API 接口实现类。
     */
    private class ApiImpl implements Api {

        /**
         * URL 地址。
         */
        private String url;

        /**
         * 请求方法。
         */
        private String method = "GET";

        /**
         * 缓存配置。
         */
        private CacheConfig cacheConfig = new CacheConfig();

        /**
         * 请求参数表
         */
        private Map<String, String> params = new ArrayMap<>();

        /**
         * 上传文件列表。
         */
        private List<Pair<String, String>> files = new ArrayList<>(1);

        @Override
        public CacheConfig getCacheConfig() {
            if (cacheConfig.isCacheEnabled()
                    && StringUtils.isEmpty(cacheConfig.getKey())) {
                cacheConfig.setKey(url);
            }
            return cacheConfig;
        }

        @Override
        public String getUrl() {
            return url;
        }

        @Override
        public String getMethod() {
            return method;
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }

        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> headers = new ArrayMap<>(1);
            return headers;
        }

        @Override
        public List<Pair<String, String>> getFiles() {
            return files;
        }
    }
}
