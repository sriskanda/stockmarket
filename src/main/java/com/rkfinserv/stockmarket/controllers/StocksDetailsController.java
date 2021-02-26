package com.rkfinserv.stockmarket.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.dto.StockDetailsDto;
import com.rkfinserv.stockmarket.model.StockDetails;
import com.rkfinserv.stockmarket.service.StockDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/stockdetails")
public class StocksDetailsController {
	
	private StockDetailsService stockDetailsService;

	@Autowired
	public StocksDetailsController(StockDetailsService stockDetailsService) {
		super();
		this.stockDetailsService = stockDetailsService;
	}


	@GetMapping(value = "/{symbol}")
	public List<StockDetailsDto> getStockDetails(@PathVariable String symbol) {
		StockDetails stockDetails = stockDetailsService.getStockDetails(symbol);
		List<StockDetailsDto> stockDetailsListDto = List.of(stockDetails.asDto());
		return stockDetailsListDto ;
	}
	
	@GetMapping()
	public List<StockDetailsDto> getAllStockDetails() {
		List<StockDetailsDto> stockDetailsListDto = new ArrayList<StockDetailsDto>();
		List<StockDetails> stockDetailsList = stockDetailsService.getAllStockDetails();
		log.info("stockDetailsList size={}",stockDetailsList.size());
		for(StockDetails stockDetails : stockDetailsList) {
			stockDetailsListDto.add(stockDetails.asDto());
		}
		return stockDetailsListDto;
	}
	
}
