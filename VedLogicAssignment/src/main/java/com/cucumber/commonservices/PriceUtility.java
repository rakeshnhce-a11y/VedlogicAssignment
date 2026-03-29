package com.cucumber.commonservices;

import java.util.List;

public class PriceUtility {

    public static double calculateTotal(List<Double> prices) {
        return prices.stream().mapToDouble(Double::doubleValue).sum();
    }

    public static double calculateTax(double total, double rate) {
        return Math.round(total * rate * 100.0) / 100.0;
    }
}