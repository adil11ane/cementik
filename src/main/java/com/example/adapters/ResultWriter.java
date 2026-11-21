package com.example.adapters;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public interface ResultWriter {
    void write(String path, Map<String, BigDecimal> totals) throws IOException;
}
