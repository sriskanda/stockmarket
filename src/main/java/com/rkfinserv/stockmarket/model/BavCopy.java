package com.rkfinserv.stockmarket.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.rkfinserv.stockmarket.dto.BavCopyDto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Document
public class BavCopy {
	@Id
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
	public void setChange() {
		this.change = close - prevClose;
	}
	public void setChangePercentage() {
		this.changePercentage = (close - prevClose)/ prevClose * 100 ;
	}
	
	
	public BavCopyDto asDto() {
		return BavCopyDto.builder()
		.symbol(symbol)
		.series(series)
		.open(open)
		.high(high)
		.low(low)
		.close(close)
		.last(last)
		.prevClose(prevClose)
		.change(change)
		.changePercentage(changePercentage)
		.totTrdQty(totTrdQty)
		.totTrdVal(totTrdVal)
		.timeStamp(timeStamp)
		.totalTrades(totalTrades)
		.isin(isin)
		.insertedAt(insertedAt)
		.build();
	}

}
