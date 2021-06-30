/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Common.Patient;
import Common.UserRole;
import Consult.Specialization;
import Doctor.Doctor;
import User.User;
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class AdminControllerTest {

    AdminController ac;
    Doctor doctor1, doctor2;
    ArrayList<User> users;

    public AdminControllerTest() {
        ac = new AdminController();

        doctor1 = new Doctor(1, "doctorTest1", Specialization.TIM_MACH, new Date("22/12/2000"),
                null, "doctorTest1", "doctorTest1", "doctorTest1", UserRole.DOCTOR);

        ArrayList<Patient> patients = new ArrayList<>();

        patients.add(new Patient());
        patients.add(new Patient());

        doctor2 = new Doctor(2, "doctorTest2", Specialization.TIM_MACH, new Date("22/12/2000"),
                patients, "doctorTest2", "doctorTest2", "doctorTest2", UserRole.DOCTOR);

        //patients.removeAll(patients);
        Doctor doctor4 = new Doctor(4, "doctorTest4", Specialization.TIM_MACH, new Date("22/12/2000"),
                patients, "doctorTest4", "doctorTest4", "doctorTest4", UserRole.DOCTOR);
        users = new ArrayList<User>();
        users.add(doctor1);
        users.add(doctor2);
        users.add(doctor4);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAdminMenu() {
    }

    @Test
    public void testFunctionBlock5() {
    }

    @Test
    public void testProcessing() throws Exception {
    }

    @Test(expected = NullPointerException.class)
    public void testQueryDoctorInfo() throws Exception {
        //test found the doctor but dont have patients list
        ac.show(users, 1);
        //test found the doctor but patients list empty
        ac.show(users, 4);
        //test not found the doctor
        ac.show(users, 10);
    }

    @Test
    public void testQueryDoctorInfo1() throws Exception {
        //found the doctor and display succesful
        ac.show(users, 2);

    }

}
