package com.example.utils;

/**
 * Created by JuTao on 2017/3/26.
 */

public class ExistUtils {
  public static boolean isExist(String className,ClassLoader loader){
    try {
      Class.forName(className);
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }
}
