package com.rkfinserv.stockmarket.service.utils;

import com.opencsv.bean.CsvToBeanBuilder;
import com.rkfinserv.stockmarket.model.position.OrderEntry;
import com.rkfinserv.stockmarket.model.position.TradeLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ZerodhaTradeBookLoader implements TradeBookLoader {

    private static final String TRADE_BOOK_FILE_PATH = "C:\\Users\\harivar\\Documents\\personal\\finance\\zerodha_tradebook\\tradebook-XX3404_OCT2019_DEC2019";

    @Override
    public List<OrderEntry> getOrdersFromTradeBook() {
        Map<String, OrderEntry> orderLogMap = new HashMap<>();

        try {
            List<TradeLog> tradeLogs = new CsvToBeanBuilder<TradeLog>(new FileReader(TRADE_BOOK_FILE_PATH))
                    .withType(TradeLog.class)
                    .build()
                    .parse();
            System.out.println("************** Trade Logs *************************");

            tradeLogs.forEach(System.out::println);
            System.out.println("Total trades "+tradeLogs.stream().count());
            tradeLogs.forEach(tradeLog -> {
                if(orderLogMap.containsKey(tradeLog.getOrderId())){
                    orderLogMap.put(tradeLog.getOrderId(),orderLogMap.get(tradeLog.getOrderId()).aggregate(tradeLog));
                }else{
                    try {
                        orderLogMap.put(tradeLog.getOrderId(), OrderEntry.convert(tradeLog));
                    } catch (ParseException e) {
                        log.error("Failed parse the date in tradebook ", e);
                    }
                }
            });

            System.out.println("************** Order Logs *************************");
            orderLogMap.values().forEach(System.out::println);
            System.out.println("Total trades "+orderLogMap.values().stream().count());
            log.info("Loading tradebook is successful ");

        } catch (Exception ioe) {
            log.error("Failed to load tradebook ", ioe);
        }
        return orderLogMap.values().stream().collect(Collectors.toList());
    }
}
