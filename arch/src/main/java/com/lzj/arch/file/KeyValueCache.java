/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.file;

import com.lzj.arch.util.map.ReadOnlyMap;

/**
 * 键值对缓存。
 *
 * @author 吴吉林
 */
public interface KeyValueCache extends ReadOnlyMap<String> {

    KeyValueCache remove(String key);

    String getName();

    KeyValueCache putString(String key, String value);

    KeyValueCache putBoolean(String key, boolean value);

    KeyValueCache putInt(String key, int value);

    KeyValueCache putLong(String key, long value);

    void apply();
}
