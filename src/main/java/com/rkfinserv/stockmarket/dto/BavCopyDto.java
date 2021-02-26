package com.rkfinserv.stockmarket.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
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
