package com.jt.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;

/**
 * Created by JuTao on 2017/2/7.
 */

public class DownloadHelper {

  private DaoMaster mMaster;

  private DaoSession mSession;

  private DownloadEntityDao mDao;

  private DownloadHelper() {
  }

  public static DownloadHelper getInstance() {
    return Holder.sDownloadHelper;
  }

  private static class Holder {
    private static DownloadHelper sDownloadHelper = new DownloadHelper();
  }

  public void init(Context context) {
    SQLiteDatabase db =
        new DaoMaster.DevOpenHelper(context, "download.db", null).getWritableDatabase();
    mMaster = new DaoMaster(db);
    mSession = mMaster.newSession();
    mDao = mSession.getDownloadEntityDao();
  }

  public void insert(DownloadEntity entity) {
    mDao.insertOrReplace(entity);
  }

  public List<DownloadEntity> getAll(String url) {
    return mDao.queryBuilder()
        .where(DownloadEntityDao.Properties.Download_url.eq(url))
        .orderAsc(DownloadEntityDao.Properties.Thread_id)
        .list();
  }
}
