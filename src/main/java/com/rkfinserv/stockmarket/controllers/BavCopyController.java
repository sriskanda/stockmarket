package com.rkfinserv.stockmarket.controllers;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.exception.BavCopyException;
import com.rkfinserv.stockmarket.service.BavCopyResult;
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
	
	@GetMapping(value = "")
	public String test() {
		return "Hello";
	}
	
	@GetMapping(value = "/load/{date}")
	public BavCopyResult loadBavCopy(@PathVariable String date) {
		LOG.info("request received to load the bavcopy");
		System.out.println("request received to load the bavcopy");
		try {
			bavCopyService.loadBavCopy(date);
			return BavCopyResult.SUCCESS;
		} catch (BavCopyException e) {
			LOG.error("Failed to load the bavcopy", e);
			return e.getBavCopyResult();
		}
	}
	
	
	
}
