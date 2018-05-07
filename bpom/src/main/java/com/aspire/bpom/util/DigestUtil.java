package com.aspire.bpom.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DigestUtil {
	// MD5加密
	// 需求32位且大写
	public static String MD5Digest(String strToDigest) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(strToDigest.getBytes());
			byte b[] = md5.digest();

			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString().toUpperCase();
		} catch(Exception e) {
			e.printStackTrace();
//			throw new BusinessException(new ErrorResp(ReturnCode.MD5_DECRYPT_FAILED));
			return null;
		}
	}

	// AES鍔犲瘑
	public static byte[] AESEncrypt(String content, String password) throws Exception{
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(password.getBytes());
		kgen.init(128, random);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 鍒涘缓瀵嗙爜鍣?
		byte[] byteContent = content.getBytes("utf-8");
		cipher.init(Cipher.ENCRYPT_MODE, key);// 鍒濆鍖?
		byte[] result = cipher.doFinal(byteContent);
		return result; // 鍔犲瘑
	}

	// AES瑙ｅ瘑
	public static byte[] AESDecrypt(byte[] content, String password) throws Exception{
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(password.getBytes());
		kgen.init(128, random);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");// 鍒涘缓瀵嗙爜鍣?
		cipher.init(Cipher.DECRYPT_MODE, key);// 鍒濆鍖?
		return cipher.doFinal(content);
	}

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
