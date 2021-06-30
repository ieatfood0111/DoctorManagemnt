/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Common.Patient;
import Common.UserRole;
import Consult.Specialization;
import Doctor.Doctor;
import static User.UserView.validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Admin
 */
public class UserControllerTest {

    UserController uc;
    ArrayList<User> users;
    User user;

    public UserControllerTest() {
        uc = new UserController();
        User user = new User("userTest", "userTest", "userTest", UserRole.USER);
        User user1 = new User("userTest1", "userTest1", "userTest", UserRole.USER);
        User user2 = new User("userTest2", "userTest2", "userTest", UserRole.USER);

        Doctor doctor2 = new Doctor(4, "doctorTest2", Specialization.TIM_MACH, new Date("22/12/2000"),
                null, "doctorTest2", "doctorTest2", "doctorTest2", UserRole.DOCTOR);
        users = new ArrayList<User>();
        users.add(user);
        users.add(user1);
        users.add(user2);
        users.add(doctor2);
        new DataIO().writeData(users);
    }

    @Test
    public void testLogin() throws IOException {
        users = new UserView().getUsers();
        String userName = "admin02";
        String password = "admin02";
        assertTrue("testLogin: ", uc.login(userName, password));
    }

    @Test
    public void testLogin1() throws IOException {
        users = new UserView().getUsers();
        String userName = "admin01";
        String password = "";
        assertFalse("testLogin: ", uc.login(userName, password));

    }

    @Test
    public void testLogin2() throws IOException {
        users = new UserView().getUsers();
        String userName = "";
        String password = "admin01";
        assertFalse("testLogin: ", uc.login(userName, password));
    }

    @Test
    public void testLogin3() throws IOException {
        users = new UserView().getUsers();
        String userName = "++++";
        String password = "++++";
        assertFalse("testLogin: ", uc.login(userName, password));

    }

    @Test
    public void testLogout() {
        uc.logout();
        assertNull(uc.getUser());
    }

    @Test
        public void testSelectSpecialization() {
        Specialization sp = uc.selectSpecialization(1);
        assertEquals(sp, Specialization.valueOf(sp.toString()));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testSelectSpecialization1() {
        Specialization sp = uc.selectSpecialization(7);
        assertEquals(sp, Specialization.valueOf(sp.toString()));
    }

    @Test
    public void testGetUsers() {
        users = uc.getUsers();
        assertNotNull(users);
    }

    @Test
    public void testAddUser() {
        User user = new User("userTestAdd", "userTestAdd", "userTestAdd", UserRole.USER);
        users = new DataIO().readData();
        uc.addUser(user, users);
        new DataIO().writeData(users);
        assertEquals(5, users.size());
    }

    @Test(expected = NullPointerException.class)
    public void testAddUser1() {
        User user = new User("", "", "", UserRole.USER);
        users = new DataIO().readData();
        uc.addUser(user, users);
    }

    @Test
    public void testDeleteUser() {
        users = new DataIO().readData();
        uc.deleteUser("userTest", users);
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.size() + "", users.size(), 3);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteUser1() {
        users = new DataIO().readData();
        uc.deleteUser("doctorTest5", users);
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.size() + "", users.size(), 3);
    }

    @Test(expected = NullPointerException.class)
    public void testDeleteUser2() {
        users = new DataIO().readData();
        uc.deleteUser("", users);
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.size() + "", users.size(), 3);
    }

    @Test
    public void testUpdateUser() {
        User userUpdate = new User("userTest2", "userTestUpdated", "userTestUpdated", UserRole.USER);
        users = new DataIO().readData();
        uc.updateUser(userUpdate, users);
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.get(2).getPassword(), userUpdate.getPassword());
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateUser3() {
        User userUpdate = new User("notFind", "userTestUpdated", "userTestUpdated", UserRole.USER);
        users = new DataIO().readData();
        uc.updateUser(userUpdate, users);
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.get(2).getPassword(), userUpdate.getPassword());
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateUser1() {
        User userUpdate = new User("", "userTestUpdated", "userTestUpdated", UserRole.USER);
        users = new DataIO().readData();
        uc.updateUser(userUpdate, users);
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.get(2).getPassword(), userUpdate.getPassword());
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateUser2() {
        User userUpdate = new User("userTest2", "", "", UserRole.USER);
        users = new DataIO().readData();
        uc.updateUser(userUpdate, users);
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.get(2).getPassword(), userUpdate.getPassword());
    }

    @Test
    public void testGetNewDoctorHighestID() {
        users = new DataIO().readData();
        int id = uc.getNewDoctorHighestID(users);
        assertEquals(id, 5);
    }

    @Test
    public void testGetNewDoctorHighestID1() {
        users = new DataIO().readData();
        int id = uc.getNewDoctorHighestID(users);
        assertNotEquals(id, 6);
    }
}
