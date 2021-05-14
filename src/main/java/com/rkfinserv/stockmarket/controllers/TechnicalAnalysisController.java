package com.rkfinserv.stockmarket.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.service.TechincalAnalysisService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/techanalysis")
public class TechnicalAnalysisController {

	private TechincalAnalysisService techincalAnalysisService;

	@Autowired
	public TechnicalAnalysisController(TechincalAnalysisService techincalAnalysisService) {
		super();
		this.techincalAnalysisService = techincalAnalysisService;
	}
	
	@GetMapping("/bearishChange")
	public List<String> findBearishStocksChangedDirection(@RequestParam Integer days, boolean isWatchListOnly) {
		log.info("Request received days={} isWatchListOnly={}", days, isWatchListOnly);
		return techincalAnalysisService.filterBearishStocksChangedDirection(days, isWatchListOnly);
	}
	
	@GetMapping("/downFromMax")
	public List<String> findStockPricePriceDownFromHigh(@RequestParam Integer days, boolean isWatchListOnly, Integer changePercentage) {
		log.info("Request received days={} isWatchListOnly={}, changePercentage={}", days, isWatchListOnly, changePercentage);		
		return techincalAnalysisService.filterStockPricePriceDownFromHigh(days, isWatchListOnly,changePercentage );
	}
	
	
}
