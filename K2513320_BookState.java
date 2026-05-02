/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.k2513320_smartlibrarymanagementsystem;

/**
 *
 * @author slthi
 */
public abstract class K2513320_BookState {
    public abstract String getStateName();

    public void borrowBook(K2513320_Book book) {
        System.out.println("Cannot borrow. Book is " + getStateName());
    }

    public void returnBook(K2513320_Book book) {
        System.out.println("Cannot borrow. Book is " + getStateName());
    }

    public void reserveBook(K2513320_Book book) {
        System.out.println("Cannot borrow. Book is " + getStateName());
    }
}
