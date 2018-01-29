package com.xinxin.passwordmanager.repository.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 魏欣欣 on 2018/1/15  0015.
 * WeChat : xin10050903
 * Email  : obstinate.coder@foxmail.com
 * Role   : 数据库表对应的实体类，这个类不能使用Kotlin写，
 *          GreenDAO目前的版本在升级表时，Kotlin对应的实体类有可能无法生成对应的表
 */
@Entity
public class DataEntity {
    @Id
    private Long id;

    @Property(nameInDb = "soft_name")
    private String softName;

    @Property
    private String account;

    @Property
    private String password;

    @Property(nameInDb = "payment_password")
    private String paymentPassword;

    @Property(nameInDb = "phone_number")
    private String phoneNumber;

    @Property(nameInDb = "register_name")
    private String registerName;

    @Property(nameInDb = "register_idcard")
    private String registerIdcard;

    @Property
    private String email;

    @Property
    private String remarks;

    @Property
    private String icon;

    @Property
    private long time;

    @Generated(hash = 162384924)
    public DataEntity(Long id, String softName, String account, String password,
            String paymentPassword, String phoneNumber, String registerName,
            String registerIdcard, String email, String remarks, String icon,
            long time) {
        this.id = id;
        this.softName = softName;
        this.account = account;
        this.password = password;
        this.paymentPassword = paymentPassword;
        this.phoneNumber = phoneNumber;
        this.registerName = registerName;
        this.registerIdcard = registerIdcard;
        this.email = email;
        this.remarks = remarks;
        this.icon = icon;
        this.time = time;
    }

    @Generated(hash = 1892108943)
    public DataEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoftName() {
        return this.softName;
    }

    public void setSoftName(String softName) {
        this.softName = softName;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPaymentPassword() {
        return this.paymentPassword;
    }

    public void setPaymentPassword(String paymentPassword) {
        this.paymentPassword = paymentPassword;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRegisterName() {
        return this.registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public String getRegisterIdcard() {
        return this.registerIdcard;
    }

    public void setRegisterIdcard(String registerIdcard) {
        this.registerIdcard = registerIdcard;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "account='" + account + '\'' +
                ", id=" + id +
                ", softName='" + softName + '\'' +
                ", password='" + password + '\'' +
                ", paymentPassword='" + paymentPassword + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", registerName='" + registerName + '\'' +
                ", registerIdcard='" + registerIdcard + '\'' +
                ", email='" + email + '\'' +
                ", remarks='" + remarks + '\'' +
                ", icon='" + icon + '\'' +
                ", time=" + time +
                '}';
    }
}
