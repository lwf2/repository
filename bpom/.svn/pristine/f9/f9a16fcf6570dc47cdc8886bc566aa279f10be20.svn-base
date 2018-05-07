package com.aspire.bpom.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aspire.bpom.constants.SysConstant;

public class RequestParam {
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		return params;
	}

	public static String createLinkString(Map<String, String> paramMap, String... excludeKeys) {
		return createLinkString(paramMap, false, false, excludeKeys);
	}

	/**
	 * 把Map所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串,不包含key值为excludeKey里的任意元素的键值对
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @param includeBlankValue
	 *            是否包括值为blank(null or "")的键值对包含在返回值中
	 * @param encodeValue
	 *            是否将值进行urlencode
	 * @param excludeKeys
	 *            该数组中的各元素对应的键值对不会包含在返回值中
	 * @return 拼接后字符串
	 */
    public static String createLinkString(Map<String, String> map, boolean includeBlankValue, boolean encodeValue,
			String... excludeKeys) {

		List<String> keys = new ArrayList<String>(map.keySet());

		if (!includeBlankValue) {
			for (String key : keys) {
				String value = map.get(key);
				if (StringUtil.isBlank(value)) {
					map.remove(key);
				}
			}
		}

		if (excludeKeys != null && excludeKeys.length > 0) {
			for (String key : excludeKeys) {
				map.remove(key);
			}
		}
		keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys);
		StringBuilder prestr = new StringBuilder(1024);
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = map.get(key);
			if (encodeValue) {
				value = URLCoder.encode(value);
			}
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr.append(key).append("=").append(value);
			} else {
				prestr.append(key).append("=").append(value).append("&");
			}
		}

		return prestr.toString();
	}

	/**
	 * 构造带签名的参数字符串，并对所有value进行UTF-8编码
	 */
	public static String createSignedParam(Map<String, String> paramMap, String privateKey) {
		String preSign = createLinkString(paramMap, "sign", "sign_type");
		String sign = RSASignature.sign(preSign, privateKey);
		paramMap.put("sign", sign);
		paramMap.put("sign_type", SysConstant.SIGN_RSA);
		List<String> keys = new ArrayList<String>(paramMap.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = URLCoder.encode(paramMap.get(key));
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;

	}
}