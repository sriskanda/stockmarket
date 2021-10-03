package com.rkfinserv.stockmarket.dto;



import com.rkfinserv.stockmarket.model.BavCopy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
public class StockDetailsDto {
	private String _id;
	private String name;
	private String description;
	private Integer employees;
	private String headQuarters;
	private String industry;
	private Long marketCap;
	private Long sharesOutstanding;
	private Integer yearIncorp;
	private String learMoreLink;
	private boolean addedToWatch;
	private PriceCategoryDto priceCategory;

	public enum PriceCategoryDto {
		UPPER_HIGH,HIGH,LOWER_HIGH, UPPER_MEDIUM, MEDIUM, LOWER_MEDIUM, LOW, VERY_LOW, PENNY, NONE
	}
	
	public void setPriceCategory(BavCopyDto bavCopyDto ) {
		double close = bavCopyDto.getClose();
		if(close < 1) {
			priceCategory= PriceCategoryDto.PENNY;
		}else if(close >= 1 && close < 10) {
			priceCategory = PriceCategoryDto.VERY_LOW;
		}else if(close >= 10 && close < 100) {
			priceCategory = PriceCategoryDto.LOW;
		}else if(close >= 100 && close < 300) {
			priceCategory = PriceCategoryDto.LOWER_MEDIUM;
		}else if(close >= 300 && close < 700) {
			priceCategory = PriceCategoryDto.MEDIUM;
		}else if(close >= 700 && close < 1000) {
			priceCategory = PriceCategoryDto.UPPER_MEDIUM;
		}else if(close >= 1000 && close < 2000) {
			priceCategory = PriceCategoryDto.LOWER_HIGH;
		}else if(close >= 2000 && close < 10000) {
			priceCategory = PriceCategoryDto.HIGH;
		}else {
			priceCategory = PriceCategoryDto.UPPER_HIGH;
		}
	}
	
	public static StockDetailsDto emptyData(String id) {
		return StockDetailsDto.builder()
		._id(id)
		.name("")
		.description("")
		.employees(0)
		.headQuarters("xxx,xxx,xxx,xxx,xxx,xxx")
		.industry("")
		.marketCap(0L)
		.sharesOutstanding(0L)
		.yearIncorp(0)
		.learMoreLink("")
		.addedToWatch(false)
		.build();
		
		
		
	}
	
	
}
