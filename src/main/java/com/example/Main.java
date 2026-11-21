package com.example;


import com.example.adapters.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        BigDecimal pricePerKg = new BigDecimal("1.2");
        BigDecimal startDiscountPercent = new BigDecimal("50.0");
        BigDecimal discountStepPercent = new BigDecimal("5.0");

        FileSourceResolver resolver = new FileSourceResolver(List.of(
                new TxtFileAdapter(),
                new PlainFileAdapter()));

        process(resolver, "discount_day.txt", "result.txt",
                pricePerKg, startDiscountPercent, discountStepPercent);

        process(resolver, "discount_day_without_ext", "result_plain.txt",
                pricePerKg, startDiscountPercent, discountStepPercent);
    }

    private static void process(FileSourceResolver resolver, String inputPath, String outputPath,
                                BigDecimal pricePerKg, BigDecimal startDiscountPercent, BigDecimal discountStepPercent) throws IOException {
        FileSource source = resolver.resolve(inputPath);
        FileReaderService readerService = new FileReaderService(source);
        SortingService sortingService = new SortingService(readerService);
        DiscountApplianceService discountService =
                new DiscountApplianceService(sortingService, startDiscountPercent, discountStepPercent, pricePerKg);
        ResultWriter writer = new TotalResultWirter();

        try {
            Map<String, BigDecimal> totals = discountService.applyDiscount(inputPath);
            writer.write(outputPath, totals);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }

    }
}