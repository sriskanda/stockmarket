	package com.rkfinserv.stockmarket.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.dto.HoldingDto;
import com.rkfinserv.stockmarket.model.Holding;
import com.rkfinserv.stockmarket.service.HoldingsService;

@RestController
@RequestMapping(value = "/holdings")
public class HoldingsController {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private final HoldingsService holdingsService;
	
	public HoldingsController(HoldingsService holdingsService) {
		this.holdingsService = holdingsService;
	}
	
	@PostMapping(value = "/create")
	public String enterHolding(@RequestBody HoldingDto holdingDto) throws Exception {
		Holding holding = asDomain(holdingDto);
		holdingsService.enterHolding(holding);
		return "SUCCESS";
	}
	
	@GetMapping(value = "")
	public String test() {
		return "Holding";
	}
	
	private Holding asDomain(HoldingDto holdingDto) throws Exception {
			return Holding.builder()
			.symbol(holdingDto.getSymbol())
			.quantity(Long.valueOf(holdingDto.getQuantity()))
			.avgPrice(Double.valueOf(holdingDto.getAvgPrice()))
			.build();
	}
	
}
