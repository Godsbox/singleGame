/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.shanyijiansan;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.lzj.arch.app.collection.CollectionResult;
import com.lzj.arch.network.Api;
import com.lzj.arch.rx.ObservableException;
import com.lzj.arch.util.ParameterizedTypeImpl;
import com.lzj.arch.util.ResourceUtils;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

import static com.lzj.arch.network.ApiClient.GSON;
import static com.lzj.arch.network.ApiClient.callApiSync;
import static com.lzj.arch.rx.ObservableException.ERROR_CODE_JSON;
import static com.lzj.arch.rx.ObservableException.ERROR_CODE_SECONDARY_EMPTY;
import static com.lzj.arch.rx.ObservableException.ofHttpError;
import static com.lzj.arch.rx.ObservableException.ofResultError;
import static com.lzj.arch.util.GsonUtils.getBoolean;
import static com.lzj.arch.util.GsonUtils.getInt;
import static com.lzj.arch.util.GsonUtils.getString;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 仓库基类。
 *
 * @author 吴吉林
 */
public abstract class AbstractRepository implements Repository {

    @Override
    public Observable<Long> timeout(int milliseconds) {
        return Observable.timer(milliseconds, MILLISECONDS)
                .observeOn(mainThread());
    }

    @Override
    public Observable<Long> countdown(final int seconds) {
        return Observable.interval(1, 1, SECONDS)
                .take(seconds)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return seconds - aLong;
                    }
                })
                .observeOn(mainThread());
    }

    /**
     * 调用 API 接口。<br /><br />
     *
     * 如果返回的 data 字段是 null，则 <code>clazz</code> 必须是 <code>String.class</code>。
     *
     * @param api API 接口
     * @return 调用结果
     */
    protected <T> Observable<T> callApi(final Api api, Class<T> clazz) {
        return callApi(api, (Type) clazz);
    }

    /**
     * 调用 API 接口。<br /><br />
     *
     * 如果返回的 data 字段是 null，则 <code>clazz</code> 必须是 <code>String.class</code>。
     *
     * @param api API 接口
     * @param raw 结果类型的原始类型（泛型擦除后的类型），如：<code>List&lt;String&gt;</code>的 <code>List.class</code>。
     * @param arg 结果类型的泛型类型，如：<code>List&lt;String&gt;</code>的 <code>String.class</code>。
     * @param <T> 结果类型
     * @return 调用结果
     */
    protected <T> Observable<T> callApi(final Api api, Class<?> raw, Type arg) {
        return callApi(api, new ParameterizedTypeImpl(raw, arg));
    }

    /**
     * 调用 API 接口。<br /><br />
     *
     * 如果返回的 data 字段是 null，则 <code>clazz</code> 必须是 <code>String.class</code>。
     *
     * @param api API 接口
     * @param type 结果类型
     * @return 调用结果
     */
    private <T> Observable<T> callApi(final Api api, final Type type) {
        return Observable.create(new OnCallApiSubscribe<T>(api, type))
                .subscribeOn(io())
                .observeOn(mainThread());
    }

    /**
     * 调用 API 订阅。
     *
     * @param <T> 结果类型
     */
    @SuppressWarnings("unchecked")
    public static class OnCallApiSubscribe<T> implements ObservableOnSubscribe<T> {

        /**
         * API。
         */
        private Api api;

        /**
         * 结果类型。
         */
        private Type type;

        public OnCallApiSubscribe(Api api, Type type) {
            this.api = api;
            this.type = type;
        }

        @Override
        public void subscribe(ObservableEmitter<T> emitter) throws Exception {
            JsonObject result;
            try {
                result = callApiSync(api);
            } catch (Exception e) {
                if(e instanceof ObservableException){
                    emitter.onError(e);
                    return;
                }
                emitter.onError(ofHttpError(ERROR_CODE_SECONDARY_EMPTY, ResourceUtils.getString(com.lzj.arch.R.string.http_code_3)));
                return;
            }
            boolean success = getBoolean(result, "success");
            int code = getInt(result, "status");
            String message = getString(result, "msg");
            if (!success) {
                throw ofResultError(code, message);
            }
            JsonElement data = result.get("data");
            try {
                if (data.isJsonNull()) {
                    emitter.onNext((T) GSON.fromJson(message, type));
                    return;
                }
                JsonElement element = data.isJsonArray() ? data.getAsJsonArray() : data.getAsJsonObject();
                Object value = GSON.fromJson(element, type);
                if (value instanceof CollectionResult) {
                    ((CollectionResult) value).setFromCache(getBoolean(result, "from_cache"));
                }
                emitter.onNext((T) value);
            } catch (JsonSyntaxException error) {
                message = ResourceUtils.getString(com.lzj.arch.R.string.http_code_server_error, ERROR_CODE_JSON);
                emitter.onError(ofHttpError(code, message));
            }
        }
    }
}
