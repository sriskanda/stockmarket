package com.rkfinserv.stockmarket.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rkfinserv.stockmarket.model.Alert;
import com.rkfinserv.stockmarket.repositories.AlertRepository;

@Service
public class AlertService {
	@Autowired
	private AlertRepository alertRepository;

	public void upsert(Alert alert) {
		alert.setCreatedDate(new Date());
		alertRepository.save(alert);
	}

	public void delete(String id) {
		alertRepository.deleteById(id);
	}

	public List<Alert> getAlerts() {
		List<Alert> alerts = alertRepository.findAll();
		return alerts;

	}

}
