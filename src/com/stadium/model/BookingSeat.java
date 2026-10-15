package com.stadium.model;

public class BookingSeat {
    private int bookingSeatId;
    private Booking booking;
    private Seat seat;

    public BookingSeat(int bookingSeatId, Booking booking, Seat seat){
        this.bookingSeatId = bookingSeatId;
        this.booking = booking;
        this.seat = seat;
    }

    public int getBookingSeatId() {
        return bookingSeatId;
    }

    public Booking getBooking() {
        return booking;
    }

    public Seat getSeat() {
        return seat;
    }

}
