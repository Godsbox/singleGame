/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.app.group;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.lzj.arch.R;
import com.lzj.arch.app.content.ContentFragment;
import com.lzj.arch.app.group.GroupContract.PassiveView;
import com.lzj.arch.app.group.GroupContract.Presenter;


/**
 * 分组内容界面。
 *
 * @author 吴吉林
 */
public abstract class GroupFragment<P extends Presenter>
        extends ContentFragment<P>
        implements PassiveView {

    /**
     * 翻页视图。
     */
    private ViewPager pager;

    /**
     * 标签布局。
     */
    private TabLayout tabLayout;

    /**
     * 分组适配器。
     */
    private GroupAdapter adapter;

    /**
     * 翻页视图 ID。
     */
    private int pagerId = R.id.pager;

    /**
     * 默认缓存页面数
     */
    private int DEFAULT_CACHE_PAGE_SIZE = 3;

    {
        getConfig().setLayoutResource(R.layout.app_fragment_group);
    }

    @Override
    public void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        adapter = new GroupAdapter(getFragmentManager(), this);
    }

    @CallSuper
    @Override
    public void onFindView() {
        super.onFindView();
        pager = findView(R.id.pager);
        tabLayout = findView(R.id.tab_layout);
    }

    @CallSuper
    @Override
    public void onInitView(Bundle state) {
        super.onInitView(state);
        pager.setId(pagerId);
        pager.addOnPageChangeListener(new OnPageChangeListener());
        pager.setOffscreenPageLimit(DEFAULT_CACHE_PAGE_SIZE);
        onInitAdapter();
        pager.setOffscreenPageLimit(DEFAULT_CACHE_PAGE_SIZE - 1);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    public void setCachePageSize(int size) {
        this.DEFAULT_CACHE_PAGE_SIZE = size;
    }

    @Override
    public void showFragment(int position) {
        pager.setCurrentItem(position);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (adapter != null) {
            adapter.setUserVisibleHint(isVisibleToUser);
        }
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    /**
     * 更新tabLayout tab
     * @param tabView
     * @param position
     */
    public void updateTab(View tabView, int position){
        if(tabView == null){
            return;
        }
        TabLayout.Tab tab = tabLayout.getTabAt(position);

        // 更新Badge前,先remove原来的customView,否则Badge无法更新
        View customView = tab.getCustomView();
        if (customView != null) {
            ViewParent parent = customView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(customView);
            }
        }
        // 更新CustomView
        tab.setCustomView(tabView);

        // 需加上以下代码,不然会出现更新Tab角标后,选中的Tab字体颜色不是选中状态的颜色
        if(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getCustomView() == null){
            return;
        }
        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getCustomView().setSelected(true);
    }

    public CharSequence getTabTitle(int position){
        return adapter.getPageTitle(position);
    }

    /**
     * 初始化适配器。
     */
    protected abstract void onInitAdapter();

    /**
     * 添加分组页面代表。
     *
     * @param delegate 分组页面代表
     */
    protected void addPageDelegate(PageDelegate delegate) {
        if (adapter != null) {
            adapter.addPageDelegate(delegate);
        }
    }


    /**
     * 设置翻页视图 ID。
     *
     * @param pagerId 翻页视图 ID
     */
    protected void setPagerId(int pagerId) {
        this.pagerId = pagerId;
    }

    /**
     * 页面变化监听器。
     */
    private class OnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            getPresenter().onPageChange(position);
        }
    }
}
