package Consult;

import Common.Patient;
import Common.UserRole;
import Doctor.Doctor;
import User.User;
import boundary.Validate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class ConsultManager {

    private DataIO<Consult> dataIO = new DataIO<>("consults.dat");
    private ArrayList<Consult> consultList;

    public ConsultManager() {
        this.consultList = dataIO.readData();
    }

    public ArrayList<Consult> getConsultList() {
        return consultList;
    }

    // ******************* Helper methods *******************
    /**
     * Load user list from file
     */
    public void loadConsultList() {
        consultList = dataIO.readData();
    }

    /**
     * Save user list to file
     */
    public void saveConsultList() {

       
        dataIO.writeData(consultList);

    }

    /**
     * Get last consult ID in consult list (for auto increment ID)
     *
     * @return
     */
    private int getLastConsultID() {
        if (consultList.size() > 0) {
            return consultList.get(consultList.size() - 1).getConsultID();
        } else {
            return -1;
        }
    }

    /**
     * Get consult from consult list
     *
     * @param consultID
     * @return
     */
    public Consult getConsult(int consultID) {
        for (Consult consult : consultList) {
            if (consult.getConsultID() == consultID) {
                return consult;
            }
        }
        return null;
    }

    public Consult getConsult(Consult consult) {
        return getConsult(consult.getConsultID());
    }

    public Patient getPatient(Doctor ofDoctor, int patientCode) {
        for (int i = 0; i < consultList.size(); i++) {
            Consult currentConsult = consultList.get(i);
            if (currentConsult.getDoctor().equals(ofDoctor)) {
                if (currentConsult.getPatient().getPatientId() == patientCode) {
                    return currentConsult.getPatient();
                }
            }
        }
        return null;
    }

    // ***************************************************
    //     Main methods
    // ***************************************************
    /**
     * Add a consult to a doctor
     *
     * @param ofDoctor
     */
    public void addConsult(Doctor ofDoctor, Patient patient) {
        if ((ofDoctor != null) && (patient != null)) {
            Specialization specialization = ofDoctor.getSpecialization();
            Date date = Validate.getDate("Date: ");
            String note = Validate.getString("Note: ");
            addConsult(ofDoctor, patient, specialization, date, note);
        } else {
            System.out.println("Patient not found");
        }
    }

    public void addConsult(Doctor doctor, Patient patient,
            Specialization diseaseType,
            Date consultTime, String note) {
        
        consultList.add(
                new Consult(
                        getLastConsultID() + 1,
                        doctor, patient, diseaseType, consultTime, note));
        
        saveConsultList();
    }

    public void updateConsult() {
        Consult toUpdate = getConsult(Validate.getINT("Consult ID: "));
        if (toUpdate != null) {
            toUpdate.setConsultTime(Validate.getDate("Consult time: "));
            toUpdate.setDiseaseType(Specialization.TIEU_HOA);
        }
        saveConsultList();
    }

    public void updateConsult(User currentUser, int consultID, Consult consultUpdate) {    
        Consult toUpdate = getConsult(consultID-1);
        if (toUpdate != null) {
            
            toUpdate.setConsultTime(consultUpdate.getConsultTime());
            toUpdate.setDiseaseType(consultUpdate.getDiseaseType());
            
            if (currentUser.getUserRole() == UserRole.ADMIN) {
                toUpdate.setDoctor(consultUpdate.getDoctor());
                toUpdate.setPatient(consultUpdate.getPatient());
            } 
            toUpdate.setNote(consultUpdate.getNote());
        } else {
            System.out.println("Consult " + consultID + " not found");
        }
        
        saveConsultList();
    }

    /**
     * Delete a consult from consult list
     *
     * @param consultID

    /**
     * Delete all consults with a doctor
     *
     * @param doctor
     */



    // ***************************************************
    //     Print methods
    // ***************************************************
    public void printConsultLists(User currentUser) {
        if (consultList.size() > 0) {

            if (currentUser.getUserRole() == UserRole.ADMIN) {
                for (Consult currentConsult : consultList) {
                    System.out.printf("%s\n", currentConsult.toString());
                }
            }

        }
    }

    public void printConsultLists(User currentUser, Doctor doctor) {
        if (consultList.size() > 0) {

            if (currentUser.getUserRole() == UserRole.ADMIN) {
                for (Consult currentConsult : consultList) {
                    if (currentConsult.getDoctor().equals(doctor)) {
                        System.out.printf("%s\n", currentConsult.toString());
                    }
                }
            }

        }
    }

    public void printPatientList(Doctor doctor) {
        System.out.println(doctor.toString());
        loadConsultList();
        if (consultList.size() > 0) {
            for (Consult currentConsult : consultList) {
                if (currentConsult.getDoctor().equals(doctor)) {
                    System.out.printf("%s\n", currentConsult.getPatient().toString());
                }
            }
        }
    }

    public void printUserByDiseaseType() {
        loadConsultList();

        System.out.println(consultList.size());
        if (consultList.size() > 0) {

            Specialization currentSpecialization = consultList.get(0).getDiseaseType();
            System.out.printf("%s\n", currentSpecialization.name());

            for (Consult currentConsult : consultList) {

                if (currentSpecialization != currentConsult.getDiseaseType()) {

                    currentSpecialization = currentConsult.getDiseaseType();
                    System.out.printf("%s\n", currentSpecialization.name());
                }
                Patient current = currentConsult.getPatient();
                System.out.format("%-10s|%-15s|%-15s|%-15s|%-15s\n",
                        current.getPatientId(), current.getName(),
                        current.getDiseaseType(),
                        current.getConsultDate(),
                        current.getConsultNote());

            }

        }
    }

    // ***************************************************
    //     Sort methods
    // ***************************************************
    public void sortByDiseaseType() {
        consultList.sort(new sortByDiseaseType());
    }
}
// ******************* Sorting class *******************

class sortByDiseaseType implements Comparator<Consult> {

    @Override
    public int compare(Consult o1, Consult o2) {
        return o1.getDiseaseType().name().compareTo(o2.getDiseaseType().name());
    }

}
