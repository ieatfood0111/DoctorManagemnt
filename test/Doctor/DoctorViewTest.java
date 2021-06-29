/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Doctor;

import Common.ConsoleColors;
import Common.Patient;
import Common.UserRole;
import Consult.Specialization;
import User.DataIO;
import User.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

/**
 *
 * @author Admin
 */
public class DoctorViewTest {

    DoctorView dv;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    ArrayList<User> users;
    Doctor doctor1;
    Doctor doctor2;

    public DoctorViewTest() {

        dv = new DoctorView();;
        doctor1 = new Doctor(4, "doctorTest1", Specialization.TIM_MACH, new Date("22/12/2000"),
                null, "doctorTest1", "doctorTest1", "doctorTest1", UserRole.DOCTOR);
        ArrayList<Patient> patients = new ArrayList<>();
        patients.add(new Patient());
        doctor2 = new Doctor(4, "doctorTest2", Specialization.TIM_MACH, new Date("22/12/2000"),
                patients, "doctorTest2", "doctorTest2", "doctorTest2", UserRole.DOCTOR);
        Doctor doctor4 = new Doctor(4, "doctorTest4", Specialization.TIM_MACH, new Date("22/12/2000"),
                patients, "doctorTest4", "doctorTest4", "doctorTest4", UserRole.DOCTOR);
        users = new ArrayList<User>();

        users.add(doctor1);
        users.add(doctor2);
        users.add(doctor4);

        new DataIO().writeData(users);
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPrintDoctorMenu() {
        dv.printDoctorMenu();
    }

}
