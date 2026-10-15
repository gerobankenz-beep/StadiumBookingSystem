package com.stadium.model;

import com.stadium.dao.TicketDAO;

public class Ticket {
    private int ticket;
    private String ticketNumber;
    private Booking booking;

    public Ticket(int ticket, String ticketNumber, Booking booking) {
        this.ticket = ticket;
        this.ticketNumber = ticketNumber;
        this.booking = booking;
    }

    public int getTicket() {
        return ticket;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public Booking getBooking() {
        return booking;
    }

    public void printTicket() {

        System.out.println("================================");
        System.out.println("       STADIUM BOOKING TICKET");
        System.out.println("================================");

        System.out.println("Ticket No: " + ticketNumber);
        System.out.println("Booking No: " + booking.getBookingReference());
        System.out.println("Customer: " + booking.getCustomer().getName());
        System.out.println("Event: " + booking.getEvent().getEventName());
        System.out.println("Date: " + booking.getEvent().getEventDate());
        System.out.println("Time: " + booking.getEvent().getEventTime());

        System.out.println("--------------------------------");
        System.out.println("Subtotal: UGX " + booking.getSubtotal());
        System.out.println("Discount: UGX " + booking.getDiscount());
        System.out.println("Total: UGX " + booking.getTotal());

        System.out.println("================================");
    }
}
