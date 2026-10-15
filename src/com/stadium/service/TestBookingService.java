package com.stadium.service;

import com.stadium.service.BookingService;

import java.util.ArrayList;

public class TestBookingService {

    public static void main(String[] args) {

        BookingService service = new BookingService();

        service.bookSeats(
                "TMT-0012",
                3,
                3,
                new ArrayList<>(),
                200000,
                20000,
                180000

        );

        System.out.println("Booking successful: ");

    }
}