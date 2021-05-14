package com.rkfinserv.stockmarket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
public class StockChartDataDto {
	private String[] dates;
	private Double[] prices;
}
