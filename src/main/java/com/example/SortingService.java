package com.example;


import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;

public class SortingService {
    private final FileReaderService outputService;


    public SortingService(FileReaderService outputService) {
        this.outputService = outputService;
    }

    public LinkedList<Order> sortingOrders(String filePath) throws IOException {
        LinkedList<Order> orders = outputService.run(filePath);
        orders.sort(Comparator.comparing(Order::date));
        return orders;
    }

}
