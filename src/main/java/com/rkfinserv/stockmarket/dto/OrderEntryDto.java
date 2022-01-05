package com.rkfinserv.stockmarket.dto;

import com.rkfinserv.stockmarket.model.position.TradeLog;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Builder
@Setter
@Getter
@ToString
public class OrderEntryDto {
    @Id
    String orderId;
    String symbol;
    BigDecimal quantity;
    BigDecimal price;
    String orderType;
    Date orderDate;
    BigDecimal latestPrice;
    BigDecimal changePercentage;
    String broker;



}
