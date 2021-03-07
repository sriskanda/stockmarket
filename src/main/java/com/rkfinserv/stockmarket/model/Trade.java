package com.rkfinserv.stockmarket.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
@Document
public class Trade {
	private String id;
	private String symbol;
	private TradeType tradeType;
	private Long quantity;
	private Double avgPrice;
	private Double targetPrice;
	private Date tradeDate;
	private Double latestPrice;
	private Double changePercentage;
	private Date lastTradedDate;
	
	
	public void setChangePercentage() {
		BigDecimal changePercentage = new BigDecimal(((latestPrice -avgPrice) / avgPrice) * 100).setScale(2, RoundingMode.HALF_UP) ;
		setChangePercentage(changePercentage.doubleValue());
	}
}
