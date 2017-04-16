package com.example.service;

/**
 * Created by JuTao on 2017/4/11.
 */

public interface MyResponse<T> {
  void success(MyRequest myRequest, T data);

  void fail(int errorCode, String errorMsg);
}
