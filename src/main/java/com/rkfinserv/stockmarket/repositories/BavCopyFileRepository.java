package com.rkfinserv.stockmarket.repositories;

import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.model.BavCopyFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BavCopyFileRepository extends MongoRepository<BavCopyFile, String> {

}
