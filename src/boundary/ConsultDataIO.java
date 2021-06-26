package boundary;

import Consult.Consult;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConsultDataIO {

    private static final String CONSULT_SAVE_FILE_NAME = "consults.dat";

    // ******************* Main methods *******************
    /**
     * Read list of consults from binary file
     *
     * @return
     */
    public static ArrayList<Consult> readData() {
        ArrayList<Consult> users = null;
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(CONSULT_SAVE_FILE_NAME));
            users = (ArrayList<Consult>) in.readObject();

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
     * Write list of consults to file
     *
     * @param users
     */
    public static void writeData(List<Consult> users) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(CONSULT_SAVE_FILE_NAME);
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
                    Logger.getLogger(Utilities.UserDataIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

            }
        }
    }
}
