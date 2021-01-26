package com.rkfinserv.stockmarket.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rkfinserv.stockmarket.model.Holding;

@Repository
public interface HoldingsRepository extends MongoRepository<Holding, String>{

}
