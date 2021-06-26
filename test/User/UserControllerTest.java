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
    public void testChangePassword() {
        user = new User("userTestChangePassword", "userTestChangePassword", UserRole.USER);

        String oldPassword = "userTestChangePassword";
        String newPassword = "123";
        String confirmNewPassword = "123";

        uc.changePass(user, newPassword, confirmNewPassword, oldPassword);
        assertEquals(user.getPassword(), "123");
    }

    @Test
    public void testChangePassword1() {
        user = new User("userTestChangePassword", "userTestChangePassword", UserRole.USER);

        String oldPassword = "userTestChangePasswordNotRight";
        String newPassword = "123";
        String confirmNewPassword = "123";

        uc.changePass(user, newPassword, confirmNewPassword, oldPassword);
        assertEquals(user.getPassword(), "userTestChangePassword");
    }

    @Test
    public void testChangePassword2() {
        user = new User("userTestChangePassword", "userTestChangePassword", UserRole.USER);

        String oldPassword = "userTestChangePassword";
        String newPassword = "1234";
        String confirmNewPassword = "123";

        uc.changePass(user, newPassword, confirmNewPassword, oldPassword);
        assertEquals(user.getPassword(), "userTestChangePassword");
    }

    @Test
    public void testChangePassword3() {
        user = new User("userTestChangePassword", "userTestChangePassword", UserRole.USER);

        String oldPassword = "userTestChangePassword";
        String newPassword = "123";
        String confirmNewPassword = "1234";

        uc.changePass(user, newPassword, confirmNewPassword, oldPassword);
        assertEquals(user.getPassword(), "userTestChangePassword");
    }


}
