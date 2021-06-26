/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import User.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public final class UserDataIO {

    private static final String USER_SAVE_FILE_NAME = "users.dat";

    // ******************* Main methods *******************
    /**
     * Read list of users from binary file
     *
     * @return
     */
    public static ArrayList<User> readData() {
        ArrayList<User> users = new ArrayList<>();
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(USER_SAVE_FILE_NAME));
            users = (ArrayList<User>) in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (in == null) {
            return users;
        } else {
            try {
                in.close();
            } catch (Exception ex) {
                Logger.getLogger(Utilities.UserDataIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return users;
    }

    /**
     * Write list of users to file
     *
     * @param users
     */
    public static void writeData(List<User> users) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(USER_SAVE_FILE_NAME, false);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (fos != null && oos != null) {
                try {
                    oos.close();
                    fos.close();
                } catch (Exception ex) {
                    Logger.getLogger(Utilities.UserDataIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

            }
        }
    }

    /**
     * Check if hash-ed string match.
     *
     * @param userPassword
     * @param passwordHash
     * @param salt
     * @return Match: true | Not match: false
     */
    public static boolean verifyPassword(String userPassword, String salt, String passwordHash) {
        String password = hashPassword(userPassword, salt);
        if (password == null) {
            return false;
        } else {
            return password.equals(passwordHash);
        }
    }

    /**
     * Part of login function, use to hash password.
     *
     * @param userPassword
     * @param salt
     * @return hashed password string
     */
    public static String hashPassword(String userPassword, String salt) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((userPassword + salt).getBytes());
            byte[] bytesResult = md.digest();
            result = String.format("%064x", new BigInteger(1, bytesResult));
        } catch (NoSuchAlgorithmException ex) {

        }
        return result;
    }
}
