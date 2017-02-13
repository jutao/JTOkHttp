package com.jt.download.http;

import android.content.Context;
import com.jt.download.file.FileStorageManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by JuTao on 2017/1/25.
 */

public class HttpManager {
  public static final int NETWORK_CODE = 1;
  public static final String NETWORK_MESSAGE = "网络出问题了!!";

  public static final int CONTENT_LENGTH_ERROR_CODE = 2;
  public static final String CONTENT_LENGTH_ERROR_MESSAGE = "content length 出错啦！";

  public static final int TASK_RUNNING_ERROR_CODE = 3;
  public static final String TASK_RUNNING_ERROR_MESSAGE = "任务已经执行了";

  private HttpManager() {
    mClient = new OkHttpClient();
  }

  public static class Holder {
    private static HttpManager sHttpManager = new HttpManager();

    public static HttpManager getInstance() {
      return sHttpManager;
    }
  }

  private Context mContext;
  private OkHttpClient mClient;

  public void init(Context context) {
    this.mContext = context;
  }

  /**
   * 同步请求
   */
  public Response syncRequest(String url) {
    Request request = new Request.Builder().url(url).build();
    try {
      return mClient.newCall(request).execute();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 同步请求
   */
  public Response syncRequestByRange(String url, long start, long end) {
    Request request =
        new Request.Builder().url(url).addHeader("Range", "bytes=" + start + "-" + end).build();
    try {
      return mClient.newCall(request).execute();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 异步请求
   */

  public void asyncRequest(final String url, Callback callback) {
    Request request = new Request.Builder().url(url).build();
    mClient.newCall(request).enqueue(callback);
  }

  public void asyncRequest(final String url, final DownloadCallback callback) {
    Request request = new Request.Builder().url(url).build();
    mClient.newCall(request).enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {
      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        if (callback == null) return;

        if (!response.isSuccessful()) {
          callback.fail(NETWORK_CODE, NETWORK_MESSAGE);
          return;
        }
        final File file = FileStorageManager.Holder.getInstance().getFileByName(url);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buff = new byte[1024 * 512];
        int len;
        InputStream is = response.body().byteStream();
        while ((len = is.read(buff)) != -1) {
          fos.write(buff, 0, len);
          fos.flush();
        }
        callback.success(file);
        is.close();
        fos.close();
      }
    });
  }
}
