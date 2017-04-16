package com.example.framework.impl.okhttp;

import com.example.bean.RequestParameters;
import com.example.framework.HttpRequestFactory;
import com.example.http.HttpRequest;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * Created by JuTao on 2017/3/25.
 */

public class OkHttpRequestFactory implements HttpRequestFactory {
  private OkHttpClient mClient;

  public OkHttpRequestFactory() {
    mClient = new OkHttpClient();
  }


  public OkHttpRequestFactory(OkHttpClient client) {
    mClient = client;
  }

  @Override
  public void setReadTimeOut(int readTimeOut) {
    this.mClient = mClient.newBuilder().readTimeout(readTimeOut, TimeUnit.MILLISECONDS).build();
  }

  @Override
  public void setWriteTimeOut(int writeTimeOut) {
    this.mClient = mClient.newBuilder().writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS).build();
  }

  @Override
  public void setConnectionTime(int connectionTime) {
    this.mClient =
        mClient.newBuilder().connectTimeout(connectionTime, TimeUnit.MILLISECONDS).build();
  }

  @Override public HttpRequest createHttpRequest(RequestParameters parameters) {
    return new OkHttpRequest(mClient, parameters.method, parameters.url);
  }
}
