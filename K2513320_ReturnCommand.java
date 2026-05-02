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

public class K2513320_ReturnCommand implements K2513320_Command {

    private K2513320_User user;
    private K2513320_Book book;
    private K2513320_SmartLibraryManagementSystem system;

    public K2513320_ReturnCommand(K2513320_User user,
                                  K2513320_Book book,
                                  K2513320_SmartLibraryManagementSystem system) {
        this.user = user;
        this.book = book;
        this.system = system;
    }

    @Override
    public void execute() {
        List<K2513320_Borrow> borrows = system.getBorrows();
        
        K2513320_Borrow br = null;
        for (K2513320_Borrow b : borrows) {
            if (b.getBookID() == book.getBookID() && b.getReturnedDate() == null) {
                br = b;
                break;
            }
        }

        if (br == null) {
            System.out.println("Cannot return: No active borrow record found for this book.");
            return;
        }

        Date now = new Date();
        br.setReturnedDate(now);

        book.returnBook();

        Date due = br.getDueDate();
        long overdueDays = 0;
        if (due != null) {
            long msLate = now.getTime() - due.getTime();
            if (msLate > 0) {
                overdueDays = (msLate + (24L*60L*60L*1000L - 1)) / (24L*60L*60L*1000L);
            }
        }

        double fineAmount = 0.0;
        if (overdueDays > 0) {
            fineAmount = user.calculateFine((int) overdueDays);
        }

        K2513320_NotificationSubject notifier = system.getNotificationSubject();
        if (notifier != null) {
            if (fineAmount > 0.0) {
                String msg = "Book '" + book.getTitle() + "' returned by " + user.getName()
                             + ". Overdue: " + overdueDays + " day(s). Fine: LKR " + fineAmount;
                notifier.setNotification(1, msg);
            } else {
                String msg = "Book '" + book.getTitle() + "' returned by " + user.getName()
                             + ". No fine.";
                notifier.setNotification(2, msg);
            }
        }

        System.out.println("Return executed for book '" + book.getTitle() + "'. Overdue days: "
                           + overdueDays + ", Fine: LKR " + fineAmount);
    }

    @Override
    public void undo() {
        System.out.println("Undo not supported for ReturnCommand.");
    }

    @Override
    public String getDescription() {
        return "ReturnCommand(User " + user.getUserID() + ", Book " + book.getBookID() + ")";
    }
}
