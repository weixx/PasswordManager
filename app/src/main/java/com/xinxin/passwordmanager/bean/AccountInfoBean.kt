package com.xinxin.passwordmanager.bean

import com.xinxin.passwordmanager.repository.db.DataEntity

/**
 * Created by 魏欣欣 on 2018/1/24  0024.
 * WeChat : xin10050903
 * Email  : obstinate.coder@foxmail.com
 * Role   :  多账号信息的实体类
 */

class AccountInfoBean {

    /**
     * account :
     * email :
     * icon :
     * id : 2
     * password :
     * paymentPassword :
     * phoneNumber :
     * registerIdcard :
     * registerName :
     * remarks :
     * softName : 百度
     * time : 1516370187801
     */

    var data: List<DataEntity>? = null

    override fun toString(): String {
        return "AccountInfoBean{" +
                "data=" + data +
                '}'
    }
}
