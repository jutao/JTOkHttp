package com.example.jtokhttp;

import android.app.Application;
import com.jt.download.file.FileStorageManager;
import com.jt.download.http.HttpManager;

/**
 * Created by JuTao on 2017/1/23.
 */

public class OKApplication extends Application{
  @Override public void onCreate() {
    super.onCreate();
    FileStorageManager.getInstance().init(this);
    HttpManager.getInstance().init(this);
  }
}