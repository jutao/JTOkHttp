package com.example.service;

import com.example.bean.RequestParameters;
import com.example.framework.HttpRequestProvider;
import com.example.http.HttpRequest;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by JuTao on 2017/4/11.
 */

public class WorkStation {
  private static final ThreadPoolExecutor sThreadPool =
      new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
          new SynchronousQueue<Runnable>(), new ThreadFactory() {
        private AtomicInteger index = new AtomicInteger();

        @Override public Thread newThread(Runnable runnable) {
          Thread thread = new Thread(runnable);
          thread.setName("http thread name is" + index.getAndIncrement());
          return thread;
        }
      });
  private static final int MAX_REQUEST_SIZE = 60;
  private Deque<MyRequest> mRunning = new ArrayDeque<>();
  private Deque<MyRequest> mCache = new ArrayDeque<>();
  private HttpRequestProvider mHttpRequestProvider;

  public WorkStation() {
    mHttpRequestProvider = new HttpRequestProvider();
  }

  public void add(MyRequest request) {
    if (mRunning.size() > MAX_REQUEST_SIZE) {
      mCache.add(request);
    } else {
      doHttpRequest(request);
    }
  }

  public void finish(MyRequest request) {
    mRunning.remove(request);
    if (mRunning.size() > MAX_REQUEST_SIZE) {
      return;
    }
    if (mCache.size() == 0) {
      return;
    }
    Iterator<MyRequest> iterator = mCache.iterator();
    while (iterator.hasNext()) {
      MyRequest next = iterator.next();
      mRunning.add(next);
      iterator.remove();
      doHttpRequest(next);
    }
  }

  private void doHttpRequest(MyRequest request) {
    HttpRequest httpRequest = mHttpRequestProvider.getHttpRequest(
        new RequestParameters(request.getUrl(), request.getMethod()));

    sThreadPool.execute(new HttpRunnable(httpRequest, request, this));
  }
}
