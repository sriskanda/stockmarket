package com.rkfinserv.stockmarket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rkfinserv.stockmarket.model.StockDetails;
import com.rkfinserv.stockmarket.repositories.StockDetailsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockDetailsService {

	private StockDetailsRepository stockDetailsRepository;
	
	
	@Autowired
	public StockDetailsService(StockDetailsRepository stockDetailsRepository) {
		super();
		this.stockDetailsRepository = stockDetailsRepository;
	}



	public StockDetails getStockDetails(String symbol) {
		 Optional<StockDetails> stockDetailsOptional = stockDetailsRepository.findById(symbol);
		 if(stockDetailsOptional.isPresent()) {
			 return stockDetailsOptional.get();
		 }else {
			 //TODO: throw custom exception and return 404 as response
			 log.warn("Stock details doesn't exists with symbol {}",symbol );
			 return null;
		 }
	}



	public List<StockDetails> getAllStockDetails() {
		return stockDetailsRepository.findAll();
	}
}
