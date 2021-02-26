package com.rkfinserv.stockmarket.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.service.TechincalAnalysisService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/techanalysis")
public class TechnicalAnalysisController {

	private TechincalAnalysisService techincalAnalysisService;

	@Autowired
	public TechnicalAnalysisController(TechincalAnalysisService techincalAnalysisService) {
		super();
		this.techincalAnalysisService = techincalAnalysisService;
	}
	
	@GetMapping
	public List<BavCopy> test(@RequestParam Integer days) {
		return techincalAnalysisService.performAnalysis(days, null);
	}
	
	
}
