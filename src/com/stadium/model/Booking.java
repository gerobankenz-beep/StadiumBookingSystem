package com.stadium.model;

import java.util.ArrayList;
import java.util.List;

public class Booking {
    private int bookingId;
    private String bookingReference;

    private Customer customer; // this creates a relationship with the Customer class
    private Event event; // this creates a relationship with the Event class

    private double subtotal;
    private double discount;
    private double total;
    private String status;

    private List<BookingSeat> bookingSeats = new ArrayList<>();

    public Booking(int bookingId, String bookingReference, Customer customer,
                   Event event, double subtotal, double total, double discount, String status) {
        this.bookingId = bookingId;
        this.bookingReference = bookingReference;
        this.customer = customer;
        this.event = event;
        this.subtotal = subtotal;
        this.total = total;
        this.discount = discount;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Event getEvent() {
        return event;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public List<BookingSeat>getBookingSeats(){
        return bookingSeats;
    }

    public void addBookingSeat(BookingSeat bookingSeat){
        bookingSeats.add(bookingSeat);
    }


    public void calculateTotal() {
        total = subtotal - discount;
    }

    public void confirmBooking() {
        status = "CONFIRMED";
    }

}
