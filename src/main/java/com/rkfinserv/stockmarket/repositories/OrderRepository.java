package com.rkfinserv.stockmarket.repositories;

import com.rkfinserv.stockmarket.model.position.OrderEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<OrderEntry, String>{

}
