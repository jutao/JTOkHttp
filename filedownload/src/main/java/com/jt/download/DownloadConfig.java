package com.jt.download;

/**
 * Created by JuTao on 2017/2/16.
 */

public class DownloadConfig {

  private int coreThreadSize;
  private int maxThreadSize;
  private int localProgressThreadSize;
  private int keepTime;

  int getKeepTime() {
    return keepTime;
  }

  int getCoreThreadSize() {
    return coreThreadSize;
  }

  int getMaxThreadSize() {
    return maxThreadSize;
  }

  int getLocalProgressThreadSize() {
    return localProgressThreadSize;
  }

   private DownloadConfig(Builder builder) {
    this.coreThreadSize =
        builder.coreThreadSize == 0 ? DownloadManager.MAX_THREAD : builder.coreThreadSize;
    this.maxThreadSize =
        builder.maxThreadSize == 0 ? DownloadManager.MAX_THREAD : builder.coreThreadSize;
    this.localProgressThreadSize =
        builder.localProgressThreadSize == 0 ? DownloadManager.LOCAL_PROGRESS_SIZE
            : builder.localProgressThreadSize;
    this.keepTime = builder.keepTime == 0 ? DownloadManager.KEEP_TIME : builder.keepTime;
  }

  public static class Builder {
    private int coreThreadSize;
    private int maxThreadSize;
    private int localProgressThreadSize;
    private int keepTime;

    public DownloadConfig build() {
      return new DownloadConfig(this);
    }

    public Builder setCoreThreadSize(int coreThreadSize) {
      this.coreThreadSize = coreThreadSize;
      return this;
    }

    public Builder setMaxThreadSize(int maxThreadSize) {
      this.maxThreadSize = maxThreadSize;
      return this;
    }

    public Builder setLocalProgressThreadSize(int localProgressThreadSize) {
      this.localProgressThreadSize = localProgressThreadSize;
      return this;
    }

    public Builder setKeepTime(int keepTime) {
      this.keepTime = keepTime;
      return this;
    }
  }
}
