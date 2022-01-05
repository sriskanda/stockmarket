package com.rkfinserv.stockmarket.service;

import com.rkfinserv.stockmarket.model.Broker;
import com.rkfinserv.stockmarket.model.position.OrderEntry;
import com.rkfinserv.stockmarket.repositories.OrderRepository;
import com.rkfinserv.stockmarket.service.utils.TradeBookLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl{

    private TradeBookLoader tradeBookLoader;
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(TradeBookLoader tradeBookLoader, OrderRepository orderRepository) {
        this.tradeBookLoader = tradeBookLoader;
        this.orderRepository = orderRepository;
    }

    public void enterOrdersFromTradeBook(Broker broker){
        List<OrderEntry> orders = TradeBookLoader.getTradeBookLoader(broker).getOrdersFromTradeBook();
        orders.forEach(orderLog -> {
            if(orderRepository.findById(orderLog.getOrderId()).isPresent()){
                log.error("Order already exisits id={}", orderLog.getOrderId());
            }else{
                orderRepository.insert(orderLog);
                log.info("added order {}", orderLog);
            }
        });
        log.info("loaded the orders successfully. Order count={}", orders.size());

    }

    public List<OrderEntry> getOrders(){
        return orderRepository.findAll();
    }
}
