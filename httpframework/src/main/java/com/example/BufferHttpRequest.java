package com.example;

import com.example.http.HttpHeader;
import com.example.http.HttpResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by JuTao on 2017/3/2.
 */

public abstract class BufferHttpRequest extends AbstractHttpRequest {
  private ByteArrayOutputStream mByteArray=new ByteArrayOutputStream();

  @Override public OutputStream getBodyOutputStream() {
    return mByteArray;
  }

  @Override protected HttpResponse executeInternal(HttpHeader header) throws IOException {
    byte[] data=mByteArray.toByteArray();
    return executeInternal(header,data);
  }

  protected abstract HttpResponse executeInternal(HttpHeader header, byte[] data)
      throws IOException;
}
