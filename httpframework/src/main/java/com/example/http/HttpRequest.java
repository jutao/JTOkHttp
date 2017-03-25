package com.example.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by JuTao on 2017/2/28.
 */

public interface HttpRequest extends Header{
  HttpMethod getMethod();

  URI getUri();

  OutputStream getBody();

  HttpResponse execute() throws IOException;
}
