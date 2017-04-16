package com.example.http;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by JuTao on 2017/2/19.
 */

public interface HttpResponse extends Header, Closeable {
  HttpStatus getStatus();

  String getStatusMsg();

  InputStream getBody() throws IOException;

  @Override void close() throws IOException;

  long getContentLength();
}
