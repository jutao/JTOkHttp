package com.example.framework.impl.origin;

import com.example.framework.BufferHttpRequest;
import com.example.http.HttpHeader;
import com.example.http.HttpMethod;
import com.example.http.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

/**
 * Created by JuTao on 2017/4/9.
 */

public class OriginHttpRequest extends BufferHttpRequest {

  private HttpURLConnection mConnection;

  private String mUrl;

  private HttpMethod mMethod;

  public OriginHttpRequest(HttpURLConnection connection, String url, HttpMethod method) {
    mConnection = connection;
    mUrl = url;
    mMethod = method;
  }

  @Override public HttpMethod getMethod() {
    return mMethod;
  }

  @Override public URI getUri() {
    return URI.create(mUrl);
  }

  @Override protected HttpResponse executeInternal(HttpHeader header, byte[] data)
      throws IOException {
    for (Map.Entry<String, String> entry : header.entrySet()) {
      mConnection.addRequestProperty(entry.getKey(), entry.getValue());
    }
    mConnection.setDoOutput(true);
    mConnection.setDoInput(true);
    mConnection.setRequestMethod(mMethod.name());
    mConnection.connect();

    if (data != null && data.length > 0) {
      OutputStream outputStream = mConnection.getOutputStream();
      outputStream.write(data, 0, data.length);
      outputStream.close();
    }
    OriginHttpResponse response = new OriginHttpResponse(mConnection);

    return response;
  }
}
