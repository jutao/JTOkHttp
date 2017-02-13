package com.jt.download;

import android.os.Process;
import com.jt.download.db.DownloadEntity;
import com.jt.download.db.DownloadHelper;
import com.jt.download.file.FileStorageManager;
import com.jt.download.http.DownloadCallback;
import com.jt.download.http.HttpManager;
import com.jt.download.utils.Logger;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import okhttp3.Response;

/**
 * Created by JuTao on 2017/1/28.
 */

public class DownloadRunnable implements Runnable {

  private long mStart;

  private long mEnd;

  private String mUrl;

  private DownloadCallback mCallback;

  private DownloadEntity mEntity;

  DownloadRunnable( DownloadCallback callback, DownloadEntity entity) {
    mStart = entity.getStart_position()+entity.getProgress_position();
    mEnd = entity.getEnd_position();
    mUrl = entity.getDownload_url();
    mCallback = callback;
    mEntity = entity;
  }

  @Override public void run() {
    //设置线程优先级为10，减少系统调度时间，使用UI线程获取更多CPU资源
    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

    Response response = HttpManager.Holder.getInstance().syncRequestByRange(mUrl, mStart, mEnd);
    if (response == null && mCallback != null) {
      mCallback.fail(HttpManager.NETWORK_CODE, HttpManager.NETWORK_MESSAGE);
      return;
    }
    if((mStart+mEntity.getProgress_position())>=mEnd){
      return;
    }
    final File file = FileStorageManager.Holder.getInstance().getFileByName(mUrl);
    try {
      RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
      randomAccessFile.seek(mStart);
      byte[] buff = new byte[1024 * 512];
      int len;
      InputStream is = response.body().byteStream();
      long progress=mEntity.getProgress_position();
      while ((len = is.read(buff)) != -1) {
        randomAccessFile.write(buff, 0, len);
        progress+=len;
        Logger.debug("Tag",mEntity.getThread_id()+"---"+progress);
        mEntity.setProgress_position(progress);
      }
      //如果所有线程都下载在完成才调用成功下载
    } catch (Exception e) {
      e.printStackTrace();
    }finally {

      DownloadHelper.Holder.getInstance().insert(mEntity);
    }
  }

}
