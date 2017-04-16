package com.example.framework.impl.origin;

import com.example.bean.RequestParameters;
import com.example.framework.HttpRequestFactory;
import com.example.http.HttpRequest;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * Created by JuTao on 2017/4/9.
 */

public class OriginHttpRequestFactory implements HttpRequestFactory {
  private HttpURLConnection mConnection;

  public OriginHttpRequestFactory() {
  }

  @Override public void setReadTimeOut(int readTimeOut) {
    mConnection.setReadTimeout(readTimeOut);
  }

  @Override public void setWriteTimeOut(int writeTimeOut) {
  }

  @Override public void setConnectionTime(int connectionTime) {
    mConnection.setConnectTimeout(connectionTime);
  }

  @Override public HttpRequest createHttpRequest(RequestParameters parameters) {
    try {
      mConnection = (HttpURLConnection) URI.create(parameters.url).toURL().openConnection();
      return new OriginHttpRequest(mConnection, parameters.url, parameters.method);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
