package com.example.service;

import com.example.http.HttpRequest;
import com.example.http.HttpResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by JuTao on 2017/4/11.
 */

public class HttpRunnable implements Runnable {
  private HttpRequest mHttpRequest;
  private MyRequest mMyRequest;
  private WorkStation mWorkStation;

  public HttpRunnable(HttpRequest httpRequest, MyRequest myRequest, WorkStation workStation) {
    mHttpRequest = httpRequest;
    mMyRequest = myRequest;
    mWorkStation = workStation;
  }

  @Override public void run() {
    try {
      mHttpRequest.getBody().write(mMyRequest.getData());
      HttpResponse response = mHttpRequest.execute();
      if (response.getStatus().isSuccess()) {
        if (mMyRequest.getMyResponse() != null) {
          mMyRequest.getMyResponse().success(mMyRequest, new String(getData(response)));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      mWorkStation.finish(mMyRequest);
    }
  }

  public byte[] getData(HttpResponse response) {
    ByteArrayOutputStream outputStream =
        new ByteArrayOutputStream((int) response.getContentLength());
    int len;
    byte[] data = new byte[(int) response.getContentLength()];
    try {
      while ((len = response.getBody().read(data)) != -1) {
        outputStream.write(data, 0, len);
      }
    } catch (IOException e) {

    }
    return data;
  }
}
