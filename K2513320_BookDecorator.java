/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.k2513320_smartlibrarymanagementsystem;

/**
 *
 * @author slthi
 */
public abstract class K2513320_BookDecorator implements K2513320_BookComponent {
    protected K2513320_BookComponent book;

    public K2513320_BookDecorator(K2513320_BookComponent book) {
        this.book = book;
    }

    @Override
    public String getDescription() {
        return book.getDescription();
    }
}
