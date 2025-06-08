package com.training;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MethodsOrderedByOrderIndexTest {


    @Test
    @Order(3)
    void testC() {
        System.out.println("Running test C");
    }
    @Test
    @Order(2)
    void testA() {
        System.out.println(" Running test A");
    }

    @Order(1)
    @Test
    void testD() {
        System.out.println("Running test D");
    }

    @Test
    @Order(4)
    void testB() {
        System.out.println(" Running test B");
    }
}
