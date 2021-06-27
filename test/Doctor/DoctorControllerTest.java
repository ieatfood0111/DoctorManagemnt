/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Doctor;

import Common.Patient;
import Common.UserRole;
import Consult.Specialization;
import User.DataIO;
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
public class DoctorControllerTest {

    DoctorController dc;
    ArrayList<User> users;
    Doctor doctor1;
    Doctor doctor2;

    
    public DoctorControllerTest() {
        dc = new DoctorController();
        doctor1 = new Doctor(1, "doctorTest1", Specialization.TIM_MACH, new Date("22/12/2000"), new ArrayList<Patient>(), UserRole.DOCTOR);
        ArrayList<Patient> patients = new ArrayList<>();
        patients.add(new Patient());
        doctor2 = new Doctor(2, "doctorTest2", Specialization.TIM_MACH, new Date("22/12/2000"), patients, UserRole.DOCTOR);
        users = new ArrayList<User>();
        users.add(doctor1);
        users.add(doctor2);
       
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDoctorMenu() {
        dc.switchOnMenu(1);
    }

    @Test()
    public void testProcessing() throws Exception {
        assertEquals(doctor1.getPatients().size(), 0);
        assertNotEquals(doctor2.getPatients().size(), 0);
    }

}
