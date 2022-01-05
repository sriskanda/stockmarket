package com.rkfinserv.stockmarket.model.position;

import com.opencsv.bean.CsvBindAndJoinByPosition;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString
public class TradeLog {
    @CsvBindByName(column = "trade_id")
    private String tradeId;
    @CsvBindByName(column = "order_id")
    private String orderId;
    @CsvBindByName(column = "symbol")
    private String symbol;
    @CsvBindByName(column = "trade_type")
    private String tradeType;
    @CsvBindByName(column = "quantity")
    private BigDecimal quantity;
    @CsvBindByName(column = "price")
    private BigDecimal price;
    @CsvBindByName(column = "trade_date")
    private String tradeDate;
    @CsvBindByName(column = "isin")
    private String isin;
    @CsvBindByName(column = "exchange")
    private String exchange;
    @CsvBindByName(column = "segment")
    private String segment;
    @CsvBindByName(column = "series")
    private String series;
    @CsvBindByName(column = "order_execution_time")
    private String order_execution_time;



}
