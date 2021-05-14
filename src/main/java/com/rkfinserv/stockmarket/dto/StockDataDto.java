package com.rkfinserv.stockmarket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class StockDataDto {
	private BavCopyDto bavCopy;
	private StockDetailsDto stockDetails;
}
