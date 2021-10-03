package com.rkfinserv.stockmarket.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.model.BavCopyAudit;
import com.rkfinserv.stockmarket.model.LiveStockPrice;
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
	private final LiveStockPriceService liveStockPriceService;

	// private TechincalAnalysisPattern pattern;
	@Autowired
	public TechincalAnalysisService(BavCopyAuditRepository bavCopyAuditRepository, BavCopyRepository bavCopyRepository,
			WatchService watchService, LiveStockPriceService liveStockPriceService) {
		this.liveStockPriceService = liveStockPriceService;
		this.bavCopyAuditRepository = bavCopyAuditRepository;
		this.bavCopyRepository = bavCopyRepository;
		this.watchService = watchService;
	}

	public List<String> filterBearishStocksChangedDirection(int days, boolean watchListOnly, boolean includeLivePrice) {
		List<String> result = new ArrayList<String>();
		List<BavCopyAudit> bavCopyAudits = bavCopyAuditRepository.findAll();
		List<Watch> watchLists = new ArrayList<Watch>();
		Map<String, List<BavCopyAudit>> bavCopyMap = new ConcurrentHashMap<String, List<BavCopyAudit>>();
		Map<String,BigDecimal> livePrices = new ConcurrentHashMap<String, BigDecimal>();
		
		if(watchListOnly) {
			watchLists = watchService.getWatchList();
		}
		if(includeLivePrice) {
			livePrices= liveStockPriceService.getLiveStockPrices().stream().collect(Collectors.toMap(LiveStockPrice::getSymbol, LiveStockPrice::getPercentageChage));
		}
		
		for(BavCopyAudit bavCopyAudit : bavCopyAudits) {
			if(bavCopyMap.containsKey(bavCopyAudit.getSymbol())){
				bavCopyMap.get(bavCopyAudit.getSymbol()).add(bavCopyAudit);
			}else {
				List<BavCopyAudit> bavCopyList = new ArrayList<BavCopyAudit>();
				bavCopyList.add(bavCopyAudit);
				bavCopyMap.put(bavCopyAudit.getSymbol(),bavCopyList);
			}
		}
		
		
		if(watchListOnly) {
			watchLists = watchService.getWatchList();
			for(String symbol : bavCopyMap.keySet()) {
				if(!watchLists.contains(new Watch(symbol))) {
					bavCopyMap.remove(symbol);
				}
			}
		}

		log.info("Executing strargey for stocks size={}, sotcks={}", bavCopyMap.keySet().size(), bavCopyMap.keySet());

		for(String symbol : bavCopyMap.keySet()) {
			boolean qualified = true;
			 List<BavCopyAudit> stockHistory = bavCopyMap.get(symbol).stream().sorted((o1,o2) -> o2.getTimeStamp().compareTo(o1.getTimeStamp())).collect(Collectors.toList());
			 Double latestPrice;
			 if(includeLivePrice) {
				 latestPrice = livePrices.get(symbol) == null? 0 : livePrices.get(symbol).doubleValue();
			 }else {
				 latestPrice = stockHistory.remove(0).getChangePercentage(); 
			 }
			 if(latestPrice > 0 && stockHistory.size() > days){ 
				 for(int i=0; i< days-1; i++ ) {
					 if(stockHistory.get(i).getChangePercentage() > 0) {
						 qualified = false;
						 break;
					 }
				 } 
			 }else {
				 qualified = false;
			 }
			 
				/*
				 * if(firstRecord.getChangePercentage() > 0 && stockHistory.size() == (days
				 * -1)){ failedToQulified = stockHistory.stream().filter( record ->
				 * record.getChangePercentage() > 0).findFirst().isPresent(); }
				 */
			 if(qualified) { 
				 result.add(symbol);
			 } 
		}
		

		
		
//		List<String> stocksSymbols  = bavCopyRepository.findAll().parallelStream().map(item -> item.getSymbol()).collect(Collectors.toList());
		
		 
		
		
			
		
		
		/*
		 * stocksSymbols.parallelStream().forEach(stockSymbol -> { boolean
		 * failedToQulified = true;
		 * //log.info("*****************executing strategy for {}*****************",
		 * stockSymbol); Pageable pageable
		 * =PageRequest.of(0,days,Sort.by(Sort.DEFAULT_DIRECTION.DESC, "timeStamp"));
		 * List<BavCopyAudit> bavCopyAudits =
		 * bavCopyAuditRepository.findBySymbol(stockSymbol, pageable); BavCopyAudit
		 * firstRecord = bavCopyAudits.remove(0); if(firstRecord.getChangePercentage() >
		 * 0 && bavCopyAudits.size() == (days -1)) { failedToQulified =
		 * bavCopyAudits.stream().filter( record -> record.getChangePercentage() >
		 * 0).findFirst().isPresent(); if(! failedToQulified) { result.add(stockSymbol);
		 * } }
		 * 
		 * });
		 */
		/*
		 * for(String stockSymbol : stocksSymbols) { boolean failedToQulified = true;
		 * log.info("*****************executing strategy for {}*****************",
		 * stockSymbol); Pageable pageable
		 * =PageRequest.of(0,days,Sort.by(Sort.DEFAULT_DIRECTION.DESC, "timeStamp"));
		 * List<BavCopyAudit> bavCopyAudits =
		 * bavCopyAuditRepository.findBySymbol(stockSymbol, pageable); BavCopyAudit
		 * firstRecord = bavCopyAudits.remove(0); if(firstRecord.getChangePercentage() >
		 * 0 && bavCopyAudits.size() == (days -1)) { failedToQulified =
		 * bavCopyAudits.stream().filter( record -> record.getChangePercentage() >
		 * 0).findFirst().isPresent(); if(! failedToQulified) { result.add(stockSymbol);
		 * } } }
		 */		
		log.info("result size ------------> "+ result.size());
		return result;		
	}

	public List<String> filterStockPricePriceDownFromHigh(Integer days, boolean watchListOnly, Integer percentage) {
		List<String> result = new ArrayList<String>();

		List<BavCopy> bavCopies = bavCopyRepository.findAll();

		if (watchListOnly) {
			List<Watch> watchList = watchService.getWatchList();
			bavCopies = bavCopies.stream().filter(item -> watchList.contains(new Watch(item.getSymbol())))
					.collect(Collectors.toList());
		}

		for (BavCopy bavCopy : bavCopies) {
			log.info("*****************executing strategy for {}*****************", bavCopy.getSymbol());
			Pageable pageable = PageRequest.of(0, days, Sort.by(Sort.DEFAULT_DIRECTION.DESC, "timeStamp"));
			List<BavCopyAudit> bavCopyAudits = bavCopyAuditRepository.findBySymbol(bavCopy.getSymbol(), pageable);
			Double maxPrice = bavCopyAudits.stream().max(Comparator.comparing(BavCopyAudit::getClose)).get().getClose();
			log.info("maxPrice={} currentPrice={} change={}", maxPrice, bavCopy.getClose(),
					(maxPrice - bavCopy.getClose()) / maxPrice * 100);
			if (bavCopy.getClose() < (maxPrice - (maxPrice * percentage / 100))) {
				result.add(bavCopy.getSymbol());
				log.info("matched --> symbol={} ", bavCopy.getSymbol());

			}

		}
		log.info("bavCopies size ------------> " + bavCopies.size());
		log.info("result size ------------> " + result.size());
		return result;
	}

}
