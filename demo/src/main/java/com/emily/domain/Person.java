package com.emily.domain;

import javax.validation.constraints.Size;

/**
 * 
 * <p>Title:Person </p>
 * <p>Description: 领域对象模型</p>
 * <p>Project:springboot学习 </p> 
 *
 * @author admin（2017年7月14日 上午10:12:23）
 *
 * @version:1.0.0 copyright © 2017-2018
 */
public class Person {
	
	//JSR-303 注解校验数据
	@Size(max=4,min=2)
	private String name;
	
	private int age;
	
	private String nation;
	
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
