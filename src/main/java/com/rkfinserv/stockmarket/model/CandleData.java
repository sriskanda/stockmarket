package com.rkfinserv.stockmarket.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CandleData {
    private String symbol;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal open;
    private BigDecimal close;

    public boolean isBullishMarubozu(double allowedVariance, double minRange, double maxRange) {
        return false;
    }

    public boolean isBearishMarubozu(double allowedVariance, double minRange, double maxRange) {
        return false;
    }

    public boolean isSpinningTop(double body_langth_variance, double shadow_length_variance) {
        return false;
    }

    public boolean isPaperUmbrella(double body_langth_variance, double shadow_length_variance) {
        return false;
    }

    public boolean isShootingStar(double body_langth_variance, double shadow_length_variance) {
        return false;
    }
}
