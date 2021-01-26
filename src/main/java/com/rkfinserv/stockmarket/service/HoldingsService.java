package com.rkfinserv.stockmarket.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rkfinserv.stockmarket.model.Holding;
import com.rkfinserv.stockmarket.repositories.HoldingsRepository;

@Service
public class HoldingsService {
	
	@Autowired
	private final HoldingsRepository holdingsRepository;

	public HoldingsService(HoldingsRepository holdingsRepository) {
		this.holdingsRepository = holdingsRepository;
	}
	
	public void enterHolding(Holding holding) {
		holdingsRepository.save(holding);
	}
	
	public Collection<Holding> getAllHolding(){
		return holdingsRepository.findAll();
	}
	
	public Optional<Holding> findHoldingById(String symbol){
		return holdingsRepository.findById(symbol);
	}
	
	public void deleteHoldingById(String symbol) {
		holdingsRepository.deleteById(symbol);
	}
	
	public void updateHolding(Holding holding) {
		holdingsRepository.save(holding);
	}
	
	public void deleteAllHoldings() {
		holdingsRepository.deleteAll();
	}
}
