package com.stadium.model;

public class Seat {
    private int seatId;
    private String seatNumber;
    private String status;
    private SeatCategory category;// every seat belongs to a seat category

    public Seat(int seatId, String seatNumber, String status, SeatCategory category){
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.status = status;
        this.category = category;
    }

    public boolean checkAvailability(){

        return status.equals("AVAILABLE");
    }

    public String getSeatNumber(){

        return seatNumber;
    }

    public int getSeatId() {
        return seatId;
    }

    public SeatCategory getCategory() {
        return category;
    }
}

