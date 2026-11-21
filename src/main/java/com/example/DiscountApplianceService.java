package com.example;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

public class DiscountApplianceService {
    private final SortingService sortingService;
    private final BigDecimal startingDiscountPercent;
    private final BigDecimal discountStepPercent;
    private final BigDecimal pricePerKg;

    public DiscountApplianceService(SortingService sortingService,
                                    BigDecimal startingDiscountPercent,
                                    BigDecimal discountStepPercent,
                                    BigDecimal pricePerKg) {
        this.sortingService = sortingService;
        this.startingDiscountPercent = startingDiscountPercent;
        this.discountStepPercent = discountStepPercent;
        this.pricePerKg = pricePerKg;
    }

    public Map<String, BigDecimal> applyDiscount(String filePath) throws IOException {
        BigDecimal discount = startingDiscountPercent;
        Map<String, BigDecimal> totals = new LinkedHashMap<>();

        for (Order order : sortingService.sortingOrders(filePath)) {
            BigDecimal cost = BigDecimal.valueOf(order.quantity()).
                    multiply(pricePerKg).multiply(BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100))));
            totals.merge(order.company(), cost, BigDecimal::add);
            discount = discount.subtract(discountStepPercent).max(BigDecimal.ZERO);
        }
        totals.replaceAll((company, total) -> total.setScale(2, RoundingMode.HALF_UP));

        return totals;
    }

}
