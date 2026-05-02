/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.k2513320_smartlibrarymanagementsystem;

/**
 *
 * @author slthi
 */
public class K2513320_AvailableState extends K2513320_BookState {

    @Override
    public String getStateName() {
        return "Available";
    }

    @Override
    public void borrowBook(K2513320_Book book) {
        book.setState(new K2513320_BorrowedState());
    }

    @Override
    public void reserveBook(K2513320_Book book) {
        book.setState(new K2513320_ReservedState());
    }
}
