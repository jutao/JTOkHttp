package com.example.http;

import com.example.framework.AbstractHttpResponse;
import java.io.InputStream;
import okhttp3.Response;

/**
 * Created by JuTao on 2017/2/19.
 */

public class OkHttpResponse extends AbstractHttpResponse {

  private Response mResponse;
  private HttpHeader mHeaders;

  public OkHttpResponse(Response response) {
    mResponse = response;
  }

  @Override public InputStream getBodyInternal() {
    return mResponse.body().byteStream();
  }

  @Override public void closeInternal() {
    mResponse.body().close();
  }

  @Override public HttpStatus getStatus() {
    return HttpStatus.getValue(mResponse.code());
  }

  @Override public String getStatusMsg() {
    return mResponse.message();
  }

  @Override public long getContentLength() {
    return mResponse.body().contentLength();
  }

  @Override public HttpHeader getHeaders() {
    if (mHeaders == null) {
      mHeaders = new HttpHeader();
    }
    for (String name : mResponse.headers().names()) {
      mHeaders.set(name, mResponse.headers().get(name));
    }
    return mHeaders;
  }
}
