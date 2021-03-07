package com.rkfinserv.stockmarket.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TradeDto {
	private String id;
	private String symbol;
	private String tradeType;
	private Long quantity;
	private Double avgPrice;
	private Double targetPrice;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date tradeDate;	
	private Double latestPrice;
	private Double changePercentage;
	private Date lastTradedDate;
}
