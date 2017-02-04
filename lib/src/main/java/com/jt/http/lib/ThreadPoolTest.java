package com.jt.http.lib;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by JuTao on 2017/1/29.
 */

public class ThreadPoolTest {
  public static void main(String[] args){
    final ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
    ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(2,4,60, TimeUnit.MICROSECONDS,queue);
    for (int i = 0; i < 16; i++) {
      final int index=i;
      threadPoolExecutor.execute(new Runnable() {

        @Override public void run() {
          System.out.println("index:"+index+" Queue Size:"+queue.size());
        }
      });
    }
  }
}
