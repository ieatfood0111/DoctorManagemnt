/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Doctor;

import Admin.AdminController;
import Admin.ValidationAdminManager;
import Common.ConsoleColors;
import Common.Patient;
import Common.UserRole;
import Consult.Consult;
import Consult.ConsultManager;
import Consult.Specialization;
import LoginLogout.LogController;
import User.User;
import static User.UserController.userController;
import User.UserView;
import Utilities.UserDataIO;
import Utilities.Validate;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class DoctorController {

    Validate validate;
    UserDataIO userDataIO;
    ValidationAdminManager validationAdminManager;
    ArrayList<User> users;
    ArrayList<User> listUsers;
    ArrayList<Patient> listPatients;
    Doctor doctorGotByUserCode;
    SimpleDateFormat dateFormat;
    int count = 0;

    public DoctorController() {
        validate = new Validate();
        validationAdminManager = new ValidationAdminManager();
        userDataIO = new UserDataIO();
        dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        initMemoryData();
    }

    public void doctorMenu() {
        int choice;
        while (true) {
            try {
                new DoctorView().printDoctorMenu();
                choice = validate.getINT_LIMIT("Your choice: ", 1, 4);
                switchOnMenu(choice);
            } catch (Exception ex) {
                break;
            }
        }
    }

    public void switchOnMenu(int choice) {
        try {
            switch (choice) {
                case 1:
                    processing(userController.getLoggedInUser());
                    break;
                case 2:
                    new AdminController().queryDoctorInfo();
                    break;
                case 3:
                    new UserView().changePassword();
                    break;
                case 4:
                    userController.logout();
                    new LogController().loginMenu();
                    new LogController().mainMenu();
                    return;
                default:
                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public User update(User updateMe, String docName, Specialization sp, Date anvalable) throws IOException {
        ((Doctor) updateMe).setName(docName);
        ((Doctor) updateMe).setSpecialization(sp);
        ((Doctor) updateMe).setAvailability(anvalable);
        return updateMe;
    }

    public void printOut(ArrayList<Patient> list) {
        System.out.println(String.format("%-10s|%-15s|%-15s|%-15s|%-15s", "ID", "NAME", "DESEASE TYPE", "CONSULT DATE", "CONSULT NOTE"));
        for (Patient patient : list) {
            System.out.println(patient.toString(dateFormat));
        }
        System.out.println("");
    }

    public void processing(User loggedInUser) throws IOException {
        initMemoryData();
        listUsers = userDataIO.readData();
        doctorGotByUserCode = (Doctor) validationAdminManager.getDoctorByUserCode(loggedInUser.getUserCode(), listUsers);
        listPatients = doctorGotByUserCode.getPatients();

        if (listPatients.isEmpty()) {
            System.out.println(ConsoleColors.RED + "You don't have any patients yet!!!");
            if (validate.getYesNo("Do you want add a new patient(Y/N): ")) {
                addNewPatient(doctorGotByUserCode);
                userDataIO.writeData(listUsers);
                return;
            }
        } else {
            printOut(listPatients);
        }

        new DoctorView().printMENU_AddUpdatePatient();

        int choice = validate.getINT_LIMIT("Enter choice: ", 1, 2);
        switch (choice) {
            case 1:
                addNewPatient(doctorGotByUserCode);
                userDataIO.writeData(listUsers);
                break;
            case 2:
                if (!listPatients.isEmpty()) {
                    updateAPatient();
                    userDataIO.writeData(listUsers);
                } else {
                    System.out.println(ConsoleColors.RED + "You don't have any patients yet!!!");
                }
                break;
            default:
                break;
        }

    }

    public void addNewPatient(Doctor doctor) throws IOException {
        while (true) {
            int patientid = validate.getINT_LIMIT("Enter patient id: ", 1, Integer.MAX_VALUE);
            Patient patient = validationAdminManager.getPatientByPatientID(patientid, listPatients);
            if (patient != null) {
                System.out.println(ConsoleColors.RED + "ID exist");
                continue;
            }

            String name = validate.getString("Enter name: ");
            String diseaseType = validate.getString("Enter diseaseType: ");
            Date consultDate = validate.getDate_LimitToCurrent("Enter consultDate: ");
            String consultNote = validate.getString("Enter consultNote: ");
            Patient newPa = new Patient(patientid, name, diseaseType, consultDate, consultNote);

            listPatients.add(newPa);

            new ConsultManager().addConsult(doctor,
                    newPa,
                    Specialization.valueOf(diseaseType),
                    consultDate,
                    consultNote);

            break;
        }

    }

    private void updateAPatient() throws IOException {
        while (true) {
            int patientid = validate.getINT_LIMIT("Enter patient id: ", 1, Integer.MAX_VALUE);
            Patient patient = validationAdminManager.getPatientByPatientID(patientid, listPatients);

            if (patient == null) {
                System.out.println(ConsoleColors.RED + "ID is not exist");
                continue;
            }

            String newName = validate.getString("Enter name: ");
            String newDiseaseType = validate.getString("Enter diseaseType: ");
            Date newConsultDate = validate.getDate_LimitToCurrent("Enter consultDate: ");
            String newConsultNote = validate.getString("Enter consultNote: ");

            patient.setName(newName);
            patient.setDiseaseType(newDiseaseType);
            patient.setConsultDate(newConsultDate);
            patient.setConsultNote(newConsultNote);

            Patient newPa = new Patient(patientid,
                    newName, newDiseaseType, newConsultDate, newConsultNote);

            Consult cs = new Consult(patientid,
                    doctorGotByUserCode,
                    newPa,
                    Specialization.valueOf(newDiseaseType),
                    newConsultDate,
                    newConsultNote);

            new ConsultManager().updateConsult(doctorGotByUserCode, patientid, cs);
            break;
        }
    }

    private void initMemoryData() {
        listUsers = new ArrayList<>();
        listPatients = new ArrayList<>();
        doctorGotByUserCode = null;
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

    public void viewDoctor(ArrayList<User> users) throws IOException {
        System.out.println("List of all Doctor");
        for (User u : users) {
            if (u instanceof Doctor) {//check class so only print doctor   
                System.out.print("DoctorID:" + ((Doctor) u).getDoctorId() + "; ");
                System.out.println(u.toString());
            }
        }
    }

    void updateDoc(User docUpdate, ArrayList<User> users) {
        users.forEach((u) -> {
            if (u instanceof Doctor) {
                if (((Doctor) u).getDoctorId() == ((Doctor) docUpdate).getDoctorId()) {
                    u = docUpdate;
                }
            }
        });
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

    public void addUser(User user, ArrayList<User> users) {
        users.add(user);

    }

    public void deleteByCode(String userCode, ArrayList<User> users) {
        System.out.println(users.size());
        User test = null;
        for (User u : users) {
            if (u.getUserCode().equals(userCode)) {
                users.remove(u);
                break;
            }
        }
        if (test == null) {
            throw new NullPointerException();
        }
    }

    public void deleteUser(String userCode) {
        users = userDataIO.readData();
        deleteByCode(userCode, users);
        userDataIO.writeData(users);
    }

    public void updateByUserCode(User userUpdate, ArrayList<User> users) {
        users.forEach((u) -> {
            if (u.getUserCode().equalsIgnoreCase(userUpdate.getUserCode())) {
                u.setUserName(userUpdate.getUserName());
                u.setPassword(userUpdate.getPassword());
            }
        });
    }

    public void updateUser(User userUpdate) {
        users = userDataIO.readData();
        updateByUserCode(userUpdate, users);
        userDataIO.writeData(users);
    }
}
