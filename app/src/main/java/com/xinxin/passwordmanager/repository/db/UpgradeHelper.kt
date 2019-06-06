package com.xinxin.passwordmanager.repository.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.xinxin.passwordmanager.repository.db.entity.DaoMaster
import org.greenrobot.greendao.database.Database

/**
 * Created by 魏欣欣 on 2016/10/18  0018.
 * 作用 : 数据库升级辅助类
 */
class UpgradeHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?) : DaoMaster.OpenHelper(context, name, factory) {

    override fun onUpgrade(db: Database, oldVersion: Int, newVersion: Int) {
        Log.e("TAG","数据库升级 >> $oldVersion,$newVersion")
        when (newVersion) {
            4 -> {
//                //不能先删除表，否则数据都木了  StudentDao.dropTable(db, true);
//                DaoMaster.createAllTables(db, true);
//                // 加入新字段
//                try {
//                    db.execSQL("ALTER TABLE 'USER' ADD 'NICK_NAME' TEXT;");
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    Log.e("TAG",e.getMessage());
//                }
            }
        }
    }
}