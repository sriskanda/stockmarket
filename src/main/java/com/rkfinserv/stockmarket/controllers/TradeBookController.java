package com.rkfinserv.stockmarket.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Multiset.Entry;
import com.rkfinserv.stockmarket.dto.TradeDto;
import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.model.Trade;
import com.rkfinserv.stockmarket.model.TradeType;
import com.rkfinserv.stockmarket.service.BavCopyService;
import com.rkfinserv.stockmarket.service.TradeBookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tradebook")
public class TradeBookController {

	private final Logger LOG = LoggerFactory.getLogger(getClass().getName());
	
	@Autowired
	private TradeBookService tradeBookService;
	@Autowired
	private BavCopyService bavCopyService;
	

	public TradeBookController(TradeBookService tradeBookService) {
		super();
		this.tradeBookService = tradeBookService;
	}
	
	@GetMapping
	public List<TradeDto> getTrades() {
		LOG.info("Request recived for get all trades");
		List<Trade> trades = tradeBookService.getAllTrades();
		List<TradeDto> tradeDtos = aggregateLatestTradePriceData(trades);
		return tradeDtos;
	}
	

	@GetMapping(value = "/{symbol}")
	public List<TradeDto> getTrades(@PathVariable String symbol) {
		
		LOG.info("Request recived for get all trades symbol={}", symbol);
		List<Trade> trades = tradeBookService.getAllTrades(symbol);
		List<TradeDto> tradeDtos = aggregateLatestTradePriceData(trades);
		return tradeDtos;
	}

	private List<TradeDto> aggregateLatestTradePriceData(List<Trade> trades) {
		List<TradeDto> tradeDtos = new ArrayList<TradeDto>();
		List<BavCopy> bavCopyEntries = bavCopyService.getBavCopy();
		trades.forEach(trade -> {
			Optional<BavCopy> opitional = bavCopyEntries.stream().filter(entry-> entry.getSymbol().equalsIgnoreCase(trade.getSymbol())).findFirst();
			if(opitional.isPresent()) {
				BavCopy bavCopy = opitional.get();
				trade.setLatestPrice(bavCopy.getClose());
				trade.setChangePercentage();
				trade.setLastTradedDate(bavCopy.getTimeStamp());
			}
			tradeDtos.add(asDto(trade));
		});
		return tradeDtos;
	}
	
	
	
	@PostMapping
	public TradeDto addTrade(@RequestBody TradeDto tradeDto) {
		log.info("method=addTrade {}",tradeDto);
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
		.targetPrice(trade.getTargetPrice())
		.quantity(trade.getQuantity())
		.tradeDate(trade.getTradeDate())
		.latestPrice(trade.getLatestPrice())
		.changePercentage(trade.getChangePercentage())
		.lastTradedDate(trade.getLastTradedDate())
		.active(trade.getActive())
		.build();
	}
	
	private Trade asDomain(TradeDto tradeDto) {
		return Trade.builder()
		.id(tradeDto.getId())
		.symbol(tradeDto.getSymbol())
		.tradeType(TradeType.valueOf(tradeDto.getTradeType()))
		.avgPrice(tradeDto.getAvgPrice())
		.targetPrice(tradeDto.getTargetPrice())
		.quantity(tradeDto.getQuantity())
		.tradeDate(tradeDto.getTradeDate())
		.active(tradeDto.getActive())
		.build();
	}
	
}
