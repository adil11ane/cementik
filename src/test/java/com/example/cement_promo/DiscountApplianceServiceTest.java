package com.example.cement_promo;


import com.example.DiscountApplianceService;
import com.example.Order;
import com.example.SortingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiscountApplianceServiceTest {
    @Mock
    private SortingService sortingService;

    private DiscountApplianceService service;

    @BeforeEach
    void setUp() {
        service = new DiscountApplianceService(sortingService,
                new BigDecimal("50.0"),
                new BigDecimal("5.0"),
                new BigDecimal("10.0"));
    }

    private Order order(String company, int hour) {
        return new Order(company, 10, LocalDateTime.of(2021, 2, 9, hour, 0));
    }

    @Test
    void discountDiscreasesWithEachOrder() throws IOException {
        LinkedList<Order> orders = new LinkedList<>();
        orders.add(order("A", 10));
        orders.add(order("B", 11));
        orders.add(order("C", 12));
        when(sortingService.sortingOrders("orders.txt")).thenReturn(orders);

        Map<String, BigDecimal> res = service.applyDiscount("orders.txt");
        assertEquals(0, new BigDecimal("50").compareTo(res.get("A")));
        assertEquals(0, new BigDecimal("55").compareTo(res.get("B")));
        assertEquals(0, new BigDecimal("60").compareTo(res.get("C")));
    }

    @Test
    void sumsOrderOfTheSameCompany() throws IOException {
        LinkedList<Order> orders = new LinkedList<>();
        orders.add(order("A", 10));
        orders.add(order("B", 11));
        orders.add(order("A", 12));

        when(sortingService.sortingOrders("orders.txt")).thenReturn(orders);
        Map<String, BigDecimal> res = service.applyDiscount("orders.txt");

        assertEquals(2, res.size());
        assertEquals(0, new BigDecimal("110").compareTo(res.get("A")));
        assertEquals(0, new BigDecimal("55").compareTo(res.get("B")));
    }

    @Test
    void discountDoesNotGoBelowZero() throws IOException {
        DiscountApplianceService service =
                new DiscountApplianceService(sortingService,
                        new BigDecimal("10.0"),
                        new BigDecimal("5.0"),
                        new BigDecimal("10.0"));

        LinkedList<Order> orders = new LinkedList<>();
        orders.add(order("A", 10));
        orders.add(order("B", 11));
        orders.add(order("C", 12));
        orders.add(order("D", 13));
        when(sortingService.sortingOrders("orders.txt")).thenReturn(orders);
        Map<String, BigDecimal> res = service.applyDiscount("orders.txt");

        assertEquals(0, new BigDecimal("90").compareTo(res.get("A")));
        assertEquals(0, new BigDecimal("95").compareTo(res.get("B")));
        assertEquals(0, new BigDecimal("100").compareTo(res.get("C")));
        assertEquals(0, new BigDecimal("100").compareTo(res.get("D")));
    }
}