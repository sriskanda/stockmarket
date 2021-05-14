package com.rkfinserv.stockmarket.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.rkfinserv.stockmarket.dto.BavCopyAuditDto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@Document
public class BavCopyAudit {
	@Id
	private String id;
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
	
	public static BavCopyAudit createAudit(BavCopy bavCopy) {
		return BavCopyAudit.builder()
		.symbol(bavCopy.getSymbol())
		.series(bavCopy.getSeries())
		.open(bavCopy.getOpen())
		.high(bavCopy.getHigh())
		.low(bavCopy.getLow())
		.close(bavCopy.getClose())
		.last(bavCopy.getLast())
		.prevClose(bavCopy.getPrevClose())
		.change(bavCopy.getChange())
		.changePercentage(bavCopy.getChangePercentage())
		.totTrdQty(bavCopy.getTotTrdQty())
		.totTrdVal(bavCopy.getTotTrdVal())
		.timeStamp(bavCopy.getTimeStamp())
		.totalTrades(bavCopy.getTotalTrades())
		.isin(bavCopy.getIsin())
		.insertedAt(bavCopy.getInsertedAt())
		.build();		
	}
	
	public BavCopyAuditDto asDto(BavCopyAudit bavCopyAudit) {
	
		return BavCopyAuditDto.builder()
				.id(bavCopyAudit.getId())
				.symbol(bavCopyAudit.getSymbol())
				.series(bavCopyAudit.getSeries())
				.open(bavCopyAudit.getOpen())
				.high(bavCopyAudit.getHigh())
				.low(bavCopyAudit.getLow())
				.close(bavCopyAudit.getClose())
				.last(bavCopyAudit.getLast())
				.prevClose(bavCopyAudit.getPrevClose())
				.change(bavCopyAudit.getChange())
				.changePercentage(bavCopyAudit.getChangePercentage())
				.totTrdQty(bavCopyAudit.getTotTrdQty())
				.totTrdVal(bavCopyAudit.getTotTrdVal())
				.timeStamp(bavCopyAudit.getTimeStamp())
				.totalTrades(bavCopyAudit.getTotalTrades())
				.isin(bavCopyAudit.getIsin())
				.insertedAt(bavCopyAudit.getInsertedAt())
				.build();		
		
		
		
	}
	
	public String getDateText() {
		return new SimpleDateFormat("dd-MM-YY").format(timeStamp);
	}
	
}
