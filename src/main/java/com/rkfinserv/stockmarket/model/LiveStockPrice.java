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
	BigDecimal livePrice;
	BigDecimal percentageChage;
	BigDecimal yearHigh;
	BigDecimal yearLow;
	Date lastTradedAt;
	BigDecimal volume;


	public LiveStockPriceDto asDto(){
		return LiveStockPriceDto.builder().symbol(symbol)
				.livePrice(livePrice)
				.percentageChage(percentageChage)
			    .yearHigh(yearHigh)
			    .yearLow(yearLow)
				.lastTradedAt(lastTradedAt)
				.volume(volume)
				.build();
	}
	

	
}
