package com.rkfinserv.stockmarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rkfinserv.stockmarket.model.Trade;
import com.rkfinserv.stockmarket.repositories.TradeBookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TradeBookService {
	
	@Autowired
	private TradeBookRepository tradeBookRepository;

	public TradeBookService(TradeBookRepository tradeBookRepository) {
		super();
		this.tradeBookRepository = tradeBookRepository;
	}
	
	public Trade addTrade(Trade trade) {
		log.info("inserting trade {}", trade);
		trade = tradeBookRepository.insert(trade);
		log.info("inserted the trade {}", trade);
		return trade;
	}
	
	public List<Trade> getAllTrades() {
		return tradeBookRepository.findAll(Sort.by(Sort.Direction.DESC, "tradeDate"));
	}
	
	public List<Trade> getAllTrades(String symbol) {
		return tradeBookRepository.findAllBySymbol(symbol, Sort.by(Sort.Direction.DESC, "tradeDate"));
	}
	
	public Trade updateTrade(Trade trade) {
		return tradeBookRepository.save(trade);
	}
	
	public void deleteTradeById(String id) {
		tradeBookRepository.deleteById(id);
	}
}
