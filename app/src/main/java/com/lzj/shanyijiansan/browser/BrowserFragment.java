/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyijiansan.browser;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lzj.arch.app.web.WebConstant;
import com.lzj.arch.app.web.WebFragment;
import com.lzj.arch.util.OsUtils;
import com.lzj.arch.util.ProcessUtils;
import com.lzj.arch.util.StringUtils;
import com.lzj.arch.util.ToastUtils;
import com.lzj.arch.util.ViewUtils;
import com.lzj.shanyijiansan.AppConstant;
import com.lzj.shanyijiansan.BaWei;
import com.lzj.shanyijiansan.R;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import java.io.File;
import timber.log.Timber;

import java.io.IOException;
import java.io.InputStream;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.wujilin.doorbell.Doorbell;

import static com.lzj.arch.app.web.WebConstant.EXTRA_LAYOUT_ID;
import static com.lzj.arch.util.ViewUtils.inflate;
import static com.lzj.shanyijiansan.AppConstant.GAME_DIR_PRE;
import static com.lzj.shanyijiansan.AppConstant.GAME_ID;
import static com.lzj.shanyijiansan.AppConstant.GAME_UUID;
import static com.lzj.shanyijiansan.AppConstant.OFFLINE_SHOULDINTER_CHAPTER_URL;
import static com.lzj.shanyijiansan.AppConstant.OFFLINE_SHOULDINTER_URL;
import static com.lzj.shanyijiansan.AppConstant.getUserAgent;

/**
 * 浏览器界面
 *
 * @author wsy
 */
public class BrowserFragment
        extends WebFragment<BrowserContract.Presenter>
        implements BrowserContract.PassiveView, View.OnClickListener {

    {
        setArgument(EXTRA_LAYOUT_ID, R.layout.app_fragment_browser);
    }

    /**
     * 离线状态
     */
    private boolean offline = true;

    /**
     * 当前界面视图。
     */
    private FrameLayout container;

    /**
     * 播放结束视图。
     */
    private View quitConfirm;

    /**
     * 按钮
     */
    private TextView again, more;

    @Override
    public void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        int layoutId = getArgument(EXTRA_LAYOUT_ID);
        getConfig().setLayoutResource(layoutId);
    }

    @Override
    public void onInitView(Bundle state) {
        super.onInitView(state);
        addWebInterface(new BrowserWebInterface());
        container = findView(R.id.load_view_container);
    }

    @Override
    protected void onInitWebSettings(WebSettings settings) {
        super.onInitWebSettings(settings);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        File cacheDir = new File(getActivity().getCacheDir().getAbsolutePath() + "/webview");
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(cacheDir.getAbsolutePath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        if (OsUtils.asOfLollipop()) {
            settings.setMixedContentMode(0);
        }
        String ua = settings.getUserAgentString();
        ua += " " + getUserAgent();
        settings.setUserAgentString(ua);
    }

    @Override
    public void onResume() {
        callback("resumeAudio");
        super.onResume();
    }

    @Override
    public void onPause() {
        callback("pauseAudio");
        super.onPause();
    }

    @Override
    protected WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (url.startsWith("blob:")) {
            return null;
        }
        Log.d("wsy","shouldInterceptRequest-> %s"+url);

        if (offline && (url.startsWith(OFFLINE_SHOULDINTER_URL) ||
                url.startsWith("https://mapi.3000api.com/apis/soft/v1.0/game-test.html"))) {
            return dealWithJson(GAME_UUID);
        }

        if (offline && url.startsWith(OFFLINE_SHOULDINTER_CHAPTER_URL)) {
            Uri uri = Uri.parse(url);
            String uuid = uri.getQueryParameter("chapter_uuid");
            return dealWithJson(uuid);
        }

        if(offline && StringUtils.isVolume(url)){
            return dealWithMedia(url);
        }
        return super.shouldInterceptRequest(view, url);
    }

    private WebResourceResponse dealWithJson(String uuid) {
        InputStream input;
        try {
            input = getContext().getAssets().open(GAME_DIR_PRE + GAME_ID +"/json/"+uuid+".json");
            /*StringBuffer gameJson = new StringBuffer(FILE_DIR_HEAD);
            gameJson.append(GAME_DIR_PRE)
                    .append(GAME_ID)
                    .append("/")
                    .append(GAME_JSON)
                    .append("/")
                    .append(uuid)
                    .append(".")
                    .append(GAME_JSON);
            input = new FileInputStream(gameJson.toString());*/
            //input = new FileInputStream(GAME_URL);
        } catch (Exception e) {
            try {
                input = getContext().getAssets().open(uuid+".json");
            } catch (IOException e1) {
                showJsonError();
                return null;
            }
        }
        return new WebResourceResponse("text/json", "UTF-8", input);
    }

    private WebResourceResponse dealWithMedia(String url) {
        InputStream input;
        String mime = "audio/mpeg";
        try {
            int dianIndex = url.lastIndexOf(".");
            if (dianIndex != -1) {//获取资源文件的格式,例如这是一个.png,还是.js,还是.mp3
                mime = url.substring(dianIndex + 1);
                mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mime);
                if (mime == null) {//假设没有获取到正常的mime类型,则设置为通用类型
                    mime = "*/*";
                }
            }
            url = url.replace("file:///android_asset/","");
            url = url.replace("/android_asset/","");
            input = getContext().getAssets().open(url + "shanyi");
        } catch (IOException e) {
            return null;
        }
        return new WebResourceResponse(mime, "UTF-8", input);
    }

    @Override
    public void showJsonError() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShort(R.string.offline_error);
            }
        });
    }

    @Override
    public void showDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getContext(), R.style.AlertDialog)
                        .setMessage(R.string.look_more_work)
                        .setPositiveButton(R.string.go_look, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(ProcessUtils.isAppInstalled(AppConstant.SHANYI_PACKAGE)){
                                    ProcessUtils.startOtherApp(getActivity(), AppConstant.SHANYI_PACKAGE);
                                    return;
                                }
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(BaWei.getInstance().getDownloadShanyiUrl()));
                                startActivity(Intent.createChooser(intent, "请选择浏览器"));
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }
        });
    }

    @Override
    public void toggleQuitConfirm(boolean show) {
        if (!show) {
            container.post(new Runnable() {
                @Override
                public void run() {
                    container.removeView(quitConfirm);
                }
            });
            return;
        }

        if (quitConfirm == null) {
            quitConfirm = inflate(R.layout.app_view_work_end, container, false);
            more = ViewUtils.findView(quitConfirm, R.id.more);
            again = ViewUtils.findView(quitConfirm, R.id.again);
            more.setOnClickListener(this);
            again.setOnClickListener(this);
            quitConfirm.setOnClickListener(this);
        }
        container.post(new Runnable() {
            @Override
            public void run() {
                container.addView(quitConfirm);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.again){
            BaWei.getInstance().removeActivity_(getActivity());
            Doorbell.with(this)
                    .start(BrowserActivity.class)
                    .extra(WebConstant.EXTRA_URL, AppConstant.GAME_PLAYURL)
                    .transition(R.anim.app_fade_in, R.anim.app_outgoing_left)
                    .ring();
        }

        if(v.getId() == R.id.more){
            if(ProcessUtils.isAppInstalled(AppConstant.SHANYI_PACKAGE)){
                ProcessUtils.startOtherApp(getActivity(), AppConstant.SHANYI_PACKAGE);
                return;
            }
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(BaWei.getInstance().getDownloadShanyiUrl()));
            startActivity(Intent.createChooser(intent, "请选择浏览器"));
        }
    }
}