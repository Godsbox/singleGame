package com.lzj.shanyijiansan;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 报错log抓取
 *
 * @author 郭香岫
 */
public class CrashHandler implements UncaughtExceptionHandler {
	/**
	 * 异常处理的默认handler
	 */
	private UncaughtExceptionHandler mDefaultHandler;
	/**
	 * 按照指定的格式化时间，用于日志的存储
	 */
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINESE);
	private static CrashHandler sCrashHandler;

	private CrashHandler() {
		init();
	}

	private void init() {
		// 获取系统默认的未捕获异常处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置本类为该应用的异常处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	public static CrashHandler createCrashHandler() {
		if (sCrashHandler == null) {
			sCrashHandler = new CrashHandler();
		}
		return sCrashHandler;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		saveCrashInfo2File(ex);
		if (mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		}
	}

	/**
	 * 保存异常信息到文件
	 * 
	 * @param tr
	 */
	private void saveCrashInfo2File(Throwable tr) {
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		tr.printStackTrace(pw);

		Throwable cause = tr.getCause();
		while(cause !=null){
			cause.printStackTrace(pw);
			cause = cause.getCause();
		}
		pw.close();

		File mFile = new File(Environment.getExternalStorageDirectory()+ File.separator + "shanyi");
		if (!mFile.exists()) {
			mFile.mkdir();
		}
		String time = format.format(new Date());
		String fileName = "mm_"+time+".log";
		FileOutputStream fos  = null;
		try {
			fos = new FileOutputStream(new File(mFile, fileName));
			fos.write(writer.toString().getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
