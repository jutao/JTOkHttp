package com.example;

import com.example.http.HttpHeader;
import com.example.http.HttpRequest;
import com.example.http.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * Created by JuTao on 2017/2/28.
 */

public abstract class AbstractHttpRequest implements HttpRequest {
  private HttpHeader mHeader = new HttpHeader();
  private final static String GZIP = "gzip";

  private ZipOutputStream mZip;

  private boolean executed;

  @Override public HttpHeader getHeaders() {
    return mHeader;
  }

  @Override public OutputStream getBody() {
    OutputStream body = getBodyOutputStream();
    if (isGzip()) {
      return getGzipOutputStream(body);
    }

    return body;
  }

  private OutputStream getGzipOutputStream(OutputStream body) {
    if (this.mZip == null) {
      this.mZip = new ZipOutputStream(body);
    }
    return mZip;
  }

  public boolean isGzip() {
    String contentEncoding = getHeaders().getContentEncoding();
    if (GZIP.equals(contentEncoding)) {
      return true;
    }
    return false;
  }

  @Override public HttpResponse execute() throws IOException {
    if (mZip != null) {
      mZip.close();
    }
    HttpResponse response = executeInternal(mHeader);
    executed = true;
    return response;
  }

  public abstract void writeBody(Map<String,String> body);

  protected abstract HttpResponse executeInternal(HttpHeader header) throws IOException;

  public abstract OutputStream getBodyOutputStream();
}
