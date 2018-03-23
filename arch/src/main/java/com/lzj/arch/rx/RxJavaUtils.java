/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.arch.rx;

import io.reactivex.disposables.Disposable;

/**
 * RxJava 工具类。
 *
 * @author 吴吉林
 */
public final class RxJavaUtils {

    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
