package com.stadium.dao;

import com.stadium.dao.SeatDAO;

public class TestSeatDao {
    public static void main(String[] args) {
        SeatDAO dao = new SeatDAO();
        dao.getAvailableSeats();
    }
}
