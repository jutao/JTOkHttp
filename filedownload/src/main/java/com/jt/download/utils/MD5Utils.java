package com.jt.download.utils;

import android.text.TextUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JuTao on 2017/1/23.
 */

public class MD5Utils {
  public static String generateCode(String url){
    if(TextUtils.isEmpty(url)){
      return null;
    }
    StringBuffer buffer=new StringBuffer();
    try {
      MessageDigest digest = MessageDigest.getInstance("md5");
      digest.update(url.getBytes());
      byte[] cipher = digest.digest();

      for (byte b : cipher) {
        //&0xff可以避免负数转换问题
        String hexStr=Integer.toHexString(b&0xff);
        buffer.append(hexStr.length()==1?"0"+hexStr:hexStr);
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  return buffer.toString();
  }
}
