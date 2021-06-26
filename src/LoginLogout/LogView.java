/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginLogout;

import Common.ConsoleColors;

/**
 *
 * @author Admin
 */
public class LogView {
    
        public  void printLoginMenu() {
        System.out.println(ConsoleColors.BLUE_BOLD + "--------------------------------");
        System.out.println(ConsoleColors.BLUE_BOLD + "Welcome to Doctor Management Program");
        System.out.println(ConsoleColors.BLUE_BOLD + "1. Login");
        System.out.println(ConsoleColors.BLUE_BOLD + "0. Exit");
        System.out.println(ConsoleColors.BLUE_BOLD + "--------------------------------");
    }
        
}
