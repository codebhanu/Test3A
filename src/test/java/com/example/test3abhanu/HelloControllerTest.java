package com.example.test3abhanu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloControllerTest {

    @Test
    void calculateTotalbill() {
        HelloController instance= new HelloController();
        assertEquals(instance.calculateTotalbill(5,"M"),54);
    }
}