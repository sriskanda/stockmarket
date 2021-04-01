package com.rkfinserv.stockmarket.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Document
public class Holding {
	@Id
	private String symbol;
	private Long quantity;
	private Double avgPrice;
	
}
