package com.stadium.service;

import com.stadium.service.BookingService;
import java.util.Arrays;

public class TestBookSeats {

    public static void main(String[] args) {

        BookingService service = new BookingService();

        boolean result = service.bookSeats(
                "TKT-003",
                1,                  // customer ID
                1,                  // event ID
                Arrays.asList(5, 6), // seat IDs
                90000,              // subtotal
                0,                  // discount
                90000               // total
        );

        System.out.println(
                "Booking successful: " + result
        );
    }
}