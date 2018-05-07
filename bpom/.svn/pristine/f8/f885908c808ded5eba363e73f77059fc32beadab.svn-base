package com.aspire.bpom.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map.Entry;

import com.aspire.bpom.util.ListMap;

public class MD5Test {
	
	 private static String ALGORITHM = "MD5";// SpringUtil.getProperty("sign.method");
	 private static String CHARSET = "UTF-8";

	public static void main(String[] args) {
		ListMap<String, String> map = createMap();
        System.out.println("map:"+map.toString());
        String secretKey = "1234567890RWK";//填写网元密钥
        String _hmac = getHmac(map, secretKey);
        System.out.println("生成签名为："+_hmac);
	}
	
	
	public static ListMap<String, String> createMap() {
		ListMap<String, String> map = new ListMap<String, String>();
		map.put("MsgType", "PaySignReq");
		map.put("Version", "1.0.0");
		map.put("systemCode", "RWKWAP");
		map.put("servPltfmCode", "RWK");
		map.put("payWay", "H5");
		map.put("payOrganization", "WEIXIN");
		map.put("payType", "1");
		map.put("ipAddress", "10.1.2.12");
		map.put("callbackUrl", "http://www.baidu.com");
		map.put("orderId", "2013243632");
		map.put("orderTime", "20171010101012");
		map.put("tradeId", "20174332235435");
		map.put("userId", "13788273872");
		map.put("merchantName", "任我看流量包");
		map.put("productId", "123455");
		map.put("productName", "任我看流量包");
		map.put("productDesc", "流量包");
		map.put("channelId", "10960");
		map.put("amount", "101");
		map.put("reserved1", "");
		map.put("productURL", "http://www.sina.com");
		map.put("wxOpenid", "wx123");
		//PayWap取值WX-JSAPI-CIP	微信JSAPI支付中签约代付或者WX-NATIVE-CIP	微信 NATIVE支付中签约代付
//		if(paysignReq.getPayWay().matches("^.*?-CIP$")){
//			String wxContract_code = paysignReq.getWxContract_code();
//			map.put("wxContract_code", wxContract_code);
//			String wxPlan_id = paysignReq.getWxPlan_id();
//			map.put("wxPlan_id", wxPlan_id);
//			String wxContract_display = paysignReq.getWxContract_display();
//			map.put("wxContract_display", wxContract_display);
//		}
		map.put("period", "1");
		map.put("periodUnit", "01");
		return map;
	}
	
	 /**
     * 对参数值、商户密钥按顺序相加获得的值进行签名
     * 
     * @param paramMap
     * @param secretKey
     * @return
     */
    public static String getHmac(ListMap<String, String> paramMap, String secretKey) {
        if (secretKey == null || "null".equals(secretKey) || secretKey.trim().length() == 0) {
            return null;
        }
        String preSign = "";
        for (Entry<String, String> entry : paramMap.entryList()) {
            if (!"hmac".equalsIgnoreCase(entry.getKey()) && entry.getValue() != null
                    && !"null".equalsIgnoreCase(entry.getValue()))
                preSign += entry.getValue();
        }
        preSign += secretKey;
        String hmac = sign(preSign);
        System.out.println("hmac=" + hmac + "; preSign=" + preSign);
        return hmac;
    }
    
    /**
     * 对待签字符串进行MD5摘要算法计算，将所得值转换成16进制串（大写）后返回
     * 
     * @param preSign
     * @return
     */
    private static String sign(String preSign) {

        if (preSign == null) {
            return null;
        }

        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.reset();
            messageDigest.update(preSign.getBytes(CHARSET));
        } catch (NoSuchAlgorithmException e) {

            return preSign;
        } catch (UnsupportedEncodingException e) {
            return preSign;
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString().toUpperCase();
    }

}
