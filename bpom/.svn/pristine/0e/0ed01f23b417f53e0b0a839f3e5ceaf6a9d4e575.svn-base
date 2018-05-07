package com.aspire.bpom.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;

import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.xml.bean.request.BeginPayReq;
import com.aspire.bpom.xml.bean.request.CancelContractReq;
import com.aspire.bpom.xml.bean.request.ClosePayReq;
import com.aspire.bpom.xml.bean.request.PaySignReq;
import com.aspire.bpom.xml.bean.request.QryContractReq;
import com.aspire.bpom.xml.bean.request.QryPayReq;
import com.aspire.bpom.xml.bean.request.RefundReq;
import com.aspire.bpom.xml.bean.request.RefundRequest;

/**
 * 订单模块与内部系统通信的签名工具类
 * 
 * @author chenpeng
 * 
 */
public class MD5Hex {
    private static Logger log = BpomLogger.getLogger(MD5Hex.class);

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
        log.debug("hmac=" + hmac + "; preSign=" + preSign);
        return hmac;
    }

    
    
    /**
     * 支付并签约签名校验
     * @param 
     * @return
     */
    public static boolean checkSign(PaySignReq paysignReq, String secretKey) {
//        ListMap<String, String> map =  BeanUtil.transBeanToMap(paysignReq, true);

    	ListMap<String, String> map = createMap(paysignReq);
        log.info("map:"+map.toString());
        String hmac = paysignReq.getHmac();
        String _hmac = getHmac(map, secretKey);
        if (_hmac != null && _hmac.equals(hmac)) {
            return true;
        } else {
            log.info("签名校验码不相等：req->hmac:" + hmac + " calc->hmac:" + _hmac);
            return false;
        }
    }
    
    /**
     * 支付签约组装map
     * @param paysignReq
     * @return
     */
    public static ListMap<String, String> createMap(PaySignReq paysignReq) {
        ListMap<String, String> map = new ListMap<String, String>();
        String msgType = paysignReq.getMsgType();
        map.put("MsgType", msgType);
        String msgVer = paysignReq.getVersion();
        map.put("Version", msgVer);
        String systemCode = paysignReq.getSystemCode();
        map.put("systemCode", systemCode);
        String servPltfmCode = paysignReq.getServPltfmCode();
        map.put("servPltfmCode", servPltfmCode);
        String payWay = paysignReq.getPayWay();
        map.put("payWay", payWay);
        String payOrganization = paysignReq.getPayOrganization();
        map.put("payOrganization", payOrganization);
        String payType = String.valueOf(paysignReq.getPayType());
        map.put("payType", payType);
        String ipAddress = paysignReq.getIpAddress();
        map.put("ipAddress", ipAddress);
        String callbackUrl = paysignReq.getCallbackUrl();
        map.put("callbackUrl", callbackUrl);
        String orderId = paysignReq.getOrderId();
        map.put("orderId", orderId);
        String orderTime = paysignReq.getOrderTime();
        map.put("orderTime", orderTime);
        String tradeId = paysignReq.getTradeId();
        map.put("tradeId", tradeId);
        String userId = paysignReq.getUserId();
        map.put("userId", userId);
        String merchantName = paysignReq.getMerchantName();
        map.put("merchantName", merchantName);
        String productId = paysignReq.getProductId();
        map.put("productId", productId);
        String productName = paysignReq.getProductName();
        map.put("productName", productName);
        String productDesc = paysignReq.getProductDesc();
        map.put("productDesc", productDesc);
        String channelId = paysignReq.getChannelId();
        map.put("channelId", channelId);
        String amount = String.valueOf(paysignReq.getAmount());
        map.put("amount", amount);
        String reserved1 = paysignReq.getReserved1();
        map.put("reserved1", reserved1);
        String productURL = paysignReq.getProductURL();
        map.put("productURL", productURL);
        String wxOpenid = paysignReq.getWxOpenid();
        map.put("wxOpenid", wxOpenid);
        String wxContract_code = paysignReq.getWxContract_code();
        map.put("wxContract_code", wxContract_code);
        String wxPlan_id = paysignReq.getWxPlan_id();
        map.put("wxPlan_id", wxPlan_id);
        String wxContract_display = paysignReq.getWxContract_display();
        map.put("wxContract_display", wxContract_display);
        String wxContract_id = paysignReq.getWxContract_id();
        map.put("wxContract_id", wxContract_id);
        String period = String.valueOf(paysignReq.getPeriod());
        map.put("period", period);
        String periodUnit = paysignReq.getPeriodUnit();
        map.put("periodUnit", periodUnit);
        return map;
    }
    
    
    /**
     * 支付签约组装请求支付网关map
     * @param beginPayReq
     * @return
     */
    public static ListMap<String, String> createMap(BeginPayReq beginPayReq) {
    	ListMap<String, String> map = new ListMap<String, String>();
    	String msgType = beginPayReq.getMsgType();
    	map.put("msgType", msgType);
    	String msgVer = beginPayReq.getMsgVer();
    	map.put("msgVer", msgVer);
    	String systemCode = beginPayReq.getSystemCode();
    	map.put("systemCode", systemCode);
    	String servPltfmCode = beginPayReq.getServPltfmCode();
    	map.put("servPltfmCode", servPltfmCode);
    	String payWay = beginPayReq.getPayWay();
    	map.put("payWay", payWay);
    	String payOrganization = beginPayReq.getPayOrganization();
    	map.put("payOrganization", payOrganization);
    	String payType = String.valueOf(beginPayReq.getPayType());
    	map.put("payType", payType);
    	String ipAddress = beginPayReq.getIpAddress();
    	map.put("ipAddress", ipAddress);
    	String callbackUrl = beginPayReq.getCallbackUrl();
    	map.put("callbackUrl", callbackUrl);
    	String userId = beginPayReq.getUserId();
    	map.put("userId", userId);
    	String orderId = beginPayReq.getOrderId();
    	map.put("orderId ", orderId);
    	String accountId = beginPayReq.getAccountId();
    	map.put("accountId", accountId == null ? null : accountId + "");
    	String amount = String.valueOf(beginPayReq.getAmount());
    	map.put("amount", amount);
    	String orderDate = beginPayReq.getOrderDate();
    	map.put("orderDate", orderDate);
    	String period = String.valueOf(beginPayReq.getPeriod());
    	map.put("period", period);
    	String periodUnit = beginPayReq.getPeriodUnit();
    	map.put("periodUnit", periodUnit);
    	String merchantName = beginPayReq.getMerchantName();
    	map.put("merchantName", merchantName);
    	String productId = beginPayReq.getProductId();
    	map.put("productId", productId);
    	String productName = beginPayReq.getProductName();
    	map.put("productName", productName);
    	String productDesc = beginPayReq.getProductDesc();
    	map.put("productDesc", productDesc);
    	String reserved1 = beginPayReq.getReserved1();
    	map.put("reserved1", reserved1);
    	String productURL = beginPayReq.getProductURL();
    	map.put("productURL", productURL);
    	String wxOpenid = beginPayReq.getWxOpenid();
    	map.put("wxOpenid", wxOpenid);
    	if(beginPayReq.getPayWay().matches("^.*?-CIP$")){
    		String wxPlan_id = beginPayReq.getWxPlan_id();
    		map.put("wxPlan_id", wxPlan_id);
    		String wxContract_code = beginPayReq.getWxContract_code();
    		map.put("wxContract_code", wxContract_code);
    		String wxContract_display = beginPayReq.getWxContract_display();
    		map.put("wxContract_display", wxContract_display);
    		String wxContract_id = beginPayReq.getWxContract_id();
    		map.put("wxContract_id", wxContract_id);
    	}
    	return map;
    }
    
    /**
     * 支付状态查询验证签名
     * @param qryPayReq
     * @param secretKey
     * @return
     */
    public static boolean checkSign(QryPayReq qryPayReq, String secretKey) {
//        ListMap<String, String> map = BeanUtil.transBeanToMap(qryPayReq, false);
    	ListMap<String, String> map = createMap(qryPayReq);
    	log.info("map:"+map.toString());
        String hmac = qryPayReq.getHmac();
        String _hmac = getHmac(map, secretKey);
        if (_hmac != null && _hmac.equals(hmac)) {
            return true;
        } else {
            log.info("签名校验码不相等：req->hmac:" + hmac + " calc->hmac:" + _hmac);
            return false;
        }
    }
    /**
     * 支付状态查询组装map
     * @param qryPayReq
     * @return
     */
    public static ListMap<String, String> createMap(QryPayReq qryPayReq) {
    	ListMap<String, String> map = new ListMap<String, String>();
    	 String MsgType = qryPayReq.getMsgType();
         map.put("MsgType", MsgType);
         String Version = qryPayReq.getVersion();
         map.put("Version", Version);
         String systemCode = qryPayReq.getSystemCode();
         map.put("systemCode", systemCode);
         String orderId = qryPayReq.getOrderId();
         map.put("orderId", orderId);
         String tradeId = qryPayReq.getTradeId();
         map.put("tradeId", tradeId);
    	return map;
    }
    
    /**
     * 关闭订单签名校验
     * @param 
     * @return
     */
    public static boolean checkSign(ClosePayReq closePayReq, String secretKey) {
//        ListMap<String, String> map =  BeanUtil.transBeanToMap(closePayReq , false);
        ListMap<String, String> map =createMap(closePayReq);
       log.info("map:"+map.toString());
        String hmac = closePayReq.getHmac();
        String _hmac = getHmac(map, secretKey);
        if (_hmac != null && _hmac.equals(hmac)) {
            return true;
        } else {
            log.info("签名校验码不相等：req->hmac:" + hmac + " calc->hmac:" + _hmac);
            return false;
        }
    }
    
    /**
     * 关闭订单组装map
     * @param closePayReq
     * @return
     */
    public static ListMap<String, String> createMap(ClosePayReq closePayReq) {
    	ListMap<String, String> map = new ListMap<String, String>();
    	String MsgType = closePayReq.getMsgType();
    	map.put("MsgType", MsgType);
    	String Version = closePayReq.getVersion();
    	map.put("Version", Version);
    	String systemCode = closePayReq.getSystemCode();
    	map.put("systemCode", systemCode);
    	String orderId = closePayReq.getOrderId();
    	map.put("orderId", orderId);
    	String tradeId = closePayReq.getTradeId();
    	map.put("tradeId", tradeId);
    	return map;
    	
    }
    /**
     * 查询签约关系签名校验
     * @param qryContractReq
     * @param secretKey
     * @return
     */
    public static boolean checkSign(QryContractReq qryContractReq, String secretKey) {
        ListMap<String, String> map = BeanUtil.transBeanToMap(qryContractReq, false);

        String hmac = qryContractReq.getHmac();
        String _hmac = getHmac(map, secretKey);
        if (_hmac != null && _hmac.equals(hmac)) {
            return true;
        } else {
            log.info("签名校验码不相等：req->hmac:" + hmac + " calc->hmac:" + _hmac);
            return false;
        }
    }
    /**
     * 申请退款签名校验
     * @param 
     * @return
     */
    public static boolean checkSign(RefundReq refundReq, String secretKey) {
        //ListMap<String, String> map = BeanUtil.transBeanToMap(refundReq, false);
        ListMap<String, String> map =createMap(refundReq);
        String hmac = refundReq.getHmac();
        String _hmac = getHmac(map, secretKey);
        if (_hmac != null && _hmac.equals(hmac)) {
            return true;
        } else {
            log.info("签名校验码不相等：req->hmac:" + hmac + " calc->hmac:" + _hmac);
            return false;
        }
    }
    /**
     * 申请退款组装map
     * @param closePayReq
     * @return
     */
    public static ListMap<String, String> createMap(RefundReq refundReq) {
        ListMap<String, String> map = new ListMap<String, String>();
        String MsgType = refundReq.getMsgType();
        map.put("MsgType", MsgType);
        String Version = refundReq.getVersion();
        map.put("Version", Version);
        String systemCode = refundReq.getSystemCode();
        map.put("systemCode", systemCode);
        String orderId = refundReq.getOrderId();
        map.put("orderId", orderId);
        String tradeId = refundReq.getTradeId();
        map.put("tradeId", tradeId);
        String refundDesc=refundReq.getRefundDesc();
        map.put("refundDesc", refundDesc);                   
        return map;
        
    }
    
    /**
     * 申请解约接口验证签名
     * @param canConReq
     * @param secretKey
     * @return
     */
    public static boolean checkSign(CancelContractReq canConReq, String secretKey) {
    	ListMap<String, String> map = BeanUtil.transBeanToMap(canConReq, false);
    	
    	String hmac = canConReq.getHmac();
    	String _hmac = getHmac(map, secretKey);
    	if (_hmac != null && _hmac.equals(hmac)) {
    		return true;
    	} else {
    		log.info("签名校验码不相等：req->hmac:" + hmac + " calc->hmac:" + _hmac);
    		return false;
    	}
    }

    private static String ALGORITHM = "MD5";// SpringUtil.getProperty("sign.method");
    private static String CHARSET = "UTF-8";// SpringUtil.getProperty("sign.charset");

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

