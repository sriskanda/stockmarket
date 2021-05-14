package com.rkfinserv.stockmarket.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.model.BavCopyAudit;
import com.rkfinserv.stockmarket.model.Watch;
import com.rkfinserv.stockmarket.repositories.BavCopyAuditRepository;
import com.rkfinserv.stockmarket.repositories.BavCopyRepository;
import com.rkfinserv.stockmarket.strategy.BullishEngulfingPattern;
import com.rkfinserv.stockmarket.strategy.TechincalAnalysisPattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TechincalAnalysisService {

	private final BavCopyAuditRepository bavCopyAuditRepository;
	private final BavCopyRepository bavCopyRepository;
	private final WatchService watchService;
	//private TechincalAnalysisPattern pattern;
	@Autowired
	public TechincalAnalysisService(BavCopyAuditRepository bavCopyAuditRepository, BavCopyRepository bavCopyRepository,
			WatchService watchService) {
		super();
		this.bavCopyAuditRepository = bavCopyAuditRepository;
		this.bavCopyRepository = bavCopyRepository;
		this.watchService = watchService;
	}

	
	
	public List<String> filterBearishStocksChangedDirection(Integer days, boolean watchListOnly) {
		List<String> result = new ArrayList<String>();
		List<String> stocksSymbols = new ArrayList<String>();
		
		if(watchListOnly) {
			watchService.getWatchList().forEach(item -> stocksSymbols.add(item.getSymbol()));	
		}else {
			bavCopyRepository.findAll().forEach(item -> stocksSymbols.add(item.getSymbol()));
		}
		
		for(String stockSymbol : stocksSymbols) {
			boolean failedToQulified = true;
			log.info("*****************executing strategy for {}*****************", stockSymbol);
			Pageable pageable =PageRequest.of(0,days,Sort.by(Sort.DEFAULT_DIRECTION.DESC, "timeStamp"));
			List<BavCopyAudit> bavCopyAudits = bavCopyAuditRepository.findBySymbol(stockSymbol, pageable);
			BavCopyAudit firstRecord = bavCopyAudits.remove(0);
			if(firstRecord.getChangePercentage() > 0 && bavCopyAudits.size() == (days -1)) {
				failedToQulified = bavCopyAudits.stream().filter( record -> record.getChangePercentage() > 0).findFirst().isPresent();
				if(! failedToQulified) {
					result.add(stockSymbol);
				}
			}			
		}
		log.info("bavCopies size ------------> "+ stocksSymbols.size());
		log.info("result size ------------> "+ result.size());
		return result;		
	}

	
	public List<String> filterStockPricePriceDownFromHigh(Integer days, boolean watchListOnly, Integer percentage) {
		List<String> result = new ArrayList<String>();
		
		List<BavCopy> bavCopies = bavCopyRepository.findAll();
		
		if(watchListOnly) {
			List<Watch> watchList = watchService.getWatchList();
			bavCopies = bavCopies.stream().filter(item -> watchList.contains(new Watch(item.getSymbol()))).collect(Collectors.toList());	
		}
		
		for(BavCopy bavCopy : bavCopies) {
			log.info("*****************executing strategy for {}*****************", bavCopy.getSymbol());
			Pageable pageable =PageRequest.of(0,days,Sort.by(Sort.DEFAULT_DIRECTION.DESC, "timeStamp"));
			List<BavCopyAudit> bavCopyAudits = bavCopyAuditRepository.findBySymbol(bavCopy.getSymbol(), pageable);
			Double maxPrice = bavCopyAudits.stream().max(Comparator.comparing(BavCopyAudit::getClose)).get().getClose();
			log.info("maxPrice={} currentPrice={} change={}", maxPrice, bavCopy.getClose(), (maxPrice - bavCopy.getClose())/maxPrice *100 );	
				if(bavCopy.getClose() < (maxPrice- (maxPrice * percentage/100))) {
					result.add(bavCopy.getSymbol());
					log.info("matched --> symbol={} ",  bavCopy.getSymbol());	
					
				}
				
		}
		log.info("bavCopies size ------------> "+ bavCopies.size());
		log.info("result size ------------> "+ result.size());
		return result;		
	}



}
