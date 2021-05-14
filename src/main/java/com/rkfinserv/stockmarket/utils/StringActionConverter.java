package com.rkfinserv.stockmarket.utils;

import org.springframework.core.convert.converter.Converter;

import com.rkfinserv.stockmarket.dto.ActionDto;

public class StringActionConverter implements Converter<String, ActionDto>{
	
	@Override
	public ActionDto convert(String action) {
		return ActionDto.valueOf(action);
	}
	

}
