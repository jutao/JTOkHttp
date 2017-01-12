package com.jt.http.lib;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HelloOkhttp {
  public static final MediaType JSON
      = MediaType.parse("application/json; charset=utf-8");

  OkHttpClient client = new OkHttpClient();

   String post(String url, String json) throws IOException {
    RequestBody body = RequestBody.create(JSON, json);
    Request request = new Request.Builder()
        .url(url)
        .post(body)
        .build();
    try (Response response = client.newCall(request).execute()) {
      return response.body().string();
    }
  }

  String bowlingJson(String player1, String player2) {
    return "{'winCondition':'HIGH_SCORE',\n"
        + "'name':'Bowling',\n"
        + "'round':4,\n"
        + "'lastSaved':1367702411696,\n"
        + "'dateStarted':1367702378785,\n"
        + "'players':["
        + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},\n"
        + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}\n"
        + "]}\n";
  }

  public static void main(String[] args) throws IOException {
    HelloOkhttp example = new HelloOkhttp();
    String json = example.bowlingJson("Jesse", "Jake");
    //System.out.println(json);
    String response = example.post("http://www.roundsapp.com/post", json);
    System.out.println(response);
  }
}
