package com.rkfinserv.stockmarket.strategy;

import java.util.List;

import com.rkfinserv.stockmarket.model.BavCopyAudit;

/*
 * High level idea 
 * ---------------
 * stocks that are in down trend from past x number of days and the last day is in up trend
 * 
 * Implementation:
 * --------------
 * 
 * Get stock trend history for past x number of days order by data in descending order
 * 
 * 
 * check for the latest day change_percentage is positive
 * check other days change_percentage is negative 
 * 
 */

public class BullishEngulfingPattern implements TechincalAnalysisPattern{

	@Override
	public List<BavCopyAudit> applyPattern(List<BavCopyAudit> stockHistory) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
