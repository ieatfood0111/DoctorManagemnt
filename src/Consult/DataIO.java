/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Consult;

import Utilities.UserDataIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
 * @param <T>
 */
public final class DataIO<T> {

    private String Consult_SAVE_FILE_NAME;

    public DataIO() {
    }

    public DataIO(String USER_SAVE_FILE_NAME) {
        this.Consult_SAVE_FILE_NAME = USER_SAVE_FILE_NAME;
    }

    // ******************* Main methods *******************
    /**
     * Read list of users from binary file
     *
     * @return
     */
    public ArrayList<T> readData() {
        ArrayList<T> users = new ArrayList<>();
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(Consult_SAVE_FILE_NAME));
            users = (ArrayList<T>) in.readObject();
            System.out.println(Consult_SAVE_FILE_NAME);
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
    public void writeData(List<T> users) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(Consult_SAVE_FILE_NAME, false);
            PrintWriter writer = new PrintWriter(new File(Consult_SAVE_FILE_NAME));
            System.out.println(Consult_SAVE_FILE_NAME + "2");
            writer.print("");
            writer.close();
            oos = new ObjectOutputStream(fos);
            oos.writeObject(users);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (fos != null && oos != null) {
                try {
                    oos.close();
                    fos.close();
                } catch (Exception ex) {
                    Logger.getLogger(UserDataIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

            }
        }
    }
}
