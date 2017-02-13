package com.jt.download;

import android.support.annotation.NonNull;
import com.jt.download.db.DownloadEntity;
import com.jt.download.db.DownloadHelper;
import com.jt.download.file.FileStorageManager;
import com.jt.download.http.DownloadCallback;
import com.jt.download.http.HttpManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

  private List<DownloadEntity> mCache;

  static final int MAX_THREAD = 2;

  //线程池存活的时间，秒为单位
  private static final int KEEP_TIME = 60;

  private long mLength;

  private static ExecutorService sLocalProgressPoll = Executors.newSingleThreadExecutor();

  private HashSet<DownloadTask> mHashSet = new HashSet<>();

  private static final ThreadPoolExecutor sThreadPool =
      new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD, KEEP_TIME, TimeUnit.SECONDS,
          new SynchronousQueue<Runnable>(), new ThreadFactory() {
        private AtomicInteger mInteger = new AtomicInteger(1);

        @Override public Thread newThread(@NonNull Runnable runnable) {
          //获取当前的值并自增
          return new Thread(runnable, "download thread #" + mInteger.getAndIncrement());
        }
      });

  private DownloadManager() {
  }

  public static class Holder {
    private static DownloadManager sDownloadManager = new DownloadManager();

    public static DownloadManager getInstance() {
      return sDownloadManager;
    }
  }

  private void finish(DownloadTask task) {
    mHashSet.remove(task);
  }

  public void download(final String url, final DownloadCallback callback) {

    final DownloadTask task = new DownloadTask(url, callback);

    if (mHashSet.contains(task)) {
      callback.fail(HttpManager.TASK_RUNNING_ERROR_CODE, HttpManager.TASK_RUNNING_ERROR_MESSAGE);
      return;
    }
    mHashSet.add(task);

    mCache = DownloadHelper.Holder.getInstance().getAll(url);
    if (mCache == null || mCache.size() == 0) {
      mCache = new ArrayList<>(MAX_THREAD);
      doDownload(url, callback, task);
    } else {
      //处理已经下载过的数据
      for (int i = 0; i < mCache.size(); i++) {
        DownloadEntity entity = mCache.get(i);
        if (mCache.size() - 1 == i) {
          mLength = entity.getEnd_position() + 1;
        }
        sThreadPool.execute(new DownloadRunnable(callback, entity));
      }
    }
    sLocalProgressPoll.execute(new Runnable() {
      @Override public void run() {
        while (true) {
          try {
            Thread.sleep(500);
            File file = FileStorageManager.Holder.getInstance().getFileByName(url);
            long fileLength = file.length();
            int progress = (int) (fileLength * 100.0 / mLength);
            callback.progress(progress);
            if (progress >= 100) {
              callback.success(file);
              return;
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });
  }

  private void doDownload(final String url, final DownloadCallback callback,
      final DownloadTask task) {
    HttpManager.Holder.getInstance().asyncRequest(url, new Callback() {
      @Override public void onFailure(Call call, IOException e) {
        finish(task);
      }

      @Override public void onResponse(Call call, Response response) throws IOException {

        if (callback == null) return;

        if (!response.isSuccessful()) {
          callback.fail(HttpManager.NETWORK_CODE, HttpManager.NETWORK_MESSAGE);
          return;
        }
        mLength = response.body().contentLength();
        if (mLength == -1) {
          callback.fail(HttpManager.CONTENT_LENGTH_ERROR_CODE,
              HttpManager.CONTENT_LENGTH_ERROR_MESSAGE);
          return;
        }
        processDownload(url, mLength, callback);
      }
    });
  }

  private void processDownload(String url, long length, DownloadCallback callback) {
    //计算每个线程所需下载的大小
    long threadDownloadSize = length / MAX_THREAD;
    //给每个线程分配起始和结束位置
    for (int i = 0; i < MAX_THREAD; i++) {
      long startPos = i * threadDownloadSize;
      long endPos;
      if (i == MAX_THREAD - 1) {
        endPos = length - 1;
      } else {
        endPos = (i + 1) * threadDownloadSize - 1;
      }

      DownloadEntity entity = new DownloadEntity();
      entity.setDownload_url(url);
      entity.setThread_id(i + 1);
      entity.setStart_position(startPos);
      entity.setEnd_position(endPos);

      sThreadPool.execute(new DownloadRunnable(callback, entity));
    }
  }
}
