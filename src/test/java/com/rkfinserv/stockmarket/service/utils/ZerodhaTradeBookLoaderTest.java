package com.rkfinserv.stockmarket.service.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZerodhaTradeBookLoaderTest {

    ZerodhaTradeBookLoader zerodhaTradeBookLoader = new ZerodhaTradeBookLoader();

    @Test
    void loadTradeBook() {
        zerodhaTradeBookLoader.getOrdersFromTradeBook();;
    }
}