//    public static boolean checkSign(RefundReq req, String secretKey) {
//        ListMap<String, String> map = createMap(req);
//        String hmac = req.getHmac();
//        String _hmac = getHmac(map, secretKey);
//        if (_hmac != null && _hmac.equals(hmac)) {
//            return true;
//        }
//        log.debug("签名校验码不相等：req->hmac:" + hmac + " calc->hmac:" + _hmac);
//        return false;
//    }

    public static ListMap<String, String> createMap(RefundRequest req) {
        ListMap<String, String> map = new ListMap<String, String>();
        map.put("msgType", req.getMsgType());
        map.put("msgVer", req.getMsgVer());
        map.put("requestId", req.getRequestId());
        map.put("userId", req.getUserId());
        map.put("orderId", req.getOrderId());
        map.put("refundAmount", String.valueOf(req.getRefundAmount()));
        map.put("refundDesc", req.getRefundDesc());
        map.put("systemCode", req.getSystemCode());

        return map;
    }

    /**
     * 微信签名
     * 
     * @param paramMap
     * @param privateKey
     * @return
     */
    public static String createrSgin(Map<String, String> paramMap, String privateKey) {
        String param = RequestParam.createLinkString(paramMap) + "&key=" + privateKey;
        String sgin = sign(param);
        return sgin;
    }

}
