package com.xinxin.passwordmanager.utils

import android.app.KeyguardManager
import android.content.Context
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal
import android.util.Log

import com.xinxin.passwordmanager.MyApplication

/**
 * Created by 魏欣欣 on 2018/1/15  0015.
 * WeChat : xin10050903
 * Email  : obstinate.coder@foxmail.com
 * Role   : 指纹识别工具类
 */

object FingerprintUtil {
    var cancellationSignal: CancellationSignal? = null

    fun callFingerPrint(listener: OnCallBackListenr?) {
        val managerCompat = FingerprintManagerCompat.from(MyApplication.mContext)
        if (!managerCompat.isHardwareDetected) { //判断设备是否支持
            listener?.onSupportFailed()
            return
        }
        val keyguardManager = MyApplication.instance.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isKeyguardSecure) {//判断设备是否处于安全保护中
            listener?.onInsecurity()
            return
        }
        if (!managerCompat.hasEnrolledFingerprints()) { //判断设备是否已经注册过指纹
            listener?.onEnrollFailed() //未注册
            return
        }
        listener?.onAuthenticationStart() //开始指纹识别
        cancellationSignal = CancellationSignal() //必须重新实例化，否则cancel 过一次就不能再使用了
        Log.e("TAG", cancellationSignal!!.hashCode().toString())
        managerCompat.authenticate(null, 0, cancellationSignal, object : FingerprintManagerCompat.AuthenticationCallback() {
            // 当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息，比如华为的提示就是：尝试次数过多，请稍后再试。
            override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
                listener?.onAuthenticationError(errMsgId, errString)
            }

            // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
            override fun onAuthenticationFailed() {
                listener?.onAuthenticationFailed()
            }

            override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {
                listener?.onAuthenticationHelp(helpMsgId, helpString)
            }

            // 当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
            override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult) {
                listener?.onAuthenticationSucceeded(result)
            }
        }, null)

    }

    interface OnCallBackListenr {
        fun onSupportFailed()
        fun onInsecurity()
        fun onEnrollFailed()
        fun onAuthenticationStart()
        fun onAuthenticationError(errMsgId: Int, errString: CharSequence)
        fun onAuthenticationFailed()
        fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence)
        fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult)
    }

    /**
     * 取消指纹验证
     */
    fun cancel() {
        if (cancellationSignal != null)
            cancellationSignal!!.cancel()
    }
}
