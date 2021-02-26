package com.rkfinserv.stockmarket.strategy;

import java.util.List;

import com.rkfinserv.stockmarket.model.BavCopyAudit;

public interface TechincalAnalysisPattern {
	
	List<BavCopyAudit> applyPattern(List<BavCopyAudit> stockHistory);

}
