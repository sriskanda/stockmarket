package com.rkfinserv.stockmarket.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

import com.rkfinserv.stockmarket.model.Trade;

public interface TradeBookRepository extends MongoRepository<Trade, String>{

}
