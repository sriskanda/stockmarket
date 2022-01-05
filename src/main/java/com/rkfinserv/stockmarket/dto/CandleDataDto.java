package com.rkfinserv.stockmarket.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
@Getter
public class CandleDataDto {
	private Date x;
	private Double[] y;
}

