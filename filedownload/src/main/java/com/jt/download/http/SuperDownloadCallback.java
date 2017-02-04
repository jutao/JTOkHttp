package com.jt.download.http;

import com.jt.download.utils.ThreadUtils;
import java.io.File;

/**
 * Created by JuTao on 2017/1/29.
 */

public abstract class SuperDownloadCallback implements DownloadCallback{

  @Override public void success(final File file) {
    ThreadUtils.runOnUIThread(new Runnable() {
      @Override public void run() {
        successOnUIThread(file);
      }
    });
  }
  //在UI线程执行
  public abstract void successOnUIThread(File file);
}
