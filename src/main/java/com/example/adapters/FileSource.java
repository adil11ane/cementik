package com.example.adapters;

import com.example.Order;


import java.io.IOException;
import java.util.List;

public interface FileSource {
    boolean supports(String filePath) throws IOException;

    List<Order> getOrders(String filePath) throws IOException;
}
