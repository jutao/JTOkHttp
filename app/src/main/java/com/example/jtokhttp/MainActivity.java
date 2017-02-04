package com.example.jtokhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.jt.download.DownloadManager;
import com.jt.download.http.SuperDownloadCallback;
import com.jt.download.utils.Logger;
import java.io.File;

public class MainActivity extends AppCompatActivity {

  private ImageView mImageView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mImageView = (ImageView) findViewById(R.id.image);
    DownloadManager.getInstance()
        .download("http://szimg.mukewang.com/57bd5f4300013d9e05400300-360-202.jpg",
            new SuperDownloadCallback() {
              @Override public void successOnUIThread(File file) {
                Logger.debug("Main","下载成功:" + file.getAbsoluteFile());
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                mImageView.setImageBitmap(bitmap);
              }

              @Override public void fail(int errorCode, String errorMessage) {
               Logger.debug("Main","下载失败：" + "错误码：" + errorCode + " : " + errorMessage);
              }

              @Override public void progress(int progress) {

              }
            });
  }
}
