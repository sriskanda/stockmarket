package com.rkfinserv.stockmarket.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.rkfinserv.stockmarket.dto.ActionDto;
import com.rkfinserv.stockmarket.dto.AlertDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document
@Builder
@Setter
@Getter
@ToString
public class Alert {
	private String id;
	private String stockId;
	private BigDecimal triggerPrice;
	private BigDecimal snapshotPrice;
	private Action action;
	private String comment;
	private Date createdDate;
	
	public AlertDto asDto() {
		return AlertDto.builder()
		.id(id)
		.stockId(stockId)
		.triggerPrice(triggerPrice)
		.snapshotPrice(snapshotPrice)
		.action(ActionDto.valueOf(action.name()))
		.comment(comment)
		.createdDate(createdDate)
		.build();
	}
	
	
	public static Alert asDomain(AlertDto alertDto) {
			
		return Alert.builder()
		.id(alertDto.getId())
		.stockId(alertDto.getStockId())
		.triggerPrice(alertDto.getTriggerPrice())
		.snapshotPrice(alertDto.getSnapshotPrice())
		.action(Action.valueOf(alertDto.getAction().name()))
		.comment(alertDto.getComment())
		.createdDate(alertDto.getCreatedDate())
		.build();
	}
	
}
