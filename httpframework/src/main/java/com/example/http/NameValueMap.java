package com.example.http;

import java.util.Map;

/**
 * Created by JuTao on 2017/2/19.
 */

public interface  NameValueMap<K, V> extends Map {
  void set(K key,V value);
  void setAll(Map<K,V> map);
}
