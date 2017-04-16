package com.example.framework;

import com.example.bean.RequestParameters;
import com.example.framework.impl.okhttp.OkHttpRequestFactory;
import com.example.framework.impl.origin.OriginHttpRequestFactory;
import com.example.http.HttpRequest;
import com.example.utils.ExistUtils;

/**
 * Created by JuTao on 2017/3/26.
 */

public class HttpRequestProvider {

  //是否支持okhttp
  private static boolean OK_HTTPREQUEST =
      ExistUtils.isExist("okhttp3.OkHttpClient", HttpRequestProvider.class.getClassLoader());

  public HttpRequestProvider() {

  }

  public static HttpRequest getHttpRequest(RequestParameters parameters) {
    if (OK_HTTPREQUEST) {
      return new OkHttpRequestFactory().createHttpRequest(parameters);
    } else {
      return new OriginHttpRequestFactory().createHttpRequest(parameters);
    }
  }
}
