package com.rkfinserv.stockmarket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.model.BavCopyAudit;
import com.rkfinserv.stockmarket.repositories.BavCopyAuditRepository;
import com.rkfinserv.stockmarket.repositories.BavCopyRepository;
import com.rkfinserv.stockmarket.strategy.BullishEngulfingPattern;
import com.rkfinserv.stockmarket.strategy.TechincalAnalysisPattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TechincalAnalysisService {

	private BavCopyAuditRepository bavCopyAuditRepository;
	private BavCopyRepository bavCopyRepository;
	//private TechincalAnalysisPattern pattern;
	@Autowired
	public TechincalAnalysisService(BavCopyAuditRepository bavCopyAuditRepository,
			BavCopyRepository bavCopyRepository) {
		super();
		this.bavCopyAuditRepository = bavCopyAuditRepository;
		this.bavCopyRepository = bavCopyRepository;
	}

	
	
	public List<BavCopy> performAnalysis(Integer days, Integer patternId) {
		List<BavCopy> result = new ArrayList<BavCopy>();
		List<BavCopy> bavCopies = bavCopyRepository.findAll();
		for(BavCopy bavCopy : bavCopies) {
			boolean failedToQulified = true;
			log.info("*****************executing strategy for {}*****************", bavCopy.getSymbol());
			Pageable pageable =PageRequest.of(0,days,Sort.by(Sort.DEFAULT_DIRECTION.DESC, "timeStamp"));
			List<BavCopyAudit> bavCopyAudits = bavCopyAuditRepository.findBySymbol(bavCopy.getSymbol(), pageable);
			BavCopyAudit firstRecord = bavCopyAudits.remove(0);
			if(firstRecord.getChangePercentage() > 0 && bavCopyAudits.size() == (days -1)) {
				failedToQulified = bavCopyAudits.stream().filter( record -> record.getChangePercentage() > 0).findFirst().isPresent();
				if(! failedToQulified) {
					result.add(bavCopy);
				}
			}			
		}
		log.info("bavCopies size ------------> "+ bavCopies.size());
		log.info("result size ------------> "+ result.size());
		return result;		
	}

	
	


}
