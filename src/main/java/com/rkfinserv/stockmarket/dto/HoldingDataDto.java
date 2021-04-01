package com.rkfinserv.stockmarket.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class HoldingDataDto {
	
	private Double totalInvestment;
	private Double currentValue;
	private Double valueChange;
	private Double changePercentage;
	
	private List<HoldingDto> holdingDtos;
	
	
	public void populateData() {
		totalInvestment = Double.valueOf(0);
		currentValue = Double.valueOf(0);
		changePercentage = Double.valueOf(0);
		for(HoldingDto holdingDto : holdingDtos) {
			totalInvestment += holdingDto.getInvestedAmount();
			currentValue += holdingDto.getCurrentValue();
		}
		valueChange = currentValue - totalInvestment;
		if(!totalInvestment.equals(Double.valueOf(0))) {
			changePercentage = (valueChange / totalInvestment)*100;
		}
	}
	
}
