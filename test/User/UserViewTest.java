/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Common.UserRole;
import Consult.Specialization;
import Doctor.Doctor;
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
public class UserViewTest {

    UserView uv;
    ArrayList<User> users;

    public UserViewTest() {
        uv = new UserView();
        User user = new User("userTest", "userTest", "userTest", UserRole.USER);
        User user1 = new User("userTest1", "userTest1", "userTest", UserRole.USER);
        User user2 = new User("userTest2", "userTest2", "userTest", UserRole.USER);
        Doctor doctor1 = new Doctor(1, "doctorTest1", Specialization.TIM_MACH, new Date("22/12/2000"), null, UserRole.DOCTOR);
        Doctor doctor2 = new Doctor(2, "doctorTest2", Specialization.TIM_MACH, new Date("22/12/2000"), null, UserRole.DOCTOR);
        users = new ArrayList<User>();
        users.add(user);
        users.add(user1);
        users.add(user2);
        users.add(doctor1);
        users.add(doctor2);
        new DataIO().writeData(users);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSelectSpecialization() {
        Specialization sp = uv.selectSpecialization(1);
        assertEquals(sp, Specialization.valueOf(sp.toString()));
    }

    @Test
    public void testGetUsers() {
        users = uv.getUsers();
        assertNotNull(users);
    }

    @Test
    public void testAddUser() {
        User user = new User("userTestChangePassword", "userTestChangePassword", "userTestChangePassword", UserRole.USER);
        users = new DataIO().readData();
        users.add(user);
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.size(), 6);
    }

    @Test
    public void testDeleteUser() {
        users = new DataIO().readData();
        String userCode = "userTest";
        for (User u : users) {
            if (u.getUserCode() != null) {
                if (u.getUserCode().equals(userCode)) {
                    users.remove(u);
                    break;
                }
            }
        }
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.size(), 4);
    }

    @Test
    public void testDeleteUser1() {
        users = new DataIO().readData();
        String userCode = "userTest4";
        for (User u : users) {
            if (u.getUserCode() != null) {
                if (u.getUserCode().equals(userCode)) {
                    users.remove(u);
                    break;
                }
            }
        }
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.size(), 5);
    }

    @Test
    public void testUpdateUser() {
        User userUpdate = new User("userTest2", "userTestUpdated", "userTestUpdated", UserRole.USER);
        users = new DataIO().readData();
        users.forEach((u) -> {
            if (u.getUserCode() != null) {
                if (u.getUserCode().equalsIgnoreCase(userUpdate.getUserCode())) {
                    u.setUserName(userUpdate.getUserName());
                    u.setPassword(userUpdate.getPassword());
                }
            }
        });
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertEquals(users.get(2).getUserName(), userUpdate.getUserName());
    }

    @Test
    public void testUpdateUser1() {
        User userUpdate = new User("userTest7", "userTestUpdated", "userTestUpdated", UserRole.USER);
        users = new DataIO().readData();
        users.forEach((u) -> {
            if (u.getUserCode() != null) {
                if (u.getUserCode().equalsIgnoreCase(userUpdate.getUserCode())) {
                    u.setUserName(userUpdate.getUserName());
                    u.setPassword(userUpdate.getPassword());
                }
            }
        });
        new DataIO().writeData(users);
        users = new DataIO().readData();
        assertNotEquals(users.get(2).getUserName(), userUpdate.getUserName());
    }

    @Test
    public void testInputUserCode() throws Exception {
        String code = uv.inputUserCodeWithACode(users, "userTest2");
        assertNull(code);
    }

    @Test
    public void testInputUserCode1() throws Exception {
        String code = uv.inputUserCodeWithACode(users, "userTest5");
        assertNotNull(code);
    }

    @Test
    public void testInputUserName() throws Exception {
        String name = uv.inputUserNameWithAName(users, "userTest2");
        assertNull(name);
    }

    @Test
    public void testInputUserName1() throws Exception {
        String name = uv.inputUserNameWithAName(users, "userTest5");
        assertNotNull(name);
    }

    @Test
    public void testGetNewDoctorHighestID() {
        users = new DataIO().readData();
        int id = uv.getNewDoctorHighestID(users);
        assertEquals(id, 3);
        
    }


}
