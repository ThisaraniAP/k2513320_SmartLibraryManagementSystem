/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.k2513320_smartlibrarymanagementsystem;

/**
 *
 * @author slthi
 */

import java.util.*;

public class K2513320_User implements K2513320_UserObserver {
    private int userID;
    private String name;
    private String email;
    private String contactNumber;
    private String membershipType;
    
    private final List<K2513320_Borrow> borrowedBooks = new ArrayList<>();
    private final List<String> notifications = new ArrayList<>();
    
    private K2513320_FineStrategy fineStrategy;

    public K2513320_User() { }

    public K2513320_User(int userID, String name, String email, String contactNumber, String membershipType) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.membershipType = membershipType;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID
            ;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public List<K2513320_Borrow> getBorrowedBooks() {
        return borrowedBooks;
    }    

    public List<String> getNotifications() {
        return notifications;
    }

    public K2513320_FineStrategy getFineStrategy() {
        return fineStrategy;
    }
    
    public void setFineStrategy(K2513320_FineStrategy fineStrategy) { 
        this.fineStrategy = fineStrategy; 
    }


    public void addBorrowRecord(K2513320_Borrow borrow) {
        if (borrow != null) borrowedBooks.add(borrow);
    }

    public void addNotification(String message) {
        if (message != null) notifications.add(message);
    }
    
    public double calculateFine(int overdueDays){
        if (fineStrategy == null) return 0.0;
        return fineStrategy.calculateFine(overdueDays);
    }
    
    @Override
    public void update(K2513320_NotificationSubject subject) {
        if (subject == null) return;
        int code = subject.getNotificationCode();
        String msg = subject.getMessage();
        String finalMsg;

        finalMsg = switch (code) {
            case 1 -> "OVERDUE! " + (msg != null ? msg : "You have an overdue book.");
            case 2 -> "DUE DATE! " + (msg != null ? msg : "A borrowed book is due soon.");
            case 3 -> "RESERVATION AVAILABLE! " + (msg != null ? msg : "A reserved book is available.");
            default -> "NOTICE! " + (msg != null ? msg : "Notification from library.");
        };

        addNotification(finalMsg);
    }

    public void printNotifications() {
        if (notifications.isEmpty()) {
            System.out.println("No notifications.");
            return;
        }
        System.out.println("Notifications for " + name + ":");
        for (String n : notifications) {
            System.out.println(" - " + n);
        }
    }
}
