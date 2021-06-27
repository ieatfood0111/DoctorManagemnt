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

    @Test
    public void testSelectSpecialization() {
        Specialization sp = dv.selectSpecialization(1);
        assertEquals(sp, Specialization.valueOf(sp.toString()));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testSelectSpecialization1() {
        Specialization sp = dv.selectSpecialization(7);
    }

    @Test
    public void testGetUsers() {
        users = dv.getUsers();
        assertNotNull(users);
    }

    @Test
    public void testAddUser() {
        Doctor doctor3 = new Doctor(3, "doctorTest3", Specialization.TIM_MACH, new Date("22/12/2000"), null, UserRole.DOCTOR);
        users = new DataIO().readData();
        users.add(doctor3);
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.size(), 4);
    }

    @Test
    public void testDeleteUser() {
        users = new DataIO().readData();
        dv.deleteByCode("doctorTest4", users);
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.size(), 2);
    }

    @Test
    public void testDeleteUser1() {
        users = new DataIO().readData();
        dv.deleteByCode("doctorTest5", users);
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.size(), 3);
    }

    @Test
    public void testUpdateUser() {
        Doctor userUpdate = new Doctor("doctorTest1", "userTestUpdated", "userTestUpdated", UserRole.DOCTOR);
        dv.updateByUserCode(userUpdate, users);
        assertEquals(userUpdate.getPassword(), users.get(0).getPassword());
    }

    @Test
    public void testUpdateUser1() {
        Doctor userUpdate = new Doctor("doctorTest10", "userTestUpdated", "userTestUpdated", UserRole.DOCTOR);
        dv.updateByUserCode(userUpdate, users);
        assertNotEquals(userUpdate.getPassword(), users.get(0).getPassword());
    }

    @Test
    public void testInputUserCode() throws Exception {
        users = new DataIO().readData();
        String code = dv.inputUserCodeWithACode(users, "doctorTest1");
        assertNull(code);
    }

    @Test
    public void testInputUserCode1() throws Exception {
        users = new DataIO().readData();
        String code = dv.inputUserCodeWithACode(users, "notExisted");
        assertNotNull(code);
    }

    @Test
    public void testInputUserName() throws Exception {
        users = new DataIO().readData();
        String name = dv.inputUserNameWithAName(users, "doctorTest1");
        assertNull(name);
    }

    @Test
    public void testInputUserName1() throws Exception {
        users = new DataIO().readData();
        String name = dv.inputUserNameWithAName(users, "userTest5");
        assertNotNull(name);
    }

    @Test
    public void testGetNewDoctorHighestID() {
        users = new DataIO().readData();
        int id = dv.getNewDoctorHighestID(users);
        assertEquals(id, 5);
    }

    @Test
    public void testInputNewDoctor() {
        Doctor doctor3 = new Doctor(3, "doctorTest3", Specialization.TIM_MACH, new Date("22/12/2000"), null, UserRole.DOCTOR);
        users = new DataIO().readData();
        users.add(doctor3);
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.size(), 4);
    }

    @Test
    public void testAskUpdate() throws IOException {
        dv.update(doctor1, "updateDoctor01", Specialization.DA_LIEU, new Date("22/10/2000"));
        assertEquals(doctor1.getName(), "updateDoctor01");
    }

    @Test
    public void testViewDoctor() throws Exception {
        dv.viewDoctor(users);
        
    }


}
