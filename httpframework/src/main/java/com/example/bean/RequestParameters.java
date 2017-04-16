package com.example.bean;

import com.example.http.HttpMethod;

/**
 * Created by JuTao on 2017/3/25.
 */

public class RequestParameters {
  public RequestParameters(String url, HttpMethod method) {
    this.url = url;
    this.method = method;
  }

  public String url;
  public HttpMethod method;
}
