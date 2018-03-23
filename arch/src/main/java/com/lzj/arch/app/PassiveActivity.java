/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.arch.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.lzj.arch.R;
import com.lzj.arch.app.lifecycle.ActivityLifecycle;
import com.lzj.arch.app.lifecycle.LifecycleManager;
import com.lzj.arch.core.Contract;
import com.lzj.arch.core.Contract.Presenter;
import com.lzj.arch.core.PresenterDelegate;
import com.lzj.arch.core.PresenterManager;
import com.lzj.arch.util.StringUtils;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static com.lzj.arch.core.Arch.newPresenterDelegate;

/**
 * 被动屏幕。
 *
 * @param <P> 表现者类型，如果不想使用表现者功能，该参数可以传 {@link Presenter}。
 * @author 吴吉林
 */
public abstract class PassiveActivity<P extends Presenter>
        extends AppCompatActivity
        implements PassiveController<P> {

    /**
     * 界面标签：单个片段。
     */
    public static final String TAG_SINGLE_FRAGMENT = "single_activity_fragment";

    /**
     * 表现者委托。
     */
    private PresenterDelegate<P> presenterDelegate = newPresenterDelegate(this);

    /**
     * 视图委托。
     */
    private PassiveDelegate<P> passiveDelegate = new PassiveDelegate<>(this);

    /**
     * 屏幕生命周期。
     */
    private ActivityLifecycle lifecycle;

    /**
     * 单个界面。
     */
    private Fragment fragment;

    /**
     * 进场动画。
     */
    private int enterAnim;

    /**
     * 退场动画。
     */
    private int exitAnim;

    /**
     * 标识是否全屏。
     */
    private boolean fullscreen;

    /**
     * 标识是否调用 {@link #onBackPressed()} 方法。
     */
    private boolean onBackPressedCallable = true;

    /**
     * 参数配置表。
     */
    private ArrayMap<String, ParamConfig> paramConfigs;

    {
        getConfig().setLayoutResource(R.layout.app_activity_single);
    }

    /**
     * 设置界面。
     *
     * @param fragment 界面
     */
    protected void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    protected void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public void setOnBackPressedCallable(boolean callable) {
        this.onBackPressedCallable = callable;
    }

    /**
     * 获取屏幕生命周期。
     *
     * @return 屏幕生命周期
     */
    protected ActivityLifecycle getLifecycle() {
        if (lifecycle == null) {
            lifecycle = LifecycleManager.getInstance().createActivityLifecycle();
        }
        return lifecycle;
    }

    /**
     * 设置屏幕生命周期。
     *
     * @param lifecycle 屏幕生命周期
     */
    protected void setLifecycle(ActivityLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    protected void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        showFullscreen();
        setContentView(passiveDelegate.getConfig().getLayoutResource());
        passiveDelegate.findView(this);
        passiveDelegate.initView();
        handleIntent(getIntent());
        onFindView();
        onInitView(state);
        Bundle extras = getIntent() != null ? getIntent().getExtras() : null;
        presenterDelegate.onViewCreate(this, state, extras, PresenterManager.getDefault());
        if (state == null) {
            commitFragment(fragment);
        }
        getLifecycle().onCreate(this, state);
        presenterDelegate.attachView(this, true);
    }

    /**
     * 显示给定界面。
     *
     * @param fragment 界面
     */
    protected void commitFragment(Fragment fragment) {
        setFragment(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        onFragmentTransaction(transaction);
        if (!transaction.isEmpty()) {
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 处理界面事务。<br /><br />
     *
     * 默认实现是添加单个界面。
     *
     * @param transaction 界面事务
     */
    protected void onFragmentTransaction(FragmentTransaction transaction) {
        if (fragment == null) {
            return;
        }
        copyIntentExtras(fragment);
        transaction.add(R.id.fragment_container, fragment, TAG_SINGLE_FRAGMENT);
    }

    /**
     * 拷贝屏幕参数到界面参数。
     *
     * @param fragment 界面
     */
    protected void copyIntentExtras(Fragment fragment) {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        fragment.getArguments().putAll(extras);
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedCallable) {
            super.onBackPressed();
        }
    }

    /**
     * 显示全屏。
     */
    private void showFullscreen() {
        if (!fullscreen) {
            return;
        }
        getWindow().addFlags(FLAG_FULLSCREEN);

    }

    /**
     * 处理意图。
     */
    protected void handleIntent(Intent intent) {
        if (paramConfigs == null || paramConfigs.size() == 0) {
            return;
        }
        Uri uri = intent == null ? null : intent.getData();
        if (uri == null || !getLifecycle().isSchemeIntent(uri.getScheme())) {
            return;
        }
        for (String name : uri.getQueryParameterNames()) {
            if (paramConfigs.containsKey(name)) {
                putIntentExtraFromUri(name, uri.getQueryParameter(name));
            }
        }
    }

    private void putIntentExtraFromUri(String name, String value) {
        ParamConfig config = paramConfigs.get(name);
        if (config.valueClass == int.class) {
            getIntent().putExtra(config.extra, StringUtils.toInt(value));
            return;
        }
        if (config.valueClass == String.class) {
            getIntent().putExtra(config.extra, value);
            return;
        }
        if (config.valueClass == double.class) {
            getIntent().putExtra(config.extra, StringUtils.toDouble(value));
            return;
        }
        if (config.valueClass == boolean.class) {
            getIntent().putExtra(config.extra, Boolean.parseBoolean(value));
        }
    }

    /**
     * 添加参数配置。
     *
     * @param config 参数配置
     */
    protected void addParamConfig(ParamConfig config) {
        if (config == null) {
            return;
        }
        if (paramConfigs == null) {
            paramConfigs = new ArrayMap<>();
        }
        paramConfigs.put(config.key, config);
    }

    @Override
    public void onFindView() {
        // 空实现
    }

    @Override
    public void onInitView(Bundle state) {
        // 空实现
    }

    @Override
    public void onNavigationClick() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        getLifecycle().onFinish(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public final <V> V findView(int id) {
        return (V) findViewById(id);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getLifecycle().onPostCreate(this, savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLifecycle().onStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterDelegate.onViewResume(true);
        getLifecycle().onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenterDelegate.onViewPause();
        getLifecycle().onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getLifecycle().onStop(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenterDelegate.onSaveState(outState);
        getLifecycle().onSaveState(this, outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterDelegate.detachView();
        presenterDelegate.onViewDestroy(this);
        getLifecycle().onDestroy(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        passiveDelegate.onActivityResult(requestCode, resultCode, data);
        getLifecycle().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public P getPresenter() {
        return presenterDelegate.getPresenter();
    }

    @Override
    public P createPresenter() {
        return presenterDelegate.createPresenter();
    }

    @Override
    public Contract.Router getRouter() {
        return lifecycle.getRouter();
    }

    /**
     * 获取视图配置。
     *
     * @return 视图配置
     */
    public PassiveConfig getConfig() {
        return passiveDelegate.getConfig();
    }

    protected PassiveDelegate<P> getPassiveDelegate() {
        return passiveDelegate;
    }

    @Override
    public void setTitle(String title) {
        passiveDelegate.setTitle(title);
    }

    @Override
    public void setTitle(int title) {
        passiveDelegate.setTitle(title);
    }

    @Override
    public void setAppbarColor(int color) {
        passiveDelegate.setAppbarColor(color);
    }

    @Override
    public int getAppbarColor() {
        return passiveDelegate.getAppbarColor();
    }

    @Override
    public void setAppbarColorRes(int color) {
        passiveDelegate.setAppbarColorRes(color);
    }

    @Override
    public void setAppbarVisible(int visible) {
        passiveDelegate.setAppbarVisible(visible);
    }
}
