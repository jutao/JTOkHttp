package com.jt.download;

import android.support.annotation.NonNull;
import com.jt.download.http.DownloadCallback;
import com.jt.download.http.HttpManager;
import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by JuTao on 2017/1/28.
 */

public class DownloadManager {
  private DownloadManager() {
  }

  static final int MAX_THREAD = 2;

  //线程池存活的时间，秒为单位
  private static final int KEEP_TIME = 60;

  private static DownloadManager sDownloadManager = new DownloadManager();

  private static final ThreadPoolExecutor sThreadPool =
      new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD, KEEP_TIME, TimeUnit.SECONDS,
          new SynchronousQueue<Runnable>(), new ThreadFactory() {
        private AtomicInteger mInteger = new AtomicInteger(1);

        @Override public Thread newThread(@NonNull Runnable runnable) {
          //获取当前的值并自增
          Thread thread = new Thread(runnable, "download thread #" + mInteger.getAndIncrement());
          return thread;
        }
      });

  public static DownloadManager getInstance() {
    return sDownloadManager;
  }

  public void download(final String url, final DownloadCallback callback) {
    HttpManager.getInstance().asyncRequest(url, new Callback() {
      @Override public void onFailure(Call call, IOException e) {

      }

      @Override public void onResponse(Call call, Response response) throws IOException {

        if (callback == null) return;

        if (!response.isSuccessful()) {
          callback.fail(HttpManager.NETWORK_CODE, HttpManager.NETWORK_MESSAGE);
          return;
        }
        long length = response.body().contentLength();
        if (length == -1) {
          callback.fail(HttpManager.CONTENT_LENGTH_ERROR_CODE,
              HttpManager.CONTENT_LENGTH_ERROR_MESSAGE);
          return;
        }
        processDownload(url, length, callback);
      }
    });
  }

  private void processDownload(String url, long length, DownloadCallback callback) {
    //计算每个线程所需下载的大小
    long threadDownloadSize = length / MAX_THREAD;
    AtomicInteger atomicInteger = new AtomicInteger(0);
    //给每个线程分配起始和结束位置
    for (int i = 0; i < MAX_THREAD; i++) {
      long startPos = i * threadDownloadSize;
      long endPos;
      if (i == MAX_THREAD - 1) {
        endPos = length - 1;
      } else {
        endPos = (i + 1) * threadDownloadSize - 1;
      }

      sThreadPool.execute(new DownloadRunnable(startPos, endPos, url, callback, atomicInteger));
    }
  }
}
