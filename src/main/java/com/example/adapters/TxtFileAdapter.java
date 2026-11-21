package com.example.adapters;

import java.nio.file.Path;

public class TxtFileAdapter extends FileReadingImpl {
    private static final String DELIMITER = "|";

    public TxtFileAdapter() {
        super(DELIMITER);
    }

    @Override
    public boolean supports(String filePath) {
        return Path.of(filePath).getFileName().toString().toLowerCase().endsWith(".txt");
    }

}
