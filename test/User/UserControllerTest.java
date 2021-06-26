/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Common.UserRole;
import static User.UserView.validate;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class UserControllerTest {

    UserController uc;
    ArrayList<User> users;

    public UserControllerTest() {
        uc = new UserController();

    }

    @Test
    public void testGetInstance() {
    }

    @Test
    public void testLogin() throws IOException {
        users = new UserView().getUsers();
        String userName = "admin02";
        String password = "admin02";
        User user = null;
        boolean check = false;
        for (User u : users) {
            if (u.getUserRole() == UserRole.ADMIN || u.getUserRole() == UserRole.AUTHORIZED_DOCTOR) {
                if (u.getUserName().equals(userName) && u.getPassword().equals(password)) {
                    user = u;
                    check = true;
                    break;
                }
            }
        }
        assertTrue("testLogin: ",check);
    }
   @Test
    public void testLogin1() throws IOException {
        users = new UserView().getUsers();
        String userName = "admin01";
        String password = "";
        User user = null;
        boolean check = false;
        for (User u : users) {
            if (u.getUserRole() == UserRole.ADMIN || u.getUserRole() == UserRole.AUTHORIZED_DOCTOR) {
                if (u.getUserName().equals(userName) && u.getPassword().equals(password)) {
                    user = u;
                    check = true;
                    break;
                }
            }
        }
        assertFalse("testLogin: ",check);
    }
       @Test
    public void testLogin2() throws IOException {
        users = new UserView().getUsers();
        String userName = "";
        String password = "admin01";
        User user = null;
        boolean check = false;
        for (User u : users) {
            if (u.getUserRole() == UserRole.ADMIN || u.getUserRole() == UserRole.AUTHORIZED_DOCTOR) {
                if (u.getUserName().equals(userName) && u.getPassword().equals(password)) {
                    user = u;
                    check = true;
                    break;
                }
            }
        }
        assertFalse("testLogin: ",check);
    }
       @Test
    public void testLogin3() throws IOException {
        users = new UserView().getUsers();
        String userName = "++++";
        String password = "++++";
        User user = null;
        boolean check = false;
        for (User u : users) {
            if (u.getUserRole() == UserRole.ADMIN || u.getUserRole() == UserRole.AUTHORIZED_DOCTOR) {
                if (u.getUserName().equals(userName) && u.getPassword().equals(password)) {
                    user = u;
                    check = true;
                    break;
                }
            }
        }
        assertFalse("testLogin: ",check);
    }

    @Test

    public void testLogout() {
    }

    @Test
    public void testChangePassword() {
    }

    @Test
    public void testGetLoggedInUser() {
    }

}
