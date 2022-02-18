package com.rkfinserv.stockmarket.model;

import java.math.BigDecimal;
import java.util.Date;
import com.rkfinserv.stockmarket.dto.LiveStockPriceDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LiveStockPrice {
	@EqualsAndHashCode.Include
	String symbol;
	BigDecimal close;
	BigDecimal percentageChage;
	BigDecimal yearHigh;
	BigDecimal yearLow;
	Date lastTradedAt;
	BigDecimal volume;
	BigDecimal open;
	BigDecimal high;
	BigDecimal low;
	BigDecimal prevClose;
	String name;
	String industry;
	String headQuarters;
	String description;
	String yearOfInc;
	Long employees;
	BigDecimal marketCap;
	BigDecimal peRation;
	BigDecimal beta;
	Long outstandingShares;





	public LiveStockPriceDto asDto(){
		return LiveStockPriceDto.builder().symbol(symbol)
				.close(close)
				.percentageChage(percentageChage)
			    .yearHigh(yearHigh)
			    .yearLow(yearLow)
				.lastTradedAt(lastTradedAt)
				.volume(volume)
				.open(open)
				.high(high)
				.low(low)
				.prevClose(prevClose)
				.name(name)
				.industry(industry)
				.headQuarters(headQuarters)
				.description(description)
				.yearOfInc(yearOfInc)
				.employees(employees)
				.marketCap(marketCap)
				.peRation(peRation)
				.beta(beta)
				.outstandingShares(outstandingShares)
				.build();
	}
	

	
}
