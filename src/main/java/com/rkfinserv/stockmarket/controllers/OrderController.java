package com.rkfinserv.stockmarket.controllers;

import com.rkfinserv.stockmarket.dto.OrderEntryDto;
import com.rkfinserv.stockmarket.model.BavCopy;
import com.rkfinserv.stockmarket.model.Broker;
import com.rkfinserv.stockmarket.model.position.OrderEntry;
import com.rkfinserv.stockmarket.service.BavCopyService;
import com.rkfinserv.stockmarket.service.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderServiceImpl orderService;
    private BavCopyService bavCopyService;

    @Autowired
    public OrderController(OrderServiceImpl orderService, BavCopyService bavCopyService) {
        this.orderService = orderService;
        this.bavCopyService = bavCopyService;
    }

    @PostMapping("/{broker}")
    public void loadOrdersFromTradeBook(@PathVariable Broker broker){
        orderService.enterOrdersFromTradeBook(broker);
    }

    @GetMapping
    public List<OrderEntryDto> getOrders(){
        log.info("Received request for order history.");
        List<OrderEntry> orders = orderService.getOrders();
        List<OrderEntryDto> orderEntryDtos = new ArrayList<>();
        orders.forEach(orderEntry -> orderEntryDtos.add(orderEntry.asDto()));
        List<BavCopy> bavCopy = bavCopyService.getBavCopy();
        populateLatestPriceData(orderEntryDtos, bavCopy);
        return orderEntryDtos;
    }


    private void populateLatestPriceData(List<OrderEntryDto> orderEntryDtos, List<BavCopy> bavCopies) {
        Map<String, BavCopy> latestPriceMap = bavCopies.stream().collect(Collectors.toMap(BavCopy::getSymbol, Function.identity()));
        orderEntryDtos.forEach(orderEntryDto -> {
            BavCopy bavCopy = latestPriceMap.get(orderEntryDto.getSymbol());
            if(bavCopy != null) {
                BigDecimal latestPrice = BigDecimal.valueOf(latestPriceMap.get(orderEntryDto.getSymbol()).getClose());
                orderEntryDto.setLatestPrice(latestPrice);
                orderEntryDto.setChangePercentage((latestPrice.subtract(orderEntryDto.getPrice())).divide(orderEntryDto.getPrice(), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
            }
            });
    }


    @GetMapping("/{symbol}")
    public List<OrderEntryDto> getOrders(@PathVariable String symbol){
        log.info("Received request for order history.");
        List<OrderEntry> orders = orderService.getOrders();
        List<OrderEntryDto> orderEntryDtos = new ArrayList<>();
        orders.forEach(orderEntry -> orderEntryDtos.add(orderEntry.asDto()));
        return orderEntryDtos;
    }


}
