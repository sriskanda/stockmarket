package com.rkfinserv.stockmarket.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.dto.StockDataDto;
import com.rkfinserv.stockmarket.dto.StockDetailsDto;
import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.model.StockDetails;
import com.rkfinserv.stockmarket.model.Watch;
import com.rkfinserv.stockmarket.service.BavCopyService;
import com.rkfinserv.stockmarket.service.StockDetailsService;
import com.rkfinserv.stockmarket.service.WatchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/stockdetails")
public class StocksDetailsController {
	
	private final StockDetailsService stockDetailsService;
	private final BavCopyService bavCopyService;
	private final WatchService watchService;
	
	@Autowired
	public StocksDetailsController(StockDetailsService stockDetailsService, BavCopyService bavCopyService,
			WatchService watchService) {
		super();
		this.stockDetailsService = stockDetailsService;
		this.bavCopyService = bavCopyService;
		this.watchService = watchService;
	}

	@GetMapping(value = "/{symbol}")
	public List<StockDetailsDto> getStockDetails(@PathVariable String symbol) {
		StockDetails stockDetails = stockDetailsService.getStockDetails(symbol);
		List<StockDetailsDto> stockDetailsListDto = List.of(stockDetails.asDto());
		return stockDetailsListDto ;
	}
	
	@GetMapping()
	public List<StockDataDto> getAllStockDetails() {
		List<StockDataDto> stockDetailsListDto = new ArrayList<StockDataDto>();
		List<StockDetails> stockDetailsList = stockDetailsService.getAllStockDetails();
		List<BavCopy> bavCopies = bavCopyService.getBavCopy();
		Set<Watch> watchSet = new HashSet<Watch>(watchService.getWatchList());
		log.info("stockDetailsList size={}",stockDetailsList.size());
		for(BavCopy bavCopy : bavCopies) {
			StockDetails stockDetails = stockDetailsService.getStockDetails(bavCopy.getSymbol());
			StockDetailsDto stockDetailsDto= stockDetails !=null ? stockDetails.asDto() : StockDetailsDto.emptyData(bavCopy.getSymbol());
			stockDetailsDto.setAddedToWatch(watchSet.contains(Watch.builder().symbol(bavCopy.getSymbol()).build()));
			StockDataDto stockDataDto = StockDataDto.builder().stockDetails(stockDetailsDto).bavCopy(bavCopy.asDto()).build();
			stockDetailsListDto.add(stockDataDto);
		}
		return stockDetailsListDto;
	}
	
}
