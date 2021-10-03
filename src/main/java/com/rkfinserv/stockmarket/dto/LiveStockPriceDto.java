package com.rkfinserv.stockmarket.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.rkfinserv.stockmarket.model.LiveStockPrice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class LiveStockPriceDto {
	String symbol;
	BigDecimal livePrice;
	BigDecimal percentageChage;
	BigDecimal yearHigh;
	BigDecimal yearLow;
	BigDecimal changeFromYearHigh;
	BigDecimal changeFromYearLow;
	BigDecimal volume;
	BigDecimal value;
	
	Date lastTradedAt;
	
	
	
	public static LiveStockPriceDto empty(){
		LiveStockPriceDto liveStockPrice = LiveStockPriceDto.builder()
				.livePrice(BigDecimal.ZERO)
				.percentageChage(BigDecimal.ZERO)
				.yearHigh(BigDecimal.ZERO)
				.yearLow(BigDecimal.ZERO)
				.lastTradedAt(new Date())
				.volume(BigDecimal.ZERO)
				.build(); 
		return liveStockPrice;
	}
	
	public void enrichData() {
		
		this.changeFromYearHigh = yearHigh == null || yearHigh.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : yearHigh.subtract(livePrice).divide(yearHigh, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
		this.changeFromYearLow = yearLow == null || yearLow.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : livePrice.subtract(yearLow).divide(yearLow, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
		this.value = volume.multiply(livePrice);
	}

}

