package com.lzj.shanyiharp_world;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzj.arch.app.PassiveActivity;
import com.lzj.arch.core.Contract;
import com.lzj.arch.util.BitmapUtils;
import com.lzj.arch.util.ProcessUtils;
import com.wujilin.doorbell.Doorbell;
import com.lzj.arch.app.web.WebConstant;
import com.lzj.shanyiharp_world.browser.BrowserActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends PassiveActivity<Contract.Presenter> implements View.OnClickListener {

    private final String TAG = "wsy";


    private ImageView open, more;

    private TextView name, count, type;

    {
        setFullscreen(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) { // 判断当前activity是不是所在任务栈的根
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
        setContentView(R.layout.activity_main);

        ImageView view = (ImageView) findViewById(R.id.work_start_bg);

        BitmapUtils.loadLowMemoryBitmap(R.mipmap.work_bg, view);

        open = (ImageView) findViewById(R.id.open);
        more = (ImageView)findViewById(R.id.more);

        type = (TextView) findViewById(R.id.type);
        name = (TextView) findViewById(R.id.name);
        count = (TextView) findViewById(R.id.work_count);

        type.setText(AppConstant.WORK_TYPE);
        name.setText(AppConstant.AUTHOR_NAME);
        count.setText(AppConstant.WORK_COUNT);

        open.setOnClickListener(this);
        more.setOnClickListener(this);
        BaWei.getInstance().addActivity_(this);
    }

    /**
     * 执行玩游戏屏幕启动。
     */
    private void doStartPlayGame() {
        Doorbell.with(this)
                .start(BrowserActivity.class)
                .extra(WebConstant.EXTRA_URL, AppConstant.GAME_PLAYURL)
                .transition(R.anim.app_fade_in, R.anim.app_outgoing_left)
                .ring();
    }

    @Override
    protected void onDestroy() {
        BaWei.getInstance().removeActivity_(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.open){
            doStartPlayGame();
        }
        if(view.getId() == R.id.more){
            if(ProcessUtils.isAppInstalled(AppConstant.SHANYI_PACKAGE)){
                ProcessUtils.startOtherApp(this, AppConstant.SHANYI_PACKAGE);
                return;
            }
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(BaWei.getInstance().getDownloadShanyiUrl()));
            startActivity(Intent.createChooser(intent, "请选择浏览器"));
        }
    }



    /**
     * 拷贝assets文件下文件到指定路径
     *
     * @param context
     * @param assetDir  源文件/文件夹
     * @param targetDir  目标文件夹
     * @throws Exception
     */
    public static void copyAssets(Context context, String assetDir, String targetDir) throws Exception{
        /*if (TextUtils.isEmpty(assetDir) || TextUtils.isEmpty(targetDir)) {
            return;
        }*/
        String separator = File.separator;
        // 获取assets目录assetDir下一级所有文件以及文件夹
        String[] fileNames = context.getResources().getAssets().list(assetDir);
        // 如果是文件夹(目录),则继续递归遍历
        if (fileNames.length > 0) {
            File targetFile = new File(targetDir);
            if (!targetFile.exists() && !targetFile.mkdirs()) {
                return;
            }
            for (String fileName : fileNames) {
                String file = assetDir.isEmpty() ? fileName : assetDir + separator + fileName;
                copyAssets(context, file, targetDir + separator + fileName);
            }
        } else { // 文件,则执行拷贝
            if (assetDir.contains("webkt") || assetDir.contains("sounds")
                    || assetDir.contains("images")) {
                return;
            }
            copy(context, assetDir, targetDir);
        }
    }

    /**
     * 复制文件
     *
     * @param context 上下文对象
     * @param zipPath 源文件
     * @param targetPath 目标文件
     * @throws Exception
     */
    public static void copy(Context context, String zipPath, String targetPath) throws Exception{
        if (TextUtils.isEmpty(zipPath) || TextUtils.isEmpty(targetPath)) {
            return;
        }
        Exception exception = null;
        File dest = new File(targetPath);
        dest.getParentFile().mkdirs();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(context.getAssets().open(zipPath));
            out = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            exception = new Exception(e);
        } catch (IOException e) {
            exception = new Exception(e);
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                exception = new Exception(e);
            }
        }
        if (exception != null) {
            throw exception;
        }
    }
}
