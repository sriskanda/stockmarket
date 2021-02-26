package com.rkfinserv.stockmarket.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rkfinserv.stockmarket.model.StockDetails;

@Repository
public interface StockDetailsRepository extends MongoRepository<StockDetails, String>{

}
