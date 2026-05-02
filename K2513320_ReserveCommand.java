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

public class K2513320_ReserveCommand  implements K2513320_Command {
    private K2513320_User user;
    private K2513320_Book book;
    private K2513320_SmartLibraryManagementSystem system;
    private K2513320_Reservation reservation;

    public K2513320_ReserveCommand(K2513320_User user, K2513320_Book book, K2513320_SmartLibraryManagementSystem system) {
        this.user = user;
        this.book = book;
        this.system = system;
    }

    @Override
    public void execute() {
        if (book.getStateName().equals("Borrowed")) {
            book.reserve();
            reservation = new K2513320_Reservation(system.generateReservationID(), user.getUserID(), book.getBookID(), new Date());
            system.getReservations().add(reservation);

            System.out.println("Book " + book.getTitle() + " reserved by " + user.getMembershipType() + user.getName());
        } else if (book.getStateName().equals("Reserved")) {
            System.out.println("Book " + book.getTitle() + " is already reserved.");
        } else {
            System.out.println("Book " + book.getTitle() + " is available.");
        } 
    }

    @Override
    public void undo() {
        if (reservation != null) {
            system.getReservations().remove(reservation);
            book.setState(new K2513320_AvailableState());
            System.out.println("Undo: Reservation cancelled.");
        }
    }

    @Override
    public String getDescription() {
        return "ReserveCommand(User " + user.getUserID() + ", Book " + book.getBookID() + ")";
    }
}
