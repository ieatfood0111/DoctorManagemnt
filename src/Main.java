/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Admin.Admin;
import Admin.AdminController;
import Common.ConsoleColors;
import Common.Patient;
import Common.UserRole;
import Consult.ConsultManager;
import Consult.Specialization;
import Doctor.Doctor;
import Doctor.DoctorController;
import Doctor.DoctorView;
import LoginLogout.LogController;
import User.User;
import User.UserController;
import User.UserView;
import Utilities.UserDataIO;
import Utilities.Validate;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Main {

    static ArrayList<User> users;
    static Validate validate;
    static UserController userController;
    static AdminController adminController;
    static DoctorController doctorController;
    static ArrayList<Patient> patient;
    private static ConsultManager consultManager = new ConsultManager();

    public static void main(String[] args) {

        validate = new Validate();
        adminController = new AdminController();
        doctorController = new DoctorController();
        userController = UserController.getInstance();

  

        // new UserDataIO().writeData(users);
        new LogController().loginMenu();
        new LogController().mainMenu();
    }
}
