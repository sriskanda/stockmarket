package com.rkfinserv.stockmarket.model;

import java.math.BigDecimal;
import java.util.Date;
import com.rkfinserv.stockmarket.dto.LiveStockPriceDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LiveStockPrice {
	String symbol;
	BigDecimal livePrice;
	BigDecimal percentageChage;
	Date lastTradedAt;


	public LiveStockPriceDto asDto(){
		return LiveStockPriceDto.builder().symbol(symbol).livePrice(livePrice).percentageChage(percentageChage).lastTradedAt(lastTradedAt).build();
	}
}
