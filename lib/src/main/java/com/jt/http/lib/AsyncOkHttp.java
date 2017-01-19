package com.jt.http.lib;

import java.io.File;
import java.io.IOException;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by JuTao on 2017/1/15.
 */
public class AsyncOkHttp {
  public static void main(String args[]) {
    //cacheHttp("http://www.qq.com");
    //queryHttp();
    //multiPartHttp();
    rangeHttp();
  }

  private static void sendRequest(String url) {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(url).build();
    try {
      Response response = client.newCall(request).execute();
      if (response.isSuccessful()) {
        System.out.println(response.body().string());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void sendAsyncRequest(String url) {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(url).build();
    System.out.println(Thread.currentThread().getName());
    client.newCall(request).enqueue(new Callback() {
      @Override public void onFailure(Call call, IOException e) {

      }

      @Override public void onResponse(Call call, Response response) throws IOException {
        System.out.println(response.body().string());
        System.out.println(Thread.currentThread().getName());
      }
    });
  }

  private static void testHeader(String url) {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(url)
        .header("User-Agent", "User-Agent: Mozilla/5.0 (Linux; X11)")
        .header("Accept", "Accept: text/plain, text/html")
        .build();
    try {
      Response response = client.newCall(request).execute();
      System.out.println(response.body().string());
      Headers headers = response.headers();
      if (response.isSuccessful()) {
        for (int i = 0; i < headers.size(); i++) {
          System.out.println(headers.name(i) + " : " + headers.value(i));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void queryHttp() {
    OkHttpClient client = new OkHttpClient();
    HttpUrl httpUrl = HttpUrl.parse("https://free-api.heweather.com/v5/now")
        .newBuilder()
        .addQueryParameter("city", "shanghai")
        .addQueryParameter("key", "6e802828ed314eb59e4b00a0fb0f29fa")
        .build();
    String url = httpUrl.toString();
    System.out.println(url);
    Request request = new Request.Builder().url(url).build();
    try {
      System.out.println(client.newCall(request).execute().body().string());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void postHttp(String url) {
    OkHttpClient client = new OkHttpClient();
    FormBody formBody =
        new FormBody.Builder().add("username", "jutao").add("userage", "99").build();
    Request request =
        new Request.Builder().url("http://localhost:8080/web/HttpServlet").post(formBody).build();
    try {
      System.out.println(client.newCall(request).execute().body().string());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 上传文件
   */
  private static void multiPartHttp() {
    RequestBody imageBody = RequestBody.create(MediaType.parse("image/jpeg"), new File(
        "F:\\AndroidStudio\\Android\\workspace\\MyApplication\\exg\\src\\main\\res\\drawable\\a03.jpg"));

    MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
        .addFormDataPart("name", "njutao")
        .addFormDataPart("filename", "girl.jpg", imageBody)
        .build();

    OkHttpClient client = new OkHttpClient();
    Request request =
        new Request.Builder().post(body).url("http://localhost:8080/web/UpLoadServlet").build();
    try {
      Response response = client.newCall(request).execute();
      System.out.println(response.body().string());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void cacheHttp(String url) {

    File file = new File(
        "G:\\Java\\apache-tomcat-8.0.9-windows-x64\\apache-tomcat-8.0.9\\work\\Catalina\\localhost\\web");
    long maxSize = 10 * 1024 * 1024;

    Cache cache = new Cache(file, maxSize);
    OkHttpClient client = new OkHttpClient.Builder().cache(cache).build();

    Request request = new Request.Builder().url(url).build();
    try {
      Response response = client.newCall(request).execute();
      System.out.println(response.body().string());
      System.out.println("----------------------");
      System.out.println("networkResponse:" + response.networkResponse());
      System.out.println("cacheResponse:" + response.cacheResponse());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void rangeHttp() {
    OkHttpClient client = new OkHttpClient();
    Request request=new Request.Builder()
        .url("http://mat1.gtimg.com/www/images/qq2012/temp/picFocusDefault.png")
        .addHeader("Range","bytes=5-").build();
    try {
      Response response=client.newCall(request).execute();
      System.out.println("contentLength:"+response.body().contentLength());
      System.out.println("--------");
      Headers headers = response.headers();
      for (int i = 0; i < headers.size(); i++) {
        System.out.println(headers.name(i) + " : " + headers.value(i));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
