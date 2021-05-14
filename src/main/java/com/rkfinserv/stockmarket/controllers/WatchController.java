package com.rkfinserv.stockmarket.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rkfinserv.stockmarket.dto.WatchDto;
import com.rkfinserv.stockmarket.model.Watch;
import com.rkfinserv.stockmarket.service.WatchService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/watchlist")
public class WatchController {
	
	private final WatchService watchService;
	
	@Autowired
	public WatchController(WatchService watchService) {
		super();
		this.watchService = watchService;
	}
	
	@GetMapping
	public List<WatchDto> getWatchList() {
		log.info("Receved request Watch List");
		List<WatchDto> watchDtos = new ArrayList<WatchDto>();
		List<Watch> watchList = watchService.getWatchList();
		for(Watch watch : watchList) {
			watchDtos.add(WatchDto.builder().symbol(watch.getSymbol()).build());
		}
		log.info("Watch List size={}", watchList.size());
		return watchDtos;
	}
	
	
	@PostMapping
	public void saveWatch(@RequestBody WatchDto watchDto) {
		log.info("Receved request to save Watch List {}", watchDto);
		Watch watch = Watch.builder().symbol(watchDto.getSymbol()).build();
		watchService.update(watch);
	}
	
	@DeleteMapping("/{id}")
	public void deleteWatch(@PathVariable String id) {
		log.info("Delete Watch id={}", id);
		watchService.remove(id);
	}
	
}
