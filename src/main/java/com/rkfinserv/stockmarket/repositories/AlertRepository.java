package com.rkfinserv.stockmarket.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rkfinserv.stockmarket.model.Alert;
@Repository
public interface AlertRepository extends MongoRepository<Alert, String> {

}
