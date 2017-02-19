package com.example.jtokhttp;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.jt.download.DownloadConfig;
import com.jt.download.DownloadManager;
import com.jt.download.db.DownloadHelper;
import com.jt.download.file.FileStorageManager;
import com.jt.download.http.HttpManager;

/**
 * Created by JuTao on 2017/1/23.
 */

public class OKApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    FileStorageManager.getInstance().init(this);
    HttpManager.getInstance().init(this);
    Stetho.initializeWithDefaults(this);

    DownloadConfig config = new DownloadConfig.Builder().setCoreThreadSize(2)
        .setMaxThreadSize(6)
        .setKeepTime(100)
        .setLocalProgressThreadSize(1)
        .build();

    DownloadManager.getInstance().init(config);

    DownloadHelper.getInstance().init(this);
  }
}
