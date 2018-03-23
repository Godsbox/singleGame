/*
 * Copyright (c) 2017 3000.com All Rights Reserved. 
 */
package com.lzj.arch.app.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lzj.arch.R;
import com.lzj.arch.app.content.ContentFragment;
import com.lzj.arch.app.web.WebContract.PassiveView;
import com.lzj.arch.app.web.WebContract.Presenter;
import com.lzj.arch.view.MyWebView;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;



import static com.lzj.arch.util.ViewUtils.setVisible;

/**
 * 网页内容界面。
 *
 * @author 吴吉林
 */
public class WebFragment<P extends Presenter>
        extends ContentFragment<P>
        implements PassiveView {

    /**
     * 网页视图。
     */
    private MyWebView webView;

    /**
     * 加载进度。
     */
    private ProgressBar progress;

    /**
     * Chrome 客户端。
     */
    private WebChromeClient webChromeClient = new WebChromeClient() {

        private View wholeView;

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progress.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            getPresenter().onTitleReceived(title);
        }

        @Override
        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            parent.removeView(webView);
            parent.addView(view);
            wholeView = view;
        }

        @Override
        public void onHideCustomView() {
            if (wholeView != null) {
                ViewGroup parent = (ViewGroup) wholeView.getParent();
                parent.removeView(wholeView);
                parent.addView(webView);
            }
        }

        // For Android 3.0+
        public void openFileChooser( ValueCallback uploadMsg, String acceptType ) {
            WebFragment.this.onShowFileChooser(uploadMsg,acceptType);
        }
        //For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
            WebFragment.this.onShowFileChooser(uploadMsg,acceptType);
        }
        // For Android 5.0+
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            String type = "";
            if(fileChooserParams.getAcceptTypes() != null && fileChooserParams.getAcceptTypes().length != 0){
                String [] types = fileChooserParams.getAcceptTypes();
                type = types[0];
            }
            WebFragment.this.onShowFileChooser5(filePathCallback, type);
            return true;
        }
    };

    {
        getConfig().setLayoutResource(R.layout.app_fragment_web);
    }

    @CallSuper
    @Override
    public void onFindView() {
        super.onFindView();
        webView = findView(R.id.web);
        progress = findView(R.id.progress);
    }

    @CallSuper
    @Override
    public void onInitView(Bundle state) {
        super.onInitView(state);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
        webView.setScrollListener(new MyWebView.IScrollListener() {
            @Override
            public void onScrollChanged(int scrollY) {
                if (scrollY == 0)
                {
                    setRefreshEnabled(true);
                } else
                {
                    setRefreshEnabled(false);
                }
            }
        });
        webView.setDownloadListener(new MyWebViewDownloadListener());
        webView.clearCache(true);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 允许js弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        onInitWebSettings(settings);
    }

    /**
     * 处理网页设置。
     *
     * @param settings 网页设置
     */
    protected void onInitWebSettings(WebSettings settings) {
        // 空实现
    }

    @Override
    public void showLoading() {
        progress.setProgress(0);
        setProgressVisible(true);
    }

    @Override
    public void setProgressVisible(boolean visible) {
        setVisible(progress, visible);
    }

    @Override
    public void load(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void refresh() {
        webView.reload();
    }

    @Override
    public void callback(String callback) {
        webView.loadUrl("javascript:" + callback + "()");
    }

    @Override
    public void callback(final String callback, final Object param) {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:" + callback + "(" + param + ")");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    /**
     * 添加网页接口。
     *
     * @param webInterface 网页接口
     */
    protected void addWebInterface(WebInterface<P> webInterface) {
        if (webInterface != null) {
            webInterface.setFragment(this);
            webView.addJavascriptInterface(webInterface, webInterface.getName());
        }
    }

    /**
     * 是否覆盖给定 URL 地址的加载。
     *
     * @param view 网页视图
     * @param url  网页 URL 地址
     * @return true：覆盖；false：未覆盖。
     */
    protected boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    /**
     * 是否覆盖给定 URL 地址的加载。
     *
     */
    protected void onShowFileChooser(ValueCallback<Uri> uploadMsg,String type) {
        // 空实现
    }

    protected void onShowFileChooser5(ValueCallback<Uri[]> uploadMsg,String type) {
        // 空实现
    }

    /**
     * 重定向 URL 地址。
     *
     * @param view 网页视图
     * @param url  网页 URL 地址
     * @return true：覆盖；false：未覆盖。
     */
    protected WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return null;
    }

    /**
     * 网页客户端。
     */
    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return WebFragment.this.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return WebFragment.this.shouldInterceptRequest(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            getPresenter().onPageStart(url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            getPresenter().onPageEnd(url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            switch (errorCode) {
                case ERROR_HOST_LOOKUP:
                case ERROR_IO:
                case ERROR_CONNECT:
                case ERROR_UNKNOWN:
                    description = getString(R.string.http_code_no_network);
                    break;
                case ERROR_TIMEOUT:
                    description = getString(R.string.http_code_timeout);
                    break;
            }
            getPresenter().onPageError(errorCode, description, failingUrl);
        }
    };

    @Override
    public void onNavigationClick() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        }
        else {
            super.onNavigationClick();
        }
    }

    /**
     * webview下载监听
     * */
    private class MyWebViewDownloadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String disposition, String mimeType, long length) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
