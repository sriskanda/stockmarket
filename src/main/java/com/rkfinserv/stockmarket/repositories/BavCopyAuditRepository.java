package com.rkfinserv.stockmarket.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.rkfinserv.stockmarket.model.BavCopyAudit;

@Repository
public interface BavCopyAuditRepository extends MongoRepository<BavCopyAudit, String> {
	
	@Query("{'symbol':?0}")
	public List<BavCopyAudit> findBySymbol(String symbol, Sort sort);
	
	@Query("{'symbol':?0}")
	public List<BavCopyAudit> findBySymbol(String symbol, Pageable pageable);

}
