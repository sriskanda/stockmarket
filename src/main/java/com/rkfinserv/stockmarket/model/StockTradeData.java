package com.rkfinserv.stockmarket.model;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class StockTradeData {
	private String symbol;
	private double buyPrice;
	private double investedAmount;
	private int quantity;
	private double sellPrice;
	private double maturityAmount;
	private double pnl;
	private double pnlPecentage;

	public void popluatePnlData() {
		quantity = (int) (investedAmount / buyPrice);
		maturityAmount = quantity * sellPrice;
		pnl = maturityAmount - investedAmount;
		pnlPecentage = pnl / investedAmount * 100; 
	}

	public String toString() {
		return new StringBuilder()
				.append(symbol).append(",")
				.append(buyPrice).append(",")
				.append(investedAmount).append(",")
				.append(sellPrice).append(",")
				.append(maturityAmount).append(",")
				.append(pnl).append(",")
				.append(pnlPecentage).append(",")
				.toString();
	}
}
