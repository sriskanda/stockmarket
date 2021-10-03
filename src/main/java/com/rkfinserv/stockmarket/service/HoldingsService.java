package com.rkfinserv.stockmarket.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.CloseableThreadContext.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rkfinserv.stockmarket.model.Action;
import com.rkfinserv.stockmarket.model.Alert;
import com.rkfinserv.stockmarket.model.Holding;
import com.rkfinserv.stockmarket.model.Trade;
import com.rkfinserv.stockmarket.model.TradeType;
import com.rkfinserv.stockmarket.repositories.HoldingsRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class HoldingsService {
	
	@Autowired
	private final HoldingsRepository holdingsRepository;
	private final TradeBookService tradeBookService;
	private final AlertService alertService;
	
	@Autowired
	public HoldingsService(HoldingsRepository holdingsRepository, TradeBookService tradeBookService,
			AlertService alertService) {
		super();
		this.holdingsRepository = holdingsRepository;
		this.tradeBookService = tradeBookService;
		this.alertService = alertService;
	}

	public void enterHolding(Holding holding) {
		log.info("adding holding for symbol={}", holding.getSymbol());
		holdingsRepository.save(holding);

		//TO-DO : implement below by spring events
		log.info("adding Trade entry for symbol={}", holding.getSymbol());
		Trade trade = Trade.builder()
		.symbol(holding.getSymbol())
		.avgPrice(holding.getAvgPrice())
		.quantity(holding.getQuantity())
		.tradeType(TradeType.BUY)
		.tradeDate(new Date())
		.build();
		tradeBookService.addTrade(trade);
		
		/*
		 * // add 10% down strategy alerts
		 * log.info("adding 10% alerts for for symbol={}", holding.getSymbol()); for(int
		 * i=10; i<=50; i+=10) { Alert alert = Alert.builder()
		 * .stockId(holding.getSymbol())
		 * .snapshotPrice(BigDecimal.valueOf(holding.getAvgPrice()))
		 * .triggerPrice(BigDecimal.valueOf(holding.getAvgPrice() -
		 * (holding.getAvgPrice() * i/100))) .action(Action.BUY) .createdDate(new
		 * Date()) .comment("Auto 10% down strategy") .build();
		 * alertService.upsert(alert); }
		 */
	}
	
	public List<Holding> getAllHolding(){
		return holdingsRepository.findAll();
	}
	
	public Optional<Holding> findHoldingById(String symbol){
		return holdingsRepository.findById(symbol);
	}
	
	public void deleteHoldingById(String symbol) {
		Holding holding = holdingsRepository.findById(symbol).get();
		log.info("adding holding for symbol={}", holding.getSymbol());
		holdingsRepository.deleteById(symbol);
		
		//TO-DO : implement below by spring events
		//add trade entry
		log.info("adding Trade entry for symbol={}", holding.getSymbol());
		Trade trade = Trade.builder()
				.symbol(holding.getSymbol())
				.avgPrice(holding.getAvgPrice())
				.quantity(holding.getQuantity())
				.tradeType(TradeType.SELL)
				.tradeDate(new Date())
				.build();
				tradeBookService.addTrade(trade);
		
					
		
	}
	
	public void updateHolding(Holding holding) {
		holdingsRepository.save(holding);
	}
	
	public void deleteAllHoldings() {
		holdingsRepository.deleteAll();
	}
}
