/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Common.ConsoleColors;
import Common.UserRole;
import Consult.Specialization;
import Doctor.Doctor;
import Utilities.UserDataIO;
import Utilities.Validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class UserController {

    public static UserController userController = null;
    static User user;
    private UserDataIO userDataIO;
    private Validate validate;
    ArrayList<User> users;
    int count = 0;

    public static UserController getUserController() {
        return userController;
    }

    public static void setUserController(UserController userController) {
        UserController.userController = userController;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDataIO getUserDataIO() {
        return userDataIO;
    }

    public void setUserDataIO(UserDataIO userDataIO) {
        this.userDataIO = userDataIO;
    }

    public Validate getValidate() {
        return validate;
    }

    public void setValidate(Validate validate) {
        this.validate = validate;
    }

    public UserController() {
        userDataIO = new UserDataIO();
        validate = new Validate();
    }

    public static UserController getInstance() {
        if (userController == null) {
            userController = new UserController();
        }

        return userController;
    }

    //Return true if log in successfully
    //Return false if not
    public Boolean login(String userName, String password) {
        try {
            //Doc file
            ArrayList<User> users = UserView.getInstance().getUsers();
            //Read userInput

            for (User u : users) {
                if (u.getUserRole() == UserRole.ADMIN || u.getUserRole() == UserRole.AUTHORIZED_DOCTOR) {
                    if (u.getUserName().equals(userName) && u.getPassword().equals(password)) {
                        user = new User();
                        user.setUserName(userName);
                        user.setPassword(password);
                        user.setUserCode(u.getUserCode());
                        user.setUserRole(u.getUserRole());
                    }
                }
            }
            System.out.println(user);
            return (user != null);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public void addUser(User user, ArrayList<User> users) throws NullPointerException {
        if (user.getUserCode().length() == 0 || user.getUserName().length() == 0 || user.getPassword().length() == 0) {
            throw new NullPointerException();
        }
        users.add(user);
    }

    public void logout() {
        this.user = null;
    }

    public void changePass(User user, String newPassword, String confirmNewPassword, String oldPassword) {
        if (user.getPassword().equals(oldPassword)) {
            if (confirmNewPassword.equals(newPassword)) {
                user.setPassword(newPassword);
                users = userDataIO.readData();
                updateUser(user, users);
                userDataIO.writeData(users);
                System.out.println(ConsoleColors.GREEN_BOLD + "Password changed successfully!!");
            } else {
                System.out.println(ConsoleColors.RED + "Passwords don't match!!");
            }
        } else {
            System.out.println(ConsoleColors.RED + "Wrong password!!");
        }
    }

    public User getLoggedInUser() {
        return this.user;
    }

    public ArrayList<User> getUsers() {
        return userDataIO.readData();
    }

    public int getNewDoctorHighestID(ArrayList<User> users) {
        int id = 0;
        for (User u : users) {
            if (u.getUserRole().equals(UserRole.DOCTOR) || u.getUserRole().equals(UserRole.AUTHORIZED_DOCTOR)) {
                int checkID = ((Doctor) u).getDoctorId();
                if (checkID >= id) {
                    id = checkID;
                }
            }
        }
        return id + 1;
    }

    public void deleteUser(String userCode, ArrayList<User> users) throws NullPointerException {
        User test = null;
        for (User u : users) {
            if (u.getUserCode() != null) {
                if (u.getUserCode().equals(userCode)) {
                    test = u;
                    users.remove(u);
                    break;
                }
            }
        }
        if (test == null) {
            throw new NullPointerException();
        }
    }

    public void updateUser(User userUpdate, ArrayList<User> users) {
        if (userUpdate.getPassword().length() == 0 || userUpdate.getUserCode().length() == 0) {
            throw new NullPointerException();
        }
        User test = null;
        for (User u : users) {
            if (u.getUserCode() != null) {
                if (u.getUserCode().equalsIgnoreCase(userUpdate.getUserCode())) {
                    test = u;
                    u.setUserName(userUpdate.getUserName());
                    u.setPassword(userUpdate.getPassword());
                }
            }
        }
        if (test == null) {
            throw new NullPointerException();
        }
    }

    public void countSpecialization() {
        for (Specialization currentSpecialization : Specialization.values()) {
            count++;
        }
    }

    public Specialization selectSpecialization(int selection) {

        for (Specialization currentSpecialization : Specialization.values()) {
            System.out.println(count + ". " + currentSpecialization.name());
        }
        return Specialization.values()[selection - 1];
    }

}
