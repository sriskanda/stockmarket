package com.rkfinserv.stockmarket.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
public class CandleChartDataDto {
	List<CandleDataDto> candleDataDtoList;
	LiveStockPriceDto liveStockPriceDto;
}

