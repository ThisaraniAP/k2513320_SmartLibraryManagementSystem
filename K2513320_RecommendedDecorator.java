/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.k2513320_smartlibrarymanagementsystem;

/**
 *
 * @author slthi
 */
public class K2513320_RecommendedDecorator extends K2513320_BookDecorator {

    public K2513320_RecommendedDecorator(K2513320_BookComponent book) {
        super(book);
    }

    @Override
    public String getDescription() {
        return  "Recommended";
    }
}
