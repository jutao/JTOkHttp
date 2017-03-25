package com.example.jtokhttp;

import com.example.OkHttpRequest;
import com.example.http.HttpMethod;
import com.example.http.HttpResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import okhttp3.OkHttpClient;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  @Test public void addition_isCorrect() throws Exception {
    OkHttpClient client = new OkHttpClient();
    OkHttpRequest request =
        new OkHttpRequest(client, HttpMethod.POST, "http://localhost:8080/web/HttpServlet");
    Map<String, String> body = new HashMap<>(2);
    body.put("username", "jutao");
    body.put("userage", "55");
    request.writeBody(body);
    HttpResponse response = request.execute();
    String content = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()));
    while ((content = reader.readLine()) != null) {
      System.out.println(content);
    }
    reader.close();
  }
}