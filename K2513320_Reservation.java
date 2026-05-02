/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.k2513320_smartlibrarymanagementsystem;

/**
 *
 * @author slthi
 */
import java.util.Date;

public class K2513320_Reservation {
    private String reservationID;
    private int bookID;
    private int userID;
    private Date reservationDate;
    private boolean fulfilled;
    private boolean cancelled;

    public K2513320_Reservation(String reservationID, int bookID, int userID, Date reservationDate) {
        this.reservationID = reservationID;
        this.bookID = bookID;
        this.userID = userID;
        this.reservationDate = reservationDate;
        this.fulfilled = false;
        this.cancelled = false;
    }

    public String getReservationID() { return reservationID; }
    public int getBookID() { return bookID; }
    public int getUserID() { return userID; }
    public Date getReservationDate() { return reservationDate; }
    public boolean isFulfilled() { return fulfilled; }
    public boolean isCancelled() { return cancelled; }

    public void setReservationID(String reservationID) { this.reservationID = reservationID; }
    public void setBookID(int bookID) { this.bookID = bookID; }
    public void setUserID(int userID) { this.userID = userID; }
    public void setReservationDate(Date reservationDate) { this.reservationDate = reservationDate; }
    public void markFulfilled() { this.fulfilled = true; }
    public void markCancelled() { this.cancelled = true; }
}
