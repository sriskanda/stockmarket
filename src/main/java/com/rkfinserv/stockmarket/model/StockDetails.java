package com.rkfinserv.stockmarket.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.rkfinserv.stockmarket.dto.StockDetailsDto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Document
@ToString
@Getter
public class StockDetails {
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
	
	public StockDetailsDto asDto() {
		return StockDetailsDto.builder()
		._id(_id)
		.name(name)
		.description(description)
		.employees(employees)
		.headQuarters(headQuarters)
		.industry(industry)
		.marketCap(marketCap)
		.sharesOutstanding(sharesOutstanding)
		.yearIncorp(yearIncorp)
		.learMoreLink(learMoreLink)
		.build();
	}
	
	
	
	
}
