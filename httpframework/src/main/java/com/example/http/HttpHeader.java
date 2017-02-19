package com.example.http;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by JuTao on 2017/2/19.
 */

public class HttpHeader implements NameValueMap<String, String> {

  HashMap<String, String> mMap = new HashMap<>();

  public static final String ACCEPT = "Accept";
  public static final String PRAGMA = "Pragma";
  public static final String USER_AGENT = "User-Agent";
  public static final String PROXY_CONNECTION = "Proxy-Connection";
  public static final String ACCEPT_ENCODING = "Accept-Encoding";
  public static final String CACHE_CONTROL = "Cache-Control";
  public static final String CONTENT_ENCODING = "Content-Encoding";
  public static final String CONNECTION = "Connection";
  public static final String CONTENT_LENGTH = "Content_Length";

  public String getAccept() {
    return get(ACCEPT);
  }

  public void setAccept(String value) {
    set(ACCEPT, value);
  }

  public String getPragma() {
    return get(PRAGMA);
  }

  public void setPragma(String value) {
    set(PRAGMA, value);
  }

  public String getUserAgent() {
    return get(USER_AGENT);
  }

  public void setUserAgent(String value) {
    set(USER_AGENT, value);
  }

  public String getProxyConnection() {
    return get(PROXY_CONNECTION);
  }

  public void setProxyConnection(String proxyConnection) {
    set(PROXY_CONNECTION, proxyConnection);
  }

  public String getAcceptEncoding() {
    return get(ACCEPT_ENCODING);
  }

  public void setAcceptEncoding(String acceptEncoding) {
    set(ACCEPT_ENCODING,acceptEncoding);
  }

  public String getCacheControl() {
    return get(CACHE_CONTROL);
  }

  public void setCacheControl(String cacheControl) {
    set(CACHE_CONTROL,cacheControl);
  }

  public String getContentEncoding() {
    return get(CONTENT_ENCODING);
  }

  public void setContentEncoding(String contentEncoding) {
    set(CONTENT_ENCODING,contentEncoding);
  }

  public String getConnection() {
    return get(CONNECTION);
  }

  public void setContection(String connection) {
    set(CONNECTION,connection);
  }

  public String getContentLength() {
    return get(CONTENT_LENGTH);
  }

  public void setContentLength(String contentLength) {
    set(CONTENT_LENGTH,contentLength);
  }

  @Override public void set(String key, String value) {
    mMap.put(key, value);
  }

  @Override public void setAll(Map<String, String> map) {
    mMap.putAll(mMap);
  }

  @Override public int size() {
    return mMap.size();
  }

  @Override public boolean isEmpty() {
    return mMap.isEmpty();
  }

  @Override public boolean containsKey(Object o) {
    return mMap.containsKey(o);
  }

  @Override public boolean containsValue(Object o) {
    return mMap.containsValue(0);
  }

  @Override public String get(Object o) {
    return mMap.get(0);
  }

  @Override public Object put(Object key, Object value) {
    return mMap.put((String) key, (String) value);
  }

  @Override public Object remove(Object o) {
    return mMap.remove(o);
  }

  @Override public void putAll(Map map) {
    mMap.putAll(map);
  }

  @Override public void clear() {
    mMap.clear();
  }

  @Override public Set<String> keySet() {
    return mMap.keySet();
  }

  @Override public Collection<String> values() {
    return mMap.values();
  }

  @Override public Set<Entry<String, String>> entrySet() {
    return mMap.entrySet();
  }
}
