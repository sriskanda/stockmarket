package com.rkfinserv.stockmarket.dto;
import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class HoldingDto {
	private String symbol;
	private String quantity;
	private String avgPrice;
}
