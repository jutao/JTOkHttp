package com.example.service;

import com.example.http.HttpMethod;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by JuTao on 2017/4/13.
 */

public class MyAPIProvider {
  private static final String ENCODING = "utf-8";
  private static WorkStation sWorkStation = new WorkStation();

  public static byte[] encodeParam(Map<String, String> value) {
    if (value == null || value.size() == 0) {
      return null;
    }
    StringBuffer buffer = new StringBuffer();
    int count = 0;
    try {
      for (Map.Entry<String, String> entry : value.entrySet()) {
        buffer.append(URLEncoder.encode(entry.getKey(), ENCODING))
            .append("=")
            .append(URLEncoder.encode(entry.getValue(), ENCODING));
        if (count != value.size() - 1) {
          buffer.append("&");
        }
        count++;
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return buffer.toString().getBytes();
  }

  public static void visitor(String url, Map<String, String> value, MyResponse<String> response) {
    MyRequest request = new MyRequest();
    request.setUrl(url);
    request.setMethod(HttpMethod.POST);
    request.setData(encodeParam(value));
    request.setMyResponse(response);
    sWorkStation.add(request);
  }
}
