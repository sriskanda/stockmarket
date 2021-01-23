package com.rkfinserv.stockmarket.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rkfinserv.stockmarket.model.BavCopy;

@Repository
public interface BavCopyRepository extends MongoRepository<BavCopy, String> {

}
