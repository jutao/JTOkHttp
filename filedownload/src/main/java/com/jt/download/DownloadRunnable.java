package com.jt.download;

import com.jt.download.file.FileStorageManager;
import com.jt.download.http.DownloadCallback;
import com.jt.download.http.HttpManager;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.Response;

/**
 * Created by JuTao on 2017/1/28.
 */

public class DownloadRunnable implements Runnable {

  private long mStart;

  private long mEnd;

  private String mUrl;

  private DownloadCallback mCallback;

  private AtomicInteger mInteger;

  DownloadRunnable(long start, long end, String url, DownloadCallback callback,
      AtomicInteger integer) {
    mStart = start;
    mEnd = end;
    mUrl = url;
    mCallback = callback;
    mInteger = integer;
  }

  @Override public void run() {
    Response response = HttpManager.getInstance().syncRequestByRange(mUrl, mStart, mEnd);
    if (response == null && mCallback != null) {
      mCallback.fail(HttpManager.NETWORK_CODE, HttpManager.NETWORK_MESSAGE);
      return;
    }
    final File file = FileStorageManager.getInstance().getFileByName(mUrl);
    try {
      RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
      randomAccessFile.seek(mStart);
      byte[] buff = new byte[1024 * 512];
      int len;
      InputStream is = response.body().byteStream();
      while ((len = is.read(buff)) != -1) {
        randomAccessFile.write(buff, 0, len);
      }
      //如果所有线程都下载在完成才调用成功下载
      if (mInteger.incrementAndGet() == DownloadManager.MAX_THREAD) {
        mCallback.success(file);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
