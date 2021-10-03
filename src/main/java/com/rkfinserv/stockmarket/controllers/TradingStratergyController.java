package com.rkfinserv.stockmarket.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.service.TradingStratergyService;

@RestController
@RequestMapping("/tradeStrategy")
public class TradingStratergyController {
	
	private final TradingStratergyService tradingStratergyService;
	
	
	@Autowired
	public TradingStratergyController(TradingStratergyService tradingStratergyService) {
		super();
		this.tradingStratergyService = tradingStratergyService;
	}



	@GetMapping
	public void executeStrategy(@RequestParam String startDateStr, 
			@RequestParam String buyDateStr, 
			@RequestParam String closeDateStr,
			@RequestParam int percentageCutOff) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss z");
		Date startDate = sdf.parse(startDateStr+ " 23:00:00 IST");
		Date buyDate = sdf.parse(buyDateStr+" 23:00:00 IST");
		Date closeDate = sdf.parse(closeDateStr+" 23:00:00 IST");
		tradingStratergyService.executeStrategy(startDate, buyDate,closeDate, percentageCutOff);
	}

}
