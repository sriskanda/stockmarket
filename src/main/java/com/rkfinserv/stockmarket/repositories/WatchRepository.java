package com.rkfinserv.stockmarket.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rkfinserv.stockmarket.model.Watch;

@Repository
public interface WatchRepository extends MongoRepository<Watch, String>{

}
