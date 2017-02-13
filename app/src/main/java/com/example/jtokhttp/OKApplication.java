package com.example.jtokhttp;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.jt.download.db.DownloadHelper;
import com.jt.download.file.FileStorageManager;
import com.jt.download.http.HttpManager;

/**
 * Created by JuTao on 2017/1/23.
 */

public class OKApplication extends Application{
  @Override public void onCreate() {
    super.onCreate();
    FileStorageManager.Holder.getInstance().init(this);
    HttpManager.Holder.getInstance().init(this);
    Stetho.initializeWithDefaults(this);
    DownloadHelper.Holder.getInstance().init(this);
  }
}
