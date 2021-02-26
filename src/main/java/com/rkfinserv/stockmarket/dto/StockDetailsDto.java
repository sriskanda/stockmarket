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
	
	
	
	
	
	
}
