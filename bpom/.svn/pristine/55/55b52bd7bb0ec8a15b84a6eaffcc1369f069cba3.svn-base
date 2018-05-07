package com.aspire.bpom.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.aspire.bpom.bean.SignPO;
import com.aspire.bpom.dao.SignDao;
import com.aspire.bpom.service.SignService;

@Component("signService")
public class SignServiceImpl implements SignService{


	@Resource
	private SignDao signDao;
	
	/**
	 * 新增签约信息
	 * @param sign
	 */
	public void insertIntoSign(SignPO sign) {
		signDao.insertSign(sign);
	}
	
	/**
	 * 更新签约信息
	 * @param sign
	 */
	public void updateSign(SignPO sign) {
		signDao.updateSign(sign);
	}
	
	/**
	 * 查询签约信息
	 * @param contractCode
	 * @return
	 */
	public SignPO getSignByContractCode(String contractCode) {
		return signDao.getSignByCode(contractCode);
	}
}
