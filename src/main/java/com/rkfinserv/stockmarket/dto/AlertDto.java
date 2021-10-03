package com.rkfinserv.stockmarket.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Builder
@Setter
@Getter
@ToString
public class AlertDto {
	private String id;
	private String stockId;
	private ActionDto action;
	private BigDecimal triggerPrice;
	private BigDecimal snapshotPrice;
	private BigDecimal latestPrice;
	private String comment;	
	private Date createdDate;
	private BigDecimal changeWithSnapshot;
	private BigDecimal changeWithLatest;
	private AlertStatusDto alertStatusDto;
	
	
	public void populateData() {
		changeWithSnapshot = latestPrice.subtract(snapshotPrice).divide(snapshotPrice, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
		changeWithLatest = latestPrice.subtract(triggerPrice).divide(triggerPrice, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
		
		if(ActionDto.SELL == action) {
			if(changeWithLatest.compareTo(BigDecimal.ZERO) >=0) {
				alertStatusDto = AlertStatusDto.GREEN;
			}else if(changeWithLatest.compareTo(BigDecimal.valueOf(-5)) >=0) {
				alertStatusDto = AlertStatusDto.ORANGE;
			}else {
				alertStatusDto = AlertStatusDto.BLACK;
			}
		}else if(ActionDto.BUY == action){
			if(changeWithLatest.compareTo(BigDecimal.ZERO) <=0) {
				alertStatusDto = AlertStatusDto.GREEN;
			}else if(changeWithLatest.compareTo(BigDecimal.valueOf(5)) <=0) {
				alertStatusDto = AlertStatusDto.ORANGE;
			}else {
				alertStatusDto = AlertStatusDto.BLACK;
			}			
		}
	
	
	}
}
