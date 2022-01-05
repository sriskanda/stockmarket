package com.rkfinserv.stockmarket.service.utils;

import com.rkfinserv.stockmarket.model.Broker;
import com.rkfinserv.stockmarket.model.position.OrderEntry;

import java.util.List;


public interface TradeBookLoader {
    public List<OrderEntry> getOrdersFromTradeBook();

    static TradeBookLoader getTradeBookLoader(Broker broker) {
        if (Broker.ZERODHA == broker) {
            return new ZerodhaTradeBookLoader();
        } else {
            return null;
        }
    }
}

