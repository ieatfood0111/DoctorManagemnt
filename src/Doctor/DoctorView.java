/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Doctor;

import Common.ConsoleColors;
import Common.UserRole;
import Consult.Specialization;
import User.User;
import Utilities.UserDataIO;
import Utilities.Validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class DoctorView {

    private final Scanner in = new Scanner(System.in);
    private ArrayList<User> users;
    private final UserDataIO userDataIO;
    private static final Validate validate = new Validate();

    public DoctorView() {
        users = new ArrayList<>();
        userDataIO = new UserDataIO();
    }
    public  void printDoctorMenu() {
        System.out.println(ConsoleColors.BLUE_BOLD + "--------------------------------");
        System.out.println(ConsoleColors.BLUE_BOLD + "DOCTOR PANEL");
        System.out.println(ConsoleColors.BLUE_BOLD + "1. Add/Update Patient info");
        System.out.println(ConsoleColors.BLUE_BOLD + "2. View Doctor info incl. Patient info");
        System.out.println(ConsoleColors.BLUE_BOLD + "3. Change password");
        System.out.println(ConsoleColors.BLUE_BOLD + "4. Log out");
        System.out.println(ConsoleColors.BLUE_BOLD + "--------------------------------");
    }
    public Specialization selectSpecialization() {
        int count = 0;
        for (Specialization currentSpecialization : Specialization.values()) {
            count++;
            System.out.println(count + ". " + currentSpecialization.name());
        }
        return Specialization.values()[boundary.Validate.getINT_LIMIT("Select specialization: ", 1, count) - 1];
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
            if (u.getUserCode().equals(userCode)) {
                users.remove(u);
                break;
            }
        }
        userDataIO.writeData(users);
    }

    public void updateUser(User userUpdate) {
        users = userDataIO.readData();
        users.forEach((u) -> {
            if (u.getUserCode().equalsIgnoreCase(userUpdate.getUserCode())) {
                u.setUserName(userUpdate.getUserName());
                u.setPassword(userUpdate.getPassword());
            }
        });
        userDataIO.writeData(users);
    }

    public String inputUserCode() throws IOException {
        while (true) {
            String code = validate.getString("Input the new code: ");
            for (User u : users) {
                if (u.getUserCode() != null) {
                    if (u.getUserCode().equalsIgnoreCase(code)) {
                        code = null;
                        break;
                    }
                }
            }
            if (code == null) {
                System.out.println("This code already exist pls input another one");
            } else {
                return code;
            }
        }
    }

    public String inputUserName() throws IOException {
        while (true) {
            String userName = validate.getUsername("Type in the new doctor UserName: ");
            for (User u : users) {
                if (u.getUserName() != null) {
                    if (u.getUserName().equals(userName)) {
                        userName = null;
                        break;
                    }
                }
            }
            if (userName == null) {
                System.out.println("This User Name already exist pls input a different one");
            } else {
                return userName;
            }
        }
    }

    public int getNewDoctorHighestID() {
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
    public void inputNewDoctor() {
        users = getUsers();
        String askPass = "Type in the Password: ";
        String askDoctorSpecialization = "Enter doctor Specialization: ";
        String askDoctorAvailability = "Enter availability: ";
        int choice;
        String userCode;
        String userName;
        String password;
        int authDocID;
        try {
            System.out.println("what doctor type do you want to create\n"
                    + "1.Authorized_Doctor\n"
                    + "2.Doctor\n"
                    + "0.Cancel");
            choice = validate.getINT_LIMIT("Your choice: ", 0, 2);
            if (choice == 0) {
                return;
            }
            System.out.print("Enter the doctor name: ");
            String docName = in.nextLine();
            switch (choice) {
                case 1:
                    userCode = inputUserCode();
                    userName = inputUserName();
                    password = validate.getPassword(askPass);

                    authDocID = getNewDoctorHighestID();
                    
                    Doctor newAuthDoctor = new Doctor(userCode, userName, password, UserRole.AUTHORIZED_DOCTOR);
                    newAuthDoctor.setDoctorId(authDocID);
                    newAuthDoctor.setName(docName);
                    System.out.print(askDoctorSpecialization);
                    newAuthDoctor.setSpecialization(selectSpecialization());
                    newAuthDoctor.setAvailability(validate.getDate_LimitToCurrent(askDoctorAvailability));

                    addUser(newAuthDoctor);
                    break;

                case 2:
                    userCode = inputUserCode();
                    userName = inputUserName();
                    password = validate.getPassword(askPass);
                    
                    authDocID = getNewDoctorHighestID();
                    
                    Doctor newDoctor = new Doctor(userCode, userName, password, UserRole.DOCTOR);
                    
                    newDoctor.setDoctorId(authDocID);
                    newDoctor.setName(docName);
                    System.out.println(askDoctorSpecialization);
                    newDoctor.setSpecialization(selectSpecialization());
                    newDoctor.setAvailability(validate.getDate_LimitToCurrent(askDoctorAvailability));

                    addUser(newDoctor);
                    break;
                case 0:
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
            System.out.println("Error input a new doctor");
            System.out.println(e);
        }
    }

    public User askUpdate(User updateMe) throws IOException {
        if (updateMe.getUserRole().equals(UserRole.AUTHORIZED_DOCTOR)) {
            updateMe.setUserName(inputUserName());
            while (true) {
                String pass = validate.getPassword("Type in this account new password: ");
                if (pass.equals(validate.getPassword("Confirm account new password: "))) {
                    updateMe.setPassword(pass);
                    break;
                } else {
                    System.out.println("confirm new password is wrong! pls retype new password");
                }
            }
        }
        String docName = validate.getString("Enter the doctor update name: ");
        ((Doctor) updateMe).setName(docName);
        System.out.print("Enter the doctor update Specialization: ");
        ((Doctor) updateMe).setSpecialization(selectSpecialization());
        
        ((Doctor) updateMe).setAvailability(validate.getDate_LimitToCurrent("Enter doctor update availability: "));

        return updateMe;
    }

    void updateDoc(User docUpdate) {
        users = userDataIO.readData();
        users.forEach((u) -> {
            if (u instanceof Doctor) {
                if (((Doctor) u).getDoctorId() == ((Doctor) docUpdate).getDoctorId()) {
                    u = docUpdate;
                }
            }
        });
        userDataIO.writeData(users);
    }

    // function4.3
    public void findAndUpdateByDoctorID() throws IOException {
        users = getUsers();
        
        while (true) {
            int id = validate.getINT("Enter doctorID that needed to be update");
            users = userDataIO.readData();
            for (User find : users) {
                if (find instanceof Doctor) {//in all user if that a doctor check id
                    if (((Doctor) find).getDoctorId() == id) {
                        find = askUpdate(find);
                        updateDoc(find);
                        return;
                    }
                }
            }
            System.out.println("Can't find the Doctor: " + id);
            System.out.println("please re-Enter the userCode that need to be update");
        }
    }

    // function4.4
    public void findAndDeletedByDoctorID() throws IOException {
        users = getUsers();
        int id = validate.getINT("Enter doctorID that needed to be deleted");
        users = userDataIO.readData();
        for (User find : users) {
            if (find instanceof Doctor) {
                if (((Doctor) find).getDoctorId() == id) {
                    users.remove(find);
                    return;
                }
            }
        }
        userDataIO.writeData(users);
    }

    public void viewDoctor() throws IOException {
        users = getUsers();
        System.out.println("List of all Doctor");
        for (User u : users) {
            if (u instanceof Doctor) {//check class so only print doctor   
                System.out.print("DoctorID:" + ((Doctor) u).getDoctorId() + "; ");
                System.out.println(u.toString());
            }
        }
    }

    public void menu() {
        System.out.println("--------------------------------\n"
                + "Option 1 please choose what you want to do\n"
                + " 1. view list of all doctor\n"
                + " 2. add new doctor\n"
                + " 3. update doctor\n"
                + " 4. deleted doctor\n"
                + " 0. Back to main menu\n"
                + "--------------------------------");
    }

    //
    public void doFunction1() throws IOException {
        int choice = 1;
        while (true) {
            menu();
            choice = validate.getINT_LIMIT("Choose: ", 0, 4);
            switch (choice) {
                case 1:
                    viewDoctor();
                    break;
                case 2:
                    inputNewDoctor();
                    break;
                case 3:
                    findAndUpdateByDoctorID();
                    break;
                case 4:
                    findAndDeletedByDoctorID();
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
