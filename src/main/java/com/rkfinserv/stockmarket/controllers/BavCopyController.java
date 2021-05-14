package com.rkfinserv.stockmarket.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.dto.BavCopyAuditDto;
import com.rkfinserv.stockmarket.dto.BavCopyDto;
import com.rkfinserv.stockmarket.dto.StockChartDataDto;
import com.rkfinserv.stockmarket.exception.BavCopyException;
import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.model.BavCopyAudit;
import com.rkfinserv.stockmarket.model.BavCopyResult;
import com.rkfinserv.stockmarket.service.BavCopyService;

@RestController
@RequestMapping(value = "/bavcopy")
public class BavCopyController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private final BavCopyService bavCopyService;

	public BavCopyController(BavCopyService bavCopyService) {
		super();
		this.bavCopyService = bavCopyService;
	}
	
	@GetMapping
	public List<BavCopyDto> getStockPrice() {
		List<BavCopyDto> bavCopiesDto = new ArrayList<BavCopyDto>();
		List<BavCopy> bavCopies = bavCopyService.getBavCopy();
		for(BavCopy bavCopy : bavCopies) {
			bavCopiesDto.add(bavCopy.asDto());
		}
		return bavCopiesDto;
	}

	
	@GetMapping("/{symbol}")
	public List<BavCopyDto> getStockPrice(@PathVariable String symbol) {
		List<BavCopyDto> bavCopiesDto = new ArrayList<BavCopyDto>();
		List<BavCopy> bavCopies = bavCopyService.getBavCopy(symbol);
		for(BavCopy bavCopy : bavCopies) {
			bavCopiesDto.add(bavCopy.asDto());
		}
		return bavCopiesDto;
	}
	
	
	@GetMapping("/chart/{symbol}")
	public StockChartDataDto getStockPriceChartData(@PathVariable String symbol) {
		List<BavCopyAudit> bavCopies = bavCopyService.getBavCopyAudit(symbol);
		List<BavCopyAudit> sortedBavCopies = bavCopies.stream().sorted(Comparator.comparing(BavCopyAudit::getTimeStamp)).collect(Collectors.toList());
		String[] dates = 
				sortedBavCopies.stream()
			              .map(BavCopyAudit::getDateText)
			              .collect(Collectors.toList()).toArray(new String[bavCopies.size()]);
		Double[] prices = 
				sortedBavCopies.stream()
			              .map(BavCopyAudit::getClose)
			              .collect(Collectors.toList()).toArray(new Double[bavCopies.size()]);
		StockChartDataDto stockChartDataDto = StockChartDataDto.builder().dates(dates).prices(prices).build();
		return stockChartDataDto;
	}
	
	@GetMapping("history/{symbol}")
	public List<BavCopyAuditDto> getStockPriceHistory(@PathVariable String symbol) {
		List<BavCopyAuditDto> bavCopiesAuditDto = new ArrayList<BavCopyAuditDto>();
		List<BavCopyAudit> bavCopyAudits = bavCopyService.getBavCopyAudit(symbol);
		for(BavCopyAudit bavCopyAudit : bavCopyAudits) {
			bavCopiesAuditDto.add(bavCopyAudit.asDto(bavCopyAudit));
		}
		return bavCopiesAuditDto;
	}

	
	@GetMapping(value = "/load/{date}")
	public BavCopyResult loadBavCopy(@PathVariable String date, @RequestParam Boolean isLatest) {
		LOG.info("request received to load the bavcopy");
		System.out.println("request received to load the bavcopy");
		try {
			bavCopyService.loadBavCopy(date, isLatest);
			return BavCopyResult.SUCCESS;
		} catch (BavCopyException e) {
			LOG.error("Failed to load the bavcopy", e);
			return e.getBavCopyResult();
		}
	}
	
	public List<BavCopy> getStockHistory(String symbol, String date){
		LOG.info("request received to get history for {} from {}");
		
		
		return null;
	}
	
	
	
}
