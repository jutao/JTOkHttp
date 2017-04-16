package com.example.framework;

import com.example.bean.RequestParameters;
import com.example.http.HttpRequest;

/**
 * Created by JuTao on 2017/3/25.
 */

public interface HttpRequestFactory {
  HttpRequest createHttpRequest(RequestParameters parameters);

  void setReadTimeOut(int readTimeOut);

  void setWriteTimeOut(int writeTimeOut);

  void setConnectionTime(int connectionTime);
}
