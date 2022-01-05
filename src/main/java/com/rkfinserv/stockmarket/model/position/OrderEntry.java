package com.rkfinserv.stockmarket.model.position;

import com.rkfinserv.stockmarket.dto.OrderEntryDto;
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
@Document(value = "OrderHistory")
public class OrderEntry {
    @Id
    String orderId;
    String symbol;
    BigDecimal quantity;
    BigDecimal price;
    String orderType;
    Date orderDate;
    String broker;

    public static OrderEntry convert(TradeLog tradeLog) throws ParseException {
        return OrderEntry.builder()
                .orderId(tradeLog.getOrderId())
                .symbol(tradeLog.getSymbol())
                .quantity(tradeLog.getQuantity())
                .price(tradeLog.getPrice())
                .orderType(tradeLog.getTradeType())
                .orderDate((new SimpleDateFormat("yyyy-MM-dd").parse(tradeLog.getTradeDate())))
                .build();
    }

    public OrderEntry aggregate(TradeLog tradeLog) {
        quantity = quantity.add(tradeLog.getQuantity());
        price = ((price.multiply(quantity)).add(tradeLog.getPrice().multiply(tradeLog.getQuantity()))).divide(quantity.add(tradeLog.getQuantity()),2, RoundingMode.HALF_UP);
        return this;
    }

    public OrderEntryDto asDto(){
        return OrderEntryDto.builder()
                .orderId(this.orderId)
                .symbol(this.symbol)
                .quantity(this.quantity)
                .price(this.price)
                .orderType(this.orderType)
                .orderDate(this.orderDate)
                .broker(this.broker)
                .build();
    }
}
