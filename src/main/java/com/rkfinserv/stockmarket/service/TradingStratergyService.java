package com.rkfinserv.stockmarket.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rkfinserv.stockmarket.model.BavCopyAudit;
import com.rkfinserv.stockmarket.model.StockTradeData;
import com.rkfinserv.stockmarket.repositories.BavCopyAuditRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class TradingStratergyService {

	private final BavCopyAuditRepository bavCopyAuditRepository;

	@Autowired
	public TradingStratergyService(BavCopyAuditRepository bavCopyAuditRepository) {
		super();
		this.bavCopyAuditRepository = bavCopyAuditRepository;
	}
	
	public void executeStrategy(Date startDate, Date buyDate, Date closeDate, int percentageCutoff) throws Exception{
		
		
		List<BavCopyAudit> startDayData = bavCopyAuditRepository.findByTimeStamp(startDate);
		
		List<BavCopyAudit> shortListedStartDayData= startDayData.stream().filter(item -> item.getClose() > 1 && item.getChangePercentage() >= Double.valueOf(percentageCutoff)).collect(Collectors.toList());
		Set<String> shortListedSymbols = shortListedStartDayData.stream().map(item -> item.getSymbol()).collect(Collectors.toSet());
		
		log.info("Shortlisted Stocks size : {}",shortListedStartDayData.size());
		
		
		List<BavCopyAudit> buyDayData = bavCopyAuditRepository.findByTimeStamp(buyDate);
		List<BavCopyAudit> shortListedBuyDayData = buyDayData.stream().filter(item -> shortListedSymbols.contains(item.getSymbol())).collect(Collectors.toList());
		
		
		List<BavCopyAudit> closeDayData = bavCopyAuditRepository.findByTimeStamp(closeDate);
		List<BavCopyAudit> shortListedCloseDayData = closeDayData.stream().filter(item -> shortListedSymbols.contains(item.getSymbol())).collect(Collectors.toList());

		
		Map<String, BavCopyAudit> shortListedCloseDayDataMap = new ConcurrentHashMap<String, BavCopyAudit>();
		shortListedCloseDayData.forEach(item ->shortListedCloseDayDataMap.put(item.getSymbol(), item));

		int investingAmountPerStock = 500000/shortListedStartDayData.size();
		
		List<StockTradeData> stockTradeDataList = new ArrayList<StockTradeData>();
		
		shortListedBuyDayData.forEach(item ->{
			log.info("preparing StockTradeData for symbol={}", item.getSymbol());
			BavCopyAudit sellBavCopy = shortListedCloseDayDataMap.get(item.getSymbol());
			StockTradeData stockTradeData = StockTradeData.builder()
			.symbol(item.getSymbol())
			.buyPrice(item.getOpen())
			.investedAmount(investingAmountPerStock)
			.sellPrice(sellBavCopy.getOpen())
			.build();
			stockTradeData.popluatePnlData();
			stockTradeDataList.add(stockTradeData);
		});
		
		
		
		stockTradeDataList.forEach(item -> log.info(item.toString()));
		
		double totalInvestedAmount = stockTradeDataList.stream().mapToDouble(item -> item.getInvestedAmount()).sum();
		double totalMaturedAmount = stockTradeDataList.stream().mapToDouble(item -> item.getMaturityAmount()).sum();
		double pnl = totalMaturedAmount - totalInvestedAmount;
		double pnlPercentage = pnl /totalInvestedAmount;
		
		log.info("investedAmount : {}", totalInvestedAmount);
		log.info("totalMaturedAmount Amount : {}", totalMaturedAmount);
		log.info("pnl  : {}", pnl);
		log.info("pnlPercentage : {}", pnlPercentage);
		
		

		
	}

	
}
