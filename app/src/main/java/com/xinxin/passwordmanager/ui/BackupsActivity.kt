package com.xinxin.passwordmanager.ui

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.google.gson.Gson
import com.xinxin.passwordmanager.MyApplication
import com.xinxin.passwordmanager.R
import com.xinxin.passwordmanager.repository.db.DataEntity
import com.xinxin.passwordmanager.utils.AESOperator

class BackupsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backups)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "复制成功，请妥善保管", Snackbar.LENGTH_LONG).show()
            val list = MyApplication.instance.getDaoSession().dataEntityDao.queryBuilder().list()
            val put = HashMap<String, List<DataEntity>>()
            put.put("data", list)
            val toJson = Gson().toJson(put)
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            cm.text = AESOperator.getInstance().setPassword().encrypt(toJson)
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
