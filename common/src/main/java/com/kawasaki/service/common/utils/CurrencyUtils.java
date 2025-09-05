package com.kawasaki.service.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public class CurrencyUtils {
    public static long toStripeAmount(BigDecimal amount, String currency) {
        if (Objects.isNull(amount) || Objects.isNull(currency)) {
            // todo: custom error
            throw new IllegalArgumentException("amount or currency is null");
        }

        Currency curr = Currency.getInstance(currency);
        int fractionDigits = curr.getDefaultFractionDigits();

        BigDecimal scaled = amount.setScale(fractionDigits, RoundingMode.HALF_UP);

        try {
            return scaled.movePointRight(fractionDigits).longValueExact();
        } catch (ArithmeticException e) {
            // todo: custom error
            throw new IllegalArgumentException("Invalid amount" +  amount + ": " + e.getMessage());
        }
    }
}
