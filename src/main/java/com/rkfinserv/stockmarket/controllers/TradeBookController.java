package com.rkfinserv.stockmarket.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.dto.TradeDto;
import com.rkfinserv.stockmarket.model.Trade;
import com.rkfinserv.stockmarket.model.TradeType;
import com.rkfinserv.stockmarket.service.TradeBookService;

@RestController
@RequestMapping("/tradebook")
public class TradeBookController {

	private final Logger LOG = LoggerFactory.getLogger(getClass().getName());
	
	@Autowired
	private TradeBookService tradeBookService;

	public TradeBookController(TradeBookService tradeBookService) {
		super();
		this.tradeBookService = tradeBookService;
	}
	
	@PostMapping(value="trade")
	public TradeDto addTrade(TradeDto tradeDto) {
		Trade trade = asDomain(tradeDto);
		LOG.info("Request recived for add trade {}", tradeDto);
		trade = tradeBookService.addTrade(trade);
		return asDto(trade);
	}

	private TradeDto asDto(Trade trade) {
		return TradeDto.builder()
		.id(trade.getId())
		.symbol(trade.getSymbol())
		.tradeType(trade.getTradeType().name())
		.avgPrice(trade.getAvgPrice())
		.quantity(trade.getQuantity())
		.tradeDate(trade.getTradeDate())
		.build();
	}
	
	private Trade asDomain(TradeDto tradeDto) {
		return Trade.builder()
		.id(tradeDto.getId())
		.symbol(tradeDto.getSymbol())
		.tradeType(TradeType.valueOf(tradeDto.getTradeType()))
		.avgPrice(tradeDto.getAvgPrice())
		.quantity(tradeDto.getQuantity())
		.tradeDate(tradeDto.getTradeDate())
		.build();
	}
	
}
