package com.rkfinserv.stockmarket.dto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class HoldingDto {
	private String symbol;
	private Long quantity;
	private Double avgPrice;
	private Double latestPrice;
	private Double investedAmount;
	private Double currentValue;
	private Double valueChange;
	private Double percentageChange;
	
	
	public void poplulateData() {
		investedAmount = avgPrice * quantity;
		currentValue = latestPrice * quantity; 
		valueChange =  currentValue - investedAmount;
		percentageChange = ((latestPrice - avgPrice) / avgPrice) * 100;
	}

}
