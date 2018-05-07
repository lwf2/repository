package com.aspire.bpom.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.bean.SettlePO;
import com.aspire.bpom.xml.bean.request.PayTransReconReq;

public interface OrderDao {

	/**
	 * 保存订单
	 * @param order
	 */
	public void inserOrder(OrderPO order);
	/**
	 * 保存订单流水
	 * @param order
	 */
	public void inserOrderHis(OrderPO order);
	
	/**
	 * 更新订单
	 * @param orderId
	 * @param requestId
	 */
	public void updateOrder(OrderPO order);


	/**
	 * 根据订单号或者支付网关流水id或者外部交易id查询订单信息
	 * @param orderId
	 * @return
	 */
	public OrderPO getOrderById(@Param("requestId")String requestId,@Param("tradeId")String tradeId);
	/**
     * 保存退款单信息
     * @param orderPO
     *
     */
	public Integer insertRefund(OrderPO orderPO);
	/**
     * 保存退款单流水信息 
     * @param orderPO
     * 
     */
	public Integer insertRefundHis(OrderPO orderPO);
	/**
     * 修改退款单状态 
     * @param orderPO
     * 
     */
	public Integer updateRefundStatus(OrderPO orderPO);
	
	/**
	 * 查询订单流水表中payStatus为支付成功或退款的记录
	 * @return
	 */
	public List<OrderPO> getOrderInfo();
	
	/**
	 * 根据外部订单ID查询支付状态
	 * @param outOrderId
	 * @return
	 */
	public OrderPO getOrderByOutOrderId(String outOrderId);
	
	/**
	 * 订单对账批量更新订单表
	 * @param payTransList
	 * @return
	 */
	public Integer batchUpdateOrder(List<PayTransReconReq> payTransList);
	
	/**
	 * 根据外部订单ID批量查询订单表中已更新的数据
	 * @param payTransList
	 * @return
	 */
	public List<OrderPO> batchQueryOrder(List<PayTransReconReq> payTransList);
	
	/**
	 * 订单对账批量新增订单历史表
	 * @param payTransList
	 * @return
	 */
	public Integer batchAddOrderHis(List<OrderPO> orderPoList);
	
	/**
     * 定时查询结算单记录
     * @return
     */
	public List<SettlePO> timingQuerySettle();

	/**
	 * 查询订单对账数据
	 * @return
	 */
	public List<OrderPO> getOrderListByTradeId(@Param("set")Set set);
}
