package com.rkfinserv.stockmarket.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.rkfinserv.stockmarket.model.Trade;

public interface TradeBookRepository extends MongoRepository<Trade, String>{
	@Query("{'symbol':?0}")
	public List<Trade> findAllBySymbol(String symbol, Sort sort);
}
