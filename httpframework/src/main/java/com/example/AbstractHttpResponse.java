package com.example;

import com.example.http.HttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * Created by JuTao on 2017/2/19.
 */

public abstract class AbstractHttpResponse implements HttpResponse {

  private static final String GZIP = "gzip";
  private InputStream body = getBodyInternal();
  private InputStream mGzipInputStream;

  private boolean isGzip() {
    String contentEncoding = getHeaders().getContentEncoding();
    return GZIP.equals(contentEncoding);
  }

  @Override public InputStream getBody() throws IOException {
    InputStream body = getBodyInternal();
    if (isGzip()) {
      return getBodyGzip(body);
    }
    return body;
  }

  private InputStream getBodyGzip(InputStream body) throws IOException {
    if (this.mGzipInputStream == null) {
      this.mGzipInputStream = new GZIPInputStream(body);
    }
    return mGzipInputStream;
  }

  @Override public void close() throws IOException {
    if (mGzipInputStream != null) {
      mGzipInputStream.close();
    }
    closeInternal();
  }

  public abstract InputStream getBodyInternal();

  public abstract void closeInternal();
}
