package com.xinxin.passwordmanager.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.view.View
import com.xinxin.passwordmanager.MainActivity
import com.xinxin.passwordmanager.MyApplication
import com.xinxin.passwordmanager.R
import com.xinxin.passwordmanager.ui.base.BaseActivity
import com.xinxin.passwordmanager.utils.FingerprintUtil
import kotlinx.android.synthetic.main.activity_login.*
import net.sqlcipher.database.SQLiteException
import org.jetbrains.anko.onClick

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnPassword_Login.onClick {
            val password = etPassword_Login.text.trim().toString()
            if (password.isEmpty()){
                etPassword_Login.error = getString(R.string.please_enter_password2)
                return@onClick
            }
            if (password.length<10){
                etPassword_Login.error = getString(R.string.password_length_short)
                return@onClick
            }
            MyApplication.password = password
            try {
                MyApplication.instance.getDaoSession().dataEntityDao.queryBuilder().list()
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                finish()
            } catch(e: Exception) {
                if (e is SQLiteException) {
                    etPassword_Login.error = getString(R.string.password_wrong)
                } else {
                    showToast(e.message!!)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkFingerprint()
    }

    private fun checkFingerprint() {
        FingerprintUtil.callFingerPrint(object : FingerprintUtil.OnCallBackListener {
            override fun onSupportFailed() {
                showToast("当前设备不支持指纹")
                showSetPassword()
            }

            override fun onInsecurity() {
                showToast("当前设备未处于安全保护中")
                showSetPassword()
            }

            override fun onEnrollFailed() {
                showToast("请到设置中设置指纹")
                showSetPassword()
            }

            override fun onAuthenticationStart() {
                llVerificationFingerprint_Login.visibility = View.VISIBLE
                llPassword_Login.visibility = View.GONE
            }

            override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
                showToast(errString.toString())
            }

            override fun onAuthenticationFailed() {
                showToast("解锁失败")
            }

            override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {
                showToast(helpString.toString())
            }

            override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult) {
                showToast("解锁成功")
                showSetPassword()

            }
        })
    }


    fun showToast(name: String) {
        Snackbar.make(activity_login, name, Snackbar.LENGTH_LONG).show()
    }

    private fun showSetPassword() {
        llVerificationFingerprint_Login.visibility = View.GONE
        llPassword_Login.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        System.exit(0)
        super.onBackPressed()
    }
}
