package com.rkfinserv.stockmarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rkfinserv.stockmarket.model.Watch;
import com.rkfinserv.stockmarket.repositories.WatchRepository;

@Service
public class WatchService {
	
	private final WatchRepository watchRepository;
	
	@Autowired
	public WatchService(WatchRepository watchRepository) {
		super();
		this.watchRepository = watchRepository;
	}

	public List<Watch> getWatchList() {
		return watchRepository.findAll();
	}

	public void update(Watch watch) {
		watchRepository.save(watch);
	}
	
	public void remove(String id) {
		watchRepository.deleteById(id);
	}
	
}
