package com.example.adapters;


import java.io.IOException;
import java.util.List;

public class FileSourceResolver {
    private final List<FileSource> sources;

    public FileSourceResolver(List<FileSource> sources){
        this.sources = sources;
    }

    public FileSource resolve(String filePath) throws IOException {
        for (FileSource source : sources) {
            if (source.supports(filePath)){
                return source;
            }
        }
        throw new IllegalArgumentException("Unsupported file path " + filePath);
    }

}
