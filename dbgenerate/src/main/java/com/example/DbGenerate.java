package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class DbGenerate {
  public static void main(String... args) {
    Schema schema = new Schema(1, "com.example.db");
    Entity entity = schema.addEntity("DownloadEntity");
    entity.addLongProperty("start_position");
    entity.addLongProperty("end_position");
    entity.addLongProperty("progress_position");
    entity.addStringProperty("download_url");
    entity.addIntProperty("thread_id");
    entity.addIdProperty().autoincrement();

    try {
      new DaoGenerator().generateAll(schema,"dbgenerate/src/gen");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
