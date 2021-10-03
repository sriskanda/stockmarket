package com.rkfinserv.stockmarket.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExcludedStocksServiceTest {

	
	@Test
	void test() {
		ExcludedStocksService excludedStocksService = new ExcludedStocksService();
		assertTrue(excludedStocksService.getExcludedList().contains("ICICIB22"));
		
	}

}
