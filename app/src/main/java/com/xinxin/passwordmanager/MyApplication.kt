package com.xinxin.passwordmanager

import android.app.Application
import android.content.Context
import com.xinxin.passwordmanager.repository.db.DaoMaster
import com.xinxin.passwordmanager.repository.db.DaoSession
import com.xinxin.passwordmanager.repository.db.greendao.UpgradeHelper

/**
 * Created by 魏欣欣 on 2018/1/15  0015.
 * WeChat : xin10050903
 * Email  : obstinate.coder@foxmail.com
 * Role   :
 */

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        mContext = instance
        password = ""
        mDevOpenHelper = UpgradeHelper(mContext, "data.db", null)
    }

    companion object {
        lateinit var instance: MyApplication
        lateinit var mContext: Context
        lateinit var password: String
        lateinit var mDevOpenHelper: UpgradeHelper
    }

    fun getDaoSession(): DaoSession {
        return DaoMaster(mDevOpenHelper.getEncryptedWritableDb(password)).newSession()
    }
}
