package com.example.adapters;


import java.nio.file.Path;

public class PlainFileAdapter extends FileReadingImpl {

    private static final String DELIMITER = "#";

    public PlainFileAdapter() {
        super(DELIMITER);
    }

    @Override
    public boolean supports(String filePath) {
        return !Path.of(filePath).getFileName().toString().contains(".");
    }
}
