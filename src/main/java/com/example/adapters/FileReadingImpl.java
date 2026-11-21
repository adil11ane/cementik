package com.example.adapters;

import com.example.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class FileReadingImpl implements FileSource {

    private final String delimiter;

    protected FileReadingImpl(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public List<Order> getOrders(String filePath) throws IOException {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split(Pattern.quote(delimiter));
                LocalDateTime time = LocalDateTime.parse(parts[0].trim());
                String company = parts[1].trim();
                int kg = Integer.parseInt(parts[2].trim());
                orders.add(new Order(company, kg, time));
            }
        }
        return orders;
    }


}

