/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.shanyigdscfz;

import io.reactivex.Observable;

/**
 * 仓库。
 *
 * @author 吴吉林
 */
public interface Repository {

    /**
     * 延迟。
     *
     * @param milliseconds 延迟时间。（单位：毫秒）
     * @return 延迟
     */
    Observable<Long> timeout(int milliseconds);

    /**
     * 倒计时。
     *
     * @param seconds 倒计时（单位：秒）
     * @return 倒计时
     */
    Observable<Long> countdown(int seconds);
}
