package com.rkfinserv.stockmarket.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.dto.AlertDto;
import com.rkfinserv.stockmarket.model.Alert;
import com.rkfinserv.stockmarket.service.AlertService;
import com.rkfinserv.stockmarket.service.BavCopyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/alert")
public class AlertController {
	
	@Autowired
	private AlertService alertService;
	
	@Autowired
	private BavCopyService bavCopyService;
	
	
	@PostMapping
	public void addAlert(@RequestBody AlertDto alertDto) {
		log.info("Recieved request to add alert {}", alertDto);
		Alert alert = Alert.asDomain(alertDto);
		alertService.upsert(alert);	
		log.info("completed adding alert {}", alert);
	}
	
	@GetMapping
	public List<AlertDto> getAllerts(){
		log.info("Recieved request to for all alerts");
		List<AlertDto> alertDtos = new ArrayList<AlertDto>();
		List<Alert> alerts = alertService.getAlerts();
		
		for(Alert alert : alerts) {
			AlertDto alertDto = alert.asDto();
			alertDto.setLatestPrice(BigDecimal.valueOf(bavCopyService.getBavCopy(alertDto.getStockId()).get(0).getClose()));
			alertDto.populateData();
			alertDtos.add(alertDto);
		}
		log.info("alerts size={}", alertDtos.size());
		return alertDtos;
	}
	
	@DeleteMapping("/{id}")
	public void deleteAlert(@PathVariable String id) {
		alertService.delete(id);
		log.info("deleted Alert id={}", id);	
	}
	
}
