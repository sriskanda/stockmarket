package com.rkfinserv.stockmarket.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.rkfinserv.stockmarket.dto.StockDetailsDto.PriceCategoryDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class BavCopyDto {
	private String symbol;
	private String series;
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private Double last;
	private Double prevClose;
	private Double change;
	private Double changePercentage;
	private Long totTrdQty;
	private Double totTrdVal;
	private Date timeStamp;
	private Long totalTrades;
	private String isin;
	private Date insertedAt;
	
}
