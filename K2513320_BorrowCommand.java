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
import java.util.List;

public class K2513320_BorrowCommand implements K2513320_Command {
    private K2513320_User user;
    private K2513320_Book book;
    private K2513320_SmartLibraryManagementSystem system;
    private K2513320_Borrow borrowRecord;

    public K2513320_BorrowCommand(K2513320_User user, K2513320_Book book, K2513320_SmartLibraryManagementSystem system) {
        this.user = user;
        this.book = book;
        this.system = system;
    }

    @Override
    public void execute() {
        String previousStateName = book.getStateName();

        K2513320_Reservation activeReservation = system.findActiveReservation(book, user);

        if (activeReservation != null) {
            if (activeReservation.getUserID() != user.getUserID()) {
                System.out.println("Book is reserved by another user. Cannot borrow.");
                return;
            }
            activeReservation.markFulfilled();
            book.setState(new K2513320_BorrowedState());
        } else {
            if ("Borrowed".equals(previousStateName)) {
                System.out.println("Cannot borrow: book already borrowed.");
                return;
            }
            book.borrow();
        }

        long days = 14L;
        String mem = (user.getMembershipType() == null) ? "" : user.getMembershipType().toLowerCase();
        if ("faculty".equals(mem)) {
            days = 30L;
        } else if ("guest".equals(mem)) {
            days = 7L;
        } else if ("student".equals(mem)) {
            days = 14L;
        }

        Date today = new Date();
        Date dueDate = new Date(today.getTime() + days * 24L * 60L * 60L * 1000L);
        
        List<K2513320_Borrow> borrows = system.getBorrows();
        String borrowID = system.generateBorrowID();
        K2513320_Borrow createdBorrow = new K2513320_Borrow(borrowID, book.getBookID(), today, dueDate);
        borrows.add(createdBorrow);

        user.addBorrowRecord(createdBorrow);

        K2513320_NotificationSubject notifier = system.getNotificationSubject();
        if (notifier != null) {
            String msg = "Book '" + book.getTitle() + "' borrowed by " + user.getName() + ". Due date: " + dueDate;
            notifier.setNotification(2, msg);
        }
        
        System.out.println("Borrow executed: Book '" + book.getTitle() + "' borrowed by " + user.getName()
                           + "\n(Due: " + dueDate + ")");
    }

    @Override
    public void undo() {
        if (borrowRecord != null) {
            book.returnBook();
            borrowRecord.setReturnedDate(new Date());
            System.out.println("Undo: Borrow action reverted.");
        }
    }

    @Override
    public String getDescription() {
        return "BorrowCommand(User " + user.getUserID() + ", Book " + book.getBookID() + ")";
    }
}
