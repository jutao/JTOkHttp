package com.example.framework.impl.origin;

import com.example.framework.AbstractHttpResponse;
import com.example.http.HttpHeader;
import com.example.http.HttpStatus;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by JuTao on 2017/4/8.
 */
public class OriginHttpResponse extends AbstractHttpResponse {

  private HttpURLConnection mConnection;

  public OriginHttpResponse(HttpURLConnection connection) {
    mConnection = connection;
  }

  @Override public HttpHeader getHeaders() {
    HttpHeader header=new HttpHeader();

    Set<Map.Entry<String, List<String>>> entries = mConnection.getHeaderFields().entrySet();
    for (Map.Entry<String, List<String>> entry : entries) {
      header.set(entry.getKey(),entry.getValue().get(0));
    }
    return header;
  }

  @Override public HttpStatus getStatus() {
    try {
      return HttpStatus.getValue(mConnection.getResponseCode());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override public String getStatusMsg() {
    try {
      return mConnection.getResponseMessage();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override public long getContentLength() {
    return mConnection.getContentLength();
  }

  @Override public InputStream getBodyInternal() throws IOException {
    return mConnection.getInputStream();
  }

  @Override public void closeInternal() {
    mConnection.disconnect();
  }
}
