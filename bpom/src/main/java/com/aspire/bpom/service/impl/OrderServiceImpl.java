package com.aspire.bpom.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.dao.OrderDao;
import com.aspire.bpom.service.OrderService;

@Component("orderService")
public class OrderServiceImpl implements OrderService{

	@Resource
	private OrderDao orderDao;
	
	public void insertOrder(OrderPO orderPO) {
		orderDao.inserOrder(orderPO);
	}
	
	public void updateOrder(OrderPO orderPO) {
		orderDao.updateOrder(orderPO);
	}
	
	/**
	 * 根据外部交易id或者支付网关交易流水ID获取订单信息
	 * @param orderId
	 * @param requestId
	 * @param tradeId
	 * @return
	 */
	public OrderPO getOrderById(String requestId,String tradeId) {
		return orderDao.getOrderById(requestId,tradeId);
	}

	/**
	 * 保存订单流水
	 */
	@Override
	public void insertOrderHis(OrderPO orderPO) {
		orderDao.inserOrderHis(orderPO);
	}
	

}
