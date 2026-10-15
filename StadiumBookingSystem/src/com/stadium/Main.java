package com.stadium;


import  com.stadium.model.Ticket;
import  com.stadium.model.Discount;
import com.stadium.model.BookingSeat;
import com.stadium.model.Booking;
import com.stadium.model.Customer;
import com.stadium.model.SeatCategory;
import com.stadium.model.Seat;
import  com.stadium.model.Event;

public class Main {
    public static void main(String[] args) {
        SeatCategory vip = new SeatCategory(1, "VIP", 150000);
        SeatCategory ordinary = new SeatCategory(101, "ORD", 50000);
//        Seat seatA1 = new Seat(1, "A1", "AVAILABLE", vip);
//        Seat seatA101 = new Seat(101, "A101", "AVAILABLE", ordinary);

//        System.out.println("Category:" + vip.getCategoryName());
//        System.out.println("Price: UGX " + vip.getPrice());
//        System.out.println("Seat: " + seatA1.getSeatNumber());
//        System.out.println("Available: " + seatA1.checkAvailability());
//
//        System.out.println("Category:" + ordinary.getCategoryName());
//        System.out.println("Price: UGX " + ordinary.getPrice());
//        System.out.println("Seat: " + seatA101.getSeatNumber());
//        System.out.println("Available: " + seatA101.checkAvailability());


        Event event = new Event(1, "Football Match", "KCCA vs Onduparaka", "23/09/2026", "18:30");
        System.out.println("Event: " + event.getEventName());
        System.out.println("Date: " + event.getEventDate());
        System.out.println("Time: " + event.getEventTime());


        Customer customer = new Customer(1,"Gerald Rubangakene",
                "Kampala", "gerobamkenz@gmail.com", "0783747740",
                "China", "Gero256", "12526"
        );

        System.out.println("Customer name: "+ customer.getName());
        System.out.println("Username: "+ customer.getUsername());
        System.out.println("Address: "+ customer.getAddress());
        System.out.println("Contact: "+customer.getContact());


        Booking booking = new Booking(
                1,
                "MVP-0001",
                customer,
                event,
                200000,
                20000,
                180000,
                "PENDING"
        );

        System.out.println("Booking: " + booking.getBookingReference());
        System.out.println("Customer: " + booking.getCustomer().getName());
        System.out.println("Event: " + booking.getEvent().getEventName());
        System.out.println("Total: UGX " + booking.getTotal());

        booking.confirmBooking();

        System.out.println("Status: " + booking.getStatus());


        Seat seatA1 = new Seat(1, "A1", "AVAILABLE", vip);

        Seat seatA2 = new Seat(2, "A2", "AVAILABLE", vip);

        Seat seatA3 = new Seat(3, "A3", "AVAILABLE", ordinary);


        BookingSeat bookingSeat1 = new BookingSeat(1, booking, seatA1);
        BookingSeat bookingSeat2 = new BookingSeat(2, booking, seatA2);
        BookingSeat bookingSeat3 = new BookingSeat(3, booking, seatA3);

        System.out.println("Booked seat: " + bookingSeat1.getSeat().getSeatNumber());

        System.out.println("Booked seat: " + bookingSeat2.getSeat().getSeatNumber());

        System.out.println("Booked seat: " + bookingSeat3.getSeat().getSeatNumber());

        Discount bulkDiscount = new Discount(1, "Bulk Booking", 10);
        Discount kidsDiscount = new Discount(2,"Kids ticket", 30);

        double discount = bulkDiscount.calculateDiscount(200000);
        double kidDiscount = kidsDiscount.calculateDiscount(300000);

        System.out.println("Discount: UGX " + discount);
        System.out.println("Kids dicount: "+ kidDiscount);

        Ticket ticket = new Ticket(1, "TKT 0001", booking);
        ticket.printTicket();
    }
}
