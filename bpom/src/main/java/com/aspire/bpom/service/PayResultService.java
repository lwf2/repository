package com.aspire.bpom.service;

import com.aspire.bpom.extensions.exception.BusinessException;
import com.aspire.bpom.xml.bean.request.PayResultNotifyReq;
import com.aspire.bpom.xml.bean.response.PayOrderResultNotifyResp;


/**
 * 支付结果通过
 * @author chenpeng
 *
 */
public interface PayResultService {
	PayOrderResultNotifyResp payResult(PayResultNotifyReq payResultNotifyReq,String tradeId) throws BusinessException;
}
