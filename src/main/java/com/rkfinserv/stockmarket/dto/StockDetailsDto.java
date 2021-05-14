package com.rkfinserv.stockmarket.dto;

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
