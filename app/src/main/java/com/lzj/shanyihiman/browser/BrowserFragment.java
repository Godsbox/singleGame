/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.shanyihiman.browser;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lzj.arch.app.web.WebConstant;
import com.lzj.arch.app.web.WebFragment;
import com.lzj.arch.network.NetworkManager;
import com.lzj.arch.rx.ObserverAdapter;
import com.lzj.arch.util.BitmapUtils;
import com.lzj.arch.util.DateUtils;
import com.lzj.arch.util.OsUtils;
import com.lzj.arch.util.ProcessUtils;
import com.lzj.arch.util.StringUtils;
import com.lzj.arch.util.TimeUtils;
import com.lzj.arch.util.ToastUtils;
import com.lzj.arch.util.ViewUtils;
import com.lzj.shanyihiman.AppConstant;
import com.lzj.shanyihiman.BaWei;
import com.lzj.shanyihiman.R;
import com.lzj.shanyihiman.doing.DoingFragment;
import com.mob4399.adunion.AdUnionVideo;
import com.mob4399.adunion.listener.OnAuVideoAdListener;
import com.mobgi.common.utils.FileUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.wujilin.doorbell.Doorbell;

import io.reactivex.Observable;

import static com.lzj.arch.app.web.WebConstant.EXTRA_LAYOUT_ID;
import static com.lzj.arch.util.ViewUtils.inflate;
import static com.lzj.shanyihiman.AppConstant.GAME_DIR_PRE;
import static com.lzj.shanyihiman.AppConstant.GAME_ID;
import static com.lzj.shanyihiman.AppConstant.GAME_UUID;
import static com.lzj.shanyihiman.AppConstant.OFFLINE_SHOULDINTER_CHAPTER_URL;
import static com.lzj.shanyihiman.AppConstant.OFFLINE_SHOULDINTER_URL;
import static com.lzj.shanyihiman.AppConstant.getUserAgent;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

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
    private ImageView again, more;

    /**
     * 开始广告
     */
    private boolean startAds = false;

    /**
     * 是否已经加载完成
     */
    private boolean finish = false;

    @Override
    public void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        int layoutId = getArgument(EXTRA_LAYOUT_ID);
        getConfig().setLayoutResource(layoutId);

        Observable.timer(10000, TimeUnit.MILLISECONDS).subscribeOn(io())
                .observeOn(mainThread()).subscribe(new ObserverAdapter<Long>(){
            @Override
            public void onNext(Long aLong) {
                if(NetworkManager.isWifi()){
                    initAds();
                }
            }
        });
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
        Log.d("should","shouldInterceptRequest-> %s"+url);

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
            if (dianIndex != -1) {
                mime = url.substring(dianIndex + 1);
                mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mime);
                if (mime == null) {
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
            ImageView view = ViewUtils.findView(quitConfirm, R.id.work_end_bg);
            BitmapUtils.loadLowMemoryBitmap(R.mipmap.work_bg, view);
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

    private AdUnionVideo mobgiVideoAd;

    private String AdBlockId = AppConstant.BLOCK_ID;

    private void callbackSucceed(){
        Log.d("wsy","callbackSucceed！");
        finish = false;
        callback("playadBack","1");
    }

    private void callBackAdFailed(){
        Log.d("wsy","callBackAdFailed加载失败！");
        finish = false;
        callback("playadBack","0");
    }

    private void initAds(){
        OnAuVideoAdListener listener = new OnAuVideoAdListener() {

            @Override
            public void onVideoAdLoaded() {
                Log.d("wsy","onVideoAdLoaded 加载完成！ startAds == "+ startAds);
                finish = true;
                if(startAds){
                    mobgiVideoAd.show();
                    startAds = false;
                } else {
                    //ToastUtils.showShort("广告加载完成，您可以前往观看广告获取闪币哦~");
                }
            }

            @Override
            public void onVideoAdShow() {
                Log.d("wsy","播放！ onVideoAdShow ");
                dismissDoing();
                finish = true;
            }

            @Override
            public void onVideoAdFailed(String s) {
                showFailed();
                callBackAdFailed();

                // 有了等待5秒的逻辑  可以忽略这个错误
                String errorRes = TimeUtils.getCurrentTimeFormat(0, DateUtils.getDatePattern()) + " ==== "+s;
                writeToLog(errorRes);
            }

            @Override
            public void onVideoAdClicked() {
                callbackSucceed();
            }

            @Override
            public void onVideoAdClosed() {
                callbackSucceed();
            }
        };
        if(mobgiVideoAd == null){
            if(!NetworkManager.isConnected()){
                ToastUtils.showShort(R.string.http_code_no_network);
                startAds = false;
                callBackAdFailed();
            }
            mobgiVideoAd = new AdUnionVideo(getActivity(), AdBlockId, listener);
        }
    }

    @Override
    public void playAdsVideo() {
        Log.d("wsy","调用了！！！");
        startAds = true;
        if(mobgiVideoAd == null){
            finish = false;
            showDoing();
            ToastUtils.showShort("正在加载广告中，请稍后....");
            initAds();
            finishWait();
            return;
        }
        if(!finish){
            showDoing();
            ToastUtils.showShort("正在加载广告中，请稍后....");
            finishWait();
        }
        mobgiVideoAd.show();
    }

    private void finishWait(){
        Observable.timer(5000, TimeUnit.MILLISECONDS).subscribeOn(io())
                .observeOn(mainThread()).subscribe(new ObserverAdapter<Long>(){
            @Override
            public void onComplete() {
                dismissDoing();
                if(!finish){
                    startAds = false;
                    callBackAdFailed();
                    showFailed();
                }
            }
        });
    }

    private void showFailed(){
        Log.d("wsy","showFailed    调用了！！！");
        ToastUtils.showShort("很抱歉，广告加载错误，请重试~");
    }

    String TAG = "loading_ad";

    private void showDoing(){
        dismissDoing();
        DialogFragment fragment = DoingFragment.newInstance("", false);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(fragment, TAG);
        ft.commitAllowingStateLoss();
    }

    private void dismissDoing(){
        DialogFragment fragment = (DialogFragment) getFragmentManager().findFragmentByTag(TAG);
        if (fragment != null) {
            fragment.dismissAllowingStateLoss();
        }
    }


    /**
     * 将字符串数据写入到文件中。
     *
     * @param data 徐写入的数据
     * @return true：保存成功；false：保存失败
     */
    public static boolean writeToLog(String data) {
        try {
            File file = new File(AppConstant.GAME_DIE, "error_log.txt");
            // 限制log文件的大小 超过20M时重置
            if (file.exists() && file.length() > 1024 * 1024 * 20) {
                FileUtil.deleteFile(file);
            }
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data + "\r\n");
            bw.close();
            fw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}