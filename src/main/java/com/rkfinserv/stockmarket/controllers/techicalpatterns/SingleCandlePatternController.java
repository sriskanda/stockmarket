package com.rkfinserv.stockmarket.controllers.techicalpatterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.service.technicalpatterns.SingleCandlePatternService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/singlecandle")
public class  SingleCandlePatternController {
	
	private final SingleCandlePatternService singleCandlePatternService;
	
	@Autowired
	public SingleCandlePatternController(SingleCandlePatternService singleCandlePatternService) {
		super();
		this.singleCandlePatternService = singleCandlePatternService;
	}


	@GetMapping("/bullishmarubozu/{dateStr}")
	public List<String> getBullishMarubozuStocks(@PathVariable String dateStr) throws ParseException{
		log.info("getBullishMarubozuStocks dateStr={}", dateStr);
		Date date = toDate(dateStr);
		return singleCandlePatternService.getBullishMarubozuStocks(date);
	}
	

	@GetMapping("/bearishmarubozu/{dateStr}")
	public List<String> getBearishMarubozuStocks(@PathVariable String dateStr) throws ParseException{
		log.info("getBearishMarubozuStocks dateStr={}", dateStr);
		Date date = toDate(dateStr);
		return singleCandlePatternService.getBearishMarubozuStocks(date);
	}

	@GetMapping("/spinningtop/{dateStr}")
	public List<String> getSpinningTopStocks(@PathVariable String dateStr) throws ParseException{
		log.info("getBearishMarubozuStocks dateStr={}", dateStr);
		Date date = toDate(dateStr);
		return singleCandlePatternService.getSprinningTopStocks(date);
	}

	@GetMapping("/paperumbrella/{dateStr}")
	public List<String> getPaperUmbrellaStocks(@PathVariable String dateStr) throws ParseException{
		log.info("getBearishMarubozuStocks dateStr={}", dateStr);
		Date date = toDate(dateStr);
		return singleCandlePatternService.getPaperUmbrellaStocks(date);
	}

	@GetMapping("/shootingstar/{dateStr}")
	public List<String> getShootingStarStocks(@PathVariable String dateStr) throws ParseException{
		log.info("getShootingStarStocks dateStr={}", dateStr);
		Date date = toDate(dateStr);
		return singleCandlePatternService.getShootingStarStocks(date);
	}

	
	private Date toDate(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss z");
		Date date = sdf.parse(dateStr + " 23:00:00 IST");
		return date;
	}

}
