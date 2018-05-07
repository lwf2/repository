package com.emily.batch;

import org.springframework.batch.item.validator.ValidatingItemProcessor;

import com.emily.domain.Person;

public class CsvItemProcessor extends ValidatingItemProcessor<Person>{

	public Person process(Person item){
		//需执行才会调用自定义校验器
		super.process(item);
		if(item.getNation().equals("汉族")){
			item.setNation("01");
		}else{
			item.setNation("02");
		}
		return item;
	}
}
