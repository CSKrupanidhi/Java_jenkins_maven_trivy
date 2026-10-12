package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    @Test
    void testGreeting() {
        App app = new App();
        assertEquals("Hello, DevSecOps Pipeline!", app.getGreeting());
    }
}