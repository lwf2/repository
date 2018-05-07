package com.aspire.bpom.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.slf4j.Logger;

import com.aspire.bpom.constants.SysConstant;
import com.aspire.bpom.extensions.log4j.BpomLogger;

public class RSASignature {

	private static final Logger log = BpomLogger.getLogger(RSASignature.class);

	/**
	 * 
	 * 解密
	 * 
	 * @param content
	 *            密文
	 * 
	 * @param privateKey
	 *            商户私钥
	 * 
	 * @return 解密后的字符串
	 */
	public static String decrypt(String content, String privateKey) throws Exception {
		log.trace("解密密文：" + content);
		log.trace("私钥：" + privateKey);
		PrivateKey prikey = getPrivateKey(privateKey);

		Cipher cipher = Cipher.getInstance(SysConstant.SIGN_RSA);
		cipher.init(Cipher.DECRYPT_MODE, prikey);

		InputStream ins = new ByteArrayInputStream(Base64.decode(content));
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		// rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
		byte[] buf = new byte[128];
		int bufl;

		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;

			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; i++) {
					block[i] = buf[i];
				}
			}

			writer.write(cipher.doFinal(block));
		}
		String ret = new String(writer.toByteArray(), SysConstant.CHARSET_UTF8);
		log.trace("解密结果：" + ret);
		return ret;
	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            原文
	 * @param publicKey
	 *            公钥
	 * @return 密文
	 * @throws Exception
	 */
	public static String encrypt(String content, String publicKey) throws Exception {
		log.trace("加密原文：" + content);
		log.trace("加密公钥：" + publicKey);
		PublicKey pubKey = getPublicKey(publicKey);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(SysConstant.SIGN_RSA);
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);

		InputStream ins = new ByteArrayInputStream(content.getBytes(SysConstant.CHARSET_UTF8));
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		byte[] buf = new byte[117];
		int bufl;

		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;

			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; i++) {
					block[i] = buf[i];
				}
			}
			writer.write(cipher.doFinal(block));
		}
		String ret = Base64.encode(writer.toByteArray());
		log.trace("加密结果：" + ret);
		return ret;
	}

	private static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes = Base64.decode(key);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(SysConstant.SIGN_RSA);
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		return pubKey;
	}

	/**
	 * 
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * 
	 * @throws Exception
	 */

	private static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;

		keyBytes = Base64.decode(key);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance(SysConstant.SIGN_RSA);

		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		return privateKey;

	}

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	/**
	 * RSA签名
	 * 
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            商户私钥
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey) {
		log.trace("签名原文：" + content);
		log.trace("签名私钥：" + privateKey);
		String ret = null;
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance(SysConstant.SIGN_RSA);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(SysConstant.CHARSET_UTF8));

			byte[] signed = signature.sign();

			ret = Base64.encode(signed);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.trace("签名结果：" + ret);
		return ret;
	}

	/**
	 * RSA验签名检查
	 * 
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param publicKey
	 *            支付宝公钥
	 * @return 布尔值
	 */
	public static boolean doCheck(String content, String sign, String publicKey) {
		log.trace("验签原文：" + content);
		log.trace("验签签名：" + sign);
		log.trace("验签公钥：" + publicKey);
		boolean isGoodSign = false;
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(SysConstant.SIGN_RSA);
			byte[] encodedKey = Base64.decode(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes(SysConstant.CHARSET_UTF8));
			isGoodSign = signature.verify(Base64.decode(sign));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.trace("验签结果：" + isGoodSign);
		return isGoodSign;
	}
	
	public static void main(String[] args) {
		String content = "body=互联网业务线测试计费点&buyer_email=dchh0771@126.com&buyer_id=2088802845837814&gmt_create=2017-09-07 09:47:39&gmt_payment=2017-09-07 09:47:39" +
				"&is_total_fee_adjust=N&notify_id=4b5c64d49c3e72a0423efe014276e8am92&notify_time=2017-09-07 09:47:40&notify_type=trade_status_sync&out_channel_type=PCREDIT_PAY&out_trade_no=20170907094629666660&payment_type=1&price=0.01&quantity=1&seller_email=alipayrisk10@alipay.com&seller_id=2088501624560335&subject=按次计费道具（测试）&total_fee=0.01&trade_no=2017090721001004810240725567&trade_status=TRADE_SUCCESS&use_coupon=N";
		String sign = "EfMsoZLuUW8VNhEMW89HqKh3OlT9IVUlQaFONim+ZnioyuFUfWC28E2aFn+VXoyfplx7YP+bv41iLlaAJ/9rtLQSwISFWvDcza1YhE9wMSnrCxuCz1vr2H8PnWUoq8krj9omFODHyopoA1AfUDdYvUkT7ljjNzsTcLywCyMocgE=";
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
		System.out.println(doCheck(content, sign, publicKey));
	}

}
