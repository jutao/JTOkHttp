package com.example.service;

import com.example.http.HttpMethod;

/**
 * Created by JuTao on 2017/4/9.
 */

public class MyRequest {
  private String mUrl;
  private HttpMethod mMethod;
  private byte[] data;
  private MyResponse mMyResponse;

  public String getUrl() {
    return mUrl;
  }

  public void setUrl(String url) {
    mUrl = url;
  }

  public HttpMethod getMethod() {
    return mMethod;
  }

  public void setMethod(HttpMethod method) {
    mMethod = method;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public MyResponse getMyResponse() {
    return mMyResponse;
  }

  public void setMyResponse(MyResponse myResponse) {
    mMyResponse = myResponse;
  }
}
