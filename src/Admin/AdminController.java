/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Common.ConsoleColors;
import Common.Patient;
import Common.UserRole;
import Consult.Consult;
import Consult.ConsultManager;
import Consult.Specialization;
import Utilities.Validate;
import java.io.IOException;
import Doctor.Doctor;
import Doctor.DoctorView;
import LoginLogout.LogController;
import User.User;
import static User.UserController.userController;
import User.UserView;
import Utilities.UserDataIO;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class AdminController {

    Validate validate;
    ValidationAdminManager adminManager;
    UserDataIO userDataIO;

    ArrayList<User> listUsers;
    ArrayList<Patient> listPatients;
    Doctor doctorGotByUserCode;

    SimpleDateFormat dateFormat;

    ConsultManager consultManager = new ConsultManager();

    public AdminController() {
        validate = new Validate();
        adminManager = new ValidationAdminManager();
        userDataIO = new UserDataIO();
        initMemoryData();
        dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
    }

    public void adminMenu() {
        int choice;
        while (true) {
            try {
                AdminView.printAdminMenu();
                choice = validate.getINT_LIMIT("Your choice: ", 1, 7);
                switch (choice) {
                    case 1:
                        DoctorView d = new DoctorView();
                        d.doFunction1();
                        break;
                    case 2:
                        User user = userController.getLoggedInUser();
                        processing(user);
                        break;
                    case 3:
                        queryDoctorInfo();
                        break;
                    case 4:
                        UserView u = new UserView();
                        u.printOutMenu();
                        break;
                    case 5:
                        functionBlock5();
                        break;
                    case 6:
                      
                        userController.changePassword();
                        break;
                    case 7:
                        userController.logout();
                        new LogController().loginMenu();
                        new LogController().mainMenu();
                        return;
                    default:
                        break;
                }
            } catch (IOException ex) {

                break;
            }
        }
    }

    public void functionBlock5() {
        consultManager.printUserByDiseaseType();
    }

    public void processing(User user1) throws IOException {
        //--------Đọc data, xóa sau
        initMemoryData();
        while (true) {
            listUsers = userDataIO.readData();

            System.out.println(ConsoleColors.BLUE_BOLD + "LIST DOCTOR");
            System.out.println(String.format("%-10s|%-20s", "USERCODE", "NAME"));
            for (User user : listUsers) {
                if (user.getUserRole() == UserRole.AUTHORIZED_DOCTOR) {
                    System.out.println(user);
                }
            }
            System.out.println("");

            while (true) {
                String usercode = validate.getString("Enter usercode: ");
                doctorGotByUserCode = (Doctor) adminManager.getDoctorByUserCode(usercode, listUsers);
                if (doctorGotByUserCode == null) {
                    System.out.println(ConsoleColors.RED + "This usercode does not exist,  enter a new usercode ");
                } else {
                    break;
                }
            }

            listPatients = doctorGotByUserCode.getPatients();

            if (listPatients.isEmpty()) {
                System.out.println(ConsoleColors.RED + "List patient's this doctor is emty");
            } else {
                System.out.println(ConsoleColors.BLUE_BOLD + "LIST PATIENT");
                System.out.println(String.format("%-10s|%-15s|%-15s|%-15s|%-15s", "ID", "NAME", "DESEASE TYPE", "CONSULT DATE", "CONSULT NOTE"));
                listPatients.forEach((patient) -> {
                    System.out.println(patient.toString(dateFormat));
                });
                System.out.println("");
            }

            printMENU_AddUpdatePatient();

            int selection = validate.getINT_LIMIT("Enter selection: ", 1, 2);
            switch (selection) {
                case 1:

                    addNewPatient(doctorGotByUserCode);

                    userDataIO.writeData(listUsers);
                    break;
                case 2:

                    if (!listPatients.isEmpty()) {

                        updateAPatient(user1, doctorGotByUserCode);

                        userDataIO.writeData(listUsers);
                    } else {
                        System.out.println("This doctor is not in charge of any patient");
                    }
                    break;
            }
            break;
        }
    }

    private void printMENU_AddUpdatePatient() {
        System.out.println(ConsoleColors.BLUE_BOLD + "-----------------------------------");
        System.out.println(ConsoleColors.BLUE_BOLD + "1. Add new a patient");
        System.out.println(ConsoleColors.BLUE_BOLD + "2. Update a patient");
        System.out.println(ConsoleColors.BLUE_BOLD + "-----------------------------------");
    }

    private void addNewPatient(Doctor doctor) throws IOException {
        while (true) {
            int patientid = validate.getINT_LIMIT("Enter patient id: ", 1, Integer.MAX_VALUE);
            Patient patient = adminManager.getPatientByPatientID(patientid, listPatients);
            if (patient != null) {
                System.out.println(ConsoleColors.RED + "ID exist");
                continue;
            }

            String name = validate.getString("Enter name: ");
            String diseaseType = validate.getSpec("Enter diseaseType: ");
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

    private void updateAPatient(User user, Doctor doctor) throws IOException {
        while (true) {
            int patientid = validate.getINT_LIMIT("Enter patient id: ", 1, Integer.MAX_VALUE);
            Patient patient = adminManager.getPatientByPatientID(patientid, listPatients);
            if (patient == null) {
                System.out.println(ConsoleColors.RED + "ID is not exist");
                continue;
            }

            String newName = validate.getString("Enter name: ");
            String newDiseaseType = validate.getSpec("Enter diseaseType: ");
            Date newConsultDate = validate.getDate_LimitToCurrent("Enter consultDate: ");
            String newConsultNote = validate.getString("Enter consultNote: ");

            patient.setName(newName);
            patient.setDiseaseType(newDiseaseType);
            patient.setConsultDate(newConsultDate);
            patient.setConsultNote(newConsultNote);

            Patient newPa = new Patient(patientid,
                    newName, newDiseaseType, newConsultDate, newConsultNote);

            Consult cs = new Consult(patientid,
                    doctor,
                    newPa,
                    Specialization.valueOf(newDiseaseType),
                    newConsultDate,
                    newConsultNote);

            new ConsultManager().updateConsult(user, patientid, cs);

            break;
        }
    }

    public void queryDoctorInfo() throws IOException {
        while (true) {
            listUsers = userDataIO.readData();
            System.out.println(ConsoleColors.BLUE_BOLD + "List of all doctors: ");
            listUsers.forEach(u -> {
                if (u.getUserRole() == UserRole.DOCTOR || u.getUserRole() == UserRole.AUTHORIZED_DOCTOR) {
                    Doctor doc = (Doctor) u;
                    System.out.println(doc.getDoctorId() + " | " + doc.getName());
                }
            });

            int doctorCode = validate.getINT("Enter doctor code (Enter 0 to exit): ");

            if (doctorCode == 0) {
                break;
            }

            listUsers.forEach(u -> {
                if (u.getUserRole() == UserRole.DOCTOR || u.getUserRole() == UserRole.AUTHORIZED_DOCTOR) {
                    Doctor doc = (Doctor) u;
                    if (doc.getDoctorId() == doctorCode) {
                        System.out.println(ConsoleColors.BLUE_BOLD + "DoctorCode : " + doc.getDoctorId() + "| DoctorName " + doc.getName());
                        System.out.println(ConsoleColors.BLUE_BOLD + "Availability : " + doc.getAvailability() + "| Spec: " + doc.getSpecialization());
                        System.out.println(ConsoleColors.BLUE_BOLD + "Patients : ");

                        doc.getPatients().forEach(p -> {
                            System.out.println(ConsoleColors.PURPLE_BOLD + "PatientName: " + p.getName() + " |PatientDisease: " + p.getDiseaseType() + " |Date: " + p.getConsultDate());
                            System.out.println(ConsoleColors.PURPLE_BOLD + "Note: " + p.getConsultNote());
                            System.out.println("***");
                        });

                        System.out.println("------------");
                    }
                }
            });
        }
    }

    private void initMemoryData() {
        listUsers = new ArrayList<>();
        listPatients = new ArrayList<>();
        doctorGotByUserCode = null;
    }

}
