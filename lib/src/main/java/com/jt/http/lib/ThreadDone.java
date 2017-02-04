package com.jt.http.lib;

/**
 * Created by JuTao on 2017/2/4.
 * 如何中止一个线程
 */
public class ThreadDone {
  public static void main(String[] args) throws InterruptedException {
    MyRunnable myRunnable=new MyRunnable();
    Thread thread = new Thread(myRunnable);
    thread.start();
    Thread.sleep(1000);
    //仅仅利用flag并不能使线程立刻中止，当run方法中有耗时操作时会等待执行完成才结束
    myRunnable.flag=false;
    thread.interrupt();
  }

  private static class MyRunnable implements Runnable {

    //立刻同步到子线程中
    private volatile boolean flag = true;

    @Override public void run() {
      while (flag&&!Thread.interrupted()) {
        System.out.println("running");
        try {
          Thread.sleep(8000);
        } catch (InterruptedException e) {
          //e.printStackTrace();
          //thread.interrupt()执行后会立刻进入catch
          return;
        }
      }
    }
  }
}
