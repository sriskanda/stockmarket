package com.rkfinserv.stockmarket.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockDetailsDtoTest {
	StockDetailsDto stockDetailsDto;
	
	
	@BeforeEach
	public void Before() {
		stockDetailsDto = StockDetailsDto.builder().build();
	}
	
	
	
	@Test
	void price_category_should_be_penny() {
		BavCopyDto bavCopyDto = BavCopyDto.builder().close(0.2).build();
		stockDetailsDto.setPriceCategory(bavCopyDto);
		assertEquals(StockDetailsDto.PriceCategoryDto.PENNY, stockDetailsDto.getPriceCategory());	
	}

	@Test
	void price_category_should_be_verylow() {
		BavCopyDto bavCopyDto = BavCopyDto.builder().close(5.0).build();
		stockDetailsDto.setPriceCategory(bavCopyDto);
		assertEquals(StockDetailsDto.PriceCategoryDto.VERY_LOW, stockDetailsDto.getPriceCategory());	
	}

	
	@Test
	void price_category_should_be_High() {
		BavCopyDto bavCopyDto = BavCopyDto.builder().close(2005.0).build();
		stockDetailsDto.setPriceCategory(bavCopyDto);
		assertEquals(StockDetailsDto.PriceCategoryDto.HIGH, stockDetailsDto.getPriceCategory());	
	}

	
	@Test
	void price_category_should_be_upper_medium() {
		BavCopyDto bavCopyDto = BavCopyDto.builder().close(727.95).build();
		stockDetailsDto.setPriceCategory(bavCopyDto);
		assertEquals(StockDetailsDto.PriceCategoryDto.UPPER_MEDIUM, stockDetailsDto.getPriceCategory());	
	}

}
