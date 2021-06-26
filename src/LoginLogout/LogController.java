/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginLogout;

import Admin.AdminController;
import Common.ConsoleColors;
import Common.UserRole;
import User.User;
import static User.UserController.userController;
import Utilities.Validate;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class LogController {

    LogView logView = new LogView();
    Validate validate = new Validate();

    public void loginMenu() {
        int choice;
        while (true) {
            try {
                logView.printLoginMenu();
                choice = validate.getINT_LIMIT("Your choice: ", 0, 1);
                Boolean isLoggedIn = false;
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        String userName = validate.getString("Input username: ");
                        String password = validate.getString("Input password: ");
                        isLoggedIn = userController.login(userName, password);
                        break;
                }

                if (isLoggedIn) {
                    System.out.println(ConsoleColors.PURPLE_BOLD + "LOGGED IN SUCCESSFULLY!!");
                    break;
                } else {
                    System.out.println(ConsoleColors.RED_BOLD + "LOGGED IN FAILED!!");
                }
            } catch (IOException ex) {

            }
        }
    }

    public static void mainMenu() {
        User user = userController.getLoggedInUser();
        if (user != null) {
            if (user.getUserRole() == UserRole.ADMIN) {
                new AdminController().adminMenu();
            } else if (user.getUserRole() == UserRole.AUTHORIZED_DOCTOR) {
                //doctorMenu();
            }
        }
    }
}
