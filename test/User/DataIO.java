/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Utilities.UserDataIO;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class DataIO {
     public ArrayList<User> readData() {
        ObjectInputStream in = null;
        ArrayList<User> users = null;
        try {
            in = new ObjectInputStream(new FileInputStream("testUsers.dat"));
            users = (ArrayList<User>) in.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (in == null) {
            return users;
        } else {
            try {
                in.close();
            } catch (Exception ex) {
                Logger.getLogger(UserDataIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return users;
    }

    public void writeData(List<User> users) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream("testUsers.dat");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
        } catch (Exception ioe) {
            return;
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
