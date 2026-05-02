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

public class K2513320_UserActionInvoker {

    private Stack<K2513320_Command> history = new Stack<>();

    public void executeCommand(K2513320_Command command) {
        command.execute();
        history.push(command);
        System.out.println("LOG: " + command.getDescription());
    }

    public void undoLast() {
        if (!history.isEmpty()) {
            K2513320_Command last = history.pop();
            last.undo();
            System.out.println("Last action undone.");
        } else {
            System.out.println("No actions to undo.");
        }
    }

    public void viewHistory() {
        System.out.println("~~~~~ Command History ~~~~~");
        for (K2513320_Command cmd : history) {
            System.out.println(cmd.getDescription());
        }
    }
}
