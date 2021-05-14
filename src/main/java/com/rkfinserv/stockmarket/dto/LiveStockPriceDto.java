package com.rkfinserv.stockmarket.dto;

import java.math.BigDecimal;
import java.util.Date;
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
	Date lastTradedAt;
}

