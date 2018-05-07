package com.aspire.bpom.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aspire.bpom.bean.SignPO;
import com.aspire.bpom.xml.bean.request.SignRelationshipReq;

public interface SignDao {

	/**
	 * 保存签约信息
	 * @param sign
	 */
	public void insertSign(SignPO sign);
	
	/**
	 * 更新签约信息
	 * @param sign
	 */
	public void updateSign(SignPO sign);
	
	/**
	 * 根据微信签约号获取签约信息
	 * @param contractCode
	 * @return
	 */
	public SignPO getSignByCode(@Param("contractCode")String contractCode);
		
	/**
	 * 查询
	 * @param contractCode
	 * @return
	 */
	public Map<String, String> getParams(String contractCode);
	
	/**
	 * 更新解约状态
	 * @param contractCode
	 */
	public void updateState(SignPO signPO);
	
	/**
	 * 获取微信签约表信息
	 * @param contractCode
	 * @return
	 */
	public Integer getSignEntity(String contractCode);
	
	/**
	 * 签约对账批量更新签约微信表
	 * @param signReq
	 */
	public void batchUpdateSign(List<SignRelationshipReq> signReqList);
}
