package com.rkfinserv.stockmarket.exception;

import com.rkfinserv.stockmarket.model.BavCopyResult;

public class BavCopyException extends Exception {
	
	private final BavCopyResult bavCopyResult;
	
	public BavCopyException(Exception e,final BavCopyResult bavCopyResult) {
		super(e);
		this.bavCopyResult = bavCopyResult;
	}

	public BavCopyResult getBavCopyResult() {
		return bavCopyResult;
	}

	
}
