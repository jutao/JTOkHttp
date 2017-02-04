package com.jt.download.http;

import java.io.File;

/**
 * Created by JuTao on 2017/1/25.
 */

public interface DownloadCallback {
  void success(File file);
  void fail(int errorCode,String errorMessage);
  void progress(int progress);

}
