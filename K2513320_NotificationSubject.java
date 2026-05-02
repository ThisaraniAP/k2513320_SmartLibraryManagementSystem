/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.k2513320_smartlibrarymanagementsystem;

/**
 *
 * @author slthi
 */

import java.util.ArrayList;
import java.util.List;

public class K2513320_NotificationSubject {
    private final List<K2513320_UserObserver> observers = new ArrayList<>();
    private int notificationCode;
    private String message;

    public void addObserver(K2513320_UserObserver o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    public void removeObserver(K2513320_UserObserver o) {
        observers.remove(o);
    }

    public void setNotification(int notifID, String message) {
        this.notificationCode = notifID;
        this.message = message;
        notifyObservers();
    }

    public void notifyObservers() {
        for (K2513320_UserObserver o : observers) {
            o.update(this);
        }
    }

    public int getNotificationCode() {
        return notificationCode;
    }

    public String getMessage() {
        return message;
    }    
}
