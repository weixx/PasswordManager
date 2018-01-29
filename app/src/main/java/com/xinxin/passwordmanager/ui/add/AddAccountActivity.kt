package com.xinxin.passwordmanager.ui.add

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.xinxin.passwordmanager.MyApplication
import com.xinxin.passwordmanager.R
import com.xinxin.passwordmanager.repository.db.DataEntity
import com.xinxin.passwordmanager.repository.db.DataEntityDao
import kotlinx.android.synthetic.main.activity_add_account.*
import org.jetbrains.anko.onClick

class AddAccountActivity : AppCompatActivity() {
    private  var insertId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)
        val bar = findViewById(R.id.toolbar_add) as Toolbar
        setSupportActionBar(bar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        insertId = intent.getLongExtra("id",-1L)
        if (insertId != -1L){
            val dataEntity = MyApplication.instance.getDaoSession().dataEntityDao.queryBuilder().where(DataEntityDao.Properties.Id.eq(insertId)).build().list()[0]
            etSoftName.setText(dataEntity.softName)
            etAccount.setText(dataEntity.account)
            etPassword.setText(dataEntity.password)
            etPaymentPassword.setText(dataEntity.paymentPassword)
            etPhoneNumber.setText(dataEntity.phoneNumber)
            etRegisterName.setText(dataEntity.registerName)
            etRegisterIdCard.setText(dataEntity.registerIdcard)
            etEmail.setText(dataEntity.email)
            etIcon.setText(dataEntity.icon)
            etRemarks.setText(dataEntity.remarks)
        }

        btnSave.onClick {
            if (insertId == -1L){
                insertId = MyApplication.instance.getDaoSession().dataEntityDao.insert(DataEntity(null, etSoftName.text.trim().toString(),
                        etAccount.text.trim().toString(), etPassword.text.trim().toString(), etPaymentPassword.text.trim().toString(),
                        etPhoneNumber.text.trim().toString(), etRegisterName.text.trim().toString(), etRegisterIdCard.text.trim().toString(),
                        etEmail.text.trim().toString(), etRemarks.text.trim().toString(),etIcon.text.trim().toString(),System.currentTimeMillis()))
                Snackbar.make(currentFocus, "添加成功", Snackbar.LENGTH_LONG).show()
            }else{
                MyApplication.instance.getDaoSession().dataEntityDao.update(DataEntity(insertId, etSoftName.text.trim().toString(),
                        etAccount.text.trim().toString(), etPassword.text.trim().toString(), etPaymentPassword.text.trim().toString(),
                        etPhoneNumber.text.trim().toString(), etRegisterName.text.trim().toString(), etRegisterIdCard.text.trim().toString(),
                        etEmail.text.trim().toString(), etRemarks.text.trim().toString(),etIcon.text.trim().toString(), System.currentTimeMillis()))
                Snackbar.make(currentFocus, "修改成功", Snackbar.LENGTH_LONG).show()
            }

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
