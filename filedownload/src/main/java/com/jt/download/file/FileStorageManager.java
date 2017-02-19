package com.jt.download.file;

import android.content.Context;
import android.os.Environment;
import com.jt.download.utils.MD5Utils;
import java.io.File;
import java.io.IOException;

/**
 * Created by JuTao on 2017/1/23.
 */

public class FileStorageManager {

  private Context mContext;

  private FileStorageManager() {
  }

  public static FileStorageManager getInstance() {
    return Holder.sFileStorageManager;
  }

  private static class Holder {
    private static FileStorageManager sFileStorageManager = new FileStorageManager();
  }

  public void init(Context context) {
    this.mContext = context;
  }

  public File getFileByName(String url) {
    File parent;

    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      //如果SD卡状态正常,获取外部缓存地址
      parent = mContext.getExternalCacheDir();
    } else {//否则获取默认缓存地址
      parent = mContext.getCacheDir();
    }
    String fileName = MD5Utils.generateCode(url);
    File file = new File(parent, fileName);

    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return file;
  }
}
