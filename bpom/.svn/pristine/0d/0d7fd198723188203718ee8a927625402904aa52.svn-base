
package com.aspire.bpom.xml.bean.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 订单模块退款结果通知接口请求 内部业务平台-->订单模块
 * 
 */

@XmlRootElement(name = "RefundNotifyReq")
@XStreamAlias("RefundNotifyReq")
public class RefundNotifyReq implements Serializable
{

    private static final long serialVersionUID = 9078762590992517034L;

    /*
     * 消息类型
     */
    private String MsgType;

    /*
     * 接口消息的版本号
     */
    private String Version;

    /*
     * 外部订单ID
     */
    private String orderId;

    /*
     * 外部交易ID
     */
    private String tradeId;

    /*
     * 退款金额
     */
    private Integer refundAmount;

    /*
     * 完成退款的时间 格式为： yyyyMMddHHmmss
     */
    private String refundDate;

    /*
     * 退款结果状态，取值：
     *  0：未提交的退款请求 
     *  1：退款中 
     *  2：退款成功
     *  3：退款失败 
     *  4：退款出错
     */
    private Integer refundStatus;

    public String getMsgType()
    {

        return MsgType;
    }

    public void setMsgType(String msgType)
    {

        MsgType = msgType;
    }

    public String getVersion()
    {

        return Version;
    }

    public void setVersion(String version)
    {

        Version = version;
    }

    public String getOrderId()
    {

        return orderId;
    }

    public void setOrderId(String orderId)
    {

        this.orderId = orderId;
    }
  
    public String getTradeId()
    {
    
        return tradeId;
    }

    
    public void setTradeId(String tradeId)
    {
    
        this.tradeId = tradeId;
    }

    public Integer getRefundAmount()
    {

        return refundAmount;
    }

    public void setRefundAmount(Integer refundAmount)
    {

        this.refundAmount = refundAmount;
    }

    public String getRefundDate()
    {

        return refundDate;
    }

    public void setRefundDate(String refundDate)
    {

        this.refundDate = refundDate;
    }

    public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	@Override
    public String toString()
    {

        return "RefundNotifyReq [MsgType=" + MsgType + ", Version=" + Version
               + ", orderId=" + orderId + ", tradeId=" + tradeId
               + ", refundAmount=" + refundAmount + ", refundDate="
               + refundDate + ", refundStatus=" + refundStatus + "]";
    }

 

}
