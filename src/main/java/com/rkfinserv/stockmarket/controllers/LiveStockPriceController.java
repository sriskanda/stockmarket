package com.rkfinserv.stockmarket.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.dto.LiveStockPriceDto;
import com.rkfinserv.stockmarket.model.LiveStockPrice;
import com.rkfinserv.stockmarket.service.LiveStockPriceService;

@RestController
@RequestMapping("liveStockPrice")
public class LiveStockPriceController {
	
	private final LiveStockPriceService liveStockPriceService;

	@Autowired
	public LiveStockPriceController(LiveStockPriceService liveStockPriceService) {
		super();
		this.liveStockPriceService = liveStockPriceService;
	}
	
	@GetMapping
	public List<LiveStockPriceDto> getLiveStockPrices(){
		List<LiveStockPriceDto> liveStockPriceDtos = new ArrayList<LiveStockPriceDto>();
		List<LiveStockPrice> liveStockPrices = liveStockPriceService.getLiveStockPrices();
		liveStockPrices.forEach(liveStockPrice -> liveStockPriceDtos.add(liveStockPrice.asDto()));
		return liveStockPriceDtos;
	}
	
	
	
}
