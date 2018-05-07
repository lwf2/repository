package com.aspire.bpom.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import com.aspire.bpom.constants.SysConstant;

public class URLCoder {

	/**
	 * 对指定字符串按照UTF-8编码格式进行URL编码
	 * 
	 * @param src
	 *            源字符串
	 * @return
	 */
	public static String encode(String src) {
		return encode(src, SysConstant.CHARSET_UTF8);
	}

	/**
	 * 对指定字符串按照指定编码格式进行URL编码
	 * 
	 * @param src
	 *            源字符串
	 * @param charset
	 *            编码格式：UTF-8,GBK,GB2312,ISO-8859-1等等
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String encode(String src, String charset) {
		String ret = src;
		try {
			ret = URLEncoder.encode(src, charset);
		} catch (Exception e) {
			ret = URLEncoder.encode(src);
		}
		return ret;
	}

	/**
	 * 对指定字符串按照UTF-8编码格式进行URL解码
	 * 
	 * @param code
	 *            已编码字符串
	 * @return
	 */
	public static String decode(String code) {
		return decode(code, SysConstant.CHARSET_UTF8);
	}

	/**
	 * 对指定字符串按照指定编码格式进行URL解码
	 * 
	 * @param code
	 *            已编码字符串
	 * @param encode
	 *            编码格式：UTF-8,GBK,GB2312,ISO-8859-1等等
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String decode(String code, String encode) {
		String ret = code;
		try {
			ret = URLDecoder.decode(code, encode);
		} catch (Exception e) {
			ret = URLDecoder.decode(code);
		}
		return ret;
	}
}
