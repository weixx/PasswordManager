package com.xinxin.passwordmanager.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.gson.Gson
import com.xinxin.passwordmanager.MyApplication
import com.xinxin.passwordmanager.R
import com.xinxin.passwordmanager.bean.AccountInfoBean
import com.xinxin.passwordmanager.ui.base.BaseActivity
import com.xinxin.passwordmanager.utils.AESOperator
import kotlinx.android.synthetic.main.content_import.*
import kotlinx.android.synthetic.main.layout_app_bar.*
import org.jetbrains.anko.onClick

/**
 * 导入数据
 */
class ImportActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager

        btnPassword_Import.onClick {
            if (cm.text.isEmpty()) {
                etPassword_Import.error = "请先复制加密数据字符串"
                return@onClick
            }
            val password = etPassword_Import.text.trim().toString()
            try {
                val json = AESOperator.getInstance().setPassword(password).decrypt(cm.text.toString())
                val bean = Gson().fromJson(json, AccountInfoBean::class.java)
                for (dataEntity in bean.data!!) {
                    dataEntity.id = null
                    MyApplication.instance.getDaoSession().dataEntityDao.insert(dataEntity)
                }
                etPassword_Import.error = "导入成功"
            } catch(e: Exception) {
                Log.e("TAG",e.message)
                etPassword_Import.error = "密码错误"
            }
            cm.text = ""
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

