package com.xinxin.passwordmanager.ui.add

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.xinxin.passwordmanager.MyApplication
import com.xinxin.passwordmanager.R
import com.xinxin.passwordmanager.repository.db.entity.DataEntity
import com.xinxin.passwordmanager.repository.db.entity.DataEntityDao
import com.xinxin.passwordmanager.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_account.*
import org.jetbrains.anko.onClick

/**
 * 添加账号信息
 */
class AddAccountActivity : BaseActivity() {
    private  var insertId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)
        setSupportActionBar(toolbar_add)
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
            setEnable(false)
        }

        btnSave.onClick {
            val dataEntity = DataEntity(null, etSoftName.text.trim().toString(),
                    etAccount.text.trim().toString(), etPassword.text.trim().toString(), etPaymentPassword.text.trim().toString(),
                    etPhoneNumber.text.trim().toString(), etRegisterName.text.trim().toString(), etRegisterIdCard.text.trim().toString(),
                    etEmail.text.trim().toString(), etRemarks.text.trim().toString(), etIcon.text.trim().toString(), System.currentTimeMillis())
            if (insertId == -1L){
                insertId = MyApplication.instance.getDaoSession().dataEntityDao.insert(dataEntity)
                Snackbar.make(currentFocus, "添加成功", Snackbar.LENGTH_SHORT).show()
            }else{
                dataEntity.id = insertId
                MyApplication.instance.getDaoSession().dataEntityDao.update(dataEntity)
                Snackbar.make(currentFocus, "修改成功", Snackbar.LENGTH_SHORT).show()
            }
            setEnable(false)
        }
    }

    fun setEnable(isE: Boolean) {
        etSoftName.isEnabled=isE
        etAccount.isEnabled=isE
        etPassword.isEnabled=isE
        etPaymentPassword.isEnabled=isE
        etPhoneNumber.isEnabled=isE
        etRegisterName.isEnabled=isE
        etRegisterIdCard.isEnabled=isE
        etEmail.isEnabled=isE
        etIcon.isEnabled=isE
        etRemarks.isEnabled=isE
        if (isE) {
            btnSave.visibility = View.VISIBLE
        } else {
            btnSave.visibility = View.GONE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_update, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
             android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_update -> {
                //修改信息
                setEnable(true)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
