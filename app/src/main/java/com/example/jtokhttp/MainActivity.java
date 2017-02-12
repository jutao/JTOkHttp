package com.example.jtokhttp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.jt.download.DownloadManager;
import com.jt.download.http.SuperDownloadCallback;
import com.jt.download.utils.Logger;
import java.io.File;

public class MainActivity extends AppCompatActivity {

  private ImageView mImageView;
  private ProgressBar mProgressBar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mProgressBar = (ProgressBar) findViewById(R.id.progress);
    mImageView = (ImageView) findViewById(R.id.image);
    DownloadManager.getInstance()
        .download("http://a4.pc6.com/lxf2/erzhanxiongyin.pc6.apk",
        //.download("http://2.im.guokr.com/iFZ14_ljcuSOKU0mPix1npXvEXCWKVZcLTrTKAiphU66AQAAFwEAAEpQ.jpg",
            new SuperDownloadCallback() {
              @Override public void successOnUIThread(File file) {
                Logger.debug("Main", "下载成功:" + file.getAbsoluteFile());
                //Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                //mImageView.setImageBitmap(bitmap);
                installApk(file);
              }

              @Override public void fail(int errorCode, String errorMessage) {
                Logger.debug("Main", "下载失败：" + "错误码：" + errorCode + " : " + errorMessage);
              }

              @Override public void progress(int progress) {
                mProgressBar.setProgress(progress);
              }
            });
  }

  private void installApk(File file){
    Intent intent=new Intent(Intent.ACTION_VIEW);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setDataAndType(Uri.parse("file://" + file.getAbsoluteFile().toString()), "application/vnd.android.package-archive");
    startActivity(intent);
  }
}
