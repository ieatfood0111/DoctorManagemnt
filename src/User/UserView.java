/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Admin.Admin;
import Common.UserRole;
import Consult.Specialization;
import Doctor.Doctor;
import Utilities.UserDataIO;
import Utilities.Validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class UserView {

    Scanner in = new Scanner(System.in);
    ArrayList<User> users;
    UserDataIO userDataIO;
    static Validate validate = new Validate();
    public static UserView userView = null;
    int count = 0;

    public UserView() {
        users = new ArrayList<>();
        userDataIO = new UserDataIO();
    }

    public static UserView getInstance() {
        if (userView == null) {
            userView = new UserView();
        }

        return userView;
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

    public ArrayList<User> getUsers() {
        return userDataIO.readData();
    }

    public void addUser(User user) {
        users = userDataIO.readData();
        users.add(user);
        userDataIO.writeData(users);
    }

    public void deleteUser(String userCode) {
        users = userDataIO.readData();
        for (User u : users) {
            if (u.getUserCode() != null) {
                if (u.getUserCode().equals(userCode)) {
                    users.remove(u);
                    break;
                }
            }
        }
        userDataIO.writeData(users);
    }

    public void updateUser(User userUpdate) {
        users = userDataIO.readData();
        users.forEach((u) -> {
            if (u.getUserCode() != null) {
                if (u.getUserCode().equalsIgnoreCase(userUpdate.getUserCode())) {
                    u.setUserName(userUpdate.getUserName());
                    u.setPassword(userUpdate.getPassword());
                }
            }
        });
        userDataIO.writeData(users);
    }

    public String inputUserCode(ArrayList<User> users) throws IOException {
        while (true) {
            String code = validate.getUsername("input new user code: ");
            for (User u : users) {
                if (u.getUserCode() != null) {
                    if (u.getUserCode().equalsIgnoreCase(code)) {
                        code = null;
                        break;
                    }
                }
            }
            if (code == null) {
                System.out.println("this code already exist pls input another one");
            } else {
                return code;
            }
        }
    }

    public String inputUserCodeWithACode(ArrayList<User> users, String code) throws IOException {
        while (true) {
            for (User u : users) {
                if (u.getUserCode() != null) {
                    if (u.getUserCode().equalsIgnoreCase(code)) {
                        code = null;
                        break;
                    }
                }
            }
            if (code == null) {
                return null;
            } else {
                return code;
            }
        }
    }

    public String inputUserName(ArrayList<User> users) throws IOException {
        while (true) {
            String userName = validate.getUsername("Type in the new UserName: ");
            for (User u : users) {
                if (u.getUserName() != null) {
                    if (u.getUserName().equals(userName)) {
                        userName = null;
                        break;
                    }
                }
            }
            if (userName == null) {
                System.out.println("this userName already exist pls input a different one");
            } else {
                return userName;
            }
        }
    }

    public String inputUserNameWithAName(ArrayList<User> users, String userName) throws IOException {
        while (true) {
            for (User u : users) {
                if (u.getUserName() != null) {
                    if (u.getUserName().equals(userName)) {
                        userName = null;
                        break;
                    }
                }
            }
            if (userName == null) {
                return null;
            } else {
                return userName;
            }
        }
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

    // function4.2
    public void inputNewUser() {
        users = getUsers();
        String askPass = "Type in your Password: ";
        String askDoctorSpecialization = "Enter doctor Specialization: ";
        String askDoctorAvailability = "Enter availability: ";
        int selection;
        int choice;
        try {
            System.out.println("what account you want to create\n" + "1.Admin\n" + "2.Authorized_Doctor\n"
                    + "3.Doctor\n" + "4.Normal User\n" + "0.Cancel");
            choice = validate.getINT_LIMIT("Your choice: ", 0, 4);
            if (choice == 0) {
                return;
            }

            String UserCode = inputUserCode(this.users);

            String UserName = inputUserName(this.users);

            String password;
            switch (choice) {
                case 1://admin
                    password = validate.getPassword(askPass);
                    Admin newAdmin = new Admin(UserCode, UserName, password, UserRole.ADMIN);
                    addUser(newAdmin);
                    break;

                case 2://authDoctor
                    String authDocName = validate.getUsername("Enter the doctor name: ");
                    password = validate.getPassword(askPass);
                    int AuthDocID = getNewDoctorHighestID(this.users);

                    Doctor newAuthDoctor = new Doctor(UserCode, UserName, password, UserRole.AUTHORIZED_DOCTOR);

                    newAuthDoctor.setDoctorId(AuthDocID);
                    newAuthDoctor.setName(authDocName);

                    System.out.print(askDoctorSpecialization);
                    selection = boundary.Validate.getINT_LIMIT("Select specialization: ", 1, count);
                    newAuthDoctor.setSpecialization(selectSpecialization(selection));
                    newAuthDoctor.setAvailability(validate.getDate_LimitToCurrent(askDoctorAvailability));
                    addUser(newAuthDoctor);
                    break;

                case 3://doctor
                    String docName = validate.getUsername("Enter the doctor name: ");
                    int docID = getNewDoctorHighestID(this.users);
                    password = validate.getPassword(askPass);

                    Doctor newDoctor = new Doctor(UserCode, UserName, password, UserRole.DOCTOR);
                    newDoctor.setDoctorId(docID);
                    newDoctor.setName(docName);
                    System.out.print(askDoctorSpecialization);
                    selection = boundary.Validate.getINT_LIMIT("Select specialization: ", 1, count);
                    newDoctor.setSpecialization(selectSpecialization(selection));
                    newDoctor.setAvailability(validate.getDate_LimitToCurrent(askDoctorAvailability));
                    addUser(newDoctor);
                    break;

                case 4://normal user
                    password = validate.getPassword("Type in your Password: ");
                    User u = new User(UserCode, UserName, password, UserRole.USER);
                    addUser(u);
                    break;
                case 0:
                    break;
            }

        } catch (IOException e) {
            System.out.println("error inputNewUser");
            System.out.println(e);
        }
    }

    public User askUpdate(User updateMe) throws IOException {
        updateMe.setUserName(inputUserName(this.users));
        while (true) {
            String pass = validate.getPassword("Type in this account new password: ");
            if (pass.equals(validate.getPassword("Confirm account new password: "))) {
                updateMe.setPassword(pass);
                break;
            } else {
                System.out.println("confirm new password is wrong! pls retype new password");
            }
        }
        return updateMe;
    }

    // function4.3
    public void findAndUpdateByUserCode() throws IOException {
        users = getUsers();
        while (true) {
            String code = validate.getUsername("Enter userCode needed to be update: ");
            users = userDataIO.readData();
            for (User find : users) {
                if (find.getUserCode() != null) {
                    if (find.getUserCode().equals(code)) {
                        find = askUpdate(find);
                        updateUser(find);
                        return;
                    }
                }
            }
            System.out.println("Can't find the userCode: " + code);
            System.out.println("please re-Enter the userCode that need to be update");
        }
    }

    // function4.4
    public void findAndDeletedByUserCode() throws IOException {
        users = getUsers();
        String code = validate.getUsername("Enter usercode needed to be deleted: ");
        deleteUser(code);
    }

    public void printOutMenu() throws IOException {
        int choice = 1;
        while (true) {
            System.out.println("--------------------------------\n" + "Option 4 please choose what you want to do\n"
                    + " 1. view list of all user\n" + " 2. add user\n" + " 3. update user\n" + " 4. deleted user\n"
                    + " 0. Back to main menu\n" + "--------------------------------");
            choice = validate.getINT_LIMIT("Choose: ", 0, 4);
            switch (choice) {
                case 1:
                    users = getUsers();
                    System.out.println("List of all User");
                    for (User u : users) {
                        if (u instanceof Doctor) {
                            System.out.print("DoctorID: " + ((Doctor) u).getDoctorId() + "; ");
                        } else if (u instanceof Admin) {
                            System.out.print("Admin user: ");
                        } else {
                            System.out.print("Normal user: ");
                        }
                        System.out.println(u.showUserInfo());
                    }
                    System.out.println();
                    break;
                case 2:
                    inputNewUser();
                    break;
                case 3:
                    findAndUpdateByUserCode();
                    break;
                case 4:
                    findAndDeletedByUserCode();
                    break;
                case 0:
                    return;
                default:
                    break;
            }
            userDataIO.writeData(users);
        }
    }

}
