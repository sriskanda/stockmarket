	package com.rkfinserv.stockmarket.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.dto.HoldingDataDto;
import com.rkfinserv.stockmarket.dto.HoldingDto;
import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.model.Holding;
import com.rkfinserv.stockmarket.service.BavCopyService;
import com.rkfinserv.stockmarket.service.HoldingsService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping(value = "/holdings")
public class HoldingsController {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private HoldingsService holdingsService;
	@Autowired
	private BavCopyService bavCopyService;
	
	@PostMapping(value = "/create")
	public String enterHolding(@RequestBody HoldingDto holdingDto) throws Exception {
		Holding holding = asDomain(holdingDto);
		holdingsService.enterHolding(holding);
		log.info("Added Holding symbol={}", holding.getSymbol());	
		return "SUCCESS";
	}
	
	@GetMapping
	public HoldingDataDto getHoldings() {
		HoldingDataDto holdingDataDto = new HoldingDataDto();
		List<HoldingDto> holdingDtos = new ArrayList<HoldingDto>();
		List<Holding> holdings = holdingsService.getAllHolding();
		
		List<BavCopy> bavCopyEntries = bavCopyService.getBavCopy();
		
		for(Holding holding : holdings) {
			HoldingDto holdingDto = asDto(holding);
			Optional<BavCopy> opitional = bavCopyEntries.stream().filter(entry-> entry.getSymbol().equalsIgnoreCase(holding.getSymbol())).findFirst();
			if(opitional.isPresent()) {
				holdingDto.setLatestPrice(opitional.get().getClose());
				holdingDto.poplulateData();
			}
			holdingDtos.add(holdingDto);
		}
		log.info("Getting all Holdings size={}", holdingDtos.size());
		holdingDataDto.setHoldingDtos(holdingDtos);
		holdingDataDto.populateData();
		return holdingDataDto;
	}
	
	@DeleteMapping("/{id}")
	public void deleteHolding(@PathVariable String id) {
		holdingsService.deleteHoldingById(id);
		log.info("deleted Holding symbol={}", id);	
	}
	
	private Holding asDomain(HoldingDto holdingDto) {
			return Holding.builder()
			.symbol(holdingDto.getSymbol())
			.quantity(Long.valueOf(holdingDto.getQuantity()))
			.avgPrice(Double.valueOf(holdingDto.getAvgPrice()))
			.build();
	}
	
	private HoldingDto asDto(Holding holding) {
		return HoldingDto.builder()
		.symbol(holding.getSymbol())
		.quantity(holding.getQuantity())
		.avgPrice(new BigDecimal(holding.getAvgPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue())
		.build();
}
	
}
