package com.jt.download;

import com.jt.download.http.DownloadCallback;

/**
 * Created by JuTao on 2017/2/5.
 */

public class DownloadTask {
  String mUrl;
  DownloadCallback mCallback;

  public DownloadTask(String url, DownloadCallback callback) {
    mUrl = url;
    mCallback = callback;
  }

  public String getUrl() {
    return mUrl;
  }

  public void setUrl(String url) {
    mUrl = url;
  }

  public DownloadCallback getCallback() {
    return mCallback;
  }

  public void setCallback(DownloadCallback callback) {
    mCallback = callback;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DownloadTask that = (DownloadTask) o;

    if (mUrl != null ? !mUrl.equals(that.mUrl) : that.mUrl != null) return false;
    return mCallback != null ? mCallback.equals(that.mCallback) : that.mCallback == null;
  }

  @Override public int hashCode() {
    int result = mUrl != null ? mUrl.hashCode() : 0;
    result = 31 * result + (mCallback != null ? mCallback.hashCode() : 0);
    return result;
  }
}
