package com.stadium.dao;

import com.stadium.dao.SeatDAO;

public class TestSeatAvailability {

    public static void main(String[] args) {

        SeatDAO dao = new SeatDAO();

        System.out.println(
                "Seat 1 available: " + dao.isSeatAvailable(1)
        );

        System.out.println(
                "Seat 2 available: " + dao.isSeatAvailable(2)
        );
    }
}