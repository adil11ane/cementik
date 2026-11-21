package com.example;

import java.time.LocalDateTime;

public record Order(String company, int quantity, LocalDateTime date) {}
