package com.xinxin.passwordmanager.utils;

import com.xinxin.passwordmanager.MyApplication;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 */
public class AESOperator {

	/*
	 * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
	 */

	private String sKey;// key，可自行修改,必须为16位
	private String ivParameter;// 偏移量,可自行修改,必须为16位
	private static AESOperator instance = null;

	private AESOperator() {
	}

	public static AESOperator getInstance() {
		if (instance == null)
			instance = new AESOperator();
		return instance;
	}

	/**
	 * 设置密码
	 * @param passwords 密码，可无，默认使用APP解锁密码
     */
	public AESOperator setPassword(String ... passwords) {
		String password;
		if (passwords.length == 0) {
			password = MyApplication.password;
		} else {
			password = passwords[0];
		}
		if (password.length() >= 16) {
			ivParameter = sKey = password.substring(0, 16);
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 16; i++) {
				sb.append(password);
			}
			ivParameter = sKey = sb.toString().substring(0, 16);
		}
		return this;
	}


	// 加
	public String encrypt(String sSrc) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码。
	}

	// 解
	public String decrypt(String sSrc) throws Exception {
		try {
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			return new String(original, "utf-8");
		} catch (Exception ex) {
			return null;
		}
	}

}