package com.rkfinserv.stockmarket.service.technicalpatterns;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rkfinserv.stockmarket.model.BavCopyAudit;
import com.rkfinserv.stockmarket.patterns.model.CandleData;
import com.rkfinserv.stockmarket.service.BavCopyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SingleCandlePatternService {
	
	
	
	private final BavCopyService bavCopyService;

	@Autowired
	public SingleCandlePatternService(BavCopyService bavCopyService) {
		super();
		this.bavCopyService = bavCopyService;
	}
	
	private List<CandleData> getCandleDataList(Date timeStamp){
		List<CandleData> candleDataList = new ArrayList<CandleData>();
		List<BavCopyAudit> bavCopyAudits = bavCopyService.getBavCopy(timeStamp);
		
		for(BavCopyAudit item : bavCopyAudits) {
			CandleData candleData = CandleData.builder()
					.symbol(item.getSymbol())
					.open(BigDecimal.valueOf(item.getOpen()))
					.close(BigDecimal.valueOf(item.getClose()))
					.high(BigDecimal.valueOf(item.getHigh()))
					.low(BigDecimal.valueOf(item.getLow()))
					.build();
			candleDataList.add(candleData);
		}
		return candleDataList;
	}
	
	public List<String> getBullishMarubozuStocks(Date timeStamp){
		final double allowedVariance = 0.2;
		final double minRange = 5;
		final double maxRange = 10;
		List<String> matchingStocks = new ArrayList<String>();
		for(CandleData candleData : getCandleDataList(timeStamp)) {
			if(candleData.isBullishMarubozu(allowedVariance, minRange, maxRange)) {
				matchingStocks.add(candleData.getSymbol());
				log.info("matched candle={}",candleData);
			}
		}
		log.info("getBullishMarubozuStocks size={} date={}", matchingStocks.size());
		return matchingStocks;
	}
	

	public List<String> getBearishMarubozuStocks(Date timeStamp){
		final double allowedVariance = 0.2;
		final double minRange = 5;
		final double maxRange = 10;
		List<String> matchingStocks = new ArrayList<String>();
		for(CandleData candleData : getCandleDataList(timeStamp)) {
			if(candleData.isBearishMarubozu(allowedVariance, minRange, maxRange)) {
				matchingStocks.add(candleData.getSymbol());
				log.info("matched candle={}",candleData);
			}
		}
		log.info("getBearishMarubozuStocks size={} ", matchingStocks.size());
		return matchingStocks;
	}


	public List<String> getSprinningTopStocks(Date timeStamp){
		final double BODY_LANGTH_VARIANCE = 0.2;
		final double SHADOW_LENGTH_VARIANCE = 0.2;
		List<String> matchingStocks = new ArrayList<String>();
		for(CandleData candleData : getCandleDataList(timeStamp)) {
//			log.info("testing candle={}",candleData);
			if(candleData.isSpinningTop(BODY_LANGTH_VARIANCE, SHADOW_LENGTH_VARIANCE)) {
				matchingStocks.add(candleData.getSymbol());
				log.info("matched candle={}",candleData);
			}
		}
		log.info("getSpinningTopStocks size={}", matchingStocks.size());
		return matchingStocks;
	}
	
	public List<String> getPaperUmbrellaStocks(Date timeStamp){
		final double BODY_LANGTH_VARIANCE = 0.2;
		final double SHADOW_LENGTH_VARIANCE = 0.2;
		List<String> matchingStocks = new ArrayList<String>();
		for(CandleData candleData : getCandleDataList(timeStamp)) {
//			log.info("testing candle={}",candleData);
			if(candleData.isPaperUmbrella(BODY_LANGTH_VARIANCE, SHADOW_LENGTH_VARIANCE)) {
				matchingStocks.add(candleData.getSymbol());
				log.info("matched candle={}",candleData);
			}
		}
		log.info("getPaperUmbrellaStocksStocks size={} ", matchingStocks.size());
		return matchingStocks;
	}
	
	public List<String> getShootingStarStocks(Date timeStamp){
		final double BODY_LANGTH_VARIANCE = 0.2;
		final double SHADOW_LENGTH_VARIANCE = 0.2;
		List<String> matchingStocks = new ArrayList<String>();
		for(CandleData candleData : getCandleDataList(timeStamp)) {
//			log.info("testing candle={}",candleData);
			if(candleData.isShootingStar(BODY_LANGTH_VARIANCE, SHADOW_LENGTH_VARIANCE)) {
				matchingStocks.add(candleData.getSymbol());
				log.info("matched candle={}",candleData);
			}
		}
		log.info("getShootingStarStocks size={} ", matchingStocks.size());
		return matchingStocks;
	}

	
}
