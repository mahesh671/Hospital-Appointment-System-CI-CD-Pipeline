package spring.orm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PatientProfileUpdate")
public class PatientProfileUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "patientId")
    private Integer patientId;

    @Column(name = "lastconsultationDate")
    private String lastConsultationDate;

    @Column(name = "prescriptionImage")
    private String prescriptionImage;

    @Column(name = "nextAppointmentDate")
    private String nextAppointmentDate;

    @Column(name = "EMRHMR")
    private String emrHmr;

    public PatientProfileUpdate() {
        // Default constructor
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getLastConsultationDate() {
        return lastConsultationDate;
    }

    public void setLastConsultationDate(String lastConsultationDate) {
        this.lastConsultationDate = lastConsultationDate;
    }

    public String getPrescriptionImage() {
        return prescriptionImage;
    }

    public void setPrescriptionImage(String prescriptionImage) {
        this.prescriptionImage = prescriptionImage;
    }

    public String getNextAppointmentDate() {
        return nextAppointmentDate;
    }

    public void setNextAppointmentDate(String nextAppointmentDate) {
        this.nextAppointmentDate = nextAppointmentDate;
    }

    public String getEmrHmr() {
        return emrHmr;
    }

    public void setEmrHmr(String emrHmr) {
        this.emrHmr = emrHmr;
    }
}
