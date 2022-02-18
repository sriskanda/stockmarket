package com.rkfinserv.stockmarket.controllers;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.rkfinserv.stockmarket.dto.*;
import com.rkfinserv.stockmarket.model.LiveStockPrice;
import com.rkfinserv.stockmarket.service.LiveStockPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rkfinserv.stockmarket.exception.BavCopyException;
import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.model.BavCopyAudit;
import com.rkfinserv.stockmarket.model.BavCopyResult;
import com.rkfinserv.stockmarket.service.BavCopyService;

@RestController
@RequestMapping(value = "/bavcopy")
public class BavCopyController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BavCopyService bavCopyService;
	private final LiveStockPriceService liveStockPriceService;

	@Autowired
	public BavCopyController(BavCopyService bavCopyService, LiveStockPriceService liveStockPriceService) {
		super();
		this.bavCopyService = bavCopyService;
		this.liveStockPriceService = liveStockPriceService;
	}

	@GetMapping
	public List<BavCopyDto> getStockPrice() {
		List<BavCopyDto> bavCopiesDto = new ArrayList<BavCopyDto>();
		List<BavCopy> bavCopies = bavCopyService.getBavCopy();
		for (BavCopy bavCopy : bavCopies) {
			bavCopiesDto.add(bavCopy.asDto());
		}
		return bavCopiesDto;
	}

	@GetMapping("/{symbol}")
	public List<BavCopyDto> getStockPrice(@PathVariable String symbol) {
		List<BavCopyDto> bavCopiesDto = new ArrayList<BavCopyDto>();
		List<BavCopy> bavCopies = bavCopyService.getBavCopy(symbol);
		for (BavCopy bavCopy : bavCopies) {
			bavCopiesDto.add(bavCopy.asDto());
		}
		LOG.info("Resutl bavCopiesDto={}",bavCopiesDto);
		return bavCopiesDto;
	}

	@GetMapping("/chart/{symbol}")
	public StockChartDataDto getStockPriceChartData(@PathVariable String symbol) {
		List<BavCopyAudit> bavCopies = bavCopyService.getBavCopyAudit(symbol);
		List<BavCopyAudit> sortedBavCopies = bavCopies.stream().sorted(Comparator.comparing(BavCopyAudit::getTimeStamp))
				.collect(Collectors.toList());
		String[] dates = sortedBavCopies.stream().map(BavCopyAudit::getDateText).collect(Collectors.toList())
				.toArray(new String[bavCopies.size()]);
		Double[] prices = sortedBavCopies.stream().map(BavCopyAudit::getClose).collect(Collectors.toList())
				.toArray(new Double[bavCopies.size()]);
		StockChartDataDto stockChartDataDto = StockChartDataDto.builder().dates(dates).prices(prices).build();
		return stockChartDataDto;
	}

	@GetMapping("/candle-chart/{symbol}")
	public CandleChartDataDto getCandleChartData(@PathVariable String symbol) {
		List<CandleDataDto> candleDataDtos = new ArrayList<>();
		List<BavCopyAudit> bavCopies = bavCopyService.getBavCopyAudit(symbol);
		List<BavCopyAudit> sortedBavCopies = bavCopies.stream().sorted(Comparator.comparing(BavCopyAudit::getTimeStamp))
				.collect(Collectors.toList());
		sortedBavCopies.forEach(bavCopyAudit -> {
			CandleDataDto candleDataDto = CandleDataDto.builder()
					.x(bavCopyAudit.getTimeStamp())
					.y(new Double[]{bavCopyAudit.getOpen(), bavCopyAudit.getHigh(), bavCopyAudit.getLow(), bavCopyAudit.getClose()})
					.build();
			candleDataDtos.add(candleDataDto);
		});
		LiveStockPrice liveStockPrice = liveStockPriceService.getLiveStockPriceMap().get(symbol);
		if(liveStockPrice != null){
			Date lastTradedAt = liveStockPrice.getLastTradedAt();
			lastTradedAt.setHours(17);
			lastTradedAt.setMinutes(30);
			lastTradedAt.setSeconds(0);
			CandleDataDto candleDataDto = CandleDataDto.builder()
					.x(lastTradedAt)
					.y(new Double[]{liveStockPrice.getOpen().doubleValue(), liveStockPrice.getHigh().doubleValue(), liveStockPrice.getLow().doubleValue(), liveStockPrice.getClose().doubleValue()})
					.build();
			candleDataDtos.add(candleDataDto);
		}
		return CandleChartDataDto.builder().candleDataDtoList(candleDataDtos).liveStockPriceDto(liveStockPrice.asDto()).build();

	}

	@GetMapping("history/{symbol}")
	public List<BavCopyAuditDto> getStockPriceHistory(@PathVariable String symbol) {
		List<BavCopyAuditDto> bavCopiesAuditDto = new ArrayList<BavCopyAuditDto>();
		List<BavCopyAudit> bavCopyAudits = bavCopyService.getBavCopyAudit(symbol);
		for (BavCopyAudit bavCopyAudit : bavCopyAudits) {
			bavCopiesAuditDto.add(bavCopyAudit.asDto(bavCopyAudit));
		}
		return bavCopiesAuditDto;
	}

	@GetMapping(value = "/load/{date}")
	public BavCopyResult loadBavCopy(@PathVariable String date, @RequestParam Boolean isLatest) throws Exception {
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


	@PostMapping(value = "/bavcopies")
	public BavCopyResult loadBavCopies(@RequestParam Boolean isLatest) throws Exception {
		LOG.info("request received to load the bavcopy from folder");
		try {
			bavCopyService.loadBavCopies(isLatest);
			return BavCopyResult.SUCCESS;
		} catch (BavCopyException e) {
			LOG.error("Failed to load the bavcopy", e);
			return e.getBavCopyResult();
		}
	}

	@GetMapping("/date")
	public List<BavCopyAuditDto> fetchBavCopy(@RequestParam String dateStr) throws Exception {
		LOG.info("request received to featch the bavcopy dateStr={}",dateStr);
		List<BavCopyAuditDto> bavCopyAuditDtos = new ArrayList<BavCopyAuditDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z");
		Date date = sdf.parse(dateStr + " 23:00:00 IST");
		List<BavCopyAudit> bavCopyAudits = bavCopyService.getBavCopy(date);
		
		bavCopyAudits.forEach(bavCopy -> bavCopyAuditDtos.add(bavCopy.asDto(bavCopy)));
		LOG.info("fetched bavcopy date={} size={}", date, bavCopyAuditDtos.size());
		return bavCopyAuditDtos;

	}

	public List<BavCopy> getStockHistory(String symbol, String date) {
		LOG.info("request received to get history for {} from {}");

		return null;
	}

}
