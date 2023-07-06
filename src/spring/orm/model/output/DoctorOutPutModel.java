package spring.orm.model.output;

import java.util.Base64;

import spring.orm.model.DoctorSchedule;
import spring.orm.model.entity.DoctorTemp;

public class DoctorOutPutModel {

	private int doctId;
	private String doctName;
	private String doctQual;
	private String specialization;
	private int doctExp;
	private String doctPhoto;

	private Double doctCfee;
	private boolean isDeleted;
	// private DoctorSchedule docsche;

	// ScheduleRelated
	private String weekday;
	private String timeFrom;
	private String timeTo;
	private int averageAppointmentTime;

	// Specialization
	private String id;
	private String title;
	private String description;

	public DoctorOutPutModel(DoctorTemp d2, DoctorSchedule docsche) {
		super();
		this.doctId = d2.getDoctId();
		this.doctName = d2.getDoctName();
		this.doctQual = d2.getDoctQual();
		this.doctExp = d2.getDoctExp();
		this.doctPhoto = Base64.getEncoder().encodeToString(d2.getDoctPhoto());
		this.doctCfee = d2.getDoctCfee();
		this.specialization = d2.getSpecialization().getTitle();
		this.weekday = docsche.getWeekday();
		this.timeFrom = docsche.getTimeFrom();
		this.timeTo = docsche.getTimeTo();
		this.averageAppointmentTime = docsche.getAverageAppointmentTime();

	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public DoctorOutPutModel() {
		// TODO Auto-generated constructor stub
	}

	public int getDoctId() {
		return doctId;
	}

	public void setDoctId(int doctId) {
		this.doctId = doctId;
	}

	public String getDoctName() {
		return doctName;
	}

	public void setDoctName(String doctName) {
		this.doctName = doctName;
	}

	public String getDoctQual() {
		return doctQual;
	}

	public void setDoctQual(String doctQual) {
		this.doctQual = doctQual;
	}

	public int getDoctExp() {
		return doctExp;
	}

	public void setDoctExp(int doctExp) {
		this.doctExp = doctExp;
	}

	public String getDoctPhoto() {
		return doctPhoto;
	}

	public void setDoctPhoto(byte[] doctPhoto) {
		this.doctPhoto = Base64.getEncoder().encodeToString(doctPhoto);
	}

	public Double getDoctCfee() {
		return doctCfee;
	}

	public void setDoctCfee(Double doctCfee) {
		this.doctCfee = doctCfee;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public int getAverageAppointmentTime() {
		return averageAppointmentTime;
	}

	public void setAverageAppointmentTime(int averageAppointmentTime) {
		this.averageAppointmentTime = averageAppointmentTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDocsche(DoctorSchedule schedulebyId) {
		// TODO Auto-generated method stub
		this.timeFrom = schedulebyId.getTimeFrom();
		this.timeTo = schedulebyId.getTimeTo();
		this.weekday = schedulebyId.getWeekday();
		this.averageAppointmentTime = schedulebyId.getAverageAppointmentTime();

	}

	public void setD(DoctorTemp d2) {
		// TODO Auto-generated method stub
		this.doctId = d2.getDoctId();
		this.doctName = d2.getDoctName();
		this.doctQual = d2.getDoctQual();
		this.doctExp = d2.getDoctExp();
		this.specialization = d2.getSpecialization().getTitle();
		this.doctPhoto = Base64.getEncoder().encodeToString(d2.getDoctPhoto());
		this.doctCfee = d2.getDoctCfee();
	}

	@Override
	public String toString() {
		return "DoctorOutPutModel [doctId=" + doctId + ", doctName=" + doctName + ", doctQual=" + doctQual
				+ ", doctExp=" + doctExp + ", doctPhoto=" + ", doctCfee=" + doctCfee + ", isDeleted=" + isDeleted
				+ ", weekday=" + weekday + ", timeFrom=" + timeFrom + ", timeTo=" + timeTo + ", averageAppointmentTime="
				+ averageAppointmentTime + ", id=" + id + ", title=" + title + ", description=" + description + "]";
	}

}
