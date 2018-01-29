package com.xinxin.passwordmanager.utils

import android.content.Context
import android.content.SharedPreferences
import java.util.*

/**
 * SP存储操作工具类
 */
class SPUtils
private constructor() {

    /**
     * 保存数据
     * @param key
     * @param value
     */
    fun save(key: String, value: Any) {
        if (value is String) {
            sp!!.edit().putString(key, value).apply()
        } else if (value is Int) {
            sp!!.edit().putInt(key, value).apply()
        } else if (value is Boolean) {
            sp!!.edit().putBoolean(key, value).apply()
        }
        valueCache!!.put(key, value)
    }

    /**
     * 删除指定key的数据
     * @param key
     */
    fun remove(key: String) {
        sp!!.edit().remove(key).apply()
        valueCache!!.remove(key)
    }

    /**
     * 从sp存储中获取一个值
     * @param key
     * *
     * @param defValue
     * *
     * @param <T>
     * *
     * @return
    </T> */
    @Suppress("UNCHECKED_CAST")
    operator fun <T> getValue(key: String, defValue: Any?): T {
        val `val` = valueCache!![key]
        var t: T? = null
        if (`val` == null) {
            if (defValue == null || defValue is String) {
                val value = sp!!.getString(key, defValue as String?)
                t = value as T
                valueCache!!.put(key, value)
            } else if (defValue is Int) {
                val value = sp!!.getInt(key, (defValue as Int?)!!)
                t = value as T
                valueCache!!.put(key, value)
            } else if (defValue is Boolean) {
                val value = sp!!.getBoolean(key, (defValue as Boolean?)!!)
                t = value as T
                valueCache!!.put(key, value)
            }
        } else {
            if (defValue == null || defValue is String) {
                val value = `val` as String?
                t = value as T?
            } else if (defValue is Int) {
                val value = `val` as Int?
                t = value as T?
            } else if (defValue is Boolean) {
                val value = `val` as Boolean?
                t = value as T?
            }
        }
        return t!!
    }

    companion object {
        private var valueCache: MutableMap<String, Any>? = null

        //声明
        private var sp: SharedPreferences? = null
        private var instance: SPUtils? = null

        //提供一个获取SP对象的方法
        fun getInstance(context: Context): SPUtils {
            if (null == instance) {
                synchronized(SPUtils::class.java) {
                    if (null == instance) {
                        sp = context.getSharedPreferences("xinxin", Context.MODE_PRIVATE)
                        instance = SPUtils()
                        valueCache = HashMap<String, Any>()
                    }
                }
            }
            return instance!!
        }
    }
}
