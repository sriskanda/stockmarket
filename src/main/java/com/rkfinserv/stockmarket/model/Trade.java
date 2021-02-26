package com.rkfinserv.stockmarket.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@Document
public class Trade {
	private String id;
	private String symbol;
	private TradeType tradeType;
	private Long quantity;
	private Double avgPrice;
	private Date tradeDate;

}
