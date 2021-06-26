package Consult;

import Common.Patient;
import Doctor.Doctor;
import User.User;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Consult implements Serializable{
    private int consultID;
    private Doctor doctor;
    private Patient patient;
    private Specialization diseaseType;
    private Date consultTime;
    private String note;

    public Consult() {
    }

    public Consult(int consultID, Doctor doctor, Patient patient, Specialization diseaseType, Date consultTime, String note) {
        this.consultID = consultID;
        this.doctor = doctor;
        this.patient = patient;
        this.diseaseType = diseaseType;
        this.consultTime = consultTime;
        this.note = note;
    }

    public int getConsultID() {
        return consultID;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Specialization getDiseaseType() {
        return diseaseType;
    }

    public Date getConsultTime() {
        return consultTime;
    }

    public String getNote() {
        return note;
    }

    public void setConsultID(int consultID) {
        this.consultID = consultID;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDiseaseType(Specialization diseaseType) {
        this.diseaseType = diseaseType;
    }

    public void setConsultTime(Date consultTime) {
        this.consultTime = consultTime;
    }

    public void setNote(String note) {
        this.note = note;
    }

    
  
    
}

