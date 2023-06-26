package spring.orm.model.output;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;

import spring.orm.model.PatientModel;
import spring.orm.model.entity.DoctorTemp;

public class RescheduleAppOutput {
	private int app_id;
	private PatientModel patient;
	private Date app_sch_date;
	private DoctorTemp doctor;
	private LocalTime slot;
	public int getApp_id() {
		return app_id;
	}
	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}
	public PatientModel getPatient() {
		return patient;
	}
	public void setPatient(PatientModel patient) {
		this.patient = patient;
	}
	public Date getApp_sch_date() {
		return app_sch_date;
	}
	public void setApp_sch_date(Date app_sch_date) {
		this.app_sch_date = app_sch_date;
	}
	public DoctorTemp getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorTemp doctor) {
		this.doctor = doctor;
	}
	public LocalTime getSlot() {
		return slot;
	}
	public void setSlot(LocalTime slot) {
		this.slot = slot;
	}
	public RescheduleAppOutput(int app_id, PatientModel patient, Date app_sch_date, DoctorTemp doctor, LocalTime slot) {
		super();
		this.app_id = app_id;
		this.patient = patient;
		this.app_sch_date = app_sch_date;
		this.doctor = doctor;
		this.slot = slot;
	}
	public RescheduleAppOutput() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "RescheduleAppOutput [app_id=" + app_id + ", patient=" + patient + ", app_sch_date=" + app_sch_date
				+ ", doctor=" + doctor + ", slot=" + slot + "]";
	}
	
	
	

}
