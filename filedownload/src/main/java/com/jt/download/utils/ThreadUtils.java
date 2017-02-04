package com.jt.download.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by jutao on 2017/1/12.
 */

public class ThreadUtils {
    //主线程 handler
    private static Handler sHanler = new Handler(Looper.getMainLooper());

    //构造有定时功能的线程池
    private static ScheduledExecutorService scheduleExec=Executors.newScheduledThreadPool(1);

    /**
     * 即时运行在主线程
     *
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        runOnUIThread(runnable, 0);
    }

    /**
     * 延时 delay 秒运行在主线程
     *
     * @param runnable
     * @param dealy
     */
    public static void runOnUIThread(Runnable runnable, long dealy) {
        sHanler.postDelayed(runnable, dealy);
    }

    /**
     * 即时运行在子线程
     */
    public static void run(Runnable runnable) {
        run(runnable,0);
    }

    /**
     * 延时 delay 秒运行在子线程
     * @param runnable
     */
    public static void run(Runnable runnable,long delay) {
        scheduleExec.schedule(runnable,delay, TimeUnit.MILLISECONDS);
    }
}
