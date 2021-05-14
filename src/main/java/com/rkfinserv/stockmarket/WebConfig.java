package com.rkfinserv.stockmarket;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rkfinserv.stockmarket.utils.StringActionConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringActionConverter());
	}
	
}
